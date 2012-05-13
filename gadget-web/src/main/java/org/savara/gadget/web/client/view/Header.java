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
package org.savara.gadget.web.client.view;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import org.savara.gadget.web.client.ApplicationEntryPoint;
import org.savara.gadget.web.client.NameTokens;
import org.savara.gadget.web.client.URLBuilder;
import org.savara.gadget.web.client.auth.CurrentUser;
import org.savara.gadget.web.client.util.RestfulInvoker;

/**
 * @author: Jeff Yu
 * @date: 28/02/12
 */
public class Header {

    private CurrentUser currentUser;
    
    @Inject
    public Header(CurrentUser user) {
        currentUser = user;
    }

    public Widget asWidget() {
        LayoutPanel headerLayout = new LayoutPanel();
        headerLayout.addStyleName("header-panel");

        HTML home = new HTML("Home");
        home.addStyleName("header-link");
        home.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                ApplicationEntryPoint.MODULES.getPlaceManager().revealPlace(
                        new PlaceRequest(NameTokens.INDEX_VIEW)
                );
            }
        });

        HTML store = new HTML("Widget Store");
        store.addStyleName("header-link");
        store.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                ApplicationEntryPoint.MODULES.getPlaceManager().revealPlace(
                        new PlaceRequest(NameTokens.WIDGET_STORE)
                );
            }
        });

        HTML logout = new HTML("Logout");
        logout.addStyleName("header-link");
        logout.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RestfulInvoker.invoke(RequestBuilder.POST, URLBuilder.getInvalidSessionURL(), null,
                        new RestfulInvoker.Response(){
                            public void onResponseReceived(Request request, Response response) {
                                currentUser.setLoggedIn(false);
                                ApplicationEntryPoint.MODULES.getPlaceManager().revealPlace(
                                        new PlaceRequest(NameTokens.LOGIN_VIEW));
                            }
                        });
            }
        });

        headerLayout.add(home);
        headerLayout.add(store);
        headerLayout.add(logout);

        headerLayout.setWidgetRightWidth(home, 100, Style.Unit.PX, 60, Style.Unit.PX);
        headerLayout.setWidgetTopHeight(home, 45, Style.Unit.PX, 28, Style.Unit.PX);

        headerLayout.setWidgetRightWidth(store, 5, Style.Unit.PX, 110, Style.Unit.PX);
        headerLayout.setWidgetTopHeight(store, 45, Style.Unit.PX, 28, Style.Unit.PX);

        headerLayout.setWidgetRightWidth(logout, 5, Style.Unit.PX, 60, Style.Unit.PX);
        headerLayout.setWidgetTopHeight(logout, 2, Style.Unit.PX, 28, Style.Unit.PX);

        return headerLayout;
    }

}
