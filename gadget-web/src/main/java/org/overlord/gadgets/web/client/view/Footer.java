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
package org.overlord.gadgets.web.client.view;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author: Jeff Yu
 * @date: 28/02/12
 */
public class Footer {

    @Inject
    public Footer(EventBus bus) {

    }

    public Widget asWidget() {

        LayoutPanel layout = new LayoutPanel();
        layout.setStyleName("footer-panel");

        HTML settings = new HTML("Messages");
        settings.addStyleName("footer-link");
        settings.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

            }
        });

        layout.add(settings);

        HTML version = new HTML("Version: 1.0.0.M5");
        version.getElement().setAttribute("style", "color:#ffffff;font-size:10px; align:left");
        layout.add(version);

        layout.setWidgetLeftWidth(version, 20, Style.Unit.PX, 200, Style.Unit.PX);
        layout.setWidgetTopHeight(version, 3, Style.Unit.PX, 16, Style.Unit.PX);

        layout.setWidgetRightWidth(settings, 5, Style.Unit.PX, 60, Style.Unit.PX);
        layout.setWidgetTopHeight(settings, 3, Style.Unit.PX, 28, Style.Unit.PX);

        return layout;
    }
}

