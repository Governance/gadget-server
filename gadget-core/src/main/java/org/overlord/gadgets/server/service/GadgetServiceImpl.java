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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

    private EntityManagerFactory entityManagerFactory;

    @Inject
    public GadgetServiceImpl(EntityManagerFactory emf) {
        this.entityManagerFactory = emf;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Gadget> getAllGadgets(int offset, int pageSize) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        
        List<Gadget> gadgets=null;
        
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
    
            Query query = entityManager.createQuery("select gadget from Gadget gadget");
            query.setFirstResult(offset).setMaxResults(pageSize);
            gadgets = query.getResultList();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }

        return gadgets;
    }

    @Override
    public int getAllGadgetsNum() {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        
        Long result=null;
        
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            Query query = entityManager.createQuery("select count(gadget.id) from Gadget gadget");
    
            result = (Long)query.getSingleResult();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }

        return result == null ? 0 : result.intValue();
    }

    @Override
    public void addGadgetToPage(Gadget gadget, Page page) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        
        try {
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
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Gadget getGadgetById(long gadgetId) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        
        Gadget gadget=null;
        
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            gadget = entityManager.find(Gadget.class, gadgetId);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    
        return gadget;
    }


}
