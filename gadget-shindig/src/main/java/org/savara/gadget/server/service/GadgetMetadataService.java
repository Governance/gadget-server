package org.savara.gadget.server.service;

/**
 * @author: Jeff Yu
 * @date: 9/02/12
 */
public interface GadgetMetadataService {

   /**
     * Fetches gadget metadata for the specified gadget URL.
     *
     * @param gadgetUrl The gadget to fetch metadata for.
     * @return The raw JSON response from the Shindig metadata RPC call.
     */
    public String getGadgetMetadata(String gadgetUrl);
}
