package com.dpwn.smartscanus.dspscanning.ui;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.dspscanning.IDSPScanOutputPort;
import com.dpwn.smartscanus.dspscanning.IDSPScanningInputPort;
import com.dpwn.smartscanus.events.RingBeepEvent;
import com.dpwn.smartscanus.events.VibrateEvent;
import com.dpwn.smartscanus.interactor.IInteractorInputPort;
import com.dpwn.smartscanus.interactor.async.AsyncInteractorFragment;
import com.dpwn.smartscanus.newopsapi.resources.MessageResponse;
import com.dpwn.smartscanus.utils.PrefsConsts;
import com.google.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by cekangak on 8/12/2015.
 */
public class DspScanningFragment extends AsyncInteractorFragment implements IDSPScanOutputPort {

    @Inject
    private IDSPScanningInputPort interactor;

    @Override
    public int getLayoutId() {return R.layout.fragment_dspscan_nextday;}

    @InjectView(R.id.tvNextDayScannedBarcode)
    private EditText tvBarcode;

    @InjectView(R.id.tvNextDayMessage)
    private TextView tvMessage;

    @InjectView(R.id.img_nextdayscan)
    private ImageView imgNextDayScan;

    @InjectView(R.id.ll_NextDayImage)
    private LinearLayout imageLayout;

    @Override
    public List<IInteractorInputPort> getInteractors() {
        ArrayList<IInteractorInputPort> interactors = new ArrayList<IInteractorInputPort>();
        interactors.add(interactor);
        return interactors;
    }

    @Override
    public void onBarcodeRead(String barcode) {
        if (outputPortHelper.isInProgress()) {
            tts("Please wait while in progress");
        } else {
            tvBarcode.setText(barcode);
            if (StringUtils.isNotBlank(barcode)) {
                if (StringUtils.length(barcode) == 21) {
                    interactor.processDSPContainerScan(barcode);
                } else if (StringUtils.length(barcode) == 24) {
                    interactor.processDSPSackScan(barcode);
                } else {
                    interactor.processDSPPieceScan(barcode);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        view.getId();
    }

    @Override
    public void scanProcessedSuccessfully(MessageResponse msg) {
        tvMessage.setText(msg.getMsg());
        tvBarcode.setTextColor(Color.BLACK);
       imgNextDayScan.setBackgroundColor(Color.GREEN);
        tvBarcode.setBackgroundColor(Color.GREEN);
        imageLayout.setBackgroundColor(Color.GREEN);
    }

    @Override
    public void scanProcessingError(MessageResponse msg) {
        tvBarcode.setTextColor(Color.BLACK);
        tvMessage.setText(msg.getMsg());
        imgNextDayScan.setBackgroundColor(Color.RED);
        tvBarcode.setBackgroundColor(Color.RED);
        imageLayout.setBackgroundColor(Color.RED);
        bus.post(new VibrateEvent(PrefsConsts.VIB_DURATION));
        tts(msg.getMsg());
        bus.post(new RingBeepEvent());
    }
}
