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

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.widgets.layout.VLayout;
import org.savara.gserver.web.client.presenter.PortletPresenter;

/**
 * @author: Jeff Yu
 * @date: 20/02/12
 */
public class PortletViewImpl extends ViewImpl implements PortletPresenter.PortletView{

    private VLayout panel;

    @Inject
    public PortletViewImpl() {
        panel = new VLayout();
        Frame frame = new Frame();
        frame.setUrl("http://localhost:8080/gadget-server/gadgets/ifr?url=http://hosting.gmodules.com/ig/gadgets/file/112016200750717054421/currency-converter.xml");
        panel.addChild(frame);
    }

    public Widget asWidget() {
        return panel;
    }

}
