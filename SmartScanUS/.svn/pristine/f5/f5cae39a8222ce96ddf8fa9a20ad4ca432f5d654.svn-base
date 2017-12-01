package com.dpwn.smartscanus.dspscanning;

import com.dpwn.smartscanus.interactor.async.IAsyncInteractorInputPort;

/**
 * Interface for DSP Next Day Scanning interface with Newops.
 *
 * Created by cekangak on 8/12/2015.
 */
public interface IDSPScanningInputPort extends IAsyncInteractorInputPort {

    /**
     * This method will send the container information used to associate the given mailitem.
     *
     * @param barcode
     */
    void processDSPContainerScan(String barcode);

    /**
     * This method will send the tray information to associate all given mailitems.
     * @param barcode
     */
    void processDSPSackScan(String barcode);

    /**
     * This method will send the given mailitem scanned information will be used in scanning.
     * @param barcode
     */
    void processDSPPieceScan(String barcode);
}
