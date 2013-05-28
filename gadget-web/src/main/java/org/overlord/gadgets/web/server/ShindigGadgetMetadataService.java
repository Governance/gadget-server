/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008-11, Red Hat Middleware LLC, and others contributors as indicated
 * by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.overlord.gadgets.web.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.shindig.auth.AnonymousSecurityToken;
import org.apache.shindig.gadgets.servlet.GadgetsHandler;
import org.apache.shindig.gadgets.servlet.GadgetsHandlerApi.BaseResponse;
import org.apache.shindig.gadgets.servlet.GadgetsHandlerApi.EnumValuePair;
import org.apache.shindig.gadgets.servlet.GadgetsHandlerApi.Error;
import org.apache.shindig.gadgets.servlet.GadgetsHandlerApi.MetadataResponse;
import org.apache.shindig.gadgets.servlet.GadgetsHandlerApi.ModulePrefs;
import org.apache.shindig.gadgets.servlet.GadgetsHandlerApi.UserPref;
import org.apache.shindig.protocol.BaseRequestItem;
import org.apache.shindig.protocol.conversion.BeanConverter;
import org.apache.shindig.protocol.conversion.BeanJsonConverter;
import org.apache.shindig.protocol.multipart.FormDataItem;
import org.json.JSONException;
import org.json.JSONObject;
import org.overlord.gadgets.server.model.Gadget;
import org.overlord.gadgets.web.shared.dto.UserPreference;
import org.overlord.gadgets.web.shared.dto.WidgetModel;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * @author: Jeff Yu
 * @date: 9/02/12
 */
public class ShindigGadgetMetadataService implements GadgetMetadataService {

    public static final String USER_PREFS = "userPrefs";
    public static final String DATA_TYPE = "dataType";

    @Inject
    private GadgetsHandler gadgetsHandler;
    @Inject
    @Named("shindig.bean.converter.json")
    protected BeanConverter jsonConverter;

    /**
     * Constructor.
     */
    public ShindigGadgetMetadataService() {
    }

    /**
     * @see org.overlord.gadgets.web.server.GadgetMetadataService#getGadgetMetadata(java.lang.String)
     */
    @Override
    public WidgetModel getGadgetMetadata(String gadgetUrl) {
        try {
            MetadataResponse metaDataFromShindig = getMetaDataFromShindig(gadgetUrl);
            WidgetModel model = new WidgetModel();
            model.setIframeUrl("http:" + metaDataFromShindig.getIframeUrl());
            model.setName(metaDataFromShindig.getModulePrefs().getTitle());
            model.setSpecUrl(gadgetUrl);

            // check to see if this gadget has at least one non-hidden user pref
            // to determine if we should display the edit prefs button
            boolean hasPrefsToEdit = false;

            if (metaDataFromShindig.getUserPrefs() != null && metaDataFromShindig.getUserPrefs().size() > 0) {
                UserPreference userPref = new UserPreference();
                Map<String, UserPref> userPrefs = metaDataFromShindig.getUserPrefs();
                Iterator<String> keys = userPrefs.keySet().iterator();
                while (keys.hasNext()) {
                    String settingName = keys.next();
                    UserPreference.UserPreferenceSetting theSetting = new UserPreference.UserPreferenceSetting();
                    UserPref setting = userPrefs.get(settingName);
                    String theType = String.valueOf(setting.getDataType());
                    if (!UserPreference.Type.HIDDEN.toString().equals(theType)) {
                        hasPrefsToEdit = true;
                    }

                    theSetting.setName(setting.getName());
                    theSetting.setDefaultValue(setting.getDefaultValue());
                    theSetting.setDisplayName(setting.getDisplayName());
                    theSetting.setRequired(setting.getRequired());
                    theSetting.setType(UserPreference.Type.valueOf(theType));

                    List<EnumValuePair> enumValues = setting.getOrderedEnumValues();
                    if (enumValues != null && enumValues.size() > 0) {
                        for (EnumValuePair enumValuePair : enumValues) {
                            UserPreference.Option option = new UserPreference.Option();
                            option.setValue(enumValuePair.getValue());
                            option.setDisplayValue(enumValuePair.getDisplayValue());
                            theSetting.addEnumOption(option);
                        }
                    }
                    userPref.addUserPreferenceSetting(theSetting);
                }
                userPref.setNeedToEdit(hasPrefsToEdit);
                model.setUserPreference(userPref);
            }

            return model;
        } catch (JSONException e) {
            throw new IllegalArgumentException(
                    "Error occurred while processing response from shindig metadata call", e);
        }

    }

    /**
     * @see org.overlord.gadgets.web.server.GadgetMetadataService#getGadgetData(java.lang.String)
     */
    @Override
    public Gadget getGadgetData(String gadgetUrl) {
        try {
            MetadataResponse metaDataFromShindig = getMetaDataFromShindig(gadgetUrl);

            ModulePrefs modulePref = metaDataFromShindig.getModulePrefs();

            Gadget gadget = new Gadget();
            gadget.setTitle(modulePref.getTitle());
            if (modulePref.getScreenshot() != null)
                gadget.setScreenshotUrl(String.valueOf(modulePref.getScreenshot()));
            gadget.setAuthorEmail(modulePref.getAuthorEmail());
            gadget.setAuthor(modulePref.getAuthor());
            if (modulePref.getTitleUrl() != null)
                gadget.setTitleUrl(modulePref.getTitleUrl().toString());
            if (modulePref.getThumbnail() != null)
                gadget.setThumbnailUrl(modulePref.getThumbnail().toString());
            gadget.setDescription(modulePref.getDescription());

            return gadget;
        } catch (JSONException e) {
            throw new IllegalArgumentException(
                    "Error occurred while processing response from shindig metadata call", e);
        }
    }

    /**
     * Gets the gadget meta-data as a JSON object (from the shindig meta-data service).
     * @param gadgetUrl
     * @throws JSONException
     */
    private MetadataResponse getMetaDataFromShindig(String gadgetUrl) throws JSONException {
        JSONObject params = new JSONObject()
                .put("container", "default")
                .put("view", "home")
                .put("st", "default")
                .put("debug", true)
                .append("ids", gadgetUrl)
                .append("fields", "iframeUrl")
                .append("fields", "modulePrefs.*")
                .append("fields", "needsTokenRefresh")
                .append("fields", "userPrefs.*")
                .append("fields", "views.preferredHeight")
                .append("fields", "views.preferredWidth")
                .append("fields", "expireTimeMs")
                .append("fields", "responseTimeMs")
                .put("userId", "@viewer")
                .put("groupId", "@self");

        AnonymousSecurityToken securityToken = new AnonymousSecurityToken();
        BaseRequestItem request = new BaseRequestItem(params, new HashMap<String, FormDataItem>(),
                securityToken, this.jsonConverter, (BeanJsonConverter) this.jsonConverter);
        Map<String, BaseResponse> responses = this.gadgetsHandler.metadata(request);
        BaseResponse response = responses.get(gadgetUrl);
        if (response instanceof MetadataResponse) {
            return (MetadataResponse) response;
        } else {
            Error error = response.getError();
            if (error != null) {
                throw new JSONException(error.getMessage());
            } else {
                throw new JSONException("Unknown error:" + response.getUrl());
            }
        }
    }

}
