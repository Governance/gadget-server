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
package org.overlord.gadgets.web.client.view;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

import org.overlord.gadgets.web.client.ApplicationEntryPoint;
import org.overlord.gadgets.web.client.NameTokens;
import org.overlord.gadgets.web.client.URLBuilder;
import org.overlord.gadgets.web.client.auth.CurrentUser;
import org.overlord.gadgets.web.client.presenter.StorePresenter;
import org.overlord.gadgets.web.client.util.RestfulInvoker;
import org.overlord.gadgets.web.client.widgets.StoreItem;
import org.overlord.gadgets.web.shared.dto.PageResponse;
import org.overlord.gadgets.web.shared.dto.StoreItemModel;

/**
 * @author: Jeff Yu
 * @date: 30/04/12
 */
public class StoreViewImpl extends ViewImpl implements StorePresenter.StoreView{

    private LayoutPanel headerPanel;
    private DockLayoutPanel panel;
    private LayoutPanel footerPanel;
    private LayoutPanel mainPanel;
    
    private StorePresenter presenter;

    private CurrentUser currentUser;
    
    private Label messageBar = new Label();

    @Inject
    public StoreViewImpl(CurrentUser user) {

        this.currentUser = user;

        headerPanel = new LayoutPanel();
        headerPanel.setStyleName("header-panel");

        HTML logout = new HTML("Logout");
        logout.addStyleName("header-link");
        logout.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RestfulInvoker.invoke(RequestBuilder.POST, URLBuilder.getInvalidSessionURL(), null,
                        new RestfulInvoker.Response() {
                            public void onResponseReceived(Request request, Response response) {
                                currentUser.setLoggedIn(false);
                                ApplicationEntryPoint.MODULES.getPlaceManager().revealPlace(
                                        new PlaceRequest(NameTokens.LOGIN_VIEW));
                            }
                        });
            }
        });

        headerPanel.add(logout);

        headerPanel.setWidgetRightWidth(logout, 5, Style.Unit.PX, 60, Style.Unit.PX);
        headerPanel.setWidgetTopHeight(logout, 2, Style.Unit.PX, 28, Style.Unit.PX);

        Label userLabel = new Label(currentUser.getDisplayName());
        userLabel.setStyleName("userinfo");
        headerPanel.add(userLabel);

        headerPanel.setWidgetRightWidth(userLabel, 55, Style.Unit.PX, 150, Style.Unit.PX);
        headerPanel.setWidgetTopHeight(userLabel, 2, Style.Unit.PX, 28, Style.Unit.PX);

        HTML backToTabs = new HTML("Back to Tabs");
        backToTabs.addStyleName("header-link");
        backToTabs.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                ApplicationEntryPoint.MODULES.getPlaceManager().revealPlace(
                        new PlaceRequest(NameTokens.INDEX_VIEW)
                );
            }
        });

        headerPanel.add(backToTabs);

        headerPanel.setWidgetRightWidth(backToTabs, 5, Style.Unit.PX, 110, Style.Unit.PX);
        headerPanel.setWidgetTopHeight(backToTabs, 45, Style.Unit.PX, 28, Style.Unit.PX);

        footerPanel = new LayoutPanel();
        footerPanel.setStyleName("footer-panel");

        panel = new DockLayoutPanel(Style.Unit.PX);
        panel.getElement().setAttribute("id", "container");

        mainPanel = new LayoutPanel();
        mainPanel.getElement().setId("mainpanel");

        panel.addNorth(headerPanel, 70);
        panel.addSouth(footerPanel, 25);
        panel.add(mainPanel);

        footerPanel.add(ApplicationEntryPoint.MODULES.getFooter().asWidget());
    }

    public void loadStoreItems(PageResponse<StoreItemModel> storeItems) {
        mainPanel.clear();

        FlowPanel storesList = new FlowPanel();
        storesList.getElement().setId("gadget-store-list");
        storesList.setWidth("100%");
        messageBar.setStyleName("storeItemInfo");
        storesList.add(messageBar);

        for (StoreItemModel item : storeItems.getResultSet()) {
            StoreItem storeItem = new StoreItem(item, currentUser, messageBar);
            storesList.add(storeItem);
        }
        mainPanel.add(storesList);
    }

    public Widget asWidget() {
        return panel;
    }

    public void setPresenter(StorePresenter presenter) {
        this.presenter = presenter;
    }

    public void clearMessageBar() {
        messageBar.setText("");
    }
}
