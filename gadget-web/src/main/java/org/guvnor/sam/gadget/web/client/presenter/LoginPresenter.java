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

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;
import org.guvnor.sam.gadget.web.client.NameTokens;

/**
 *
 */
public class LoginPresenter extends Presenter<LoginPresenter.LoginView,
        LoginPresenter.LoginProxy>{

    @Inject
    public LoginPresenter(EventBus bus, LoginView view, LoginProxy proxy){
         super(bus, view, proxy);
    }

    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(this, this);
    }

    public interface LoginView extends View {

    }

    @ProxyCodeSplit
    @NameToken(NameTokens.LOGIN_VIEW)
    @NoGatekeeper
    public interface LoginProxy extends ProxyPlace<LoginPresenter> {}

}
