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

import java.util.HashMap;
import java.util.Map;

import org.overlord.gadgets.web.client.URLBuilder;
import org.overlord.gadgets.web.client.auth.CurrentUser;
import org.overlord.gadgets.web.client.util.RestfulInvoker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author: Jeff Yu
 * @date: 3/03/12
 */
public class TabLayout extends Composite {

    interface TabLayoutUiBinder extends UiBinder<Widget, TabLayout>{}

    private static TabLayoutUiBinder uiBinder = GWT.create(TabLayoutUiBinder.class);

    private String id;

    private String promptId;

    @UiField UnorderedList tabsBar;

    @UiField FlowPanel tabsContent;

    @UiField DivElement tabs;

    @UiField DivElement promptDiv;

    private ListItem addTabAnchorItem;

    private CurrentUser currentUser;

    private Map<String, String> tabNames = new HashMap<String, String>();

    private Map<String, String> indexIdMap = new HashMap<String, String>();

    private static int index = 0;

    public TabLayout(CurrentUser user) {
        currentUser = user;
        id = "gadget-web-tabs";
        initWidget(uiBinder.createAndBindUi(this));
        tabs.setId(id);
        promptId = "gadget-web-tabs-prompt";
        promptDiv.setId(promptId);
    }

    public void addTab(String pageId, String tabTitle, PortalLayout widget){
        if (tabTitle == null || tabTitle.trim().length()==0) {
            tabTitle = "Tab-" + pageId;
        }
        String tabContentId = getTabContentId(pageId);
        tabNames.put(tabContentId, tabTitle);

        addTabTitle(tabTitle, tabContentId);

        widget.addClosingDiv();

        FlowPanel theContent = new FlowPanel();
        theContent.getElement().setId(tabContentId);
        theContent.add(widget);
        tabsContent.add(theContent);

        indexIdMap.put(String.valueOf(index), pageId);
        index ++;
    }

    public void setTabAnchor(Anchor anchor) {
        addTabAnchorItem = new ListItem();
        addTabAnchorItem.add(anchor);
        tabsBar.add(addTabAnchorItem);
    }

