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
