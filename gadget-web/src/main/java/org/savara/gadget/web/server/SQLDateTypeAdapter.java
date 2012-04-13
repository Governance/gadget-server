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
package org.savara.gadget.web.server;

import com.google.gson.*;

import java.util.Date;
import java.text.*;
import java.lang.reflect.Type;

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
