package com.dpwn.smartscanus.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

import com.google.inject.Inject;

import roboguice.receiver.RoboBroadcastReceiver;

/**
 * Created by fshamim on 27.02.14.
 */
public class BluetoothReceiver extends RoboBroadcastReceiver {

    @Inject
    private ConnectionHandler connectionHandler;

    @Override
    public void handleReceive(Context context, Intent intent) {
        BluetoothDevice bd = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        if (intent.getAction().equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
            BluetoothDevice btDevice = connectionHandler.getConnectedDevice();
            if (btDevice != null && btDevice.getAddress().equals(bd.getAddress())) {
                connectionHandler.deviceDisconnected();
            }
        }
    }
}