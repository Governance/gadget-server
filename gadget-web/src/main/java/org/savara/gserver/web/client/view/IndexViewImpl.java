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
package org.savara.gserver.web.client.view;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import org.savara.gserver.web.client.ApplicationEntryPoint;
import org.savara.gserver.web.client.presenter.IndexPresenter;

/**
 * @author: Jeff Yu
 * @date: 20/02/12
 */
public class IndexViewImpl extends ViewImpl implements IndexPresenter.IndexView {

    private LayoutPanel headerPanel;
    private TabLayoutPanel mainContentPanel;
    private LayoutPanel footerPanel;

    private DockLayoutPanel panel;

    @Inject
    public IndexViewImpl() {
        mainContentPanel = new TabLayoutPanel(2.5, Style.Unit.EM);

        mainContentPanel.add(new HTML("Tabs are generally used to break content into multiple sections that can be swapped to save space, much like an accordion.\n" +
                "\n" +
                "By default a tab widget will swap between tabbed sections onClick, but the events can be changed to onHover through an option. Tab content can be loaded via Ajax by setting an href on a tab.\n" +
                "\n" +
                "NOTE: Tabs created dynamically using .tabs( \"add\", ... ) are given an id of ui-tabs-NUM, where NUM is an auto-incrementing id. If you use this naming convention for your own elements, you may encounter problems."), " Home ");

        mainContentPanel.add(new HTML("The jQuery UI Tabs plugin uses the jQuery UI CSS Framework to style its look and feel, including colors and background textures. We recommend using the ThemeRoller tool to create and download custom themes that are easy to build and maintain.\n" +
                "\n" +
                "If a deeper level of customization is needed, there are widget-specific classes referenced within the jquery.ui.tabs.css stylesheet that can be modified. These classes are highlighed in bold below."), " Finance ");

        Button addTabBtn = new Button("Add Tab");
        addTabBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {

            }
        });
        mainContentPanel.add(addTabBtn);

        headerPanel = new LayoutPanel();
        headerPanel.setStyleName("header-panel");

        footerPanel = new LayoutPanel();
        footerPanel.setStyleName("footer-panel");

        panel = new DockLayoutPanel(Style.Unit.PX);
        panel.getElement().setAttribute("id", "container");

        panel.addNorth(headerPanel, 110);
        panel.addSouth(footerPanel, 25);
        panel.add(mainContentPanel);

        getHeaderPanel().add(ApplicationEntryPoint.MODULES.getHeader().asWidget());
        getFooterPanel().add(ApplicationEntryPoint.MODULES.getFooter().asWidget());

    }

    public Widget asWidget() {
        return panel;
    }

    public LayoutPanel getHeaderPanel() {
        return headerPanel;
    }

    public LayoutPanel getFooterPanel() {
        return footerPanel;
    }

}
