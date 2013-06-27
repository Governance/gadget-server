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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author: Jeff Yu
 * @date: 28/02/12
 */
public class PortalLayout extends Composite {

    private int m_column;

    private FlowPanel portalPanel;
    
    private String portalId;
    
    private List<FlowPanel> columnPanel;
    
    public static final int THREE_COLUMN_WIDTH = 325;
    public static final int TWO_COLUMN_WIDTH = 500;
    public static final int ONE_COLUMN_WIDTH = 980;
    
    private int columnWidth = TWO_COLUMN_WIDTH;

    public PortalLayout(String pid) {
        portalPanel = new FlowPanel();
        this.portalId = "portal-" + pid;
        portalPanel.getElement().addClassName("portal");
        portalPanel.getElement().setId(portalId);
        initWidget(portalPanel);
    }

    public PortalLayout(String portalId,int column) {
        this(portalId);
        this.m_column = column;
        columnPanel = new ArrayList<FlowPanel>(column);
        if (column == 3) {
            columnWidth = THREE_COLUMN_WIDTH;
        } else if (column == 1) {
            columnWidth = ONE_COLUMN_WIDTH;
        }

        for (int i = 0; i < column; i++) {
           FlowPanel cpanel = new FlowPanel();
           cpanel.getElement().addClassName("column");
           cpanel.setWidth(columnWidth + "px");
           columnPanel.add(cpanel);
           portalPanel.add(cpanel);
        }
    }
    
    public int getPortletWidth() {
    	return columnWidth;
    }
    
    
    public String getPortalId() {
    	return this.portalId;
    }

    public void addPortlet(int i, Widget portlet) {
        columnPanel.get(i).add(portlet);
    }
    
    /**
     * This is for getting the portal's height.
     */
    public void addClosingDiv() {
    	portalPanel.add(new HTML("<div style='clear:both'></div>"));
    }

    @Override
    public void onAttach() {
        super.onAttach();
        sortableScript();
    }

    /**
     * JSNI methods
     */
    private static native void sortableScript() /*-{
        $wnd.$(".column").sortable({
            connectWith: ".column"
        });
    }-*/;
}