    public void addTabAnchor() {
        tabsBar.add(addTabAnchorItem);
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

    public void insertTab(String pageId, String tabTitle, Widget widget) {
        if (tabTitle == null || tabTitle.trim().length()==0) {
            tabTitle = "Tab-" + pageId;
        }
        String tabContentId = getTabContentId(pageId);

        FlowPanel theContent = new FlowPanel();
        theContent.getElement().setId(tabContentId);
        theContent.add(widget);
        tabsContent.add(theContent);

        int theIndex = tabNames.size();

        tabNames.put(tabContentId, tabTitle);
        indexIdMap.put(String.valueOf(index), pageId);
        index = index + 1;

        tabsBar.remove(addTabAnchorItem);

        addNewTab(id, tabContentId, tabTitle, theIndex);

        tabsBar.add(addTabAnchorItem);

        updateUserCurrentPageId(Long.valueOf(pageId));

        hidePrompt(promptId);

    }

    private String getTabContentId(String pageId) {
        return "tab-content-" + pageId;
    }

    @Override
    public void onAttach() {
        super.onAttach();
        if (currentUser.getCurrentPage() == 0) {
        	showPrompt(promptId);
        } else {
        	hidePrompt(promptId);
        }
    }

    public void initializeTab() {
        initTabs(this, id);
        registerCloseEvent(this,id);
    }

    public void clearAllTabs(){
        for(String contentId : tabNames.keySet()) {
            removeTab(id, contentId);
        }
        tabsBar.remove(addTabAnchorItem);
        destroyTab(id);
        index = 0;
    }

    private void setCurrentPage(Long indexId) {
        String theIndexId = String.valueOf(indexId);
        String pageId = indexIdMap.get(theIndexId);
        long thePageId = Long.valueOf(pageId).longValue();
        updateUserCurrentPageId(thePageId);
    }

    private void removePage(Long indexId) {
        final String theIndexId = String.valueOf(indexId);
        String pageId = indexIdMap.get(theIndexId);

        RestfulInvoker.invoke(RequestBuilder.POST, URLBuilder.getRemovePageURL(Long.valueOf(pageId).longValue()),
                null, new RestfulInvoker.Response() {
                    @Override
                    public void onResponseReceived(Request request, Response response) {
                    	indexIdMap.remove(theIndexId);
                    	decrementIndexes(Integer.valueOf(theIndexId));
                        index--;
                    	if (indexIdMap.size() == 0) {
                    		updateUserCurrentPageId(0);
                    		showPrompt(promptId);
                    	}
                    }
        });
    }

    /**
     * @param indexId
     */
    protected void decrementIndexes(int indexId) {
        for (long idx = indexId; idx < index; idx++) {
            String theIdx = String.valueOf(idx);
            if (indexIdMap.containsKey(theIdx)) {
                String value = indexIdMap.remove(theIdx);
                indexIdMap.put(String.valueOf(idx - 1), value);
            }
        }
    }

    public void selectCurrentActiveTab() {
        String tabContentId = getTabContentId(String.valueOf(currentUser.getCurrentPage()));
        selectTab(id, tabContentId);
    }

    private void updateUserCurrentPageId(final long pageId) {
    	RestfulInvoker.invoke(RequestBuilder.POST, URLBuilder.updateCurrentPageId(currentUser.getUserId(), pageId), null,
    			new RestfulInvoker.Response() {
					@Override
                    public void onResponseReceived(Request arg0, Response arg1) {
						currentUser.setCurrentPage(pageId);
					}
				});
    }

    /**
     * JSNI methods
     */

    private static native void initTabs(final TabLayout layout, String id) /*-{
        $wnd.$('#'+id).tabs({
            tabTemplate: "<li><a href='#{href}'>#{label}</a> <span class='ui-icon ui-icon-close'>remove</span></li>",
            select: function(event, ui) {
                layout.@org.overlord.gadgets.web.client.widgets.TabLayout::setCurrentPage(Ljava/lang/Long;)(ui.index);
            }
        });
    }-*/;

    private static native void selectTab(String id, String tabContentId) /*-{
        var theTabs = $wnd.$('#'+id).tabs();
        theTabs.tabs("select","#"+tabContentId);
    }-*/;

    private static native void addNewTab(String id, String tabContentId, String tabTitle, int index) /*-{
        var theTabs = $wnd.$('#'+id).tabs();
        theTabs.tabs("add", "#"+tabContentId, tabTitle, index);
        theTabs.tabs("select","#"+tabContentId);
    }-*/;

    private static native void removeTab(String id, String tabContentId) /*-{
        var theTabs = $wnd.$('#'+id).tabs();
        theTabs.tabs("remove","#"+tabContentId);
    }-*/;

    private static native void destroyTab(String id) /*-{
        var theTabs = $wnd.$('#'+id).tabs();
        theTabs.tabs("destroy");
    }-*/;

    private static native void hidePrompt(String promptId) /*-{
	    $wnd.$('#'+promptId).hide();
	}-*/;

    private static native void showPrompt(String promptId) /*-{
    	$wnd.$('#'+promptId).show();
	}-*/;

    /**
     *  TODO: This is a hack, somehow couldn't attach the click event to removetBtn;
     *  if (confirm('Are you sure to delete the page?')) not working properly, it will trigger confirm window multiple times.
     **/
    private static native void registerCloseEvent(final TabLayout layout, String id) /*-{
        $wnd.$('#'+id + ' span.ui-icon-close').live('click', function(){
            var theTabs = $wnd.$('#'+id).tabs();
            var index = $wnd.$(this).parent().index();
            if (index > -1 && confirm('Are you sure you want to delete this page?')) {
                layout.@org.overlord.gadgets.web.client.widgets.TabLayout::removePage(Ljava/lang/Long;)(index);
                theTabs.tabs('remove', index);
            }
        });
    }-*/;


}
