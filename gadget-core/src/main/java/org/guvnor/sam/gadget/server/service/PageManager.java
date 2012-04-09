package org.guvnor.sam.gadget.server.service;

import org.guvnor.sam.gadget.server.model.Page;

import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 8/04/12
 */
public interface PageManager {

    List<Page> getPages(Long userId);

}
