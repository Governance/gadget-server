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
package org.overlord.gadgets.web.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

import org.overlord.gadgets.web.client.NameTokens;
import org.overlord.gadgets.web.client.URLBuilder;
import org.overlord.gadgets.web.client.auth.CurrentUser;
import org.overlord.gadgets.web.client.auth.LoggedInGateKeeper;
import org.overlord.gadgets.web.client.model.JSOParser;
import org.overlord.gadgets.web.client.util.RestfulInvoker;
import org.overlord.gadgets.web.shared.dto.PageModel;

import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 20/02/12
 */
public class IndexPresenter extends Presenter<IndexPresenter.IndexView,
        IndexPresenter.IndexProxy>{

    private CurrentUser currentUser;

    @Inject
    public IndexPresenter(EventBus bus, IndexView view, IndexProxy proxy, CurrentUser user){
        super(bus, view, proxy);
        currentUser = user;
    }

    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(this, this);
    }

    public interface IndexView extends View {
        public void setPresenter(IndexPresenter presenter);
        public void initializePages(List<PageModel> models);
    }
    
    public void getPages() {
        RestfulInvoker.invoke(RequestBuilder.GET, URLBuilder.getPagesURL(currentUser.getUserId()), null,
                new RestfulInvoker.Response(){

                    public void onResponseReceived(Request request, Response response) {
                        List<PageModel> pageModels = JSOParser.getPageModels(response.getText());
                        getView().initializePages(pageModels);
                    }
                });
    }


    @ProxyCodeSplit
    @NameToken(NameTokens.INDEX_VIEW)
    @UseGatekeeper(LoggedInGateKeeper.class)
    public interface IndexProxy extends ProxyPlace<IndexPresenter> {}


    @Override
    public void onBind() {
        super.onBind();
        getView().setPresenter(this);
    }

    @Override
    public void onReveal() {
        super.onReveal();
        getPages();
    }

}
