package com.dpwn.smartscanus.deliverynetwork;

import com.dpwn.smartscanus.interactor.async.IAsyncInteractorInputPort;

/**
 * Interface for the endpoints associated with delivery network module in NOC
 *
 * Created by cekangaki on 11/30/2017.
 */
public interface IDeliveryNetworkScanningInputPort extends IAsyncInteractorInputPort {

    /**
     * This will call the process mailitem endpoint for delivery network in NOC.
     *
     * It is used for doing DSP inbound scanning.
     * @param barcode
     */
    void processMailItem(String barcode);
}
