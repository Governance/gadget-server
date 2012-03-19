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

/**
 * @author: Jeff Yu
 * @date: 16/03/12
 */
public class UserManagerImpl implements UserManager{

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

}
