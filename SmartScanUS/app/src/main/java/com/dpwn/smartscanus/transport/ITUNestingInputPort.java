package com.dpwn.smartscanus.transport;

import com.dpwn.smartscanus.interactor.async.IAsyncInteractorInputPort;

/**
 * This interface is used for the Receptacle to TU nessting
 *
 * Created by cekangak on 9/25/2015.
 */
public interface ITUNestingInputPort extends IAsyncInteractorInputPort {

    /**
     * This method will send the TU for which receptacles will be nested for validation.
     *
     * @param barcode
     */
    void getTrasportUnit(String barcode);

    /**
     * This method will send the transport unit and receptacle information to be associated.
     * @param transportUnitNumber
     * @param receptacleNumber
     */
    void processReceptacleToTU(String transportUnitNumber, String receptacleNumber, boolean checkOptionalErrors);

}
