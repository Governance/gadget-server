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

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import org.overlord.gadgets.web.client.URLBuilder;
import org.overlord.gadgets.web.client.util.RestfulInvoker;
import org.overlord.gadgets.web.shared.dto.WidgetModel;

/**
 * @author: Jeff Yu
 * @date: 2/03/12
 */
public class Portlet extends Composite {

    interface PortletUiBinder extends UiBinder<Widget, Portlet> {}

    private static PortletUiBinder uiBinder = GWT.create(PortletUiBinder.class);
    
    private String id;

    private String widgetId;

    @UiField InlineLabel minBtn;
    @UiField InlineLabel title;
    @UiField InlineLabel removeBtn;
    @UiField InlineLabel maxBtn;
    @UiField InlineLabel settingBtn;
    @UiField InlineLabel restoreBtn;
    @UiField FlowPanel userPreference;
    @UiField FlowPanel portletContent;
    @UiField Frame gadgetSpec;

    public Portlet(final String wid) {
        widgetId = wid;
        id = "portlet-" + widgetId;
        initWidget(uiBinder.createAndBindUi(this));
        getElement().setId(id);

        minBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                 toggle(id);
            }
        });

        removeBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                String theURL =  URLBuilder.getRemoveWidgetURL(Long.valueOf(widgetId));
                RestfulInvoker.invoke(RequestBuilder.POST, theURL,
                        null, new RestfulInvoker.Response() {

                    public void onResponseReceived(Request request, Response response) {
                        remove(id);
                    }
                });
            }
        });
        
        maxBtn.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				maximizeWindow(id);
				showRestoreButton(id);
			}        	
        });
        
        restoreBtn.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				restoreWindow(id);
				hideRestoreButton(id);
			}        	
        });

        gadgetSpec.getElement().setId(widgetId);
    }

    public Portlet(WidgetModel model, int width) {
        this(String.valueOf(model.getWidgetId()));
        title.setText(model.getName());
        gadgetSpec.getElement().setAttribute("scrolling", "no");
        gadgetSpec.getElement().setAttribute("frameborder", "0");
        gadgetSpec.setWidth(width - 20 + "px");
        gadgetSpec.setHeight("250px");
        gadgetSpec.setUrl("http://localhost:8080/gadget-server/gadgets/ifr?url=" + model.getSpecUrl());
    }

    @Override
    public void onAttach() {
        super.onAttach();
        hideRestoreButton(id);
    }

    /**
     * JSNI methods
     */
    private static native void toggle(String id) /*-{
        $wnd.$('#' + id).find(".portlet-min")
                .toggleClass( "ui-icon-triangle-1-s" )
                .toggleClass( "ui-icon-triangle-1-n" );

        $wnd.$('#' + id).find(".portlet-content").toggle();
    }-*/;

    private static native void remove(String id) /*-{
        $wnd.$('#' + id).remove();
    }-*/;

    private static native void showUserPreference(String id) /*-{
        $wnd.$('#' + id).find(".portlet-menu").show();
    }-*/;
    
    private static native void hideRestoreButton(String id) /*-{
    	$wnd.$('#'+id).find(".portlet-restore").hide();
    	$wnd.$('#'+id).find(".portlet-max").show();
    	$wnd.$('#'+id).find(".portlet-setting").show();
    	$wnd.$('#'+id).find(".portlet-close").show();
    }-*/;
    
    private static native void showRestoreButton(String id) /*-{
    	$wnd.$('#'+id).find(".portlet-restore").show();
    	$wnd.$('#'+id).find(".portlet-max").hide();
    	$wnd.$('#'+id).find(".portlet-setting").hide();
    	$wnd.$('#'+id).find(".portlet-close").hide();
    }-*/;
    
    private static native void maximizeWindow(String id) /*-{
	    var overlay = $wnd.$('<div></div>');
	    var jqElm = $wnd.$('#gadget-web-tabs');
	    var styleMap = {
	        position: "absolute",
	        height : jqElm.height(),
	        width : jqElm.width(),
	        'z-index': 10,
	        opacity : 0.7,
	        background : "#FFFFFF"
	    };
	    $wnd.$(overlay).css(styleMap);
	    $wnd.$(overlay).addClass("added-overlay");
	    jqElm.prepend(overlay[0]);
		$wnd.$(".column").sortable( "option", "disabled", true);
		$wnd.$('#' + id).removeClass("portlet").addClass("portlet-canvas");
	}-*/;
    
    private static native void restoreWindow(String id) /*-{
		$wnd.$(".added-overlay").remove();
		$wnd.$(".column").sortable( "option", "disabled", false);
		$wnd.$('#' + id).removeClass("portlet-canvas").addClass("portlet");
	}-*/;

}
