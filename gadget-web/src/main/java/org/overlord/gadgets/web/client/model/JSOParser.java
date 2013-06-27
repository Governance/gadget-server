/*
 * 2012-3 Red Hat Inc. and/or its affiliates and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.overlord.gadgets.web.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.overlord.gadgets.web.shared.dto.PageModel;
import org.overlord.gadgets.web.shared.dto.PageResponse;
import org.overlord.gadgets.web.shared.dto.StoreItemModel;
import org.overlord.gadgets.web.shared.dto.UserModel;
import org.overlord.gadgets.web.shared.dto.UserPreference;
import org.overlord.gadgets.web.shared.dto.WidgetModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;

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
            pageModel.setId(pageModelValue.getLong("id"));

            JsArray<JSOModel> widgetModels = pageModelValue.getArray("models");
            for (int j = 0; j < widgetModels.length(); j++) {
                WidgetModel widgetModel = new WidgetModel();
                JSOModel gadgetValue = widgetModels.get(j);
                widgetModel.setIframeUrl(gadgetValue.get("iframeUrl"));
                widgetModel.setName(gadgetValue.get("name"));
                widgetModel.setSpecUrl(gadgetValue.get("specUrl"));
                widgetModel.setOrder(gadgetValue.getLong("order"));
                widgetModel.setWidgetId(gadgetValue.getLong("widgetId"));

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

                widgetModel.setUserPreference(userPreference);
                pageModel.addModel(widgetModel);
            }
          pageModels.add(pageModel);
        }

        return  pageModels;
    }
    
    public static PageResponse<StoreItemModel> getStoreItems(String jsonValue) {
        JSOModel model = JSOModel.fromJson(jsonValue);
        JsArray<JSOModel> resultSets = model.getArray("resultSet");

        List<StoreItemModel> items = new ArrayList<StoreItemModel>();
        String baseUrl = getBaseUrl();
        for (int i = 0; i < resultSets.length(); i++) {
            StoreItemModel itemModel = new StoreItemModel();
            JSOModel theItem = resultSets.get(i);
            itemModel.setId(theItem.getLong("id"));
            itemModel.setName(theItem.get("title"));
            itemModel.setDescription(theItem.get("description"));
            
            itemModel.setThumbnailUrl(theItem.get("thumbnailUrl").replace("${server}", baseUrl));
            itemModel.setAuthor(theItem.get("author"));

            items.add(itemModel);
        }
        int numOfRec = model.getInt("totalResults");
        int offset = model.getInt("offset");
        int pageSize = model.getInt("pageSize");

        PageResponse<StoreItemModel> response = new PageResponse<StoreItemModel>(items, numOfRec);
        response.setOffset(offset);
        response.setPageSize(pageSize);

        return response;
    }
    
    private static String getBaseUrl() {
    	String pageBaseUrl = GWT.getHostPageBaseURL();
    	int end = pageBaseUrl.indexOf("gadget-web");
    	//remote the end slash.
    	return pageBaseUrl.substring(0, end-1);
    }
    
    public static UserModel getUserModel(String jsonValue) {
        JSOModel model = JSOModel.fromJson(jsonValue);
        UserModel user = new UserModel();
        user.setUserId(model.getLong("userId"));
        user.setUserName(model.get("userName"));
        user.setCurrentPageId(model.getLong("currentPageId"));
        user.setDisplayName(model.get("displayName"));
        return user;
    }
    
    public static Map<String, String> getPreferenceValues(String jsonValue) {
    	Map<String, String> result = new HashMap<String, String>();
    	JsArray<JSOModel> model = JSOModel.arrayFromJson(jsonValue);
    	for (int i = 0; i < model.length(); i++) {
    		JSOModel pairModel = model.get(i);
    		result.put(pairModel.get("name"), pairModel.get("value"));
    	}
    	
    	return result;
    }

}
