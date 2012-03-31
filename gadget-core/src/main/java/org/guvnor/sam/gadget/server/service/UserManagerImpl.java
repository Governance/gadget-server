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
package org.guvnor.sam.gadget.server.service;

import com.google.inject.Inject;
import org.guvnor.sam.gadget.server.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 16/03/12
 */
public class UserManagerImpl implements UserManager {

    EntityManager entityManager;

    @Inject
    public UserManagerImpl(EntityManager manager) {
        this.entityManager = manager;
    }

    public User createUser(User user) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        return user;
    }

    public void updateUser(User user) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

    public void removeUser(User user) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.remove(user);
        entityManager.getTransaction().commit();
    }

    public List<User> getAllUser() {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Query query = entityManager.createQuery("select user from User user");
        List<User> users = query.getResultList();
        for (User user : users) {
            System.out.println("User is: " + user.getEmail() + "->" + user.getName());
        }
        entityManager.getTransaction().commit();
        return users;
    }

    public User getUser(String username, String password) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Query query = entityManager.createQuery("select user from User user where user.name = :username and user.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        
        List<User> users = query.getResultList();
        entityManager.getTransaction().commit();

        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

}
