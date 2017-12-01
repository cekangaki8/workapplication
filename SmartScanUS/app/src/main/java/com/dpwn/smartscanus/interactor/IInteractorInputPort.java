package com.dpwn.smartscanus.interactor;

/**
 * Interface for registering an output port to the interactors
 * Created by fshamim on 18.09.14.
 */
public interface IInteractorInputPort {
    /**
     * register output port
     * @param outputPort outputPort to register
     */
    void registerOutputPort(IInteractorOutputPort outputPort);
    /**
     * unregister output port
     * @param outputPort outputPort to unregister
     */
    void unregisterOutputPort(IInteractorOutputPort outputPort);
}
