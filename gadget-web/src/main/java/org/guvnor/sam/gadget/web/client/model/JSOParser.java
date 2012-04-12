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
package org.guvnor.sam.gadget.web.client.model;

import com.google.gwt.core.client.JsArray;
import org.guvnor.sam.gadget.web.shared.dto.GadgetModel;
import org.guvnor.sam.gadget.web.shared.dto.PageModel;
import org.guvnor.sam.gadget.web.shared.dto.UserPreference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 11/04/12
 */
public class JSOParser {

    public static List<PageModel> getPageModels(String jsonValue) {
        List<PageModel> pageModels = new ArrayList<PageModel>();
        JsArray<JSOModel> tabModels = JSOModel.arrayFromJson(jsonValue);
        for (int i = 0; i < tabModels.length(); i++) {
            PageModel pageModel = new PageModel();
            JSOModel pageModelValue = tabModels.get(i);
            pageModel.setName(pageModelValue.get("name"));
            pageModel.setOrder(pageModelValue.getLong("order"));
            pageModel.setColumns(pageModelValue.getLong("columns"));

            JsArray<JSOModel> widgetModels = pageModelValue.getArray("models");
            for (int j = 0; j < widgetModels.length(); j++) {
                GadgetModel gadgetModel = new GadgetModel();
                JSOModel gadgetValue = widgetModels.get(j);
                gadgetModel.setIframeUrl(gadgetValue.get("iframeUrl"));
                gadgetModel.setName(gadgetValue.get("name"));
                gadgetModel.setSpecUrl(gadgetValue.get("specUrl"));
                gadgetModel.setOrder(gadgetValue.getLong("order"));

                UserPreference userPreference = new UserPreference();
                JSOModel prefValue = gadgetValue.getObject("userPreference");
                userPreference.setNeedToEdit(prefValue.getBoolean("needToEdit"));

                JsArray<JSOModel> settings = prefValue.getArray("data");
                for (int k = 0; k < settings.length(); k ++) {
                    UserPreference.UserPreferenceSetting prefSetting = new UserPreference.UserPreferenceSetting();
                    JSOModel settingValue = settings.get(k);
                    prefSetting.setDisplayName(settingValue.get("displayName"));
                    prefSetting.setDefaultValue(settingValue.get("defaultValue"));
                    prefSetting.setRequired(settingValue.getBoolean("required"));
                    prefSetting.setName(settingValue.get("name"));
                    prefSetting.setType(UserPreference.Type.valueOf(settingValue.get("type")));

                    JsArray<JSOModel> options = settingValue.getArray("enumOptions");
                    for (int m = 0; m < options.length(); m++ ) {
                        UserPreference.Option option = new UserPreference.Option();
                        JSOModel optionValue = options.get(m);
                        option.setDisplayValue(optionValue.get("displayValue"));
                        option.setValue(optionValue.get("value"));

                        prefSetting.addEnumOption(option);
                    }

                    userPreference.addUserPreferenceSetting(prefSetting);
                }

                gadgetModel.setUserPreference(userPreference);
                pageModel.addModel(gadgetModel);
            }
          pageModels.add(pageModel);
        }

        return  pageModels;
    }

}
