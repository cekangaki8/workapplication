package com.dpwn.smartscanus.transport;

import com.dpwn.smartscanus.newopsapi.resources.MessageResponse;

/**
 * Created by cekangak on 9/25/2015.
 */
public interface ILoadTransportOutputPort {
    /**
     * This will handle all positive respsonse returned from the API
     * @param msg
     */
    void scanProcessedSuccessfully(MessageResponse msg);

    /**
     * This method will handle all negative response returned from the API
     * @param msg
     */
    void scanProcessingError(MessageResponse msg);

    /**
     * This willl return a messageResponse with a warning
     * @param msg
     */
    void scanProcessingWarning(MessageResponse msg);
}
