package org.savara.gadget.server.service;

import org.savara.gadget.server.model.Gadget;

import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 27/04/12
 */
public interface GadgetService {

    List<Gadget> getAllGadgets(int offset, int pageSize);

    int getAllGadgetsNum();

}
