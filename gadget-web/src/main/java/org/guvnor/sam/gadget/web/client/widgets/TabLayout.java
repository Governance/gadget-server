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
package org.guvnor.sam.gadget.web.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.*;

import org.guvnor.sam.gadget.web.client.util.UUID;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Jeff Yu
 * @date: 3/03/12
 */
public class TabLayout extends Composite {

    interface TabLayoutUiBinder extends UiBinder<Widget, TabLayout>{}

    private static TabLayoutUiBinder uiBinder = GWT.create(TabLayoutUiBinder.class);

    private String id;

    @UiField UnorderedList tabsBar;
    
    @UiField FlowPanel tabsContent;

    @UiField DivElement tabs;
    
    private Map<String, String> tabNames = new HashMap<String, String>();

    public TabLayout() {
        id = "tabs-" + UUID.uuid(4);
        initWidget(uiBinder.createAndBindUi(this));
        tabs.setId(id);

    }
    
    
    public void addTab(String tabTitle, Widget widget){
        String idSuffix = UUID.uuid(4);
        String tabContentId = "tab-content-" + idSuffix;
        tabNames.put(tabContentId, tabTitle);

        addTabTitle(tabTitle, tabContentId);
        
        FlowPanel theContent = new FlowPanel();
        theContent.getElement().setId(tabContentId);
        theContent.add(widget);
        tabsContent.add(theContent);

    }

    public void addTabAnchor(Anchor anchor) {
        ListItem li = new ListItem();
        li.add(anchor);
        tabsBar.add(li);
    }

    private void addTabTitle(String tabTitle, String tabContentId) {
        ListItem li = new ListItem();
        li.getElement().setClassName("ui-state-default ui-corner-top");
        Anchor anchor = new Anchor();
        anchor.setHref("#" + tabContentId);
        anchor.setText(tabTitle);
        li.add(anchor);

        InlineLabel removeBtn = new InlineLabel();
        removeBtn.setText("remove");
        removeBtn.setStyleName("ui-icon ui-icon-close");
        li.add(removeBtn);
        
        tabsBar.add(li);

    }
    
    public void insertTab(String tabTitle, Widget widget) {
        String idSuffix = UUID.uuid(4);
        String tabContentId = "tab-content-" + idSuffix;

        FlowPanel theContent = new FlowPanel();
        theContent.getElement().setId(tabContentId);
        theContent.add(widget);
        tabsContent.add(theContent);

        addNewTab(id, tabContentId, tabTitle);
    }

    public void onAttach() {
        super.onAttach();
    }

    public void initializeTab() {
        initTabs(id);
        registerCloseEvent(id);
    }

    /**
     * JSNI methods
     */

    private static native void initTabs(String id) /*-{
        $wnd.$('#'+id).tabs({
            tabTemplate: "<li><a href='#{href}'>#{label}</a> <span class='ui-icon ui-icon-close'>remove</span></li>"
        });
    }-*/;

    private static native void addNewTab(String id, String tabContentId, String tabTitle) /*-{
        var theTabs = $wnd.$('#'+id).tabs();
        $wnd.$('#'+id).tabs("add", "#"+tabContentId, tabTitle);
        theTabs.tabs("select","#"+tabContentId);
    }-*/;

    /**
     *  TODO: This is a hack, somehow couldn't attach the click event to removetBtn;
     * */
    private static native void registerCloseEvent(String id) /*-{
        $wnd.$('#'+id + ' span.ui-icon-close').live('click', function(){
            var theTabs = $wnd.$('#'+id).tabs();
            var index = $wnd.$("li", theTabs).index($wnd.$(this).parent());
            theTabs.tabs('remove', index - 1);
        });
    }-*/;


}
