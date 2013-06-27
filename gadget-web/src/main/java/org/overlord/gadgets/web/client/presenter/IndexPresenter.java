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
package org.overlord.gadgets.web.client.presenter;

import java.util.List;

import org.overlord.gadgets.web.client.NameTokens;
import org.overlord.gadgets.web.client.URLBuilder;
import org.overlord.gadgets.web.client.auth.CurrentUser;
import org.overlord.gadgets.web.client.model.JSOParser;
import org.overlord.gadgets.web.client.util.RestfulInvoker;
import org.overlord.gadgets.web.shared.dto.PageModel;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

/**
 * @author: Jeff Yu
 * @date: 20/02/12
 */
public class IndexPresenter extends Presenter<IndexPresenter.IndexView,
        IndexPresenter.IndexProxy>{

    private CurrentUser currentUser;

    @Inject
    public IndexPresenter(EventBus bus, IndexView view, IndexProxy proxy, CurrentUser user){
        super(bus, view, proxy);
        currentUser = user;
    }

    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(this, this);
        RootLayoutPanel.get().getElement().getStyle().setTop(80, Unit.PX);
        RootLayoutPanel.get().getElement().getStyle().setBottom(5, Unit.PX);
    }

    public interface IndexView extends View {
        public void setPresenter(IndexPresenter presenter);
        public void initializePages(List<PageModel> models);
    }

    public void getPages() {
        RestfulInvoker.invoke(RequestBuilder.GET, URLBuilder.getPagesURL(currentUser.getUserId()), null,
                new RestfulInvoker.Response(){

                    @Override
                    public void onResponseReceived(Request request, Response response) {
                        List<PageModel> pageModels = JSOParser.getPageModels(response.getText());
                        getView().initializePages(pageModels);
                    }
                });
    }


    @ProxyCodeSplit
    @NameToken(NameTokens.INDEX_VIEW)
    public interface IndexProxy extends ProxyPlace<IndexPresenter> {}


    @Override
    public void onBind() {
        super.onBind();
        getView().setPresenter(this);
    }

    @Override
    public void onReveal() {
        super.onReveal();
        getPages();
    }

}
