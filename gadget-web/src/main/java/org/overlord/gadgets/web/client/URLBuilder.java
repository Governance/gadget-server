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
package org.overlord.gadgets.web.client;

import com.google.gwt.core.client.GWT;

/**
 * @author: Jeff Yu
 * @date: 20/03/12
 */
public class URLBuilder {

    private final static String urlBase = GWT.getHostPageBaseURL();

    public static String getPagesURL(long userId) {
        return urlBase + "rs/users/user/" + userId + "/pages";
    }

    public static String getAddPageURL(long userId) {
        return urlBase + "rs/users/user/" + userId + "/page";
    }

    public static String updateCurrentPageId(long userId, long pageId) {
    	return urlBase + "rs/users/user/" + userId + "/current/" + pageId;
    }

    public static String getStoreURL(int offset, int pageSize) {
        return urlBase + "rs/stores/all/" + offset + "/" + pageSize;
    }

    public static String getAddGadgetToPageURL(long pageId, long gadgetId) {
        return urlBase + "rs/stores/page/" + pageId + "/gadget/" + gadgetId;
    }

    public static String getRemoveWidgetURL(long widgetId) {
        return urlBase + "rs/users/widget/" + widgetId + "/remove";
    }

    public static String getRemovePageURL(long pageId) {
        return urlBase + "rs/users/user/page/" + pageId + "/remove";
    }

    public static String updatePreferenceURL(long widgetId) {
    	return urlBase + "rs/users/widget/"+widgetId+"/update";
    }

    public static String getPreferenceValuesURL(long widgetId) {
    	return urlBase + "rs/users/widget/" + widgetId;
    }

}
