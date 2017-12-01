package com.dpwn.smartscanus.dspscanning;

import com.dpwn.smartscanus.newopsapi.resources.MessageResponse;

/**
 * Created by cekangak on 8/12/2015.
 */
public interface IDSPScanOutputPort {

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

}
