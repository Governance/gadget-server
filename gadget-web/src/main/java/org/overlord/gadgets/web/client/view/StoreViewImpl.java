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
package org.overlord.gadgets.web.client.view;

import org.overlord.gadgets.web.client.ApplicationEntryPoint;
import org.overlord.gadgets.web.client.NameTokens;
import org.overlord.gadgets.web.client.auth.CurrentUser;
import org.overlord.gadgets.web.client.presenter.StorePresenter;
import org.overlord.gadgets.web.client.widgets.StoreItem;
import org.overlord.gadgets.web.shared.dto.PageResponse;
import org.overlord.gadgets.web.shared.dto.StoreItemModel;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * @author: Jeff Yu
 * @date: 30/04/12
 */
public class StoreViewImpl extends ViewImpl implements StorePresenter.StoreView{

    private LayoutPanel headerPanel;
    private DockLayoutPanel panel;
    private LayoutPanel footerPanel;
    private LayoutPanel mainPanel;

//    private StorePresenter presenter;

    private CurrentUser currentUser;

    private Label messageBar = new Label();

    @Inject
    public StoreViewImpl(CurrentUser user) {

        this.currentUser = user;

        headerPanel = new LayoutPanel();
        headerPanel.setStyleName("header-panel");

        HTML backToTabs = new HTML("Back to Tabs");
        backToTabs.addStyleName("header-link");
        backToTabs.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ApplicationEntryPoint.MODULES.getPlaceManager().revealPlace(
                        new PlaceRequest(NameTokens.INDEX_VIEW)
                );
            }
        });

        headerPanel.add(backToTabs);

        headerPanel.setWidgetRightWidth(backToTabs, 5, Style.Unit.PX, 110, Style.Unit.PX);
        headerPanel.setWidgetTopHeight(backToTabs, 5, Style.Unit.PX, 28, Style.Unit.PX);

        footerPanel = new LayoutPanel();
        footerPanel.setStyleName("footer-panel");

        panel = new DockLayoutPanel(Style.Unit.PX);
        panel.getElement().setAttribute("id", "container");

        mainPanel = new LayoutPanel();
        mainPanel.getElement().setId("mainpanel");

        panel.addNorth(headerPanel, 25);
        panel.addSouth(footerPanel, 25);
        panel.add(mainPanel);

        footerPanel.add(ApplicationEntryPoint.MODULES.getFooter().asWidget());
    }

    @Override
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

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setPresenter(StorePresenter presenter) {
//        this.presenter = presenter;
    }

    @Override
    public void clearMessageBar() {
        messageBar.setText("");
    }
}
