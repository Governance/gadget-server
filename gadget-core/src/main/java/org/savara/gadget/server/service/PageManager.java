package org.savara.gadget.server.service;

import org.savara.gadget.server.model.Page;

import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 8/04/12
 */
public interface PageManager {

    List<Page> getPages(Long userId);

}
