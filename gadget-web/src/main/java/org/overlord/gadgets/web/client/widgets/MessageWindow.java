/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008-12, Red Hat Middleware LLC, and others contributors as indicated
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
package org.overlord.gadgets.web.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author Jeff Yu
 * @date Nov 7, 2012
 *
 */
public class MessageWindow extends Composite{
	
	interface WindowUiBinder extends UiBinder<Widget, MessageWindow> {}
	
	private static WindowUiBinder uiBinder = GWT.create(WindowUiBinder.class);
	
	@UiField
	DivElement message;
	
	@UiField
	SpanElement content;
	
	public MessageWindow() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
    @Override
    public void onAttach() {
        super.onAttach();
    }
    
    /**
     * JSNI methods
     */
    private static native void initializeDialog( String id) /*-{
        $wnd.$('#' + id).dialog({
            autoOpen:false,
            modal:true,
            width:300,
            height:200,
            buttons:{
                "Submit": function(){
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
