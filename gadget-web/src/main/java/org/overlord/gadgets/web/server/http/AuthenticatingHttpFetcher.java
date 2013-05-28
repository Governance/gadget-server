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
package org.overlord.gadgets.web.server.http;

import java.util.HashSet;
import java.util.Set;

import org.apache.shindig.common.Nullable;
import org.apache.shindig.common.uri.Uri;
import org.apache.shindig.gadgets.GadgetException;
import org.apache.shindig.gadgets.http.BasicHttpFetcher;
import org.apache.shindig.gadgets.http.HttpRequest;
import org.apache.shindig.gadgets.http.HttpResponse;
import org.overlord.gadgets.web.server.http.auth.AuthenticationConstants;
import org.overlord.gadgets.web.server.http.auth.AuthenticationProvider;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * Extends the shindig http fetcher to provide additional authentication support.
 *
 * @author eric.wittmann@redhat.com
 */
@Singleton
public class AuthenticatingHttpFetcher extends BasicHttpFetcher {

    @Inject
    private AuthenticationProvider authenticationProvider;
    private final Set<String> authEndpoints = new HashSet<String>();

    /**
     * Constructor.
     * @param basicHttpFetcherProxy
     */
    @Inject
    public AuthenticatingHttpFetcher(
            @Nullable @Named("org.apache.shindig.gadgets.http.basicHttpFetcherProxy") String basicHttpFetcherProxy,
            @Named(AuthenticationConstants.CONFIG_AUTHENTICATION_ENDPOINTS) String endpoints) {
        super(basicHttpFetcherProxy);
        parseEndpoints(endpoints);
    }

    /**
     * Parses the comma-separated list of endpoints that should participate in authentication.
     * @param endpoints
     */
    private void parseEndpoints(String endpoints) {
        if (endpoints != null) {
            String[] split = endpoints.split(",");
            for (String endpoint : split) {
                authEndpoints.add(endpoint.trim());
            }
        }
    }

    /**
     * Constructor.
     * @param maxObjSize
     * @param connectionTimeoutMs
     * @param readTimeoutMs
     * @param basicHttpFetcherProxy
     */
    public AuthenticatingHttpFetcher(int maxObjSize, int connectionTimeoutMs, int readTimeoutMs,
                            String basicHttpFetcherProxy) {
        super(maxObjSize, connectionTimeoutMs, readTimeoutMs, basicHttpFetcherProxy);
    }

    /**
     * @see org.apache.shindig.gadgets.http.BasicHttpFetcher#fetch(org.apache.shindig.gadgets.http.HttpRequest)
     */
    @Override
    public HttpResponse fetch(HttpRequest request) throws GadgetException {
        // Add authentication information if necessary
        if (request.getOAuthArguments() == null && request.getOAuth2Arguments() == null && isAuthEndpoint(request)) {
            this.authenticationProvider.provideAuthentication(request);
        }
        return super.fetch(request);
    }

    /**
     * @param request the outbound http request
     * @return true if the request should be authenticated
     */
    private boolean isAuthEndpoint(HttpRequest request) {
        Uri uri = request.getUri();
        String path = uri.getPath();
        for (String authEndpoint : authEndpoints) {
            if (path.startsWith(authEndpoint)) {
                return true;
            }
        }
        return false;
    }
}
