/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.overlord.gadgets.server;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Bootstrap {

    private static final String DB_DRIVER = "db.driver";
    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String ENVIRONMENT = "environment";

    private EntityManager entityManager;

    private String dbDriver;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    @Inject
    public Bootstrap(@Named(DB_DRIVER) String dbDriver,
                     @Named(DB_URL) String dbUrl, @Named(DB_USER) String dbUser,
                     @Named(DB_PASSWORD) String dbPassword) {

        this.dbDriver = dbDriver;
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;

    }

    public Bootstrap() {

    }

    public void init(String unitName) {

        Map<String, String> properties = new HashMap<String, String>();
        if (isNotEmpty(dbDriver)) {
            properties.put("hibernate.connection.driver_class", dbDriver);
        }
        if (isNotEmpty(dbUrl)) {
            properties.put("hibernate.connection.url", dbUrl);
        }
        if (isNotEmpty(dbUser)) {
            properties.put("hibernate.connection.username", dbUser);
        }
        if (isNotEmpty(dbPassword)) {
            properties.put("hibernate.connection.password", dbPassword);
        }
        properties.put("hibernate.hbm2ddl.auto", "create-drop");

        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(unitName, properties);
        entityManager = emFactory.createEntityManager();
    }

    private boolean isNotEmpty(String value) {
        if (value == null || "".equalsIgnoreCase(value.trim())) {
            return false;
        }
        return true;
    }

    /**
     * @param unitName
     * @return
     */
    public EntityManager getEntityManager(String unitName) {
        if (entityManager == null) {
            init(unitName);
        }
        return entityManager;
    }
}
