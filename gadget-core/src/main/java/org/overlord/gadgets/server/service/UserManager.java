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

    User getUser(String username);

    List<Page> getPages(long userId);

    Page addPage(Page page, User user);

    Page getPage(long pageId);

    void removePage(long pageId);

    void removeWidget(long widgetId);

    void updateWidgetPreference(long widgetId, List<WidgetPreference> prefs);

    Map<String, String> getWidgetPreference(long widgetId);

    Widget getWidgetById(long widgetId);
}
