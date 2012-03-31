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
package org.guvnor.sam.gadget.web.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;
import org.guvnor.sam.gadget.web.client.NameTokens;
import org.guvnor.sam.gadget.web.client.URLBuilder;

/**
 *
 */
public class LoginPresenter extends Presenter<LoginPresenter.LoginView,
        LoginPresenter.LoginProxy>{

    private PlaceManager placeManager;
    
    @Inject
    public LoginPresenter(EventBus bus, LoginView view, LoginProxy proxy, PlaceManager manager){
         super(bus, view, proxy);
         this.placeManager = manager;
    }

    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(this, this);
    }

    public interface LoginView extends View {

        void setPresenter(LoginPresenter presenter);
        
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.LOGIN_VIEW)
    @NoGatekeeper
    public interface LoginProxy extends ProxyPlace<LoginPresenter> {}

    @Override
    public void onBind() {
        super.onBind();
        getView().setPresenter(this);
    }

    public void forwardToIndex() {
        placeManager.revealPlace(new PlaceRequest(NameTokens.INDEX_VIEW));
    }

    public void forwardToLogin() {
        placeManager.revealPlace(new PlaceRequest(NameTokens.LOGIN_VIEW));
    }
    
    public void authenticateUser(String username, String password, RequestCallback callback) {

        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URLBuilder.getAuthenticationURL());
        builder.setHeader("content-type", "application/x-www-form-urlencoded");
        StringBuffer postData = new StringBuffer();
        postData.append(URL.encode("username")).append("=").append(URL.encode(username))
                .append("&").append("password").append("=").append(URL.encode(password));
        try {
            builder.sendRequest(postData.toString(), callback);
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }
    
    public void registerUser(String username, String password, String email,
                             String displayName, RequestCallback callback) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(URLBuilder.getRegisterUserURL()));
        builder.setHeader("content-type", "application/json");
        JSONObject postData = new JSONObject();
        postData.put("username", new JSONString(username));
        postData.put("password", new JSONString(password));
        postData.put("email", new JSONString(email));
/*        StringBuffer postData = new StringBuffer();
        postData.append(URL.encode("username")).append("=").append(URL.encode(username))
                .append("&").append("password").append("=").append(URL.encode(password))
                .append("&").append("email").append("=").append(URL.encode(email));*/
        try {

            builder.setRequestData(postData.toString());
            builder.setCallback(callback);
            builder.send();
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }
    
}
