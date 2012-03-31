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
import org.guvnor.sam.gadget.server.model.User;
import org.guvnor.sam.gadget.server.service.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 20/03/12
 */
@Path("/users")
public class UserController {

    private UserManager userManager;

    public UserController() {
        System.out.println("===> UserController .... <=======");
        Injector injector = Guice.createInjector(new CoreModule());
        userManager = injector.getInstance(UserManager.class);
    }

    @GET
    @Path("all")
    @Produces("application/json")
    public List<User> getAllUsers() {
        List<User> users = userManager.getAllUser();
        for (User user : users) {
            System.out.println("User email is: " + user.getEmail() + "->" + user.getName());
        }
        return users;
    }
    
    @POST
    @Path("user")
    @Produces("application/json")
    @Consumes("application/json")
    public User createUser(@Context HttpServletRequest request,
                               @FormParam("username") String username,
                               @QueryParam("password") String password,
                               @QueryParam("email") String email){
        System.out.println("create user...");
        User user = new User();
        System.out.println(request.getParameterMap().size());
        user.setName(username);
        request.getParameterMap();
        user.setPassword(password);
        user.setEmail(email);
        user.setDisplayName(username);
        
        userManager.createUser(user);
        return user;
    }

    @GET
    @Path("user")
    @Produces("application/json")
    public Response getUser(@Context HttpServletRequest request,
                            @QueryParam("username") String username,
                               @QueryParam("password") String password){
        boolean result = false;
        User user = userManager.getUser(username, password);
        if (user != null) {
            result = true;
        }
        return createJsonResponse(result);
    }
    
    
    private Response createJsonResponse(Object wrapper) {
        Gson gson = GsonFactory.createInstance();
        String json = gson.toJson(wrapper);
        return Response.ok(json).type("application/json").build();
    }

}
