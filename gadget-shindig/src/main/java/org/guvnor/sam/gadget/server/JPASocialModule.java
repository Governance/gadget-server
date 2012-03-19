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
package org.guvnor.sam.gadget.server;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import org.apache.shindig.social.opensocial.spi.AppDataService;
import org.apache.shindig.social.opensocial.spi.PersonService;
import org.guvnor.sam.gadget.server.service.UserManager;
import org.guvnor.sam.gadget.server.service.UserManagerImpl;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author: Jeff Yu
 * @date: 18/01/12
 */
public class JPASocialModule extends AbstractModule{
    
    private final static String DEFAULT_PROPERTIES = "gadget-server.properties";
    private Properties properties;
    private EntityManager entityManager;

    
    public JPASocialModule() {
        this(null);
    }

    /**
     *
     */
    public JPASocialModule(EntityManager entityManager) {
        this.entityManager = entityManager;
        InputStream is = null;
        try {
            is = this.getClass().getClassLoader().getResourceAsStream(DEFAULT_PROPERTIES);
            if (is == null) {
                System.err.println("Cant locate properties");
                throw new IOException("Failed to open " + DEFAULT_PROPERTIES);
            }
            properties = new Properties();
            properties.load(is);
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

        bind(PersonService.class).to(DefaultPersonServiceImpl.class).in(Scopes.SINGLETON);
        bind(AppDataService.class).to(DefaultAppDataServiceImpl.class).in(Scopes.SINGLETON);
        bind(UserManager.class).to(UserManagerImpl.class).in(Scopes.SINGLETON);

    }

}
