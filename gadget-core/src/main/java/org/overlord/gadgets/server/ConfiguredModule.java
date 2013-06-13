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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
        File configFile = null;

        String configFileLoc = System.getProperty("gadget-server.config.file.name");
        if (configFileLoc != null) {
            File file = new File(configFileLoc);
            if (file.isFile())
                configFile = file;
        } else {
            String userHomeDir = System.getProperty("user.home");
            if (userHomeDir != null) {
                File dirFile = new File(userHomeDir);
                if (dirFile.isDirectory()) {
                    File cfile = new File(dirFile, DEFAULT_PROPERTIES);
                    if (cfile.isFile())
                        configFile = cfile;
                }
            }
            String jbossConfigDir = System.getProperty("jboss.server.config.dir");
            if (configFile == null && jbossConfigDir != null) {
                File dirFile = new File(jbossConfigDir);
                if (dirFile.isDirectory()) {
                    File cfile = new File(dirFile, DEFAULT_PROPERTIES);
                    if (cfile.isFile())
                        configFile = cfile;
                }
            }
        }

        if (configFile != null) {
            try {
                FileReader reader = null;
                try {
                    reader = new FileReader(configFile);
                    Properties props = new Properties();
                    props.load(reader);
                    properties.putAll(props);
                } finally {
                    reader.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Loads properties from the system properties.
     */
    private static void loadFromSystemProperties() {
        properties.putAll(System.getProperties());
    }

}
