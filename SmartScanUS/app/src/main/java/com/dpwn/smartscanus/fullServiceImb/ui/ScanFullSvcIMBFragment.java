package com.dpwn.smartscanus.fullServiceImb.ui;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dpwn.smartscanus.events.VibrateEvent;
import com.dpwn.smartscanus.utils.PrefsConsts;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.newopsapi.resources.MessageResponse;
import com.dpwn.smartscanus.events.RingBeepEvent;
import com.dpwn.smartscanus.interactor.IInteractorInputPort;
import com.dpwn.smartscanus.interactor.async.AsyncInteractorFragment;
import com.dpwn.smartscanus.logging.L;
import com.dpwn.smartscanus.fullServiceImb.IFullServiceInputPort;
import com.dpwn.smartscanus.fullServiceImb.IFullServiceOutputPort;
import roboguice.inject.InjectView;

/**
 * Created by cekangak on 07.01.15.
 */
public class ScanFullSvcIMBFragment extends AsyncInteractorFragment implements IFullServiceOutputPort {

    private static final java.lang.String TAG = ScanFullSvcIMBFragment.class.getSimpleName();

    @InjectView(R.id.img_load_trans_scanner_laser)
    private ImageView imgLoadTransScannerLaser;

    @InjectView(R.id.etTrayCntrBarcode)
    private EditText etTrayCntrBarcode;

    @InjectView(R.id.tvMessage)
    private TextView tvMessage;

    @InjectView(R.id.img_fsimbscanner)
    private ImageView imgFSScanning;

    @Inject
    private IFullServiceInputPort interactor;

    /**
     * Empty Constructor needed
     */
    public ScanFullSvcIMBFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fsimb_scantubs;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAnimation();
    }

    private void setupAnimation() {
        Animation fadeInOutAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_out);
        imgLoadTransScannerLaser.startAnimation(fadeInOutAnimation);
    }

    @Override
    public List<IInteractorInputPort> getInteractors() {
        ArrayList<IInteractorInputPort> interactors = new ArrayList<IInteractorInputPort>();
        interactors.add(interactor);
        return interactors;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        L.d(TAG, "onConfigurationChange");
        super.onConfigurationChanged(newConfig);
        setupAnimation();
    }

    @Override
    public void onBarcodeRead(String barcode) {
        if (outputPortHelper.isInProgress()) {
            tts(getString(R.string.plz_wait_while_in_prg));
        } else {
            etTrayCntrBarcode.setText(barcode);
            interactor.scanFullServiceCotainers(barcode);
        }
    }

    @Override
    public void onClick(View view) {
        view.getId();
    }

    @Override
    public void fullServiceBCScanError(MessageResponse msg) {
        tvMessage.setText(msg.getMsg());
        tvMessage.setTextColor(Color.RED);
        imgFSScanning.setBackgroundColor(Color.RED);
        bus.post(new VibrateEvent(PrefsConsts.VIB_DURATION));
       tts(msg.getMsg());
        bus.post(new RingBeepEvent());
    }

    @Override
    public void fullServiceBCScanSuccess(MessageResponse msg) {
        tvMessage.setText(msg.getMsg());
        tvMessage.setTextColor(Color.GREEN);
        imgFSScanning.setBackgroundColor(Color.GREEN);

    }
}
