/**
 * 
 */
package org.savara.gserver.web.client.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;
import org.savara.gserver.web.client.BootstrapContext;
import org.savara.gserver.web.client.NameTokens;

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
	}
	
}
