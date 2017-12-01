package com.dpwn.smartscanus.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Set;

import com.dpwn.smartscanus.events.RefreshEvent;
import com.dpwn.smartscanus.events.RingBeepEvent;
import com.dpwn.smartscanus.events.RingReadEvent;
import com.dpwn.smartscanus.events.RingStateChangeEvent;
import com.dpwn.smartscanus.events.ShowToastEvent;
import com.dpwn.smartscanus.logging.L;
import com.dpwn.smartscanus.main.App;

/**
 * Connection handler class for bluetooth device connections
 * supports ringscanner for now.
 * Created by mmansar on 07.10.14.
 */
@Singleton
public class ConnectionHandler {

    private static final java.lang.String TAG = ConnectionHandler.class.getSimpleName();
    private Bus mBus;

    private BluetoothSPPConnection deviceConnection;

    /**
     * Empty constructor needed for singleton
     */
    @Inject
    public ConnectionHandler(Bus bus) {
        this.mBus = bus;
        mBus.register(this);
    }

    /**
     * Connect to a bluetooth device through its mac macAddress using SPP protocol.
     *
     * @param macAddress mac address of the device to connect
     */
    public void connectToDevice(String macAddress) {
        mBus.post(new RefreshEvent());

        if (this.deviceConnection == null) {
            mBus.register(this);
            final BluetoothDevice device = getBluetoothDevice(macAddress);
            this.deviceConnection = new BluetoothSPPConnection(new DeviceHandler(mBus));

            App.executeInBackground(new Runnable() {
                @Override
                public void run() {
                    // a persistent timestamp of last disconnection should be read and waited out
                    // until a new  connection can be made
                    deviceConnection.connect(device);
                }
            });
        } else {
            new DisconnectAndReconnectTask().execute(macAddress);
        }
    }

    private class DisconnectAndReconnectTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            disconnectDevice();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

                L.e(TAG, "waiting for disconnect interrupted");
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(String macAddress) {
            connectToDevice(macAddress);
        }
    }

    private BluetoothDevice getBluetoothDevice(String macAddress) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        //if the mac address is available in the paired devices
        Set<BluetoothDevice> devices = adapter.getBondedDevices();
        for (BluetoothDevice device : devices) {
            if (device.getAddress().equalsIgnoreCase(macAddress)) {
                return device;
            }
        }
        return adapter.getRemoteDevice(macAddress);
    }

    /**
     * Disconnect the connected device if any and also unregisters the bus.
     */
    public void disconnectDevice() {
        L.d(TAG, "disconnect");
        if (this.deviceConnection != null) {
            this.deviceConnection.disconnect();
            this.deviceConnection = null;
            mBus.unregister(this);
        }
    }

    /**
     * Subscribe method for RingerrorEvent
     *
     * @param event ring error event
     */
    @Subscribe
    public void sendErrorBeepToRingscanner(RingBeepEvent event) {
        if (this.deviceConnection != null && this.deviceConnection.getState() == BluetoothSPPConnection.STATE_CONNECTED) {
            this.deviceConnection.write(new byte[]{7});
        }
    }

    /**
     * Returns the status of the ring scanner connection
     *
     * @return connection status
     */
    public int getRingscannerConnectionState() {
        int ret = BluetoothSPPConnection.STATE_NONE;
        if (deviceConnection != null) {
            ret = deviceConnection.getState();
        }
        return ret;
    }

    /**
     * Get the current connected device if any
     *
     * @return
     */
    public BluetoothDevice getConnectedDevice() {
        BluetoothDevice ret = null;
        if (deviceConnection != null) {
            ret = deviceConnection.getBtDevice();
        }
        return ret;
    }

    /**
     * connection of the device was lost.
     */
    public void deviceDisconnected() {
        if (deviceConnection != null) {
            deviceConnection.deviceDisconnected();
        }
    }

    /**
     * Bluetooth Device handler optimized for ringscanner
     */
    static class DeviceHandler extends Handler {

        private final Bus bus;

        private DeviceHandler(Bus bus) {
            this.bus = bus;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothSPPConnection.MESSAGE_STATE_CHANGE:
                    bus.post(new RingStateChangeEvent(msg.arg1));
                    break;
                case BluetoothSPPConnection.MESSAGE_WRITE:
                    break;
                case BluetoothSPPConnection.MESSAGE_SPP_READ:
                    bus.post(new RingReadEvent(msg));
                    break;
                case BluetoothSPPConnection.MESSAGE_DEVICE_NAME:
                    break;
                case BluetoothSPPConnection.MESSAGE_TOAST:
                    break;
                default:
                    bus.post(new ShowToastEvent("Unknown scanner connection error occurred: please restart the app and try again!", ShowToastEvent.ToastDuration.LONG)
                            .setColor(ShowToastEvent.ToastColor.RED));
                    break;
            }
        }
    }
}
