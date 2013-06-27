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
package org.overlord.gadgets.web.client;

import org.overlord.gadgets.web.client.auth.CurrentUser;
import org.overlord.gadgets.web.client.presenter.IndexPresenter;
import org.overlord.gadgets.web.client.presenter.StorePresenter;
import org.overlord.gadgets.web.client.view.Footer;
import org.overlord.gadgets.web.client.view.IndexViewImpl;
import org.overlord.gadgets.web.client.view.StoreViewImpl;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
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

        bind(CurrentUser.class).in(Singleton.class);
        bind(BootstrapContext.class).in(Singleton.class);
        bind(ApplicationProperties.class).to(BootstrapContext.class).in(Singleton.class);

        bind(Footer.class).in(Singleton.class);

        //Presenters
        bindPresenter(IndexPresenter.class, IndexPresenter.IndexView.class, IndexViewImpl.class,
                IndexPresenter.IndexProxy.class);
        bindPresenter(StorePresenter.class, StorePresenter.StoreView.class, StoreViewImpl.class,
                StorePresenter.StoreProxy.class);
    }
}
