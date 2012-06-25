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

import com.google.inject.Inject;

import org.overlord.gadgets.server.model.Gadget;
import org.overlord.gadgets.server.model.Page;
import org.overlord.gadgets.server.model.Widget;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 27/04/12
 */
public class GadgetServiceImpl implements GadgetService{

    private EntityManager entityManager;

    //TODO: need to be replaced with initial data sql.
    private static Gadget todoList;
    private static Gadget currencyConverter;
    private static Gadget slaWidget;
    private static Gadget dateAndTime;
    private static Gadget graph;
    
    @Inject
    public GadgetServiceImpl(EntityManager em) {
        this.entityManager = em;
        initialize();
    }

    private void initialize() {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.persist(todoList);
        entityManager.persist(currencyConverter);
        entityManager.persist(slaWidget);
        entityManager.persist(dateAndTime);
        entityManager.persist(graph);
        entityManager.getTransaction().commit();
    }
    
    static {
        todoList = new Gadget();
        todoList.setAuthorEmail("weather@google.com");
        todoList.setTitle(" To-Do List");
        todoList.setAuthor("Labpixies");
        todoList.setDescription(" Easily manage and track everything you need To-Do. The gadget lets you create multiple To-Do lists, each with a unique purpose.");
        todoList.setThumbnailUrl("http://www.gstatic.com/ig/modules/labpixies/todo/images/thumbnail.cache.jpg");
        todoList.setTitleUrl("");
        todoList.setUrl("http://www.labpixies.com/campaigns/todo/todo.xml");

        currencyConverter = new Gadget();
        currencyConverter.setAuthor("Google");
        currencyConverter.setAuthorEmail("info@tofollow.com");
        currencyConverter.setTitle(" Currency Converter");
        currencyConverter.setThumbnailUrl("http://www.gstatic.com/ig/modules/currency_converter/currency_converter_content/en_us-thm.cache.png");
        currencyConverter.setDescription(" This is the currency converter widget");
        currencyConverter.setUrl("http://www.gstatic.com/ig/modules/currency_converter/currency_converter_v2.xml");
        
        slaWidget = new Gadget();
        slaWidget.setAuthor("Jeff Yu");
        slaWidget.setAuthorEmail("Jeff@test.com");
        slaWidget.setTitle("SLA Gadget");
        slaWidget.setThumbnailUrl("http://slagadget.googlecode.com/git/thumbnail.png");
        slaWidget.setDescription(" This is the Service Level Violation Gadget");
        slaWidget.setUrl("http://slagadget.googlecode.com/git/gadget.xml");

        dateAndTime = new Gadget();
        dateAndTime.setAuthor("Google");
        dateAndTime.setAuthorEmail("admin@google.com");
        dateAndTime.setTitle(" Date & Time");
        dateAndTime.setThumbnailUrl("http://gadgets.adwebmaster.net/images/gadgets/datetimemulti/thumbnail_en.jpg");
        dateAndTime.setDescription(" Add a clock to your page. Click edit to change it to the color of your choice");
        dateAndTime.setUrl("http://www.gstatic.com/ig/modules/datetime_v3/datetime_v3.xml");
        
        graph = new Gadget();
        graph.setAuthor("Research Department");
        graph.setThumbnailUrl("http://research.stlouisfed.org/gadgets/images/alfredgraphgadgetthumbnail.png");
        graph.setDescription("Vintage Economic Data from the Federal Reserve Bank of St. Louis");
        graph.setAuthorEmail("webmaster@research.stlouisfed.org");
        graph.setTitle("Economic Data - ALFRED Graph");
        graph.setUrl("http://research.stlouisfed.org/gadgets/code/alfredgraph.xml");


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

    public Gadget getGadgetById(long gadgetId) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Gadget gadget = entityManager.find(Gadget.class, gadgetId);
        entityManager.getTransaction().commit();

        return gadget;
    }


}
