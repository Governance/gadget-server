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
package org.overlord.gadgets.web.client.presenter;

import org.overlord.gadgets.web.client.NameTokens;
import org.overlord.gadgets.web.client.URLBuilder;
import org.overlord.gadgets.web.client.auth.CurrentUser;
import org.overlord.gadgets.web.client.auth.LoggedInGateKeeper;
import org.overlord.gadgets.web.client.model.JSOParser;
import org.overlord.gadgets.web.client.util.RestfulInvoker;
import org.overlord.gadgets.web.shared.dto.PageResponse;
import org.overlord.gadgets.web.shared.dto.StoreItemModel;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

/**
 * @author: Jeff Yu
 * @date: 30/04/12
 */
public class StorePresenter extends Presenter<StorePresenter.StoreView, StorePresenter.StoreProxy>{

//    private CurrentUser currentUser;

    @Inject
    public StorePresenter(EventBus bus, StoreView view, StoreProxy proxy, CurrentUser user) {
        super(bus, view, proxy);
//        currentUser = user;
    }


    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(this, this);
        RootLayoutPanel.get().getElement().getStyle().setTop(80, Unit.PX);
        RootLayoutPanel.get().getElement().getStyle().setBottom(5, Unit.PX);
    }

    public interface StoreView extends View {
        void setPresenter(StorePresenter presenter);
        void clearMessageBar();
        void loadStoreItems(PageResponse<StoreItemModel> storeItems);

    }

    @ProxyCodeSplit
    @NameToken(NameTokens.WIDGET_STORE)
    @UseGatekeeper(LoggedInGateKeeper.class)
    public interface StoreProxy extends ProxyPlace<StorePresenter> {}


    public void getStoreItems(int offset, int pageSize) {
        RestfulInvoker.invoke(RequestBuilder.GET, URLBuilder.getStoreURL(offset, pageSize),
                null, new RestfulInvoker.Response() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                     PageResponse<StoreItemModel> storeItems = JSOParser.getStoreItems(response.getText());
                     getView().loadStoreItems(storeItems);
                }
        });
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
        getStoreItems(0, 10);
    }

}
