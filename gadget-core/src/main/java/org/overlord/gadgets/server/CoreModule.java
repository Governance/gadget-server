/*
 * 2012-3 Red Hat Inc. and/or its affiliates and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.overlord.gadgets.server;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.overlord.gadgets.server.service.ApplicationDataManager;
import org.overlord.gadgets.server.service.ApplicationDataManagerImpl;
import org.overlord.gadgets.server.service.GadgetService;
import org.overlord.gadgets.server.service.GadgetServiceImpl;
import org.overlord.gadgets.server.service.UserManager;
import org.overlord.gadgets.server.service.UserManagerImpl;

import com.google.inject.Scopes;
import com.google.inject.name.Names;

/**
 * @author: Jeff Yu
 * @date: 18/01/12
 */
public class CoreModule extends ConfiguredModule {

    private EntityManager entityManager;

    /**
     * Constructor.
     */
    public CoreModule() {
        this(null);
    }

    /**
     * Constructor.
     * @param entityManager
     */
    public CoreModule(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc}
     *
     * @see com.google.inject.AbstractModule#configure()
     */
    @Override
    protected void configure() {
        Properties names = new Properties();
        names.put(Bootstrap.DB_DRIVER, properties.getProperty(Bootstrap.DB_DRIVER, ""));
        names.put(Bootstrap.DB_URL, properties.getProperty(Bootstrap.DB_URL, ""));
        names.put(Bootstrap.DB_USER, properties.getProperty(Bootstrap.DB_USER, ""));
        names.put(Bootstrap.DB_PASSWORD, properties.getProperty(Bootstrap.DB_PASSWORD, ""));
        names.put(Bootstrap.JPA_UNITNAME, properties.getProperty(Bootstrap.JPA_UNITNAME, ""));
        names.put(Bootstrap.HIBERNATE_HBM2DDL_AUTO, properties.getProperty(Bootstrap.HIBERNATE_HBM2DDL_AUTO, ""));
        Names.bindProperties(this.binder(), names);

        //if (entityManager == null) {
            bind(EntityManagerFactory.class).toProvider(EntityManagerFactoryProvider.class).in(Scopes.SINGLETON);
        //} else {
        //    bind(EntityManager.class).toInstance(this.entityManager);
        //}
            
        bind(UserManager.class).to(UserManagerImpl.class).in(Scopes.SINGLETON);
        bind(ApplicationDataManager.class).to(ApplicationDataManagerImpl.class).in(Scopes.SINGLETON);
        bind(GadgetService.class).to(GadgetServiceImpl.class).in(Scopes.SINGLETON);
    }

}
