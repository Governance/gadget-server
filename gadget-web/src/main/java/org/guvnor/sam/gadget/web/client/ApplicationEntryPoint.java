package org.guvnor.sam.gadget.web.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ApplicationEntryPoint implements EntryPoint {

    public static final ApplicationUI MODULES = GWT.create(ApplicationUI.class);

    public void onModuleLoad() {

        Log.setUncaughtExceptionHandler();

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand(){

            public void execute() {
                actualModuleLoad();
            }
        });
    }


    public void actualModuleLoad() {
        DelayedBindRegistry.bind(MODULES);
        MODULES.getPlaceManager().revealCurrentPlace();

        Log.debug("This is a 'DEBUG' test message");
        Log.info("This is a 'INFO' test message");
        Log.warn("This is a 'WARN' test message");
        Log.error("This is a 'ERROR' test message");
        Log.fatal("This is a 'FATAL' test message");

    }
}
