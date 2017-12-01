package com.dpwn.smartscanus.dspscanning.interactor;

import com.dpwn.smartscanus.dspscanning.IDSPScanOutputPort;
import com.dpwn.smartscanus.dspscanning.IDSPScanningInputPort;
import com.dpwn.smartscanus.interactor.IInteractorOutputPort;
import com.dpwn.smartscanus.interactor.async.AbstractAsyncInteractor;
import com.dpwn.smartscanus.interactor.async.IUnauthorizedOutputPort;
import com.dpwn.smartscanus.logging.L;
import com.dpwn.smartscanus.newopsapi.ISortingServiceApi;
import com.dpwn.smartscanus.newopsapi.resources.BarcodeRequest;
import com.dpwn.smartscanus.newopsapi.resources.BarcodeResponse;
import com.dpwn.smartscanus.newopsapi.resources.MessageResponse;
import com.google.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import retrofit.RetrofitError;
import rx.Observer;

/**
 * Created by cekangak on 8/14/2015.
 */
public class DspScanningInteractor extends AbstractAsyncInteractor implements IDSPScanningInputPort {

    private static final String TAG = DspScanningInteractor.class.getSimpleName();

    @Inject
    protected ISortingServiceApi sortingServiceApi;

    /**
     * Empty Constructor
     */
    public DspScanningInteractor() {}

    @Override
    public void processDSPContainerScan(String barcode) {
        sendProgressIndication("Sending Container to Newops ...");
        subscription = sortingServiceApi.processDSPContainerScan(new BarcodeRequest(barcode))
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

    @Override
    public void processDSPSackScan(String barcode) {
        sendProgressIndication("Sending Tray to Newops ...");
        subscription = sortingServiceApi.processDSPSackScan(new BarcodeRequest(barcode))
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

    @Override
    public void processDSPPieceScan(String barcode) {
        sendProgressIndication("Sending MailItem to Newops ...");
        barcode = StringUtils.remove(barcode, "\u001d");
        subscription = sortingServiceApi.processDSPPieceScan(new BarcodeRequest(barcode))
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
                ((IDSPScanOutputPort) outputPort).scanProcessedSuccessfully(msg);
            } catch (Exception ex) {
                L.e(TAG, ex.getMessage(), ex);
            }
        }
    }

    private void sendErrorMessage(MessageResponse msg) {
        for (IInteractorOutputPort outputPort : outputPorts) {
            try {
                ((IDSPScanOutputPort) outputPort).scanProcessingError(msg);
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
