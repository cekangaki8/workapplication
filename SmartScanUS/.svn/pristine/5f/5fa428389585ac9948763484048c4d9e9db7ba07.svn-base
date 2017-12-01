package com.dpwn.smartscanus.interactor.async;

import com.google.inject.Inject;
import com.squareup.otto.Bus;

import com.dpwn.smartscanus.annotations.IOSchedular;
import com.dpwn.smartscanus.annotations.UISchedular;
import com.dpwn.smartscanus.events.ShowToastEvent;
import com.dpwn.smartscanus.interactor.AbstractInteractor;
import com.dpwn.smartscanus.interactor.IInteractorOutputPort;
import com.dpwn.smartscanus.logging.L;
import rx.Scheduler;
import rx.Subscription;

/**
 * Created by fshamim on 29.09.14.
 */
public abstract class AbstractAsyncInteractor extends AbstractInteractor implements IAsyncInteractorInputPort {
    private static final String TAG = AbstractAsyncInteractor.class.getSimpleName();
    protected Subscription subscription;

    @Inject
    Bus mBus;

    @Inject
    @UISchedular
    protected Scheduler uiSchedular;

    @Inject
    @IOSchedular
    protected Scheduler ioSchedular;

    protected void sendProgressIndication(String loadingMsg) {
        for (IInteractorOutputPort outputPort : outputPorts) {
            try {
                ((IAsyncInteractorOutputPort) outputPort).indicateProgress(loadingMsg);
            } catch (Exception ignored) {
                L.i(TAG, ignored.getMessage(), ignored);
            }
        }
    }

    protected void sendProgressFinished() {
        for (IInteractorOutputPort outputPort : outputPorts) {
            try {
                ((IAsyncInteractorOutputPort) outputPort).indicateProgressFinished();
            } catch (Exception ignored) {
                L.i(TAG, ignored.getMessage(), ignored);
            }
        }
    }

    protected void sendUnknownErrorOccured(int statusCode, String reason) {
        for (IInteractorOutputPort outputPort : outputPorts) {
            try {
                ((IAsyncInteractorOutputPort) outputPort).unknownErrorOccurred(statusCode, reason);
            } catch (Exception ignored) {
                L.i(TAG, ignored.getMessage(), ignored);
            }
        }
    }

    protected void networkError() {
        String noInternetMessage = "Please check your WiFi connection: Host unreachable timeout";
        mBus.post(new ShowToastEvent(noInternetMessage, ShowToastEvent.ToastDuration.LONG).setColor(ShowToastEvent.ToastColor.RED));
    }

    @Override
    public void cancel() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}
