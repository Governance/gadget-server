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
import org.guvnor.sam.gadget.server.model.ApplicationData;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 1/04/12
 */
public class ApplicationDataManagerImpl implements ApplicationDataManager{

    private EntityManager entityManager;

    @Inject
    public ApplicationDataManagerImpl(EntityManager em) {
        this.entityManager = em;
    }

    public ApplicationData getApplicationData(String userId, String appUrl) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Query query = entityManager.createQuery("select app from ApplicationData app where app.userId = :userId and app.appUrl = :appUrl");
        query.setParameter("userId", userId);
        query.setParameter("appUrl", appUrl);
        List<ApplicationData> data = query.getResultList();

        entityManager.getTransaction().commit();

        if (data == null) {
            return  null;
        }

        return data.get(0);
    }

    public List<ApplicationData> getApplicationData(Long userId) {
        List<ApplicationData> data = new ArrayList<ApplicationData>();
        
        ApplicationData d = new ApplicationData();
        d.setUserId(userId);
        d.setAppUrl("http://sam-gadget.appspot.com/Gadget/SamGadget.gadget.xml");
        
        ApplicationData d2 = new ApplicationData();
        d2.setUserId(userId);
        d2.setAppUrl("http://www.gstatic.com/ig/modules/tabnews/kennedy/tabnews.xml");
        
        ApplicationData d3 = new ApplicationData();
        d3.setUserId(userId);
        d3.setAppUrl("http://hosting.gmodules.com/ig/gadgets/file/112016200750717054421/currency-converter.xml");

        data.add(d);
        data.add(d2);
        data.add(d3);
        
        return data;
    }
}
