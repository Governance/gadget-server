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
import org.overlord.gadgets.web.client.util.UUID;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author: Jeff Yu
 * @date: 3/03/12
 */
public class AddTabForm extends Composite {

    interface DialogUiBinder extends UiBinder<Widget, AddTabForm>{}
    
    private static DialogUiBinder uiBinder = GWT.create(DialogUiBinder.class);
    
    private String id;
    
    @UiField
    DivElement dialog;
    @UiField
    TextBox tabName;
    @UiField
    ListBox layoutColumns;
    
    private TabLayout tab;

    private CurrentUser currentUser;
        
    public AddTabForm(CurrentUser user) {
        id = "dialog-" + UUID.uuid(4);
        currentUser = user;

        initWidget(uiBinder.createAndBindUi(this));
        dialog.setId(id);

        layoutColumns.insertItem("One Column", "1", 0);
        layoutColumns.insertItem("Two Columns", "2", 1);
        layoutColumns.insertItem("Three Columns", "3", 2);
    }
    
    public AddTabForm(CurrentUser user, TabLayout tabLayout) {
        this(user);
        this.tab = tabLayout;
    }


    @Override
    public void onAttach() {
        super.onAttach();
        initializeDialog(this, id);
    }

    public void show() {
        open(id);
    }

    public void addNewTab() {
        String name = tabName.getValue();
        String colNum = layoutColumns.getValue(layoutColumns.getSelectedIndex());

        Log.debug("the tab name is: " + name + ", and the colNum is: " + colNum + ", the userId : " + currentUser.getUserId());

        JSONObject postData = new JSONObject();
        postData.put("name", new JSONString(name));
        postData.put("columns", new JSONNumber(Long.valueOf(colNum)));
        
        RestfulInvoker.invoke(RequestBuilder.POST, URLBuilder.getAddPageURL(currentUser.getUserId()),
                postData.toString(), new RestfulInvoker.Response(){

            public void onResponseReceived(Request request, Response response) {
                Log.debug("The response is: " + response.getText() + ", and the currentUser is : " + currentUser);
                String newPageId = response.getText();
                PortalLayout portal = new PortalLayout(newPageId, Integer.valueOf(layoutColumns.getValue(layoutColumns.getSelectedIndex())));
                tab.insertTab(newPageId, tabName.getValue(), portal);
                tabName.setValue("");
                currentUser.setCurrentPage(Long.valueOf(newPageId));
            }
        });

    }


    /**
     * JSNI methods
     */
    private static native void initializeDialog(final AddTabForm form, String id) /*-{
        $wnd.$('#' + id).dialog({
            autoOpen:false,
            modal:true,
            width:300,
            height:200,
            buttons:{
                "Submit": function(){
                    form.@org.overlord.gadgets.web.client.widgets.AddTabForm::addNewTab()();
                    $wnd.$(this).dialog("close");
                },
                Cancel: function() {
                    $wnd.$(this).dialog("close");
                }
            },
            close:function(){

            }
        });
    }-*/;
    
    private static native void open(String id) /*-{
        $wnd.$('#' + id).dialog('open');
    }-*/;

    private static native void destroy(String id) /*-{
        $wnd.$('#' + id).dialog('destroy');
    }-*/;

}
