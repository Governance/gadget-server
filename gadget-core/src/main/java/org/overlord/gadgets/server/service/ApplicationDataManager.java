package org.overlord.gadgets.server.service;

import java.util.List;

import org.overlord.gadgets.server.model.ApplicationData;

/**
 * @author: Jeff Yu
 * @date: 31/03/12
 */
public interface ApplicationDataManager {


    public ApplicationData getApplicationData(String userId, String appUrl);

    public List<ApplicationData> getApplicationData(Long userId);

}
