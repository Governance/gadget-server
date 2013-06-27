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

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Dictionary;
import com.google.inject.Inject;

/**
 * @author: Jeff Yu
 * @date: 19/05/11
 */
public class BootstrapContext implements ApplicationProperties{

    private Map<String, String> ctx = new HashMap<String, String>();

    private String gadgetServerUrl;
    
    @Inject
    public BootstrapContext() {
        String svcUrl = GWT.isScript() ? getBaseUrl() + "sam-web-server" : "http://127.0.0.1:8888/app/proxy";
        setProperty(SERVICE_URL, svcUrl);
        Dictionary props = Dictionary.getDictionary("gadgetWebConfig");
        gadgetServerUrl = props.get("gadgetServerUrl");
    }


    private String getBaseUrl() {
        // extract host
        String base = GWT.getHostPageBaseURL();
        String protocol = base.substring(0, base.indexOf("//")+2);
        String remainder = base.substring(base.indexOf(protocol)+protocol.length(), base.length());
        String host = remainder.indexOf(":")!=-1 ?
                remainder.substring(0, remainder.indexOf(":")) :
                remainder.substring(0, remainder.indexOf("/"));

        // default url
        return protocol + host + ":8080/";

    }

    public void setProperty(String key, String value) {
        ctx.put(key, value);
    }

    public String getProperty(String key) {
        return ctx.get(key);
    }

    public boolean hasProperty(String key) {
        return ctx.containsKey(key);
    }

    public void removeProperty(String key) {
        ctx.remove(key);
    }

    public String getGadgetServerUrl() {
        return gadgetServerUrl;
    }
    
}
