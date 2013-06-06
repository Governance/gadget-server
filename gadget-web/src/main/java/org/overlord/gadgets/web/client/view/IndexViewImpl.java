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

import java.util.List;

import org.overlord.gadgets.web.client.ApplicationEntryPoint;
import org.overlord.gadgets.web.client.BootstrapContext;
import org.overlord.gadgets.web.client.NameTokens;
import org.overlord.gadgets.web.client.auth.CurrentUser;
import org.overlord.gadgets.web.client.presenter.IndexPresenter;
import org.overlord.gadgets.web.client.widgets.AddTabForm;
import org.overlord.gadgets.web.client.widgets.PortalLayout;
import org.overlord.gadgets.web.client.widgets.Portlet;
import org.overlord.gadgets.web.client.widgets.TabLayout;
import org.overlord.gadgets.web.shared.dto.PageModel;
import org.overlord.gadgets.web.shared.dto.WidgetModel;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * @author: Jeff Yu
 * @date: 20/02/12
 */
public class IndexViewImpl extends ViewImpl implements IndexPresenter.IndexView {

    private LayoutPanel headerPanel;
    private LayoutPanel mainPanel;
    private TabLayout mainContentPanel;
    private LayoutPanel footerPanel;

    private DockLayoutPanel panel;

    private BootstrapContext context;

    private IndexPresenter presenter;

    private CurrentUser currentUser;

    @Inject
    public IndexViewImpl(BootstrapContext ctx, CurrentUser user) {
        context = ctx;
        currentUser = user;
        headerPanel = new LayoutPanel();
        headerPanel.setStyleName("header-panel");

        HTML store = new HTML("Gadget Store");
        store.addStyleName("header-link");
        store.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
            	if (currentUser.getCurrentPage() != 0) {
	                ApplicationEntryPoint.MODULES.getPlaceManager().revealPlace(
	                        new PlaceRequest(NameTokens.WIDGET_STORE)
	                );
            	} else {
            		Window.alert("You need to create a Page before adding widgets from Widget Store!");
            	}
            }
        });

        headerPanel.add(store);

        headerPanel.setWidgetRightWidth(store, 5, Style.Unit.PX, 110, Style.Unit.PX);
        headerPanel.setWidgetTopHeight(store, 5, Style.Unit.PX, 28, Style.Unit.PX);


        footerPanel = new LayoutPanel();
        footerPanel.setStyleName("footer-panel");

        panel = new DockLayoutPanel(Style.Unit.PX);
        panel.getElement().setAttribute("id", "container");

        mainContentPanel = new TabLayout(currentUser);

        final AddTabForm addTabForm = new AddTabForm(currentUser, mainContentPanel);

        Anchor anchor = new Anchor();
        anchor.setText("+");
        anchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                addTabForm.show();
            }
        });
        mainContentPanel.setTabAnchor(anchor);

        mainPanel = new LayoutPanel();
        mainPanel.add(addTabForm);
        mainPanel.add(mainContentPanel);

        panel.addNorth(headerPanel, 25);
        panel.addSouth(footerPanel, 25);
        panel.add(mainPanel);

        footerPanel.add(ApplicationEntryPoint.MODULES.getFooter().asWidget());

    }

    @Override
    public void initializePages(List<PageModel> pageModels) {

        mainContentPanel.clearAllTabs();

        for (PageModel page : pageModels) {
            int i = 0;
            int columnNum = Long.valueOf(page.getColumns()).intValue();
            PortalLayout portalLayout = new PortalLayout(String.valueOf(page.getId()), columnNum);
            for(WidgetModel model : page.getModels()) {
                portalLayout.addPortlet( i % columnNum, new Portlet(model, portalLayout.getPortletWidth(), portalLayout.getPortalId()));
                i ++;
            }
            mainContentPanel.addTab(String.valueOf(page.getId()), page.getName(), portalLayout);
        }
        mainContentPanel.addTabAnchor();

        mainContentPanel.initializeTab();
        mainContentPanel.selectCurrentActiveTab();
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setPresenter(IndexPresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * JNSI methods
     *
     * @param alertMsg
     */
    private static native void alertWindow(String alertMsg) /*-{
    	$wnd.alert(alertMsg);
 	}-*/;

}
