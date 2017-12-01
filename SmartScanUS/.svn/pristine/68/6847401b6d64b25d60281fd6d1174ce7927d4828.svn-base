package com.dpwn.smartscanus.interactor;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Interactor that handles registeration process of the output ports.
 * Created by fshamim on 18.09.14.
 */
public abstract class AbstractInteractor implements IInteractorInputPort {

    /**
     * List of output ports
     */
    protected List<IInteractorOutputPort> outputPorts = new ArrayList<IInteractorOutputPort>();

    @Override
    public void registerOutputPort(IInteractorOutputPort listener) {
        unregisterOutputPort(listener);
        outputPorts.add(listener);
    }

    @Override
    public void unregisterOutputPort(IInteractorOutputPort listener) {
        if (outputPorts.contains(listener)) {
            outputPorts.remove(listener);
        }
    }
}
