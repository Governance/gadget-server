package org.savara.gadget.server.service;

import org.savara.gadget.server.model.Gadget;
import org.savara.gadget.server.model.Page;

import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 27/04/12
 */
public interface GadgetService {

    List<Gadget> getAllGadgets(int offset, int pageSize);

    int getAllGadgetsNum();

    public void addGadgetToPage(Gadget gadget, Page page);
    
    public Gadget getGadgetById(long gadgetId);

}
