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
package org.overlord.gadgets.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.overlord.commons.config.ConfigurationFactory;

import com.google.inject.AbstractModule;

/**
 * Base class for modules that utilize information from the gadget server's configuration file.
 *
 * @author eric.wittmann@redhat.com
 */
public abstract class ConfiguredModule extends AbstractModule {

    private final static String DEFAULT_PROPERTIES = "gadget-server.properties";
    public static Properties properties;
    static {
        loadConfigurationProperties();
    }

    /**
     * Constructor.
     */
    public ConfiguredModule() {
    }

    /**
     * Loads the configuration properties.
     */
    protected static void loadConfigurationProperties() {
        if (properties == null) {
            properties = new Properties();
            loadFromClasspath();
            loadFromFile();
            loadFromSystemProperties();
        }
    }

    /**
     * Loads properties from a properties file found on the classpath.
     */
    private static void loadFromClasspath() {
        InputStream is = null;
        try {
            try {
                is = ConfiguredModule.class.getClassLoader().getResourceAsStream(DEFAULT_PROPERTIES);
                Properties props = new Properties();
                props.load(is);
                properties.putAll(props);
            } finally {
                is.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads configuration properties from a file on the file system. We look for the file in a variety of
     * places, although users can override the file's location by setting a system property.
     */
    private static void loadFromFile() {
        String configFile = System.getProperty("gadget-server.config.file.name");
        Long refreshDelay = 60000l;
        Configuration config = ConfigurationFactory.createConfig(
                configFile,
                "gadget-server.properties",
                refreshDelay,
                "/META-INF/config/org.overlord.gadgets.properties",
                ConfiguredModule.class);

        Iterator<?> keys = config.getKeys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String value = config.getString(key);
            properties.put(key, value);
        }
    }

    /**
     * Loads properties from the system properties.
     */
    private static void loadFromSystemProperties() {
        properties.putAll(System.getProperties());
    }

}
