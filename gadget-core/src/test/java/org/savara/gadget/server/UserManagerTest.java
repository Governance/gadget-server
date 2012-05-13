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
package org.savara.gadget.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.savara.gadget.server.model.Gadget;
import org.savara.gadget.server.model.Page;
import org.savara.gadget.server.model.User;
import org.savara.gadget.server.service.GadgetService;
import org.savara.gadget.server.service.UserManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 15/03/12
 */
public class UserManagerTest {

    private static UserManager userManager;

    private static GadgetService gadgetService;
    
    private static User user;
    
    static {
        user = new User();
        user.setName("jeff");
        user.setDisplayName("Jeff Yu");
        user.setEmail("jeff@test.com");
        user.setPassword("passwd");
    }
    
    @BeforeClass
    public static void setUp() throws Exception{
        Injector injector = Guice.createInjector(new CoreModule());
        userManager = injector.getInstance(UserManager.class);
        gadgetService = injector.getInstance(GadgetService.class);
        
        userManager.createUser(user);
    }

    @Test
    public void testGetUserById() throws Exception {
        User theUser = userManager.getUserById(user.getId());        
        Assert.assertTrue(theUser.getId() == user.getId());
    }
    
    @Test
    public void testGetUser() throws Exception {
        User theUser = userManager.getUser("jeff", "passwd");
        Assert.assertTrue(theUser.getId() == user.getId());
    }
    
    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = userManager.getAllUser();
        Assert.assertTrue(users.size() > 0);
    }
    
    @Test
    public void testIsNameExist() throws Exception {
        boolean result = userManager.isUsernameExist("jeff");
        Assert.assertTrue(result);
        result = userManager.isUsernameExist("not-exist-jeff");
        Assert.assertFalse(result);
    }
    
    @Test
    public void testGetAllGadgets() throws Exception {
        List<Gadget> gadgets = gadgetService.getAllGadgets(0, 10);
        Assert.assertTrue(gadgets.size() == 3);
    }
    
    @Test
    public void testGetGadgetById() throws Exception {
        Gadget gadget = gadgetService.getGadgetById(1);
        Assert.assertTrue(gadget.getId() == 1);
    }

    @Test
    public void testGetGadgetsCount() throws Exception {
        int result = gadgetService.getAllGadgetsNum();
        Assert.assertTrue(result == 3);
    }

    @Test
    public void testGetUserPage() throws Exception {
        List<Page> pages = userManager.getPages(user.getId());
        Assert.assertTrue(pages.size() > 0);
    }

}
