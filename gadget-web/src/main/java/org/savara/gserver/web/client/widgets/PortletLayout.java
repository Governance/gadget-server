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
package org.savara.gserver.web.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import org.savara.gserver.web.client.util.UUID;

/**
 * @author: Jeff Yu
 * @date: 28/02/12
 */
public class PortletLayout extends Composite {
    
    private FlowPanel portlet;
    private String id;
    private FlowPanel header;
    private FlowPanel content;

    public PortletLayout() {
        portlet = new FlowPanel();
        id = "portlet-" + UUID.uuid(4);
        portlet.getElement().setId(id);
        portlet.getElement().addClassName("portlet ui-widget ui-widget-content ui-helper-clearfix ui-corner-all");

        header = new FlowPanel();
        header.getElement().addClassName("portlet-header ui-widget-header ui-corner-all");
        portlet.add(header);

        InlineHTML minIcon = new InlineHTML("min");
        minIcon.getElement().setClassName("ui-icon ui-icon-minusthick portlet-min");
        minIcon.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                toggle(id);
            }
        });

        InlineHTML closeIcon = new InlineHTML("settings");
        closeIcon.getElement().setClassName("ui-icon ui-icon-gear portlet-settings");
        
        header.add(closeIcon);
        header.add(minIcon);

        content = new FlowPanel();
        content.getElement().addClassName("portlet-content");
        portlet.add(content);
        
        initWidget(portlet);
    }
    
    public PortletLayout(String title, String info) {
        this();
        Label label = new Label();
        label.setText(title);
        header.add(label);

        HTML html = new HTML();
        html.setHTML(info);
        content.add(html);

       }

    @Override
    public void onAttach() {
        super.onAttach();
    }

    /**
     * JSNI methods
     */
    private static native void toggle(String id) /*-{
        $wnd.$('#' + id).find(".portlet-min")
            .toggleClass( "ui-icon-minusthick" )
            .toggleClass( "ui-icon-plusthick" );

        $wnd.$('#' + id).find(".portlet-content").toggle();
    }-*/;

    private static native void remove(String id) /*-{
        $wnd.$('#' + id).remove();
    }-*/;

}
