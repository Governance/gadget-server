/*
 * Copyright 2013 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.overlord.gadgets.web.server.servlets;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;

/**
 * A simple BASIC auth provider for the rest proxy servlet.
 * @author eric.wittmann@redhat.com
 */
public class RestProxyBasicAuthProvider implements RestProxyAuthProvider {

    private String proxyName;
    private Properties configProperties;

    /**
     * Constructor.
     */
    public RestProxyBasicAuthProvider() {
    }

    /**
     * @see org.overlord.gadgets.web.server.servlets.RestProxyAuthProvider#setConfiguration(java.lang.String, java.util.Properties)
     */
    @Override
    public void setConfiguration(String proxyName, Properties configProperties) {
        this.proxyName = proxyName;
        this.configProperties = configProperties;
    }

    /**
     * @see org.overlord.gadgets.web.server.servlets.RestProxyAuthProvider#provideAuthentication(java.net.HttpURLConnection)
     */
    @Override
    public void provideAuthentication(HttpURLConnection connection) {
        String basicUser = getBasicUsername();
        String basicPass = getBasicPassword();

        String basicAuthHeader = createBasicAuthHeader(basicUser, basicPass);
        connection.setRequestProperty("Authorization", basicAuthHeader);
    }

    /**
     * @return the configured basic auth username
     */
    private String getBasicUsername() {
        String propKey = "gadget-server.rest-proxy." + this.proxyName + ".authentication.basic.username";
        return this.configProperties.getProperty(propKey);
    }

    /**
     * @return the configured basic auth password
     */
    private String getBasicPassword() {
        String propKey = "gadget-server.rest-proxy." + this.proxyName + ".authentication.basic.password";
        return this.configProperties.getProperty(propKey);
    }

    /**
     * Creates the BASIC auth header value.
     * @param username
     * @param password
     */
    public static String createBasicAuthHeader(String username, String password) {
        try {
            String up = username + ":" + password;
            String base64 = new String(Base64.encodeBase64(up.getBytes("UTF-8")));
            String authHeader = "Basic " + base64;
            return authHeader;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
