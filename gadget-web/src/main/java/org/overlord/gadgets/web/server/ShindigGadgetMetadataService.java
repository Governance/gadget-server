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

import org.overlord.gadgets.server.model.Gadget;
import org.overlord.gadgets.web.shared.dto.UserPreference;
import org.overlord.gadgets.web.shared.dto.WidgetModel;
import org.jboss.resteasy.client.ClientRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.util.Iterator;

/**
 * @author: Jeff Yu
 * @date: 9/02/12
 */
public class ShindigGadgetMetadataService implements GadgetMetadataService {

    private static Logger logger = LoggerFactory.getLogger(ShindigGadgetMetadataService.class);
    
    public static final String USER_PREFS = "userPrefs";
    public static final String DATA_TYPE = "dataType";


    public WidgetModel getGadgetMetadata(String gadgetUrl) {

        String responseString = getMetadata(gadgetUrl);

        logger.debug( "gadget url is: " + gadgetUrl +  ", gadget metadata is: " + responseString);

        //now trim back the response to just the metadata for the single gadget
        try {
            JSONObject responseObject = new JSONArray(responseString).
                    getJSONObject(0).
                    getJSONObject("result").
                    getJSONObject(gadgetUrl);

            WidgetModel model = new WidgetModel();
            model.setIframeUrl("http:" + responseObject.getString("iframeUrl"));
            model.setName(responseObject.getJSONObject("modulePrefs").getString("title"));
            model.setSpecUrl(gadgetUrl);

            logger.debug(responseObject.toString());

            // check to see if this gadget has at least one non-hidden user pref
            // to determine if we should display the edit prefs button
            boolean hasPrefsToEdit = false;

            if (responseObject.has(USER_PREFS)) {
                UserPreference userPref = new UserPreference();
                JSONObject userPrefs = responseObject.getJSONObject(USER_PREFS);
                Iterator keys = userPrefs.keys();
                while(keys.hasNext()) {
                    String settingName = (String) keys.next();
                    UserPreference.UserPreferenceSetting theSetting = new UserPreference.UserPreferenceSetting();
                    JSONObject setting = userPrefs.getJSONObject(settingName);
                    String theType = setting.getString(DATA_TYPE);
                    if (!UserPreference.Type.HIDDEN.toString().equals(theType)) {
                        hasPrefsToEdit = true;
                    }

                    theSetting.setName(setting.getString("name"));
                    theSetting.setDefaultValue(setting.getString("defaultValue"));
                    theSetting.setDisplayName(setting.getString("displayName"));
                    theSetting.setRequired(Boolean.valueOf(setting.getString("required")));
                    theSetting.setType(UserPreference.Type.valueOf(theType));

                    if (setting.has("orderedEnumValues")) {
                        JSONArray enumValues = setting.getJSONArray("orderedEnumValues");
                        for (int i =0; i < enumValues.length(); i++) {
                            UserPreference.Option option = new UserPreference.Option();
                            JSONObject theOption = enumValues.getJSONObject(i);
                            option.setValue(theOption.getString("value"));
                            option.setDisplayValue(theOption.getString("displayValue"));
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
            throw new IllegalArgumentException("Error occurred while processing response from shindig metadata call", e);
        }

    }

    private String getMetadata(String gadgetUrl) {
        JSONArray rpcArray = new JSONArray();
        try {
            JSONObject fetchMetadataRpcOperation = new JSONObject()
                    .put("method", "gadgets.metadata")
                    .put("id", "gadgets.metadata")
                    .put("params", new JSONObject()
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
                            .put("groupId", "@self")
                    );

            rpcArray.put(fetchMetadataRpcOperation);
        } catch (JSONException e) {
            throw new IllegalArgumentException("Error occurred while generating data for shindig metadata call", e);
        }

        //convert the json object to a string
        String postData = rpcArray.toString();

        if (logger.isDebugEnabled()) {
            logger.debug("requestContent: {}", postData);
        }

        ClientRequest request = new ClientRequest(ConfigurationUtil.SHINDIG_RPC_URL);
        request.accept("application/json").body(MediaType.APPLICATION_JSON, postData);

        String responseString = null;
        try {
            responseString = request.postTarget(String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseString;
    }

    public Gadget getGadgetData(String gadgetUrl) {
        String responseString = getMetadata(gadgetUrl);
        
        //now trim back the response to just the metadata for the single gadget
        try {
            JSONObject responseObject = new JSONArray(responseString).
                    getJSONObject(0).
                    getJSONObject("result").
                    getJSONObject(gadgetUrl);

            JSONObject modulePref = responseObject.getJSONObject("modulePrefs");
            
            Gadget gadget = new Gadget();
            gadget.setTitle(modulePref.getString("title"));
            gadget.setScreenshotUrl(modulePref.getString("screenshot"));
            gadget.setAuthorEmail(modulePref.getString("authorEmail"));
            gadget.setAuthor(modulePref.getString("author"));
            gadget.setTitleUrl(modulePref.getString("titileUrl"));
            gadget.setThumbnailUrl(modulePref.getString("thumbnail"));
            gadget.setDescription(modulePref.getString("description"));

            return gadget;
        } catch (JSONException e) {
            throw new IllegalArgumentException("Error occurred while processing response from shindig metadata call", e);
        }
        
    }


    public static void main(String[] args) throws Exception {
        ShindigGadgetMetadataService svc = new ShindigGadgetMetadataService();
        //svc.getGadgetMetadata("http://www.gstatic.com/ig/modules/currency_converter/currency_converter_v2.xml");
        //svc.getGadgetMetadata("http://www.gstatic.com/ig/modules/datetime_v3/datetime_v3.xml");
        //svc.getGadgetMetadata("http://rt-gadget.googlecode.com/git/gadget.xml");
        //svc.getGadgetMetadata("http://www.labpixies.com/campaigns/todo/todo.xml");
        //svc.getGadgetMetadata("http://sam-gadget.appspot.com/Gadget/SamGadget.gadget.xml");
        svc.getGadgetMetadata("http://localhost:8080/gadgets/rt-gadget/gadget.xml");
    }
}
