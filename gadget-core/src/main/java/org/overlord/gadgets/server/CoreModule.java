/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008-12, Red Hat Middleware LLC, and others contributors as indicated
 * by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.overlord.gadgets.server;

import java.util.Properties;

import javax.persistence.EntityManager;

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

        if (entityManager == null) {
            bind(EntityManager.class).toProvider(EntityManagerProvider.class).in(Scopes.SINGLETON);
        } else {
            bind(EntityManager.class).toInstance(this.entityManager);
        }

        bind(UserManager.class).to(UserManagerImpl.class).in(Scopes.SINGLETON);
        bind(ApplicationDataManager.class).to(ApplicationDataManagerImpl.class).in(Scopes.SINGLETON);
        bind(GadgetService.class).to(GadgetServiceImpl.class).in(Scopes.SINGLETON);
    }

}
