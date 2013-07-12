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
package org.overlord.gadgets.server;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.overlord.gadgets.server.model.Gadget;
import org.overlord.gadgets.server.model.Page;
import org.overlord.gadgets.server.model.User;
import org.overlord.gadgets.server.model.Widget;
import org.overlord.gadgets.server.model.WidgetPreference;
import org.overlord.gadgets.server.service.GadgetService;
import org.overlord.gadgets.server.service.UserManager;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author: Jeff Yu
 * @date: 15/03/12
 */
public class UserManagerTest {

    private static UserManager userManager;

    private static GadgetService gadgetService;

    private static User user;

    private static Page page;

    static {
        user = new User();
        user.setName("jeff");
        user.setDisplayName("Jeff Yu");
        user.setEmail("jeff@test.com");

        page = new Page();
        page.setName("Home");
        page.setColumns(2);
        page.setUser(user);

    }

    @BeforeClass
    public static void setUp() throws Exception{
        Injector injector = Guice.createInjector(new CoreModule());
        try {
            userManager = injector.getInstance(UserManager.class);
            gadgetService = injector.getInstance(GadgetService.class);
    
            userManager.createUser(user);
            userManager.addPage(page, user);
        } catch (Throwable t) {
            t.printStackTrace();
            Assert.fail("Failed to initialize: "+t);
        }
    }

    @Test
    public void testGetUserById() throws Exception {
        User theUser = userManager.getUserById(user.getId());
        Assert.assertTrue(theUser.getId() == user.getId());
    }

    @Test
    public void testGetUser() throws Exception {
        User theUser = userManager.getUser("jeff");
        Assert.assertTrue(theUser.getId() == user.getId());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = userManager.getAllUser();
        Assert.assertTrue(users.size() > 0);
    }

    @Test
    public void testGetAllGadgets() throws Exception {
        List<Gadget> gadgets = gadgetService.getAllGadgets(0, 10);
        Assert.assertTrue(gadgets.size() == 5);
    }

    @Test
    public void testGetGadgetById() throws Exception {
        Gadget gadget = gadgetService.getGadgetById(1);
        Assert.assertTrue(gadget.getId() == 1);
    }

    @Test
    public void testGetGadgetsCount() throws Exception {
        int result = gadgetService.getAllGadgetsNum();
        Assert.assertTrue(result == 5);
    }

    @Test
    public void testGetUserPage() throws Exception {
        List<Page> pages = userManager.getPages(user.getId());
        Assert.assertTrue(pages.size() > 0);
    }

    @Test
    public void testAddGadgetToPageAndRemoveWidget() throws Exception {
        Gadget gadget = gadgetService.getGadgetById(1);
        Page thePage = userManager.getPage(page.getId());
        gadgetService.addGadgetToPage(gadget, thePage);
        List<Widget> widgets = userManager.getPage(page.getId()).getWidgets();
        Assert.assertTrue(widgets.size() == 1);

        Widget theWidget = widgets.get(0);
        userManager.removeWidget(theWidget.getId());

        Widget w = userManager.getWidgetById(theWidget.getId());
        Assert.assertNull(w);
        widgets = userManager.getPage(page.getId()).getWidgets();
        Assert.assertEquals(widgets.size(),0);
    }

    @Test
    public void testUpdateWidgetPref() throws Exception {
        Gadget gadget = gadgetService.getGadgetById(1);
        Page thePage = userManager.getPage(page.getId());
        gadgetService.addGadgetToPage(gadget, thePage);
        List<Widget> widgets = userManager.getPage(page.getId()).getWidgets();
        Assert.assertTrue(widgets.size() == 1);

        List<WidgetPreference> wps = new ArrayList<WidgetPreference>();
        WidgetPreference wp = new WidgetPreference();
        wp.setName("testName");
        wp.setValue("testValue");
        wps.add(wp);

        WidgetPreference wp2 = new WidgetPreference();
        wp2.setName("testName2");
        wp2.setValue("testValue2");
        wps.add(wp2);

        userManager.updateWidgetPreference(widgets.get(0).getId(), wps);

        Widget theWidget = widgets.get(0);
        Widget w = userManager.getWidgetById(theWidget.getId());
        Assert.assertTrue(w.getPrefs().size() == 2);
        Assert.assertEquals("testName", w.getPrefs().get(0).getName());
        userManager.removeWidget(theWidget.getId());
    }

}
