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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.overlord.gadgets.server.model.Page;
import org.overlord.gadgets.server.model.User;
import org.overlord.gadgets.server.model.Widget;
import org.overlord.gadgets.server.model.WidgetPreference;

import com.google.inject.Inject;

/**
 * @author: Jeff Yu
 * @date: 16/03/12
 */
public class UserManagerImpl implements UserManager {

    private EntityManager entityManager;

    @Inject
    public UserManagerImpl(EntityManager manager) {
        this.entityManager = manager;
    }

    protected boolean startTxn() {
    	boolean started=false;

        if (!entityManager.getTransaction().isActive()) {
        	try {
        		entityManager.getTransaction().begin();
        		started = true;
        	} catch (Throwable t) {
        		// Ignore for now
        	}
        }

        return (started);
    }

    protected void endTxn(boolean started) {
    	if (started) {
            entityManager.getTransaction().commit();
    	}
    }

    @Override
    public User createUser(User user) {
        boolean startedTxn=startTxn();
        entityManager.persist(user);

        Page page = new Page();
        page.setName("Home");
        page.setColumns(2);
        page.setUser(user);
        entityManager.persist(page);

        user.setCurrentPageId(page.getId());

        endTxn(startedTxn);

        return user;
    }

    @Override
    public User getUserById(long userId) {
        boolean startedTxn=startTxn();
        User user = entityManager.find(User.class, userId);
        endTxn(startedTxn);
        return user;
    }

    @Override
    public void updateUser(User user) {
        boolean startedTxn=startTxn();
        entityManager.merge(user);
        endTxn(startedTxn);
    }

    @Override
    public void removeUser(User user) {
        boolean startedTxn=startTxn();
        entityManager.remove(user);
        endTxn(startedTxn);
    }

    @Override
    public List<User> getAllUser() {
        boolean startedTxn=startTxn();
        Query query = entityManager.createQuery("select user from User user");
        @SuppressWarnings("unchecked")
        List<User> users = query.getResultList();
        endTxn(startedTxn);
        return users;
    }

    @Override
    public User getUser(String username) {
        boolean startedTxn=startTxn();
        Query query = entityManager.createQuery("select user from User user where user.name = :username");
        query.setParameter("username", username);

        @SuppressWarnings("unchecked")
        List<User> users = query.getResultList();
        User user = null;
        if (users.size() > 0) {
            user =  users.get(0);
        }
        endTxn(startedTxn);

        return user;
    }

    @Override
    public List<Page> getPages(long userId) {
        Query query = entityManager.createQuery("select page from Page page where page.user.id = :userId order by page.pageOrder asc");
        query.setParameter("userId", userId);
        boolean startedTxn=startTxn();
        @SuppressWarnings("unchecked")
        List<Page> pages = query.getResultList();
        endTxn(startedTxn);
        return pages;
    }

    @Override
    public Page addPage(Page page, User user) {
        boolean startedTxn=startTxn();
        page.setUser(user);
        entityManager.persist(page);
        endTxn(startedTxn);
        return page;
    }

    @Override
    public Page getPage(long pageId) {
        boolean startedTxn=startTxn();
        Page page = entityManager.find(Page.class, pageId);
        endTxn(startedTxn);
        return page;
    }

    @Override
    public void removePage(long pageId) {
        boolean startedTxn=startTxn();
        Page page = entityManager.find(Page.class, pageId);
        entityManager.remove(page);
        endTxn(startedTxn);
    }

    @Override
    public void removeWidget(long widgetId) {
        boolean startedTxn=startTxn();
        Widget widget = entityManager.find(Widget.class, widgetId);
        widget.getPage().getWidgets().remove(widget);
        entityManager.remove(widget);
        endTxn(startedTxn);
    }

    @Override
    public Widget getWidgetById(long widgetId) {
        boolean startedTxn=startTxn();
        Widget widget = entityManager.find(Widget.class, widgetId);
        endTxn(startedTxn);
        return widget;
    }

	@Override
    public void updateWidgetPreference(long widgetId,
			List<WidgetPreference> prefs) {
		boolean startedTxn=startTxn();
		Query query = entityManager.createQuery("delete from WidgetPreference pref where pref.widget.id = :id");
		query.setParameter("id", widgetId);
		query.executeUpdate();

		Widget widget = entityManager.find(Widget.class, widgetId);
		for (WidgetPreference pref : prefs) {
			pref.setWidget(widget);
		}

		widget.setPrefs(prefs);
        endTxn(startedTxn);
	}

	@Override
    public Map<String, String> getWidgetPreference(long widgetId) {
		Map<String, String> result = new HashMap<String, String>();
		boolean startedTxn=startTxn();
		Widget widget = entityManager.find(Widget.class, widgetId);
		if (widget.getPrefs() != null) {
			for (WidgetPreference pref : widget.getPrefs()) {
				result.put(pref.getName(), pref.getValue());
			}
		}
        endTxn(startedTxn);
		return result;
	}

}
