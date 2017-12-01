package com.dpwn.smartscanus.deliverynetwork;

import com.dpwn.smartscanus.newopsapi.resources.MessageResponse;

/**
 * The output port for sending all
 *
 * Created by cekangaki on 11/30/2017.
 */
public interface IDeliveryNetworkScanningOutputPort {

    /**
     * This will handle all positive response returned from the API
     * @param msg
     */
    void scanProcessedSuccessfully(MessageResponse msg);

    /**
     * This method will handle all negative response returned from the API
     * @param msg
     */
    void scanProcessingError(MessageResponse msg);

}
