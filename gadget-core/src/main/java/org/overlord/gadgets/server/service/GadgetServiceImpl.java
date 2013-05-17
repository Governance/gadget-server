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

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.overlord.gadgets.server.model.Gadget;
import org.overlord.gadgets.server.model.Page;
import org.overlord.gadgets.server.model.Widget;

import com.google.inject.Inject;

/**
 * @author: Jeff Yu
 * @date: 27/04/12
 */
public class GadgetServiceImpl implements GadgetService{

    private EntityManager entityManager;

    @Inject
    public GadgetServiceImpl(EntityManager em) {
        this.entityManager = em;
    }


    @Override
    public List<Gadget> getAllGadgets(int offset, int pageSize) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }

        Query query = entityManager.createQuery("select gadget from Gadget gadget");
        query.setFirstResult(offset).setMaxResults(pageSize);
        @SuppressWarnings("unchecked")
        List<Gadget> gadgets = query.getResultList();
        entityManager.getTransaction().commit();
        return gadgets;
    }

    @Override
    public int getAllGadgetsNum() {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Query query = entityManager.createQuery("select count(gadget.id) from Gadget gadget");

        Long result = (Long)query.getSingleResult();
        entityManager.getTransaction().commit();
        return result.intValue();
    }

    @Override
    public void addGadgetToPage(Gadget gadget, Page page) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Widget widget = new Widget();
        widget.setAppUrl(gadget.getUrl());
        widget.setName(gadget.getTitle());
        widget.setPage(page);
        //TODO: hard-coded for testing...
        widget.setOrder(page.getWidgets().size() + 1);

        entityManager.persist(widget);

        page.getWidgets().add(widget);
        entityManager.merge(page);

        entityManager.getTransaction().commit();
    }

    @Override
    public Gadget getGadgetById(long gadgetId) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Gadget gadget = entityManager.find(Gadget.class, gadgetId);
        entityManager.getTransaction().commit();

        return gadget;
    }


}
