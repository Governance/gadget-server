/**
 * 
 */
package org.savara.gserver.web.client.presenter;

import java.util.List;
import java.util.Map;

import org.savara.gserver.web.client.BootstrapContext;
import org.savara.gserver.web.client.NameTokens;
import org.savara.gserver.web.client.util.DefaultCallback;
import org.savara.gserver.web.shared.dto.AQChartModel;
import org.savara.gserver.web.shared.dto.Conversation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 
 * @author Jeff Yu
 * @date Nov 03, 2011
 */
public class MainLayoutPresenter extends Presenter<MainLayoutPresenter.MainLayoutView, 
												   MainLayoutPresenter.MainLayoutProxy> {
	
	private BootstrapContext bootstrap;

	private PlaceManager placeManager;
		
	public interface MainLayoutView extends View {
				
		public void setPresenter(MainLayoutPresenter presenter);
				
		public void refreshConversationChart(AQChartModel model, List<Conversation> conversations, VLayout panel, int width, int height);
		
		public void refreshConversationChart(AQChartModel model, List<Conversation> conversations);
				
		public void refreshChart(AQChartModel model, Map data, VLayout panel, int width, int height);
		
		public void refreshChart(AQChartModel model, Map data);
		
		public void setActiveQueries(List<String> activeQueries);
		
		public void refreshAllCharts();
		
	}
		
	@ProxyCodeSplit
	@NameToken(NameTokens.MAIN_VIEW)
	@NoGatekeeper
	public interface MainLayoutProxy extends ProxyPlace<MainLayoutPresenter>{}
	
    @Inject
    public MainLayoutPresenter(
            EventBus eventBus,
            MainLayoutView view,
            MainLayoutProxy proxy, BootstrapContext bootstrap, PlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.bootstrap = bootstrap;
        this.placeManager = placeManager;
    }
	
    
    
	@Override
	protected void revealInParent() {
		RevealRootLayoutContentEvent.fire(this, this);
	}
	
	@Override
	public void onBind() {
		super.onBind();
		getView().setPresenter(this);
	}
	
	@Override
	public void onReveal() {
		super.onReveal();
		Timer timer = new Timer() {

			@Override
			public void run() {
				getView().refreshAllCharts();
			}			
		};
		//TODO: hack for now, delay for 1sec.
		timer.schedule(1000);
	}
	
	public void refreshChartData(final AQChartModel model) {

	}
	
	public void refreshChartData(final AQChartModel model, final VLayout panel, final int width, final int height) {

	}
	
	public void refreshTableChart(final AQChartModel model, final VLayout panel, final int width, final int height) {

	}
	
	public void refreshTableChart(final AQChartModel model) {

	}
	
	public void refreshActiveQueries() {

	}
	
}