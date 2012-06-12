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
package org.overlord.gadgets.web.client.util;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.http.client.*;

/**
 * @author: Jeff Yu
 * @date: 31/03/12
 */
public class RestfulInvoker {
    
    public static abstract class Response implements RequestCallback {

        //Global error handler
        public void onError(Request request, Throwable throwable) {
            Log.error(throwable.toString());
        }
    }
    
    public static void invoke(RequestBuilder.Method method, String url,
                              String data, Response callback) {
        RequestBuilder builder = new RequestBuilder(method, url);
        builder.setHeader("content-type", "application/json");
        try {
            builder.sendRequest(data, callback);
        } catch (RequestException e) {
            Log.error("Error on invoking the service at [" + url + "], detailed info: " + e);
        }
    }
    
}
