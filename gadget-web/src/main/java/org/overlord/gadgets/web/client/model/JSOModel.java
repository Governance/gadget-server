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
package org.overlord.gadgets.web.client.model;

/**
 * @author: Jeff Yu
 * @date: 11/04/12
 */

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;

/**
 * Java overlay of a JavaScriptObject.
 * Borrowed from Matt Raible:
 * http://raibledesigns.com/rd/entry/json_parsing_with_javascript_overlay
 */
public abstract class JSOModel extends JavaScriptObject {

    // Overlay types always have protected, zero-arg constructors
    protected JSOModel() {
    }

    /**
     * Create an empty instance.
     *
     * @return new Object
     */
    public static native JSOModel create() /*-{
        return new Object();
    }-*/;

    /**
     * Convert a JSON encoded string into a JSOModel instance.
     * <p/>
     * Expects a JSON string structured like '{"foo":"bar","number":123}'
     *
     * @return a populated JSOModel object
     */
    public static native JSOModel fromJson(String jsonString) /*-{
        return eval('(' + jsonString + ')');
    }-*/;

    /**
     * Convert a JSON encoded string into an array of JSOModel instance.
     * <p/>
     * Expects a JSON string structured like '[{"foo":"bar","number":123}, {...}]'
     *
     * @return a populated JsArray
     */
    public static native JsArray<JSOModel> arrayFromJson(String jsonString) /*-{
        return eval('(' + jsonString + ')');
    }-*/;

    public final native boolean hasKey(String key) /*-{
        return this[key] != undefined;
    }-*/;

    public final native JsArrayString keys() /*-{
        var a = new Array();
        for (var p in this) { a.push(p); }
        return a;
    }-*/;


    public final native String get(String key) /*-{
        return "" + this[key];
    }-*/;

    public final native String get(String key, String defaultValue) /*-{
        return this[key] ? ("" + this[key]) : defaultValue;
    }-*/;

    public final native void set(String key, String value) /*-{
        this[key] = value;
    }-*/;

    public final int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public final boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public final native JSOModel getObject(String key) /*-{
        return this[key];
    }-*/;

    public final native JsArray<JSOModel> getArray(String key) /*-{
        return this[key] ? this[key] : new Array();
    }-*/;

    public final long getLong(String key)
    {
        return Long.valueOf(get(key));
    }

    private final boolean isNull(String val)
    {
        return (val != null && "null".equals(val) || "undefined".equals(val));
    }
}
