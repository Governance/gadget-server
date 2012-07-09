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
package org.overlord.gadgets.web.server;

import com.google.gson.Gson;
import com.google.inject.Inject;

import org.overlord.gadgets.server.model.Page;
import org.overlord.gadgets.server.model.User;
import org.overlord.gadgets.server.model.Widget;
import org.overlord.gadgets.server.model.WidgetPreference;
import org.overlord.gadgets.server.service.GadgetService;
import org.overlord.gadgets.server.service.UserManager;
import org.overlord.gadgets.web.shared.dto.PageModel;
import org.overlord.gadgets.web.shared.dto.UserModel;
import org.overlord.gadgets.web.shared.dto.WidgetModel;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: Jeff Yu
 * @date: 20/03/12
 */
@Path("/users")
public class UserController {

    @Inject
    private UserManager userManager;

    @Inject
    private GadgetMetadataService metadataService;

    @Inject
    public UserController() {

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
    public UserModel createUser(User user, @Context HttpServletRequest request){
        userManager.createUser(user);
        return getUser(user, request);
    }

    @POST
    @Path("authentication")
    @Consumes("application/json")
    @Produces("application/json")
    public UserModel getUser(User user, @Context HttpServletRequest request){
        User theUser = userManager.getUser(user.getName(), user.getPassword());
        UserModel userModel = new UserModel();
        if (theUser != null) {
            userModel.setUserId(theUser.getId());
            userModel.setUserName(theUser.getName());
            List<Page> pages = userManager.getPages(theUser.getId());
            userModel.setCurrentPageId(pages.get(0).getId());
            userModel.setDisplayName(theUser.getDisplayName());
            request.getSession().setAttribute("user", userModel);
        }
        return userModel;
    }

    @POST
    @Path("user/invalidate")
    @Consumes("application/json")
    @Produces("application/json")
    public Response invalidSession(@Context HttpServletRequest request) {
        request.getSession().invalidate();
        return Response.ok().build();
    }

    @GET
    @Path("user/{username}/check")
    public Response checkUsername(@PathParam("username") String username) {
        boolean result = userManager.isUsernameExist(username);
        return createJsonResponse(result);
    }
    
    @GET
    @Path("user/{userId}/pages")
    @Produces("application/json")
    public List<PageModel> getPageModels(@PathParam("userId") long userId) {
        List<Page> pages = userManager.getPages(userId);
        
        List<PageModel> pageModels = new ArrayList<PageModel>();
        
        for (Page page : pages) {
            PageModel pageModel = new PageModel();
            pageModel.setName(page.getName());
            pageModel.setOrder(page.getPageOrder());
            pageModel.setColumns(page.getColumns());
            pageModel.setId(page.getId());

            for (Widget widget :page.getWidgets()) {
                WidgetModel widgetModel = metadataService.getGadgetMetadata(widget.getAppUrl());
                widgetModel.setWidgetId(widget.getId());
                widgetModel.setOrder(widget.getOrder());
                pageModel.addModel(widgetModel);
            }

            pageModels.add(pageModel);
        }
        
        return pageModels;
    }
    
    @POST
    @Path("widget/{widgetId}/remove")
    @Produces("application/json")
    public Response removeWidget(@PathParam("widgetId") long widgetId) {
        userManager.removeWidget(widgetId);
        return Response.ok().build();
    }
    
    @POST
    @Path("user/page/{pageId}/remove")
    @Produces("application/json")
    @Consumes("application/json")
    public Response removePage(@PathParam("pageId") long pageId) {
        userManager.removePage(pageId);
        return Response.ok().build();
    }
    
    @POST
    @Path("widget/{widgetId}/update")
    @Consumes("application/json")
    public Response updateWidgetPreference(@PathParam("widgetId") long widgetId, Map<String, String> prefs) {
    	
    	List<WidgetPreference> wps = new ArrayList<WidgetPreference>();
    	for (String name : prefs.keySet()) {
    		String value = prefs.get(name);
    		WidgetPreference wp = new WidgetPreference();
    		wp.setName(name);
    		wp.setValue(value);
    		wps.add(wp);
    	}
    	userManager.updateWidgetPreference(widgetId, wps);
        return Response.ok().build();
    }

    @POST
    @Path("user/{userId}/page")
    @Consumes("application/json")
    public Response createPage(@PathParam("userId") long userId, PageModel pageModel) {
        Page page = new Page();
        page.setName(pageModel.getName());
        page.setColumns(pageModel.getColumns());
        
        User theUser = userManager.getUserById(userId);

        Page thePage = userManager.addPage(page, theUser);

        return createJsonResponse(thePage.getId());
    }
    
    
    private Response createJsonResponse(Object wrapper) {
        Gson gson = GsonFactory.createInstance();
        String json = gson.toJson(wrapper);
        return Response.ok(json).type("application/json").build();
    }

}
