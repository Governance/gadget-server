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
package org.overlord.gadgets.web.server;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * A default type adapter for a {@link java.util.Date} object.<br>
 * Create a GSON instance that can serialize/deserialize "java.util.Date" objects:
 * <pre>
 * Gson gson = new GsonBuilder()
 * .registerTypeAdapter(new DateTypeAdapter())
 * .create();
 * </pre>
 *
 * @author Joel Leitch
 */
public class SQLDateTypeAdapter implements JsonSerializer<java.sql.Timestamp>, JsonDeserializer<Date>
{
    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public JsonElement serialize(java.sql.Timestamp src, Type typeOfSrc, JsonSerializationContext context)
    {
        String dateFormatAsString = format.format(src);
        return new JsonPrimitive(dateFormatAsString);
    }

    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException
    {
        if (!(json instanceof JsonPrimitive)) {
            throw new JsonParseException("The date should be a string value");
        }

        try
        {
            return format.parse(json.getAsString());
        }
        catch (ParseException e)
        {
            throw new JsonParseException(e);
        }

    }
}
