/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008-11, Red Hat Middleware LLC, and others contributors as indicated
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
import org.overlord.gadgets.web.client.presenter.IndexPresenter;
import org.overlord.gadgets.web.client.presenter.StorePresenter;
import org.overlord.gadgets.web.client.view.Footer;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * @author: Jeff Yu
 * @date: 10/05/11
 */
@GinModules(ApplicationModule.class)
public interface ApplicationUI extends Ginjector {

    BootstrapContext getBootstrapContext();

    PlaceManager getPlaceManager();

    EventBus getEventBus();

    AsyncProvider<IndexPresenter> getIndexPresenter();
    AsyncProvider<StorePresenter> getStorePresenter();

    Footer getFooter();

    CurrentUser getCurrentUser();
}
