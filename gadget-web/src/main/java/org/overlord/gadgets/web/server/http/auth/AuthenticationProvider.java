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
package org.overlord.gadgets.web.server.http.auth;

import org.apache.shindig.gadgets.http.HttpRequest;

import com.google.inject.ImplementedBy;

/**
 * Provides authentication support to outgoing shindig REST calls.
 * @author eric.wittmann@redhat.com
 */
@ImplementedBy(NoAuthenticationProvider.class)
public interface AuthenticationProvider {

    /**
     * Provides authentication for the given http request.
     * @param httpRequest
     */
    public void provideAuthentication(HttpRequest httpRequest);
}
