package com.dpwn.smartscanus.fullServiceImb.interactor;

import com.dpwn.smartscanus.newopsapi.ISortingServiceApi;
import com.dpwn.smartscanus.newopsapi.resources.BarcodeRequest;
import com.dpwn.smartscanus.newopsapi.resources.BarcodeResponse;
import com.google.inject.Inject;

import com.dpwn.smartscanus.newopsapi.resources.MessageResponse;
import com.dpwn.smartscanus.interactor.IInteractorOutputPort;
import com.dpwn.smartscanus.interactor.async.AbstractAsyncInteractor;
import com.dpwn.smartscanus.interactor.async.IUnauthorizedOutputPort;
import com.dpwn.smartscanus.logging.L;
import com.dpwn.smartscanus.fullServiceImb.IFullServiceInputPort;
import com.dpwn.smartscanus.fullServiceImb.IFullServiceOutputPort;
import retrofit.RetrofitError;
import rx.Observer;

/**
 * Created by cekangak on 07.01.14.
 */
public class FullServiceInteractor extends AbstractAsyncInteractor implements IFullServiceInputPort {

    private static final String TAG = FullServiceInteractor.class.getSimpleName();

    @Inject
    protected ISortingServiceApi sortingServiceApi;



    /**
     * Empty Constructor required
     */
    public FullServiceInteractor() {
    }

    @Override
    public void scanFullServiceCotainers(String barcode) {
        sendProgressIndication("Sending Barcode to Newops ...");
        subscription = sortingServiceApi.processBarcode(new BarcodeRequest(barcode))
                .subscribeOn(ioSchedular)
                .observeOn(uiSchedular)
                .subscribe(new Observer<BarcodeResponse>() {
                    @Override
                    public void onCompleted() {
                        sendProgressFinished();
                    }

                    @Override
                    public void onError(Throwable e) {
                        sendProgressFinished();
                        if (e instanceof RetrofitError) {
                            handleRequestErrorForFullService((RetrofitError) e);
                        } else {
                            sendUnknownErrorOccured(600, "Not specified");
                        }
                    }

                    @Override
                    public void onNext(BarcodeResponse response) {

                        if (response != null && response.getStatus() != null) {
                            if ("0".equals(response.getStatus().getReturnCode())) {
                                sendFSBarcodeScanSuccess(new MessageResponse(response.getResponse().getDescription()));
                            } else {
                                sendFSBarcodeScanError(new MessageResponse(response.getStatus().getMessage()));
                            }
                        } else {
                            sendFSBarcodeScanError(new MessageResponse("Error occurred during authentication."));
                        }
                    }
                });
    }


    private void handleRequestErrorForFullService(RetrofitError error) {
        if (error.isNetworkError()) {
            networkError();
            return;
        }
        int statusCode = error.getResponse().getStatus();
        switch (statusCode) {
            case 401:
                sendUnauthorized();
                break;
            default:
                sendUnknownErrorOccured(statusCode, error.getResponse().getReason());
                break;
        }
    }


    private void sendUnauthorized() {
        for (IInteractorOutputPort outputPort : outputPorts) {
            try {
                ((IUnauthorizedOutputPort) outputPort).unauthorizedRequest();
            } catch (Exception ignored) {
                L.i(TAG, ignored.getMessage(), ignored);
            }
        }
    }

    private void sendFSBarcodeScanError(MessageResponse msg) {
        for (IInteractorOutputPort outputPort : outputPorts) {
            try {
                ((IFullServiceOutputPort) outputPort).fullServiceBCScanError(msg);
            } catch (Exception ignored) {
                L.i(TAG, ignored.getMessage(), ignored);
            }
        }
    }

    private void sendFSBarcodeScanSuccess(MessageResponse msg) {
        for (IInteractorOutputPort outputPort : outputPorts) {
            try {
                ((IFullServiceOutputPort) outputPort).fullServiceBCScanSuccess(msg);
            } catch (Exception ignored) {
                L.i(TAG, ignored.getMessage(), ignored);
            }
        }
    }

}
