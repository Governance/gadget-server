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
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

import org.savara.gadget.web.client.ApplicationEntryPoint;
import org.savara.gadget.web.client.BootstrapContext;
import org.savara.gadget.web.client.auth.CurrentUser;
import org.savara.gadget.web.client.model.JSOParser;
import org.savara.gadget.web.client.presenter.IndexPresenter;
import org.savara.gadget.web.client.util.RestfulInvoker;
import org.savara.gadget.web.client.widgets.*;
import org.savara.gadget.web.shared.dto.WidgetModel;
import org.savara.gadget.web.shared.dto.PageModel;

import java.util.List;

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

        footerPanel = new LayoutPanel();
        footerPanel.setStyleName("footer-panel");

        panel = new DockLayoutPanel(Style.Unit.PX);
        panel.getElement().setAttribute("id", "container");

        mainContentPanel = new TabLayout();

        final AddTabForm addTabForm = new AddTabForm(mainContentPanel);

        Anchor anchor = new Anchor();
        anchor.setText("Add a tab");
        anchor.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                addTabForm.show();
            }
        });
        mainContentPanel.addTabAnchor(anchor);

        mainPanel = new LayoutPanel();
        mainPanel.add(addTabForm);
        mainPanel.add(mainContentPanel);

        panel.addNorth(headerPanel, 70);
        panel.addSouth(footerPanel, 25);
        panel.add(mainPanel);

        getHeaderPanel().add(ApplicationEntryPoint.MODULES.getHeader().asWidget());
        getFooterPanel().add(ApplicationEntryPoint.MODULES.getFooter().asWidget());

    }

    public void initializePages(List<PageModel> pageModels) {

        for (PageModel page : pageModels) {
            int i = 0;
            int columnNum = page.getColumns().intValue();
            PortalLayout portalLayout = new PortalLayout(columnNum);
            for(WidgetModel model : page.getModels()) {
                portalLayout.addPortlet( i % columnNum, new Portlet(model));
                i ++;
            }
            mainContentPanel.addTab(page.getName(), portalLayout);
        }

        //Hard-coded for testing...
/*        PortalLayout sndLayout = new PortalLayout(2);
        PortletLayout helloWorld = new PortletLayout("HelloWorld", "Hello World Portlet");
        sndLayout.addPortlet(0, helloWorld);

        mainContentPanel.addTab("Finance", sndLayout);*/

        mainContentPanel.initializeTab();
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

    public void setPresenter(IndexPresenter presenter) {
        this.presenter = presenter;
    }


}
