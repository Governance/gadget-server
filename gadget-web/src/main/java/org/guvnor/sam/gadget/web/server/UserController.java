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
package org.guvnor.sam.gadget.web.server;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.guvnor.sam.gadget.server.CoreModule;
import org.guvnor.sam.gadget.server.model.ApplicationData;
import org.guvnor.sam.gadget.server.model.User;
import org.guvnor.sam.gadget.server.service.ApplicationDataManager;
import org.guvnor.sam.gadget.server.service.UserManager;
import org.guvnor.sam.gadget.web.shared.dto.GadgetModel;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.Enumeration;
import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 20/03/12
 */
@Path("/users")
public class UserController {

    private UserManager userManager;
    private ApplicationDataManager applicationDataManager;

    public UserController() {
        Injector injector = Guice.createInjector(new CoreModule());
        userManager = injector.getInstance(UserManager.class);
        applicationDataManager = injector.getInstance(ApplicationDataManager.class);
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
    @Path("user/{userId}/gadgets")
    @Produces("application/json")
    public List<GadgetModel> getGadgetModels(@PathParam("userId") long userId) {
        List<ApplicationData> gadgets = applicationDataManager.getApplicationData(userId);
        return null;
    }
    
    
    private Response createJsonResponse(Object wrapper) {
        Gson gson = GsonFactory.createInstance();
        String json = gson.toJson(wrapper);
        return Response.ok(json).type("application/json").build();
    }

}
