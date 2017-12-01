package com.dpwn.smartscanus.interactor;


import android.app.Activity;

import java.util.List;

import com.dpwn.smartscanus.utils.ui.ProgressUtils;

/**
 * OutputPort Helper class for some useful functions to avoid code duplicity
 * Created by fshamim on 01.10.14.
 */
public class OutputPortHelper {

    protected List<IInteractorInputPort> interactors;
    protected IInteractorOutputPort outputPort;
    protected ProgressUtils utils;

    /**
     * default constructor that instantiates the attribute utils.
     */
    public OutputPortHelper() {
        this.utils = new ProgressUtils();
    }

    /**
     * registers the given output port to the given interactors.
     *
     * @param outputPort  out put port for the interactors
     * @param interactors list of interactors
     */
    public void register(IInteractorOutputPort outputPort, List<IInteractorInputPort> interactors) {
        if (interactors != null) {
            for (IInteractorInputPort interactor : interactors) {
                interactor.registerOutputPort(outputPort);
            }
            this.interactors = interactors;
            this.outputPort = outputPort;
        }
    }

    /**
     * unregister the previous registration.
     */
    public void unregister() {
        if (this.interactors != null) {
            for (IInteractorInputPort interactor : interactors) {
                interactor.unregisterOutputPort(outputPort);
            }
        }
    }

    /**
     * Indicate a progress bar on the given activity
     *
     * @param activity   android context
     * @param loadingMsg Loading msg for progress
     */
    public void showProgress(Activity activity, String loadingMsg) {
        this.utils.showProgress(activity, loadingMsg);
    }

    /**
     * hide the previously shown progress bar
     */
    public void hideProgress() {
        this.utils.hideProgress();
    }

    /**
     * check if the progress bar is showing
     *
     * @return true if showing
     */
    public boolean isInProgress() {
        return this.utils.isInProgress();
    }
}
