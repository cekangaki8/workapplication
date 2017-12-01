package com.dpwn.smartscanus.utils.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.dpwn.smartscanus.deliverynetwork.ui.ScanMailItemFragment;
import com.dpwn.smartscanus.dspscanning.ui.DspScanningFragment;
import com.dpwn.smartscanus.events.TTSEvent;
import com.dpwn.smartscanus.fullServiceImb.ui.ScanFullSvcIMBFragment;
import com.dpwn.smartscanus.login.ui.LoginFragment;
import com.dpwn.smartscanus.main.ui.WifiCountdownFragment;
import com.dpwn.smartscanus.transport.ui.ScanReceptactleIntoTuFragment;
import com.dpwn.smartscanus.transport.ui.ScanTransportUnitFragment;
import com.dpwn.smartscanus.utils.PrefsConsts;
import com.google.inject.Inject;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import com.dpwn.smartscanus.BuildConfig;
import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.events.ShowToastEvent;
import com.dpwn.smartscanus.events.VibrateEvent;
import com.dpwn.smartscanus.logging.L;
import com.dpwn.smartscanus.main.App;
import com.dpwn.smartscanus.utils.PrefUtils;
import roboguice.activity.RoboFragmentActivity;
import roboguice.receiver.RoboBroadcastReceiver;

/**
 * This Activity allows the Clickable functionality to all its hosting fragments.
 * Add onClick attribute in the xml file for receiving the event.
 * Created by fshamim on 17.09.14.
 */
public class OnClickableActivity extends RoboFragmentActivity implements IOnClickEventSender {

    private static final java.lang.String TAG = OnClickableActivity.class.getSimpleName();
    @Inject
    protected Bus mBus;
    @Inject
    protected PrefUtils prefUtils;
    @Inject
    private DialogUtils dlgUtils;
    @Inject
    private SharedPreferences prefs;

    private List<IOnClickEventReceiver> onClickReceiverList;
    NetworkReceiver networkReceiver;
    BluetoothDisconnectReceiver bluetoothDisconnectReceiver;
    private ProgressUtils progressUtils;
    private CustomizedDialog m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressUtils = new ProgressUtils();
        onClickReceiverList = new ArrayList<IOnClickEventReceiver>();

        networkReceiver = new NetworkReceiver(this);
        bluetoothDisconnectReceiver = new BluetoothDisconnectReceiver(this);

        if (BuildConfig.BLUETOOTH_ON) {
            this.registerReceiver(bluetoothDisconnectReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        }
        if (BuildConfig.WIFI_ON) {
            this.registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (BuildConfig.WIFI_ON) {
            this.unregisterReceiver(networkReceiver);
        }
        if (BuildConfig.BLUETOOTH_ON) {
            this.unregisterReceiver(bluetoothDisconnectReceiver);
        }
    }

    @Override
    public void onClick(View view) {
        if (!onClickReceiverList.isEmpty()) {
            for (IOnClickEventReceiver receiver : onClickReceiverList) {
                receiver.onClick(view);
            }
        }
        //mBus.post(new VibrateEvent(100l));
    }

    @Override
    public void addOnClickEventReceiver(IOnClickEventReceiver receiver) {
        this.onClickReceiverList.add(receiver);
    }

    @Override
    public void removeOnClickEventReceiver(IOnClickEventReceiver receiver) {
        this.onClickReceiverList.remove(receiver);
    }

    /**
     * BluetoothDisconnectReceiver throw a dialog if the Bluetooth is disable
     */
    public class BluetoothDisconnectReceiver extends RoboBroadcastReceiver {
        private final Activity activity;

        /**
         * Constructor
         *
         * @Param activity
         */
        public BluetoothDisconnectReceiver(Activity activity) {
            this.activity = activity;
        }

        /**
         * handleReceive for ACTION_STATE_CHANGED
         */
        @Override
        public void handleReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                if (state == BluetoothAdapter.STATE_OFF) {
                    m = new CustomizedDialog(activity, mBus, prefUtils)
                            .setMessage(getString(R.string.dialog_turn_off_bluetooth))
                            .setDrawable(R.drawable.ic_warning_red)
                            .setPositiveButton(getString(android.R.string.yes), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    progressUtils.showProgress(OnClickableActivity.this, getString(R.string.turning_bl_on));
                                    App.executeInBackground(new Runnable() {
                                        @Override
                                        public void run() {
                                            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                                            mBluetoothAdapter.enable();
                                        }
                                    });


                                }
                            });
                    m.show();
                } else if (state == BluetoothAdapter.STATE_ON) {
                    L.i(TAG, "Bluetooth ON");
                    hideDialog(m);
                    progressUtils.hideProgress();
                }
            }
        }
    }

    private void hideDialog(CustomizedDialog m) {
        if (m != null && m.isShowing()) {
            m.dismiss();
        }
    }

    /**
     * Check the state of the Network. Throw a Dialog if the WIFI is disconnected.
     */
    public class NetworkReceiver extends RoboBroadcastReceiver {
        private final Activity activity;

        /**
         * Constructor
         *
         * @Param activity
         */
        public NetworkReceiver(Activity activity) {
            this.activity = activity;
        }

        /**
         * handleReceive for CONNECTIVITY_ACTION
         */
        @Override
        public void handleReceive(Context context, Intent intent) {
            final WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    L.i(TAG, "WiFi CONNECTED.");
                    hideDialog(m);
                    progressUtils.hideProgress();
                } else if (!wm.isWifiEnabled()) {
                    mBus.post(new ShowToastEvent("WIFI is disconnected.", ShowToastEvent.ToastDuration.LONG).setColor(ShowToastEvent.ToastColor.RED));
                    mBus.post(new TTSEvent(getString(R.string.dialog_turn_on_wifi)));
                    wm.setWifiEnabled(true);
                    progressUtils.showProgress(OnClickableActivity.this, getString(R.string.turning_wifi_on));
                } else {
                    Fragment frag = getSupportFragmentManager().findFragmentById(R.id.content_frame);
                    IFragmentContainer fragmentContainer = (IFragmentContainer) activity;
                    Bundle bundle = new Bundle();
                    bundle.putString(PrefsConsts.FRAGMENT_ORIENTATION, "PORTRAIT");
                    if (frag != null && frag instanceof LoginFragment) {
                        bundle.putString("previousFragment", "loginFragment");
                    } else if (frag != null && frag instanceof ScanFullSvcIMBFragment) {
                        bundle.putString("previousFragment", "ScanFullSvcIMBFragment");
                    } else if (frag != null && frag instanceof DspScanningFragment) {
                        bundle.putString("previousFragment", "DspScanningFragment");
                    } else if (frag != null && (frag instanceof ScanTransportUnitFragment || frag instanceof ScanReceptactleIntoTuFragment)) {
                        bundle.putString("previousFragment", "LoadTUFragment");
                    } else if (frag != null && frag instanceof ScanMailItemFragment) {
                        bundle.putString("previousFragment", "ScanMailItemFragment");
                    } else {
                        bundle.putString("previousFragment", "Dashboard");
                    }
                    fragmentContainer.setContentFragment(WifiCountdownFragment.class, true, bundle);
                }
            }


        }
    }
}