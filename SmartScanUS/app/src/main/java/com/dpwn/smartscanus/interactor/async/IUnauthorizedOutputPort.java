package com.dpwn.smartscanus.interactor.async;

/**
 * Corresponding to the input port this output port should entertain all the possible output
 * actions resulting from the input port.
 * Created by fshamim on 08.10.14.
 */
public interface IUnauthorizedOutputPort extends IAsyncInteractorOutputPort{

    /**
     * username or token is not valid.
     */
    void unauthorizedRequest();
}
