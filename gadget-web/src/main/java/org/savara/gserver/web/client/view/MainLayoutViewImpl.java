/**
 * 
 */
package org.savara.gserver.web.client.view;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.*;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import org.savara.gserver.web.client.presenter.MainLayoutPresenter;
import org.savara.gserver.web.client.presenter.MainLayoutPresenter.MainLayoutView;
import org.savara.gserver.web.shared.dto.GadgetModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jeff Yu
 * @date Nov 4, 2011
 */
public class MainLayoutViewImpl extends ViewImpl implements MainLayoutView{
	
	private VLayout panel;
				
	private MainLayoutPresenter presenter;
	
	private VLayout main;
	
	private GadgetPortalLayout portal;
	
	private List<GadgetModel> gadgets = new ArrayList<GadgetModel>();

	@Inject
	public MainLayoutViewImpl() {
				
        Runnable onloadCallback = new Runnable() {
			public void run() {				
				initializeWindow();
			}        	
        };
                
        VisualizationUtils.loadVisualizationApi(onloadCallback, PieChart.PACKAGE);
	}
	
	

	private void initializeWindow() {
		panel  = LayoutUtil.getPagePanel();
		panel.addMember(LayoutUtil.getHeaderLayout());
		
		HLayout body = new HLayout();
		body.setWidth100();
		body.setPadding(3);
		body.setHeight(850);
		panel.addMember(body);
				
		body.addMember(LayoutUtil.getMenuStack());
		
		main = new VLayout();
		main.setMargin(5);
		body.addMember(main);
		
		portal = new GadgetPortalLayout(3);
		portal.setMargin(8);
		
		setPortalMenus(main);
		
        main.addMember(portal);
        

        GadgetModel samGadget = new GadgetModel();
        samGadget.setName("SAM Gadget");
        samGadget.setSpecUrl("http://sam-gadget.appspot.com/Gadget/SamGadget.gadget.xml");
               
        portal.addPortlet(createPortlet(samGadget));

        GadgetModel googleTranslate = new GadgetModel();
        googleTranslate.setName("Google Translate");
        googleTranslate.setSpecUrl("http://www.gstatic.com/ig/modules/dictionary/kennedy/dictionary.xml");
        portal.addPortlet(createPortlet(googleTranslate));

        GadgetModel todoList = new GadgetModel();
        todoList.setName("To-do List");
        todoList.setSpecUrl("http://www.labpixies.com/campaigns/todo/todo.xml");
        portal.addPortlet(createPortlet(todoList));

        GadgetModel hw = new GadgetModel();
        hw.setName("Hello World");
        hw.setSpecUrl("http://www.google.com/ig/modules/hello.xml");
        portal.addPortlet(createPortlet(hw));

        GadgetModel worldTime = new GadgetModel();
        worldTime.setName("World Time");
        worldTime.setSpecUrl("http://hosting.gmodules.com/ig/gadgets/file/112016200750717054421/currency-converter.xml");
        portal.addPortlet(createPortlet(worldTime));
        
		panel.addMember(LayoutUtil.getFooterLayout());
		panel.draw();
	}
	
	private void maximizeGadgetWindow(final GadgetModel model) {
		final Window window = new Window();
		window.setWidth(850);
		window.setHeight(650);
		
		final VLayout chartLayout = new VLayout();
		chartLayout.setMargin(25);
		
		HeaderControl refreshBtn = new HeaderControl(HeaderControl.REFRESH, new ClickHandler() {
			public void onClick(ClickEvent event) {
				
			}			
		});
		window.setHeaderControls(HeaderControls.HEADER_LABEL, refreshBtn, HeaderControls.CLOSE_BUTTON);
		
		window.setTitle(model.getName());
		window.setIsModal(true);
		window.setShowModalMask(true);
		window.centerInPage();

		window.addChild(chartLayout);
		window.show();
	}	


	private void setPortalMenus(VLayout main) {
		final DynamicForm form = new DynamicForm();  
        form.setAutoWidth();  
        form.setNumCols(1);  
		
        ButtonItem addColumn = new ButtonItem("addAQ", "Add Widget");
        addColumn.setAutoFit(true);  
        addColumn.setStartRow(false);  
        addColumn.setEndRow(false);
        
        form.setItems(addColumn);
        
        HLayout menus = new HLayout();
        menus.setWidth100();
        menus.setBackgroundColor("#B3BEE3");
        
        menus.addMember(form);
        
        main.addMember(menus);
	}	
	
	public Widget asWidget() {
		return panel;
	}

	public void setPresenter(MainLayoutPresenter presenter) {
		this.presenter = presenter;
	}
	
	private GadgetPortlet createPortlet(final GadgetModel model) {
	    GadgetPortlet gadgetPortlet = new GadgetPortlet(model);
	    gadgets.add(model);
		return gadgetPortlet;
	}

}
