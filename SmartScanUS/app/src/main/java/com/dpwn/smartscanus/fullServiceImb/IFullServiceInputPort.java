package com.dpwn.smartscanus.fullServiceImb;

import com.dpwn.smartscanus.interactor.async.IAsyncInteractorInputPort;

/**
 * Interface for the input Port for the fullServiceIMB user stories.
 * Created by cekangak on 07.01.15.
 */
public interface IFullServiceInputPort extends IAsyncInteractorInputPort {

    /**
     * Submits a container or tray to the fullservice imb interface.
     * @param barcode
     */
    void scanFullServiceCotainers(String barcode);
}
