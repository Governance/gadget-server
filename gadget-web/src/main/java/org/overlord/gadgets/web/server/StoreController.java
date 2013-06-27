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

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.overlord.gadgets.server.model.Gadget;
import org.overlord.gadgets.server.model.Page;
import org.overlord.gadgets.server.service.GadgetService;
import org.overlord.gadgets.server.service.UserManager;
import org.overlord.gadgets.web.shared.dto.PageResponse;

import com.google.inject.Inject;

/**
 * @author: Jeff Yu
 * @date: 30/04/12
 */
@Path("/stores")
public class StoreController {

    @Inject
    GadgetService gadgetService;

    @Inject
    UserManager userManager;

    @Inject
    public StoreController() {

    }

    @GET
    @Path("all/{offset}/{pageSize}")
    @Produces("application/json")
    public PageResponse<Gadget> getGadgets(@PathParam("offset") int offset,
                                           @PathParam("pageSize") int pageSize) {
        List<Gadget> gadgets = gadgetService.getAllGadgets(offset, pageSize);
        int number = gadgetService.getAllGadgetsNum();
        PageResponse<Gadget> result = new PageResponse<Gadget>(gadgets, number);
        return result;
    }

    @POST
    @Path("page/{pageId}/gadget/{gadgetId}")
    @Produces("application/json")
    public Response addGadgetToPage(@PathParam("pageId") long pageId, @PathParam("gadgetId") long gadgetId) {
        Gadget gadget = gadgetService.getGadgetById(gadgetId);
        Page page = userManager.getPage(pageId);
        gadgetService.addGadgetToPage(gadget, page);
        return Response.ok().build();
    }

}
