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

import com.allen_sauer.gwt.log.client.Log;
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

    private LayoutPanel headerLayout;

    private CurrentUser user;
    
    private HTML backToTabs;
    
    private HTML store;
    
    @Inject
    public Header(CurrentUser cuser) {
        user = cuser;
        headerLayout = new LayoutPanel();
        headerLayout.addStyleName("header-panel");

        Log.debug("header instance gets initialised.");
    }

    public Widget asWidget() {
        Log.debug("header instance's asWidget method");
        HTML logout = new HTML("Logout");
        logout.addStyleName("header-link");
        logout.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RestfulInvoker.invoke(RequestBuilder.POST, URLBuilder.getInvalidSessionURL(), null,
                        new RestfulInvoker.Response(){
                            public void onResponseReceived(Request request, Response response) {
                                user.setLoggedIn(false);
                                ApplicationEntryPoint.MODULES.getPlaceManager().revealPlace(
                                        new PlaceRequest(NameTokens.LOGIN_VIEW));
                            }
                        });
            }
        });

        headerLayout.add(logout);

        headerLayout.setWidgetRightWidth(logout, 5, Style.Unit.PX, 60, Style.Unit.PX);
        headerLayout.setWidgetTopHeight(logout, 2, Style.Unit.PX, 28, Style.Unit.PX);

        return headerLayout;
    }

    public void initializeNavigationMenu(final CurrentUser currentUser) {
        Log.debug("the current user is in widget store?: " + currentUser.isInWidgetStore());

        if (backToTabs != null) {
            headerLayout.remove(backToTabs);
            backToTabs = null;
        }
        if (store != null) {
            headerLayout.remove(store);
            store = null;
        }
        Log.debug("the headerLayout is: " + headerLayout);
        
        if (currentUser.isInWidgetStore()) {
            backToTabs = new HTML("Back to Tabs");
            backToTabs.addStyleName("header-link");
            backToTabs.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    ApplicationEntryPoint.MODULES.getPlaceManager().revealPlace(
                            new PlaceRequest(NameTokens.INDEX_VIEW)
                    );
                }
            });

            headerLayout.add(backToTabs);

            headerLayout.setWidgetRightWidth(backToTabs, 5, Style.Unit.PX, 110, Style.Unit.PX);
            headerLayout.setWidgetTopHeight(backToTabs, 45, Style.Unit.PX, 28, Style.Unit.PX);
            Log.debug("added the backToTabs " + backToTabs);

        } else {
            store = new HTML("Widget Store");
            store.addStyleName("header-link");
            store.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    ApplicationEntryPoint.MODULES.getPlaceManager().revealPlace(
                            new PlaceRequest(NameTokens.WIDGET_STORE)
                    );
                }
            });

            headerLayout.add(store);

            headerLayout.setWidgetRightWidth(store, 5, Style.Unit.PX, 110, Style.Unit.PX);
            headerLayout.setWidgetTopHeight(store, 45, Style.Unit.PX, 28, Style.Unit.PX);
            
            Log.debug("added to the widget store " + store);
        }

    }

}
