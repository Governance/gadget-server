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
package org.savara.gadget.web.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import org.savara.gadget.web.client.URLBuilder;
import org.savara.gadget.web.client.auth.CurrentUser;
import org.savara.gadget.web.client.util.RestfulInvoker;
import org.savara.gadget.web.shared.dto.StoreItemModel;

/**
 * @author: Jeff Yu
 * @date: 30/04/12
 */
public class StoreItem extends Composite {

    interface StoreItemUiBinder extends UiBinder<Widget, StoreItem> {}

    private static StoreItemUiBinder uiBinder = GWT.create(StoreItemUiBinder.class);

    @UiField Image itemImage;
    @UiField Label itemName;
    @UiField Label itemDesc;
    @UiField Button addBtn;

    public StoreItem(final StoreItemModel model, final CurrentUser user, final Label messageBar) {
        initWidget(uiBinder.createAndBindUi(this));
        itemImage.setUrl(model.getThumbnailUrl());
        itemImage.setHeight("60px");
        itemImage.setWidth("100px");

        itemName.setText(model.getName());
        itemDesc.setText(model.getDescription());
        
        addBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                RestfulInvoker.invoke(RequestBuilder.POST, URLBuilder.getAddGadgetToPageURL(user.getCurrentPage(), model.getId()),
                        null, new RestfulInvoker.Response(){

                        public void onResponseReceived(Request request, Response response) {
                             messageBar.setText("The Gadget [" + model.getName() + "] has been added successfully.");
                    }
                });
            }
        });
    }

}
