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
package org.overlord.gadgets.web.shared.dto;

/**
 * @author: Jeff Yu
 * @date: 9/02/12
 */
public class WidgetModel {

    private long widgetId;

    private String specUrl;

    private String name;

    private String iframeUrl;

    private UserPreference userPreference;
    
    private Long order;

    public String getSpecUrl() {
        return specUrl;
    }

    public void setSpecUrl(String specUrl) {
        this.specUrl = specUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIframeUrl() {
        return iframeUrl;
    }

    public void setIframeUrl(String iframeUrl) {
        this.iframeUrl = iframeUrl;
    }

    public UserPreference getUserPreference() {
        return userPreference;
    }

    public void setUserPreference(UserPreference userPreference) {
        this.userPreference = userPreference;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public long getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(long widgetId) {
        this.widgetId = widgetId;
    }

    public String toString() {
        StringBuilder sbuffer = new StringBuilder();
        sbuffer.append("[");
        sbuffer.append(" name => " + name);
        sbuffer.append(" iframUrl =>" + iframeUrl);
        sbuffer.append(" userPreference => [");
        sbuffer.append( userPreference.getData().size());
        sbuffer.append("]");
        
        sbuffer.append("]");
        return sbuffer.toString();
    }
}
