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

import java.net.HttpURLConnection;
import java.util.Properties;

/**
 * Implement this interface to provide authentication to proxied REST invokations.  Implementations
 * will be used by the {@link RestProxyServlet} when making outgoing REST calls on behalf of the
 * inbound request.
 *
 * @author eric.wittmann@redhat.com
 */
public interface RestProxyAuthProvider {

    /**
     * Sets the configuration properties.
     * @param proxyName
     * @param configProperties
     */
    public void setConfiguration(String proxyName, Properties configProperties);

    /**
     * Provides any authentication headers on the request.
     */
    public void provideAuthentication(HttpURLConnection connection);

}
