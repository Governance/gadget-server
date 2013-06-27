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
package org.overlord.gadgets.web.client.util;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;

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
