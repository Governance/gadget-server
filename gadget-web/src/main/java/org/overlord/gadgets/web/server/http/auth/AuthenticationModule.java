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

import java.util.Properties;
import java.util.Set;

import org.apache.shindig.gadgets.http.HttpFetcher;
import org.overlord.gadgets.server.ConfiguredModule;
import org.overlord.gadgets.web.server.http.AuthenticatingHttpFetcher;

import com.google.inject.name.Names;

/**
 * Module that loads the authentication provider.
 *
 * @author eric.wittmann@redhat.com
 */
public class AuthenticationModule extends ConfiguredModule {

    /**
     * Constructor.
     */
    public AuthenticationModule() {
    }

    /**
     * @see com.google.inject.AbstractModule#configure()
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void configure() {
        Properties names = new Properties();
        Set<Object> keySet = properties.keySet();
        for (Object object : keySet) {
            String key = String.valueOf(object);
            if (key.startsWith("gadget-server.config.auth")) {
                names.put(key, properties.getProperty(key, ""));
            }
        }
        Names.bindProperties(this.binder(), names);

        bind(HttpFetcher.class).to(AuthenticatingHttpFetcher.class);

        String authProviderClassname = properties.getProperty(AuthenticationConstants.CONFIG_AUTHENTICATION_PROVIDER);
        if (authProviderClassname != null) {
            try {
                Class<?> c = Class.forName(authProviderClassname);
                if (AuthenticationProvider.class.isAssignableFrom(c))
                    bind(AuthenticationProvider.class).to((Class<? extends AuthenticationProvider>)c);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
