/**
 * 
 */
package org.savara.gserver.web.client.view;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.LayoutPolicy;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.DragRepositionStopHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Portlet;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

/**
 * Customized Portal Layout, to incorporate the PortalColumn feature,
 * so a new portlet will be added into the fewest portal column.
 * 
 * Portlet's title should be unique.
 * 
 * @author Jeff Yu
 * @date Nov 16, 2011
 */
public class GadgetPortalLayout extends HLayout{
	
	private List<String> portletTitles = new ArrayList<String>();
	
	public GadgetPortalLayout(int numColumns) {
		setMargin(0);
		setWidth100();
		setHeight100();
		setCanAcceptDrop(true);	
		
		for (int i = 0; i < numColumns; i++) {
			addMember(new ChartPortalColumn());
		}
	}
	
    public void addPortlet(GadgetPortlet portlet) {
        int fewestPortlets = Integer.MAX_VALUE;  
        ChartPortalColumn fewestPortletsColumn = null;  
        for (int i = 0; i < getMembers().length; i++) {  
            int numPortlets = ((ChartPortalColumn) getMember(i)).getMembers().length;  
            if (numPortlets < fewestPortlets) {  
                fewestPortlets = numPortlets;  
                fewestPortletsColumn = (ChartPortalColumn) getMember(i);  
            }  
        }  
        fewestPortletsColumn.addMember(portlet);
        portletTitles.add(portlet.getTitle());
    }
    
    public boolean isTitleUnique(String title) {
    	return !portletTitles.contains(title);
    }
    
    public void removePortlet(String title) {
    	for (int i = 0; i< getMembers().length; i++) {
    		ChartPortalColumn portalColumn = (ChartPortalColumn)getMember(i);
    		for (int y = 0; y < portalColumn.getMembers().length; y++) {
    			GadgetPortlet portlet = (GadgetPortlet)portalColumn.getMember(y);
    			if (title.equals(portlet.getTitle())) {
    				portalColumn.removeMember(portlet);
    			}
    		}
    	}
    }
    
    public GadgetPortlet getGadgetPortlet(String title) {
    	for (int i = 0; i< getMembers().length; i++) {
    		ChartPortalColumn portalColumn = (ChartPortalColumn)getMember(i);
    		for (int y = 0; y < portalColumn.getMembers().length; y++) {
    			GadgetPortlet portlet = (GadgetPortlet) portalColumn.getMember(y);
	    			if (title.equals(portlet.getTitle())) {
	    				return portlet;
	    			}
    		}
    	}
    	return null;
    }
	
	
	public class ChartPortalColumn extends VStack {
		
		public ChartPortalColumn() {
            setMembersMargin(6);  
            
            // enable predefined component animation  
            setAnimateMembers(true);  
            setAnimateMemberTime(300);  
  
            // enable drop handling  
            setCanAcceptDrop(true);  
  
            // change appearance of drag placeholder and drop indicator  
            setDropLineThickness(4);  
  
            Canvas dropLineProperties = new Canvas();  
            dropLineProperties.setBackgroundColor("aqua");  
            setDropLineProperties(dropLineProperties);  
  
            setShowDragPlaceHolder(true);  
  
            Canvas placeHolderProperties = new Canvas();  
            placeHolderProperties.setBorder("2px solid #8289A6");  
            setPlaceHolderProperties(placeHolderProperties);		
		}
	
	}

}
