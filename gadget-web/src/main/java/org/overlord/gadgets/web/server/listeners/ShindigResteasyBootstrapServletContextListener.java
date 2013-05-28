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
package org.overlord.gadgets.web.server.listeners;

import java.lang.reflect.Type;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.ext.Provider;

import org.apache.shindig.common.servlet.GuiceServletContextListener;
import org.jboss.resteasy.plugins.guice.GuiceResourceFactory;
import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResourceFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.util.GetRestful;

import com.google.inject.Binding;
import com.google.inject.Injector;

/**
 * Bootstraps resteasy based on the guice context set up by the shindig listener.
 *
 * @author eric.wittmann@redhat.com
 */
public class ShindigResteasyBootstrapServletContextListener extends ResteasyBootstrap implements
        ServletContextListener {

    /**
     * @see org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(final ServletContextEvent event) {
        super.contextInitialized(event);
        final ServletContext context = event.getServletContext();
        Injector injector = (Injector) context.getAttribute(GuiceServletContextListener.INJECTOR_ATTRIBUTE);
        if (injector == null) {
            injector = (Injector) context.getAttribute(GuiceServletContextListener.INJECTOR_NAME);
            if (injector == null) {
                throw new RuntimeException("Guice Injector not found! Make sure you registered "
                        + GuiceServletContextListener.class.getName() + " as a listener");
            }
        }

        final Registry registry = (Registry) context.getAttribute(Registry.class.getName());
        final ResteasyProviderFactory providerFactory = (ResteasyProviderFactory) context
                .getAttribute(ResteasyProviderFactory.class.getName());
        processInjector(injector, registry, providerFactory);
    }

    /**
     * Find all resteasy beans and providers.
     *
     * @param injector
     * @param registry
     * @param providerFactory
     */
    private void processInjector(final Injector injector, final Registry registry,
            final ResteasyProviderFactory providerFactory) {
        for (final Binding<?> binding : injector.getBindings().values()) {
            final Type type = binding.getKey().getTypeLiteral().getType();
            if (type instanceof Class) {
                final Class<?> beanClass = (Class<?>) type;
                if (GetRestful.isRootResource(beanClass)) {
                    final ResourceFactory resourceFactory = new GuiceResourceFactory(binding.getProvider(),
                            beanClass);
                    registry.addResourceFactory(resourceFactory);
                }
                if (beanClass.isAnnotationPresent(Provider.class)) {
                    providerFactory.registerProviderInstance(binding.getProvider().get());
                }
            }
        }
    }

}
