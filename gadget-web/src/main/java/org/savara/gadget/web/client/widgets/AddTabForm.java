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
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import org.savara.gadget.web.client.util.UUID;

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
        
    public AddTabForm() {
        id = "dialog-" + UUID.uuid(4);
        initWidget(uiBinder.createAndBindUi(this));
        dialog.setId(id);

        layoutColumns.insertItem("Two Columns", "2", 0);
        layoutColumns.insertItem("Three Columns", "3", 1);
    }
    
    public AddTabForm(TabLayout tabLayout) {
        this();
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
        PortalLayout portal = new PortalLayout(Integer.valueOf(colNum));

        tab.insertTab(name, portal);

        tabName.setValue("");
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
                    form.@org.savara.gadget.web.client.widgets.AddTabForm::addNewTab()();
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
