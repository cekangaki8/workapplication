package com.dpwn.smartscanus.interactor.async;

import com.dpwn.smartscanus.interactor.IInteractorOutputPort;

/**
 * Output port for every web interactors, allowing them to indicate the status of a
 * request being made by the current interactors.
 * Created by fshamim on 18.09.14.
 */
public interface IAsyncInteractorOutputPort extends IInteractorOutputPort {
    /**
     * communicate through the output port that a request is in progress.
     */
    void indicateProgress(String loadingMsg);

    /**
     * communicate through the output port that the request progress has been finished.
     */
    void indicateProgressFinished();

    /**
     * Communicate through the output port that an unknown error has occurred.
     * @param statusCode http status code that is received
     * @param reason http reason
     */
    void unknownErrorOccurred(int statusCode, String reason);
}
