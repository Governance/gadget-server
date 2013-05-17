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
package org.overlord.gadgets.server.service;

import java.util.List;
import java.util.Map;

import org.overlord.gadgets.server.model.Page;
import org.overlord.gadgets.server.model.User;
import org.overlord.gadgets.server.model.Widget;
import org.overlord.gadgets.server.model.WidgetPreference;

/**
 * @author: Jeff Yu
 * @date: 16/03/12
 */
public interface UserManager {
    
    User createUser(User user);
    
    User getUserById(long userId);
    
    void updateUser(User user);
    
    void removeUser(User user);

    List<User> getAllUser();

    User getUser(String username, String password);
    
    boolean isUsernameExist(String username);

    List<Page> getPages(long userId);
    
    Page addPage(Page page, User user);
    
    Page getPage(long pageId);

    void removePage(long pageId);

    void removeWidget(long widgetId);
    
    void updateWidgetPreference(long widgetId, List<WidgetPreference> prefs);
    
    Map<String, String> getWidgetPreference(long widgetId);

    Widget getWidgetById(long widgetId);
}
