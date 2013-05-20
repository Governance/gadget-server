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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.overlord.gadgets.server.model.Page;
import org.overlord.gadgets.server.model.User;
import org.overlord.gadgets.server.model.Widget;
import org.overlord.gadgets.server.model.WidgetPreference;
import org.overlord.gadgets.server.service.UserManager;
import org.overlord.gadgets.web.shared.dto.PageModel;
import org.overlord.gadgets.web.shared.dto.Pair;
import org.overlord.gadgets.web.shared.dto.UserModel;
import org.overlord.gadgets.web.shared.dto.UserPreference.UserPreferenceSetting;
import org.overlord.gadgets.web.shared.dto.WidgetModel;

import com.google.gson.Gson;
import com.google.inject.Inject;

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
    @Path("user/currentUser")
    @Produces("application/json")
    public UserModel getCurrentUser(@Context HttpServletRequest request) {
        String username = request.getRemoteUser();
        if (username == null) {
            throw new RuntimeException("No JAAS username found: application not properly configured?!");
        }
        User user = userManager.getUser(username);
        // Lazy create the user
        if (user == null) {
            user = new User();
            user.setName(username);
            user.setDisplayName(username);
            user = userManager.createUser(user);
        }

        UserModel currentUser = new UserModel();
        currentUser.setUserId(user.getId());
        currentUser.setUserName(user.getName());
        currentUser.setCurrentPageId(user.getCurrentPageId());
        currentUser.setDisplayName(user.getDisplayName());
        request.getSession().setAttribute("user", currentUser);
        return currentUser;
    }

    @GET
    @Path("user/{userId}/pages")
    @Produces("application/json")
    public List<PageModel> getPageModels(@PathParam("userId") long userId, @Context HttpServletRequest request) {
        List<Page> pages = userManager.getPages(userId);

        List<PageModel> pageModels = new ArrayList<PageModel>();
        String serverBaseUrl = getServerBaseUrl(request);
        metadataService.setGadgetServerRPCUrl(serverBaseUrl + "/gadget-server/rpc");

        for (Page page : pages) {
            PageModel pageModel = new PageModel();
            pageModel.setName(page.getName());
            pageModel.setOrder(page.getPageOrder());
            pageModel.setColumns(page.getColumns());
            pageModel.setId(page.getId());

            for (Widget widget :page.getWidgets()) {
                WidgetModel widgetModel = metadataService.getGadgetMetadata(widget.getAppUrl().replace("${server}", serverBaseUrl));
                widgetModel.setWidgetId(widget.getId());
                widgetModel.setOrder(widget.getOrder());

                populateWidgetsDefaultValue(widget, widgetModel);

                pageModel.addModel(widgetModel);
            }

            pageModels.add(pageModel);
        }

        return pageModels;
    }

    private String getServerBaseUrl(HttpServletRequest request) {
    	String scheme = request.getScheme();
    	String serverName = request.getServerName();
    	int serverPort = request.getServerPort();
    	StringBuilder sbuilder = new StringBuilder();
    	sbuilder.append(scheme).append("://").append(serverName);
    	if ((serverPort != 80) && (serverPort != 443)) {
    		sbuilder.append(":" + serverPort);
    	}
    	return sbuilder.toString();
    }

	private void populateWidgetsDefaultValue(Widget widget, WidgetModel widgetModel) {
		if (widget.getPrefs() != null && widget.getPrefs().size() > 0) {
			for(UserPreferenceSetting ups : widgetModel.getUserPreference().getData()) {
				for(WidgetPreference wp: widget.getPrefs()) {
					if (ups.getName().equals(wp.getName())) {
						ups.setDefaultValue(wp.getValue());
					}
				}
			}
		}
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

    @GET
    @Path("widget/{widgetId}")
    @Produces("application/json")
    public List<Pair> getWidgetPreferenceValues(@PathParam("widgetId") long widgetId) {
    	Map<String, String> result = userManager.getWidgetPreference(widgetId);
    	List<Pair> pairs = new ArrayList<Pair>();
    	for (String key : result.keySet()) {
    		Pair pair = new Pair(key, result.get(key));
    		pairs.add(pair);
    	}
        return pairs;
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

    @POST
    @Path("user/{userId}/current/{pageId}")
    @Consumes("application/json")
    public Response updateUserCurrentPageId(@PathParam("userId") long userId, @PathParam("pageId") long pageId) {
    	User theUser = userManager.getUserById(userId);
    	theUser.setCurrentPageId(pageId);
    	userManager.updateUser(theUser);
    	return createJsonResponse(pageId);
    }


    private Response createJsonResponse(Object wrapper) {
        Gson gson = GsonFactory.createInstance();
        String json = gson.toJson(wrapper);
        return Response.ok(json).type("application/json").build();
    }

    public static void main(String[] args) throws Exception {
    	System.out.println("${server}/gadgets-server/test".replace("${server}", "http://localhost:8080"));
    }

}
