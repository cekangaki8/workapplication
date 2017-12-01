package com.dpwn.smartscanus.utils.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.inject.Inject;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.events.RingBeepEvent;
import com.dpwn.smartscanus.events.RingReadEvent;
import com.dpwn.smartscanus.events.TTSEvent;
import com.dpwn.smartscanus.events.VibrateEvent;
import com.dpwn.smartscanus.logging.L;
import com.dpwn.smartscanus.utils.PrefsConsts;
import com.dpwn.smartscanus.utils.Utils;

/**
 * Abstract Fragment that handles the indication of the progress communicated from the web interactors.
 * Created by fshamim on 18.09.14.
 */
public abstract class ScannerFragment extends OnClickableFragment {

    private static final String TAG = ScannerFragment.class.getSimpleName();

    @Inject
    protected Bus bus;

    /**
     * This interface is needed since bus cannot register subscribers in parent classes
     */
    interface IScannerReader {
        /**
         * Subscriber method that will the ring read event from the bus
         *
         * @param event containing the barcode
         */
        void onScannerRead(RingReadEvent event);
    }

    private IScannerReader busEventListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        busEventListener = new IScannerReader() {

            @Subscribe
            @Override
            public void onScannerRead(RingReadEvent event) {
                String barcode = Utils.extractBarcode(event);
                if (barcode != null) {
                    L.d(TAG, "onScannerRead: " + barcode);
                    if (dlgUtils.isShowing()) {
                        vibrate();
                        tts("Please check error first");
                    } else {
                        onBarcodeRead(barcode);
                    }
                } else {
                    L.e(TAG, "Barcode cannot be processed");
                }
            }
        };
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        activity.getMenuInflater().inflate(R.menu.input, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(busEventListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(busEventListener);
    }


    protected void vibrate() {
        bus.post(new VibrateEvent(PrefsConsts.VIB_DURATION));
    }

    protected void tts(String msg) {
        bus.post(new TTSEvent(msg));
    }

    protected void beep() {
        bus.post(new RingBeepEvent());
    }

    /**
     * Event handler method when barcode from the ring scanner is read.
     *
     * @param barcode read from scanner
     */
    public abstract void onBarcodeRead(String barcode);
}
