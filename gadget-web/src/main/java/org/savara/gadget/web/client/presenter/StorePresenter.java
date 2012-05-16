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
package org.savara.gadget.web.client.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.RequestBuilder;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;
import org.savara.gadget.web.client.NameTokens;
import org.savara.gadget.web.client.URLBuilder;
import org.savara.gadget.web.client.auth.LoggedInGateKeeper;
import org.savara.gadget.web.client.util.RestfulInvoker;

/**
 * @author: Jeff Yu
 * @date: 30/04/12
 */
public class StorePresenter extends Presenter<StorePresenter.StoreView, StorePresenter.StoreProxy>{

    @Inject
    public StorePresenter(EventBus bus, StoreView view, StoreProxy proxy) {
        super(bus, view, proxy);
    }
    

    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(this, this);
    }

    public interface StoreView extends View {
        void setPresenter(StorePresenter presenter);
        void clearMessageBar();
        
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.WIDGET_STORE)
    @UseGatekeeper(LoggedInGateKeeper.class)
    public interface StoreProxy extends ProxyPlace<StorePresenter> {}


    public void getStoreItems(int offset, int pageSize, RestfulInvoker.Response callback) {
        RestfulInvoker.invoke(RequestBuilder.GET, URLBuilder.getStoreURL(offset, pageSize),
                null, callback);
    }

    @Override
    public void onBind() {
        super.onBind();
        getView().setPresenter(this);
    }
    
    @Override
    public void onReveal() {
        super.onReveal();
        getView().clearMessageBar();
    }
    
}
