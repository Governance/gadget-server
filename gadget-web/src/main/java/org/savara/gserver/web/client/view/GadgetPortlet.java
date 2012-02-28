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
import com.google.gwt.user.client.ui.Frame;
import com.smartgwt.client.types.*;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.layout.Portlet;
import com.smartgwt.client.widgets.layout.VLayout;
import org.savara.gserver.web.shared.dto.GadgetModel;

/**
 * @author: Jeff Yu
 * @date: 9/02/12
 */
	public  class GadgetPortlet extends Portlet {

        private VLayout configurePanel;

		public GadgetPortlet(final GadgetModel model) {
			super();
	        setTitle(model.getName());
	        setShowShadow(false);
	        setDragAppearance(DragAppearance.TARGET);
	        setHeaderControls(HeaderControls.MINIMIZE_BUTTON, HeaderControls.HEADER_LABEL,HeaderControls.CLOSE_BUTTON);

	        setVPolicy(LayoutPolicy.NONE);
	        setOverflow(Overflow.VISIBLE);
	        setAnimateMinimize(true);
	        setCanDrop(true);

	        setWidth(300);
	        setHeight(250);
	        setCanDragResize(false);

	        //TODO: how to add a confirmation box before closing the porlet?
	        setCloseConfirmationMessage("Are you sure to close the " + model.getName() + " portlet ?");
	        setShowCloseConfirmationMessage(true);

	        //addDragRepositionStopHandler(dsHandler);

/*           Frame gadgetContent = new Frame();
           gadgetContent.setWidth("100%");
           gadgetContent.setHeight("100%");
           

           gadgetContent.setUrl("http://localhost:8080/gadget-server/gadgets/ifr?url=" + model.getSpecUrl()
                   + "&view=canvas&lang=EN&type=js");*/
           HTMLPane gadgetContent = new HTMLPane();
           gadgetContent.setIFrameURL("http://localhost:8080/gadget-server/gadgets/ifr?url=" + model.getSpecUrl()
                   + "&view=canvas&lang=EN&type=js");
           gadgetContent.setWidth100();
           gadgetContent.setHeight100();

           VLayout contentPanel = new VLayout();
           contentPanel.setMargin(5);
           contentPanel.setHeight100();
           contentPanel.setWidth100();
           contentPanel.addChild(gadgetContent);
            
           addChild(contentPanel);

		}


    }