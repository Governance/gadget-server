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

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.savara.gadget.server.CoreModule;
import org.savara.gadget.server.model.Page;
import org.savara.gadget.server.model.User;
import org.savara.gadget.server.model.Widget;
import org.savara.gadget.server.service.ApplicationDataManager;
import org.savara.gadget.server.service.PageManager;
import org.savara.gadget.server.service.UserManager;
import org.savara.gadget.web.shared.dto.GadgetModel;
import org.savara.gadget.web.shared.dto.PageModel;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 20/03/12
 */
@Path("/users")
public class UserController {

    private UserManager userManager;
    private ApplicationDataManager applicationDataManager;
    private PageManager pageManager;
    private GadgetMetadataService metadataService;

    public UserController() {
        Injector injector = Guice.createInjector(new CoreModule());
        userManager = injector.getInstance(UserManager.class);
        applicationDataManager = injector.getInstance(ApplicationDataManager.class);
        pageManager = injector.getInstance(PageManager.class);
        metadataService = new ShindigGadgetMetadataService();
    }

    @GET
    @Path("all")
    @Produces("application/json")
    public List<User> getAllUsers() {
        List<User> users = userManager.getAllUser();
        return users;
    }
    
    @POST
    @Path("user")
    @Produces("application/json")
    @Consumes("application/json")
    public User createUser(User user){
        userManager.createUser(user);
        return user;
    }

    @POST
    @Path("authentication")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getUser(User user){
        boolean result = false;
        User theUser = userManager.getUser(user.getName(), user.getPassword());
        if (theUser != null) {
            result = true;
        }
        return createJsonResponse(result);
    }
    
    @GET
    @Path("user/{userId}/pages")
    @Produces("application/json")
    public List<PageModel> getPageModels(@PathParam("userId") long userId) {
        List<Page> pages = pageManager.getPages(userId);
        
        List<PageModel> pageModels = new ArrayList<PageModel>();
        
        for (Page page : pages) {
            PageModel pageModel = new PageModel();
            pageModel.setName(page.getName());
            pageModel.setOrder(page.getPageOrder());
            pageModel.setColumns(page.getColumns());

            for (Widget widget :page.getWidgets()) {
                GadgetModel gadgetModel = metadataService.getGadgetMetadata(widget.getAppUrl());
                gadgetModel.setOrder(widget.getOrder());
                pageModel.addModel(gadgetModel);
            }

            pageModels.add(pageModel);
        }
        
        return pageModels;
    }
    
    
    private Response createJsonResponse(Object wrapper) {
        Gson gson = GsonFactory.createInstance();
        String json = gson.toJson(wrapper);
        return Response.ok(json).type("application/json").build();
    }

}
