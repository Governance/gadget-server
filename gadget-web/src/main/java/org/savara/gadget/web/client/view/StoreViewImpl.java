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
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.ViewImpl;
import org.savara.gadget.web.client.ApplicationEntryPoint;
import org.savara.gadget.web.client.auth.CurrentUser;
import org.savara.gadget.web.client.model.JSOParser;
import org.savara.gadget.web.client.presenter.StorePresenter;
import org.savara.gadget.web.client.util.RestfulInvoker;
import org.savara.gadget.web.client.widgets.StoreItem;
import org.savara.gadget.web.shared.dto.PageResponse;
import org.savara.gadget.web.shared.dto.StoreItemModel;

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
    
    private PageResponse<StoreItemModel> storeItems;
    
    private CurrentUser currentUser;

    private Label messageBar = new Label();

    @Inject
    public StoreViewImpl(CurrentUser user) {

        this.currentUser = user;

        headerPanel = new LayoutPanel();
        headerPanel.setStyleName("header-panel");

        footerPanel = new LayoutPanel();
        footerPanel.setStyleName("footer-panel");

        panel = new DockLayoutPanel(Style.Unit.PX);
        panel.getElement().setAttribute("id", "container");

        mainPanel = new LayoutPanel();
        mainPanel.getElement().setId("mainpanel");

        panel.addNorth(headerPanel, 70);
        panel.addSouth(footerPanel, 25);
        panel.add(mainPanel);

        headerPanel.add(ApplicationEntryPoint.MODULES.getHeader().asWidget());
        footerPanel.add(ApplicationEntryPoint.MODULES.getFooter().asWidget());
        
        presenter.getStoreItems(0, 10, new RestfulInvoker.Response() {
            public void onResponseReceived(Request request, Response response) {
                
                VerticalPanel storesList = new VerticalPanel();
                storesList.setWidth("100%");
                messageBar.setStyleName("storeItemInfo");
                storesList.add(messageBar);
                storeItems = JSOParser.getStoreItems(response.getText());
                
                for (StoreItemModel item : storeItems.getResultSet()) {
                    StoreItem storeItem = new StoreItem(item, currentUser, messageBar);
                    storesList.add(storeItem);
                }
                mainPanel.add(storesList);
            }
        });
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
