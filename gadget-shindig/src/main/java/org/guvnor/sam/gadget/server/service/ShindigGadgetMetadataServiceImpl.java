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
package org.guvnor.sam.gadget.server.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author: Jeff Yu
 * @date: 9/02/12
 */
public class ShindigGadgetMetadataServiceImpl implements GadgetMetadataService{

    public String getGadgetMetadata(String gadgetUrl) {

        JSONArray rpcArray = new JSONArray();
        try {
            JSONObject fetchMetadataRpcOperation = new JSONObject()
                    .put("method", "gadgets.metadata")
                    .put("id", "gadgets.metadata")
                    .put("params", new JSONObject()
                            .put("container", "default")

                            .append("ids", gadgetUrl)

                            .append("fields", "iframeUrl")
                            .append("fields", "modulePrefs.*")
                            .append("fields", "needsTokenRefresh")
                            .append("fields", "userPrefs.*")
                            .append("fields", "views.preferredHeight")
                            .append("fields", "views.preferredWidth")
                            .append("fields", "expireTimeMs")
                            .append("fields", "responseTimeMs")

                            .put("userId", "@viewer")
                            .put("groupId", "@self")
                    );

            rpcArray.put(fetchMetadataRpcOperation);
        } catch (JSONException e) {
            throw new IllegalArgumentException("Error occurred while generating data for shindig metadata call", e);
        }



        return null;
    }
}
