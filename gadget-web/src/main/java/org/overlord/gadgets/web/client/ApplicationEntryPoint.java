package org.overlord.gadgets.web.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ApplicationEntryPoint implements EntryPoint {

    public static final ApplicationUI MODULES = GWT.create(ApplicationUI.class);

    @Override
    public void onModuleLoad() {

        Log.setUncaughtExceptionHandler();

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand(){

            @Override
            public void execute() {
                actualModuleLoad();
            }
        });
    }


    public void actualModuleLoad() {
        RootLayoutPanel.get().getElement().setId("root-layout");
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                RootLayoutPanel.get().getElement().getStyle().setTop(80, Unit.PX);
                RootLayoutPanel.get().getElement().getStyle().setBottom(5, Unit.PX);
            }
          });

        DelayedBindRegistry.bind(MODULES);
        MODULES.getPlaceManager().revealCurrentPlace();
    }
}
