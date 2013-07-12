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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

    private EntityManagerFactory entityManagerFactory;

    @Inject
    public UserManagerImpl(EntityManagerFactory manager) {
        this.entityManagerFactory = manager;
    }

    protected boolean startTxn(EntityManager entityManager) {
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

    protected void endTxn(boolean started, EntityManager entityManager) {
    	if (started) {
            entityManager.getTransaction().commit();
    	}
    }

    @Override
    public User createUser(User user) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        
        try {
            boolean startedTxn=startTxn(entityManager);
            entityManager.persist(user);
    
            Page page = new Page();
            page.setName("Home");
            page.setColumns(2);
            page.setUser(user);
            entityManager.persist(page);
    
            user.setCurrentPageId(page.getId());
    
            endTxn(startedTxn, entityManager);
        } finally {
            entityManager.close();
        }

        return user;
    }

    @Override
    public User getUserById(long userId) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        
        User user=null;
        
        try {
            boolean startedTxn=startTxn(entityManager);
            user = entityManager.find(User.class, userId);
            endTxn(startedTxn, entityManager);
        } finally {
            entityManager.close();
        }
        
        return user;
    }

    @Override
    public void updateUser(User user) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        
        try {
            boolean startedTxn=startTxn(entityManager);
            entityManager.merge(user);
            endTxn(startedTxn, entityManager);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void removeUser(User user) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        
        try {
            boolean startedTxn=startTxn(entityManager);
            entityManager.remove(user);
            endTxn(startedTxn, entityManager);
        } finally {
            entityManager.close();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllUser() {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        
        List<User> users=null;
        
        try {
            boolean startedTxn=startTxn(entityManager);
            Query query = entityManager.createQuery("select user from User user");
            users = query.getResultList();
            endTxn(startedTxn, entityManager);
        } finally {
            entityManager.close();
        }

        return users;
    }

    @Override
    public User getUser(String username) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        
        User user = null;

        try {
            boolean startedTxn=startTxn(entityManager);
            Query query = entityManager.createQuery("select user from User user where user.name = :username");
            query.setParameter("username", username);
    
            @SuppressWarnings("unchecked")
            List<User> users = query.getResultList();
            if (users.size() > 0) {
                user =  users.get(0);
                
                // Eagarly fetch contains objects
                for (Page page : user.getPages()) {
                    fetch(page);
                }
            }
            endTxn(startedTxn, entityManager);
    
        } finally {
            entityManager.close();
        }
        
        return user;
    }
    
    protected void fetch(Page page) {
        if (page.getWidgets().size() > 0) {
            for (Widget w : page.getWidgets()) {
                fetch(w);
            }
        }
    }
    
    protected void fetch(Widget widget) {
        widget.getPrefs().size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Page> getPages(long userId) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        
        List<Page> pages=null;
        
        try {
           Query query = entityManager.createQuery("select page from Page page where page.user.id = :userId order by page.pageOrder asc");
            query.setParameter("userId", userId);
            boolean startedTxn=startTxn(entityManager);
            pages = query.getResultList();
            
            // Force eager fetch of pages, widgets and prefs
            if (pages != null) {
                for (Page page : pages) {
                    fetch(page);
                }
            }

            endTxn(startedTxn, entityManager);
        } finally {
            entityManager.close();
        }

        return pages;
    }

    @Override
    public Page addPage(Page page, User user) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        
        try {
            boolean startedTxn=startTxn(entityManager);
            page.setUser(user);
            entityManager.persist(page);
            endTxn(startedTxn, entityManager);
        } finally {
            entityManager.close();
        }

        return page;
    }

    @Override
    public Page getPage(long pageId) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        
        Page page = null;
        
        try {
            boolean startedTxn=startTxn(entityManager);
            page = entityManager.find(Page.class, pageId);
            
            // Force eager fetch of widgets and prefs
            if (page != null) {
                fetch(page);
            }
            
            endTxn(startedTxn, entityManager);
        } finally {
            entityManager.close();
        }
        
        return page;
    }

    @Override
    public void removePage(long pageId) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        
        try {
            boolean startedTxn=startTxn(entityManager);
            Page page = entityManager.find(Page.class, pageId);
            entityManager.remove(page);
            endTxn(startedTxn, entityManager);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void removeWidget(long widgetId) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        
        try {
            boolean startedTxn=startTxn(entityManager);
            Widget widget = entityManager.find(Widget.class, widgetId);
            widget.getPage().getWidgets().remove(widget);
            entityManager.remove(widget);
            endTxn(startedTxn, entityManager);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Widget getWidgetById(long widgetId) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        
        Widget widget = null;
        
        try {
            boolean startedTxn=startTxn(entityManager);
            widget = entityManager.find(Widget.class, widgetId);
            
            // Access the preferences to eagarly load
            if (widget != null) {
                fetch(widget);
            }
            
            endTxn(startedTxn, entityManager);
        } finally {
            entityManager.close();
        }
        
        return widget;
    }

	@Override
    public void updateWidgetPreference(long widgetId,
			List<WidgetPreference> prefs) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        
        try {
    		boolean startedTxn=startTxn(entityManager);
    		Query query = entityManager.createQuery("delete from WidgetPreference pref where pref.widget.id = :id");
    		query.setParameter("id", widgetId);
    		query.executeUpdate();
    
    		Widget widget = entityManager.find(Widget.class, widgetId);
    		for (WidgetPreference pref : prefs) {
    			pref.setWidget(widget);
    		}
    
    		widget.setPrefs(prefs);
            endTxn(startedTxn, entityManager);
        } finally {
            entityManager.close();
        }
	}

	@Override
    public Map<String, String> getWidgetPreference(long widgetId) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        
        Map<String, String> result = new HashMap<String, String>();

        try {
    		boolean startedTxn=startTxn(entityManager);
    		Widget widget = entityManager.find(Widget.class, widgetId);
    		if (widget.getPrefs() != null) {
    			for (WidgetPreference pref : widget.getPrefs()) {
    				result.put(pref.getName(), pref.getValue());
    			}
    		}
            endTxn(startedTxn, entityManager);
        } finally {
            entityManager.close();
        }
        
		return result;
	}

}
