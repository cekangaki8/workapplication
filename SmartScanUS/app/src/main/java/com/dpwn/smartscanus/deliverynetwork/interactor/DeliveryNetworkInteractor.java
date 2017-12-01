package com.dpwn.smartscanus.deliverynetwork.interactor;

import com.dpwn.smartscanus.deliverynetwork.IDeliveryNetworkScanningInputPort;
import com.dpwn.smartscanus.deliverynetwork.IDeliveryNetworkScanningOutputPort;
import com.dpwn.smartscanus.interactor.IInteractorOutputPort;
import com.dpwn.smartscanus.interactor.async.AbstractAsyncInteractor;
import com.dpwn.smartscanus.interactor.async.IUnauthorizedOutputPort;
import com.dpwn.smartscanus.logging.L;
import com.dpwn.smartscanus.newopsapi.IDeliveryNetworkApi;
import com.dpwn.smartscanus.newopsapi.resources.BarcodeResponse;
import com.dpwn.smartscanus.newopsapi.resources.DNScanBarcodeRequest;
import com.dpwn.smartscanus.newopsapi.resources.MessageResponse;
import com.google.inject.Inject;

import retrofit.RetrofitError;
import rx.Observer;

/**
 * Created by cekangaki on 11/30/2017.
 */
public class DeliveryNetworkInteractor extends AbstractAsyncInteractor implements IDeliveryNetworkScanningInputPort {

    private static final String TAG = DeliveryNetworkInteractor.class.getSimpleName();

    @Inject
    protected IDeliveryNetworkApi deliveryNetworkApi;

    /**
     * This will call the process mailitem endpoint for delivery network in NOC.
     * <p/>
     * It is used for doing DSP inbound scanning.
     *
     * @param barcode
     */
    @Override
    public void processMailItem(String barcode) {
        sendProgressIndication("Sending mailitem to Newops ...");
        subscription = deliveryNetworkApi.processBarcode(new DNScanBarcodeRequest(barcode))
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
                            handleRetrofitError((RetrofitError) e);
                        } else {
                            sendUnknownErrorOccured(600, "Not specified");
                        }
                    }

                    @Override
                    public void onNext(BarcodeResponse response) {
                        if (response != null && response.getStatus() != null) {
                            if ("0".equals(response.getStatus().getReturnCode())) {
                                sendSuccessfulMessage(new MessageResponse(response.getResponse().getDescription()));
                            } else {
                                sendErrorMessage(new MessageResponse(response.getStatus().getMessage()));
                            }
                        } else {
                            sendErrorMessage(new MessageResponse("Error occurred during authentication."));
                        }
                    }

                });
    }

    private void sendSuccessfulMessage(MessageResponse msg) {
        for (IInteractorOutputPort outputPort : outputPorts) {
            try {
                ((IDeliveryNetworkScanningOutputPort) outputPort).scanProcessedSuccessfully(msg);
            } catch (Exception ex) {
                L.e(TAG, ex.getMessage(), ex);
            }
        }
    }

    private void sendErrorMessage(MessageResponse msg) {
        for (IInteractorOutputPort outputPort : outputPorts) {
            try {
                ((IDeliveryNetworkScanningOutputPort) outputPort).scanProcessingError(msg);
            } catch (Exception ex) {
                L.e(TAG, ex.getMessage(), ex);
            }
        }
    }

    private void handleRetrofitError(RetrofitError error) {
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

}
