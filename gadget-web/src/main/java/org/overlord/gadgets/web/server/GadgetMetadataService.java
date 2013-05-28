package org.overlord.gadgets.web.server;

import org.overlord.gadgets.server.model.Gadget;
import org.overlord.gadgets.web.shared.dto.WidgetModel;

/**
 * @author: Jeff Yu
 * @date: 9/02/12
 */
public interface GadgetMetadataService {

   /**
     * Fetches gadget metadata for the specified gadget URL.
     *
     * @param gadgetUrl The gadget to fetch metadata for.
     * @return The WidgetModel that constructed from JSON response from the Shindig metadata RPC call.
     */
    public WidgetModel getGadgetMetadata(String gadgetUrl);

    /**
     * Fetches gadget data fro the specified gadget URL.
     *
     * @param gadgetUrl
     */
    public Gadget getGadgetData(String gadgetUrl);
}
