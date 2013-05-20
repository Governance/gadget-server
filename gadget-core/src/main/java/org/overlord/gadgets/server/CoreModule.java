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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.overlord.gadgets.server.service.ApplicationDataManager;
import org.overlord.gadgets.server.service.ApplicationDataManagerImpl;
import org.overlord.gadgets.server.service.GadgetService;
import org.overlord.gadgets.server.service.GadgetServiceImpl;
import org.overlord.gadgets.server.service.UserManager;
import org.overlord.gadgets.server.service.UserManagerImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

/**
 * @author: Jeff Yu
 * @date: 18/01/12
 */
public class CoreModule extends AbstractModule{

    private final static String DEFAULT_PROPERTIES = "gadget-server.properties";
    private Properties properties;
    private EntityManager entityManager;


    public CoreModule() {
        this(null);
    }

    /**
     *
     */
    public CoreModule(EntityManager entityManager) {
        this.entityManager = entityManager;
        InputStream is = null;
        try {
            is = this.getClass().getClassLoader().getResourceAsStream(DEFAULT_PROPERTIES);
            if (is == null) {
                System.err.println("Can't locate properties");
                throw new IOException("Failed to open " + DEFAULT_PROPERTIES);
            }
            properties = new Properties();
            properties.load(is);

            // Now override with system properties
            if (System.getProperty(Bootstrap.DB_DRIVER) != null)
                properties.put(Bootstrap.DB_DRIVER, System.getProperty(Bootstrap.DB_DRIVER));
            if (System.getProperty(Bootstrap.DB_URL) != null)
                properties.put(Bootstrap.DB_URL, System.getProperty(Bootstrap.DB_URL));
            if (System.getProperty(Bootstrap.DB_USER) != null)
                properties.put(Bootstrap.DB_USER, System.getProperty(Bootstrap.DB_USER));
            if (System.getProperty(Bootstrap.DB_PASSWORD) != null)
                properties.put(Bootstrap.DB_PASSWORD, System.getProperty(Bootstrap.DB_PASSWORD));
            if (System.getProperty(Bootstrap.HIBERNATE_HBM2DDL_AUTO) != null)
                properties.put(Bootstrap.HIBERNATE_HBM2DDL_AUTO, System.getProperty(Bootstrap.HIBERNATE_HBM2DDL_AUTO));
        } catch (IOException e) {
            throw new RuntimeException( "Unable to load properties: " + DEFAULT_PROPERTIES);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch(Exception e) {
                    System.err.println("closed inputstream error.");
                }

            }

        }
    }

    /**
     * {@inheritDoc}
     *
     * @see com.google.inject.AbstractModule#configure()
     */
    @Override
    protected void configure() {
        Names.bindProperties(this.binder(), properties);

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
