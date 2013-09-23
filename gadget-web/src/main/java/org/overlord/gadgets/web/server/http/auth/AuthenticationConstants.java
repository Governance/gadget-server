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

/**
 * Authentication related constants.
 * @author eric.wittmann@redhat.com
 */
public class AuthenticationConstants {

    public static final String CONFIG_AUTHENTICATION_PROVIDER  = "gadget-server.config.auth.provider";
    public static final String CONFIG_AUTHENTICATION_ENDPOINTS = "gadget-server.config.auth.endpoints";
    public static final String CONFIG_BASIC_AUTH_USER          = "gadget-server.config.auth.basic.username";
    public static final String CONFIG_BASIC_AUTH_PASS          = "gadget-server.config.auth.basic.password";
    public static final String CONFIG_SAML_AUTH_ISSUER         = "gadget-server.config.auth.saml.issuer";
    public static final String CONFIG_SAML_AUTH_SERVICE        = "gadget-server.config.auth.saml.service";
    public static final String CONFIG_SAML_AUTH_SIGN_ASSERTIONS   = "gadget-server.config.auth.saml.sign-assertions"; //$NON-NLS-1$
    public static final String CONFIG_SAML_AUTH_KEYSTORE          = "gadget-server.config.auth.saml.keystore"; //$NON-NLS-1$
    public static final String CONFIG_SAML_AUTH_KEYSTORE_PASSWORD = "gadget-server.config.auth.saml.keystore-password"; //$NON-NLS-1$
    public static final String CONFIG_SAML_AUTH_KEY_ALIAS         = "gadget-server.config.auth.saml.key-alias"; //$NON-NLS-1$
    public static final String CONFIG_SAML_AUTH_KEY_PASSWORD      = "gadget-server.config.auth.saml.key-password"; //$NON-NLS-1$

}
