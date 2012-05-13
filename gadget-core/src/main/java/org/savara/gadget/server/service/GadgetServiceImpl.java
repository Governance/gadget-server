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
package org.savara.gadget.server.service;

import com.google.inject.Inject;
import org.savara.gadget.server.model.Gadget;
import org.savara.gadget.server.model.Page;
import org.savara.gadget.server.model.Widget;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 27/04/12
 */
public class GadgetServiceImpl implements GadgetService{

    private EntityManager entityManager;

    //TODO: need to be replaced with initial data sql.
    private static Gadget gadget1;
    private static Gadget gadget2;
    private static Gadget gadget3;
    
    @Inject
    public GadgetServiceImpl(EntityManager em) {
        this.entityManager = em;
        initialize();
    }

    private void initialize() {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.persist(gadget1);
        entityManager.persist(gadget2);
        entityManager.persist(gadget3);
        entityManager.getTransaction().commit();
    }
    
    static {
        gadget1 = new Gadget();
        gadget1.setAuthorEmail("googlemodules+tabnews+kennedy+201203211@google.com");
        gadget1.setTitle("Google News");
        gadget1.setAuthor("Google");
        gadget1.setDescription("Customizable news gadget that shows different news sections in separate tabs.");
        gadget1.setThumbnailUrl("http://www.gstatic.com/ig/modules/tabnews/tabnews_content/us-thm.png");
        gadget1.setScreenshotUrl("http://www.gstatic.com/ig/modules/tabnews/tabnews_content/us.png");
        gadget1.setTitleUrl("http://news.google.com/");

        gadget2 = new Gadget();
        gadget2.setAuthor("ToFollow");
        gadget2.setAuthorEmail("info@tofollow.com");
        gadget2.setTitle("Currency Converter");
        gadget2.setThumbnailUrl("http://hosting.gmodules.com/ig/gadgets/file/112016200750717054421/74e562e0-7881-4ade-87bb-ca9977151084.jpg");
        gadget2.setScreenshotUrl("http://hosting.gmodules.com/ig/gadgets/file/112016200750717054421/74e562e0-7881-4ade-87bb-ca9977151084.jpg");
        gadget2.setTitleUrl("http://tofollow.com");
        gadget2.setDescription("This is the currency converter widget");
        
        gadget3 = new Gadget();
        gadget3.setAuthor("Jeff Yu");
        gadget3.setAuthorEmail("Jeff@test.com");
        gadget3.setTitle("BAM Gadget");
        gadget3.setThumbnailUrl("http://hosting.gmodules.com/ig/gadgets/file/112016200750717054421/74e562e0-7881-4ade-87bb-ca9977151084.jpg");
        gadget3.setDescription("This is the BAM gadget prototype...");

    }

    public List<Gadget> getAllGadgets(int offset, int pageSize) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        
        Query query = entityManager.createQuery("select gadget from Gadget gadget");
        query.setFirstResult(offset).setMaxResults(pageSize);
        List<Gadget> gadgets = query.getResultList();
        entityManager.getTransaction().commit();
        return gadgets;
    }

    public int getAllGadgetsNum() {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Query query = entityManager.createQuery("select count(gadget.id) from Gadget gadget");

        Long result = (Long)query.getSingleResult();
        entityManager.getTransaction().commit();
        return result.intValue();
    }

    public void addGadgetToPage(Gadget gadget, Page page) {
        Widget widget = new Widget();
        widget.setAppUrl(gadget.getUrl());
        widget.setName(gadget.getTitle());
        widget.setPage(page);
        //TODO: hard-coded for testing...
        widget.setOrder(page.getWidgets().size() + 1);

        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.persist(widget);

        page.getWidgets().add(widget);
        entityManager.merge(page);

        entityManager.getTransaction().commit();
    }

    public Gadget getGadgetById(long gadgetId) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Gadget gadget = entityManager.find(Gadget.class, gadgetId);
        entityManager.getTransaction().commit();

        return gadget;
    }


}
