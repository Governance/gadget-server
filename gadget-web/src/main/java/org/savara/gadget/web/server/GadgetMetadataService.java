package org.savara.gadget.web.server;

import org.savara.gadget.server.model.Gadget;
import org.savara.gadget.web.shared.dto.GadgetModel;

/**
 * @author: Jeff Yu
 * @date: 9/02/12
 */
public interface GadgetMetadataService {

   /**
     * Fetches gadget metadata for the specified gadget URL.
     *
     * @param gadgetUrl The gadget to fetch metadata for.
     * @return The GadgetModel that constructed from raw JSON response from the Shindig metadata RPC call.
     */
    public GadgetModel getGadgetMetadata(String gadgetUrl);
    
    
    public Gadget getGadgetData(String gadgetUrl);
}
