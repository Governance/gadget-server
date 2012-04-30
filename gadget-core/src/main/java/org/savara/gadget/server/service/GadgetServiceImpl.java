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

import org.savara.gadget.server.model.Gadget;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 27/04/12
 */
public class GadgetServiceImpl implements GadgetService{

    public List<Gadget> getAllGadgets(int offset, int pageSize) {
        
        List<Gadget> gadgets = new ArrayList<Gadget>();
        
        Gadget gadget = new Gadget();
        gadget.setAuthorEmail("googlemodules+tabnews+kennedy+201203211@google.com");
        gadget.setTitle("Google News");
        gadget.setAuthor("Google");
        gadget.setDescription("Customizable news gadget that shows different news sections in separate tabs.");
        gadget.setThumbnailUrl("http://www.gstatic.com/ig/modules/tabnews/tabnews_content/us-thm.png");
        gadget.setScreenshotUrl("http://www.gstatic.com/ig/modules/tabnews/tabnews_content/us.png");
        gadget.setId(11L);
        gadget.setTitleUrl("http://news.google.com/");
        
        gadgets.add(gadget);
        
        Gadget gadget2 = new Gadget();
        gadget2.setAuthor("ToFollow");
        gadget2.setAuthorEmail("info@tofollow.com");
        gadget2.setTitle("Currency Converter");
        gadget2.setThumbnailUrl("http://hosting.gmodules.com/ig/gadgets/file/112016200750717054421/74e562e0-7881-4ade-87bb-ca9977151084.jpg");
        gadget2.setScreenshotUrl("http://hosting.gmodules.com/ig/gadgets/file/112016200750717054421/74e562e0-7881-4ade-87bb-ca9977151084.jpg");
        gadget2.setId(12L);
        gadget2.setTitleUrl("http://tofollow.com");

        gadgets.add(gadget2);

        return gadgets;
    }

    public int getAllGadgetsNum() {
        return 2;
    }


}
