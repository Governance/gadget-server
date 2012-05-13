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

import org.savara.gadget.server.model.Gadget;
import org.savara.gadget.server.model.Page;
import org.savara.gadget.server.service.GadgetService;
import org.savara.gadget.server.service.UserManager;
import org.savara.gadget.web.shared.dto.PageResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

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
    @Path("page/${pageId}/gadget/${gadgetId}")
    @Produces("application/json")
    public Response addGadgetToPage(@PathParam("pageId") long pageId, @PathParam("gadgetId") long gadgetId) {
        Gadget gadget = gadgetService.getGadgetById(gadgetId);
        Page page = userManager.getPage(pageId);
        gadgetService.addGadgetToPage(gadget, page);
        return Response.ok().build();
    }

}
