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

import com.google.inject.Singleton;

/**
 * Provides no authentication.  This is the default if no authentication provider
 * is provided by a guice module.
 * @author eric.wittmann@redhat.com
 */
@Singleton
public class NoAuthenticationProvider implements AuthenticationProvider {

    /**
     * Constructor.
     */
    public NoAuthenticationProvider() {
    }

    /**
     * @see org.overlord.gadgets.web.server.http.auth.AuthenticationProvider#provideAuthentication(org.apache.shindig.gadgets.http.HttpRequest)
     */
    @Override
    public void provideAuthentication(HttpRequest httpRequest) {
        // Does nothing.
    }

}
