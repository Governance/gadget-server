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
