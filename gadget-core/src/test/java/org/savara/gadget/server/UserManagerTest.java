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
import org.savara.gadget.server.model.User;
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
    
    @BeforeClass
    public static void setUp() throws Exception{
        Injector injector = Guice.createInjector(new CoreModule());
        userManager = injector.getInstance(UserManager.class);

    }

    @Test
    public void testPersonService() throws Exception {
        User user = new User();
        user.setName("jeff");
        user.setDisplayName("Jeff Yu");
        user.setEmail("jeff@test.com");
        user.setPassword("passwd");
        
        userManager.createUser(user);

        Assert.assertTrue(user.getId() > 0);
    }
    
    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = userManager.getAllUser();
        Assert.assertTrue(users.size() > 0);
        for (User theUser : users) {
            System.out.println("User email2 is: " + theUser.getEmail() + "->" + theUser.getName());
        }
    }



}
