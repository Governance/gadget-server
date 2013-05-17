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
package org.overlord.gadgets.web.client;

import org.overlord.gadgets.web.client.auth.CurrentUser;
import org.overlord.gadgets.web.client.auth.LoggedInGateKeeper;
import org.overlord.gadgets.web.client.presenter.IndexPresenter;
import org.overlord.gadgets.web.client.presenter.LoginPresenter;
import org.overlord.gadgets.web.client.presenter.StorePresenter;
import org.overlord.gadgets.web.client.view.Footer;
import org.overlord.gadgets.web.client.view.IndexViewImpl;
import org.overlord.gadgets.web.client.view.LoginViewImpl;
import org.overlord.gadgets.web.client.view.StoreViewImpl;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;
import com.gwtplatform.mvp.client.proxy.ParameterTokenFormatter;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

/**
 * @author: Jeff Yu
 * @date: 9/05/11
 */
public class ApplicationModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        bind(PlaceManager.class).to(ApplicationPlaceManager.class).in(Singleton.class);
        bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
        bind(RootPresenter.class).asEagerSingleton();

        bind(Gatekeeper.class).to(LoggedInGateKeeper.class);
        bind(CurrentUser.class).in(Singleton.class);
        bind(BootstrapContext.class).in(Singleton.class);
        bind(ApplicationProperties.class).to(BootstrapContext.class).in(Singleton.class);
        
        bind(Footer.class).in(Singleton.class);

        //Presenters
        bindPresenter(IndexPresenter.class, IndexPresenter.IndexView.class, IndexViewImpl.class,
                IndexPresenter.IndexProxy.class);
        bindPresenter(LoginPresenter.class, LoginPresenter.LoginView.class, LoginViewImpl.class,
                LoginPresenter.LoginProxy.class);
        bindPresenter(StorePresenter.class, StorePresenter.StoreView.class, StoreViewImpl.class,
                StorePresenter.StoreProxy.class);
    }
}
