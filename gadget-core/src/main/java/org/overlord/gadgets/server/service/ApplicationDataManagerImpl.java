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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.overlord.gadgets.server.model.ApplicationData;

import com.google.inject.Inject;

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

    @Override
    public ApplicationData getApplicationData(String userId, String appUrl) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Query query = entityManager.createQuery("select app from ApplicationData app where app.userId = :userId and app.appUrl = :appUrl");
        query.setParameter("userId", userId);
        query.setParameter("appUrl", appUrl);
        @SuppressWarnings("unchecked")
        List<ApplicationData> data = query.getResultList();

        entityManager.getTransaction().commit();

        if (data == null) {
            return  null;
        }

        return data.get(0);
    }

    @Override
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
