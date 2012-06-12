package org.overlord.gadgets.server.service;

import org.overlord.gadgets.server.model.Gadget;
import org.overlord.gadgets.server.model.Page;

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
