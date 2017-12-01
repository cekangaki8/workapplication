package com.dpwn.smartscanus.fullServiceImb;

import com.dpwn.smartscanus.newopsapi.resources.MessageResponse;
import com.dpwn.smartscanus.interactor.async.IUnauthorizedOutputPort;

/**
 * Interface for the output port for fullserviceimb communication.
 * Created by cekangak on 07.01.15.
 */
public interface IFullServiceOutputPort extends IUnauthorizedOutputPort {

    /**
     * This method sends the specific error message back to the output port
     * that occured during scanning of the barcode for full service IMB
     * @param msg
     */
    void fullServiceBCScanError(MessageResponse msg);

    /**
     * This method will return the successfully message from Newops to
     * the output port.
     * @param msg
     */
    void fullServiceBCScanSuccess(MessageResponse msg);
}
