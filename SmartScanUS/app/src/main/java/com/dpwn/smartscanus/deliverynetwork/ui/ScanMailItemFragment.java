package com.dpwn.smartscanus.deliverynetwork.ui;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.deliverynetwork.IDeliveryNetworkScanningInputPort;
import com.dpwn.smartscanus.deliverynetwork.IDeliveryNetworkScanningOutputPort;
import com.dpwn.smartscanus.events.RingBeepEvent;
import com.dpwn.smartscanus.events.VibrateEvent;
import com.dpwn.smartscanus.interactor.IInteractorInputPort;
import com.dpwn.smartscanus.interactor.async.AsyncInteractorFragment;
import com.dpwn.smartscanus.newopsapi.resources.MessageResponse;
import com.dpwn.smartscanus.utils.PrefsConsts;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by cekangaki on 11/15/2017.
 */
public class ScanMailItemFragment extends AsyncInteractorFragment implements IDeliveryNetworkScanningOutputPort {
    private static final java.lang.String TAG = ScanMailItemFragment.class.getSimpleName();

    @InjectView(R.id.tvDeliveryNetworkScanMailItem)
    TextView tvScanMailItem;

    @InjectView(R.id.tv_dnsm_ErrorMessage)
    TextView tvErrorMessage;

    @InjectView(R.id.ll_dnScanMailItem)
    LinearLayout dnMessageArae;

    @Inject
    IDeliveryNetworkScanningInputPort interactor;

    /**
     * Abstract method to get the interactors of the implementing Fragment
     *
     * @return web interactors for registration as a output ports
     */
    @Override
    public List<IInteractorInputPort> getInteractors() {
        ArrayList<IInteractorInputPort> interactors = new ArrayList<IInteractorInputPort>();
        interactors.add(interactor);
        return interactors;
    }

    /**
     * Event handler method when barcode from the ring scanner is read.
     *
     * @param barcode read from scanner
     */
    @Override
    public void onBarcodeRead(String barcode) {
        if (outputPortHelper.isInProgress()) {
            tts("Please wait while in progress");
        } else {
            tvScanMailItem.setText(barcode);
            interactor.processMailItem(barcode);
        }
    }

    /**
     * Abstract method for initializing the layout with the default method overrides like
     * onCreateView etc.
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_deliverynetwork_scanmailitem;
    }

    /**
     * OnClick EventListener method that is called by OnClickSender
     *
     * @param view widget that is being clicked.
     */
    @Override
    public void onClick(View view) {

    }

    /**
     * This will handle all positive respsonse returned from the API
     *
     * @param msg
     */
    @Override
    public void scanProcessedSuccessfully(MessageResponse msg) {
        dnMessageArae.setBackgroundColor(Color.GREEN);
        tvErrorMessage.setText(msg.getMsg());
    }

    /**
     * This method will handle all negative response returned from the API
     *
     * @param msg
     */
    @Override
    public void scanProcessingError(MessageResponse msg) {
        dnMessageArae.setBackgroundColor(Color.RED);
        tvErrorMessage.setText(msg.getMsg());
        bus.post(new VibrateEvent(PrefsConsts.VIB_DURATION));
        tts(msg.getMsg());
        bus.post(new RingBeepEvent());
    }
}
