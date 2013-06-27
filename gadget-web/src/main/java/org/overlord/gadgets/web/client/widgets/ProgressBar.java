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

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 *
 * A Jquery-UI ProgressBar wrapper
 *
 * @author: Jeff Yu
 * @date: 29/02/12
 */
public class ProgressBar extends SimplePanel{

    private int value = 0;

    public ProgressBar() {
        this(HTMLPanel.createUniqueId());
    }

    public ProgressBar(String id) {
        super();
        getElement().setId(id);
    }

    @Override
    public void onAttach() {
        super.onAttach();
        addProgressBarJS(getElement().getId(), value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        setValueJS(getElement().getId(), value);
    }

    /**
     * JSNI methods
     */
    private static native void addProgressBarJS(String id, int barValue) /*-{
        $wnd.$('#' + id).progressbar({
            value:barValue
        });
    }-*/;

    private static native void setValueJS(String id, int barValue) /*-{
        $wnd.$('#' + id).progressbar('destroy');
        $wnd.$('#' + id).progressbar({
            value:barValue
        });
    }-*/;

}
