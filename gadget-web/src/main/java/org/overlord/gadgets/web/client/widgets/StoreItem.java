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
package org.overlord.gadgets.web.client.widgets;

import org.overlord.gadgets.web.client.URLBuilder;
import org.overlord.gadgets.web.client.auth.CurrentUser;
import org.overlord.gadgets.web.client.util.RestfulInvoker;
import org.overlord.gadgets.web.shared.dto.StoreItemModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

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
    @UiField Label itemAuthor;

    public StoreItem(final StoreItemModel model, final CurrentUser user, final Label messageBar) {
        initWidget(uiBinder.createAndBindUi(this));
        itemImage.setUrl(model.getThumbnailUrl());
        itemImage.setHeight("60px");
        itemImage.setWidth("120px");

        itemName.setText(model.getName());
        itemDesc.setText(model.getDescription());
        itemAuthor.setText("By " + model.getAuthor());
        
        addBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                addBtn.setText("Adding...");
                addBtn.setEnabled(false);
                RestfulInvoker.invoke(RequestBuilder.POST, URLBuilder.getAddGadgetToPageURL(user.getCurrentPage(), model.getId()),
                        null, new RestfulInvoker.Response(){
                    @Override
                    public void onResponseReceived(Request request, Response response) {
                        Window.alert("The Gadget [" + model.getName() + "] has been added successfully.");
                        addBtn.setText("Add To Page");
                        addBtn.setEnabled(true);
                    }
                    @Override
                    public void onError(Request request, Throwable throwable) {
                        super.onError(request, throwable);
                        addBtn.setText("Add To Page");
                        addBtn.setEnabled(true);
                    }
                });
            }
        });
    }

}
