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

import org.guvnor.sam.gadget.server.model.Page;
import org.guvnor.sam.gadget.server.model.User;
import org.guvnor.sam.gadget.server.model.Widget;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 8/04/12
 */
public class PageManagerImpl implements PageManager{

    public List<Page> getPages(Long userId) {
        
        User user = new User();
        user.setId(userId);
        
        List<Page> pages = new ArrayList<Page>();
        
        Page page = new Page();
        page.setName("Home");
        page.setPageOrder(0);
        page.setColumns(3);

        Widget samGadget = new Widget();
        samGadget.setAppUrl("http://sam-gadget.appspot.com/Gadget/SamGadget.gadget.xml");
        samGadget.setOrder(0);
        
        Widget gnews = new Widget();
        gnews.setOrder(1);
        gnews.setAppUrl("http://www.gstatic.com/ig/modules/tabnews/kennedy/tabnews.xml");
        
        Widget cWidget = new Widget();
        cWidget.setOrder(2);
        cWidget.setAppUrl("http://hosting.gmodules.com/ig/gadgets/file/112016200750717054421/currency-converter.xml");
        
        List<Widget> widgets = new ArrayList<Widget>();
        widgets.add(samGadget);
        widgets.add(gnews);
        widgets.add(cWidget);
        page.setWidgets(widgets);
        
        pages.add(page);
        
        return pages;
    }

}
