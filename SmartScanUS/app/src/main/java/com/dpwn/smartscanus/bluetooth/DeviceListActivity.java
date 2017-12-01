/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dpwn.smartscanus.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.dpwn.smartscanus.events.ShowToastEvent;
import com.dpwn.smartscanus.utils.PrefUtils;
import com.dpwn.smartscanus.utils.ui.CustomizedDialog;
import com.google.inject.Inject;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Set;

import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.events.RingStateChangeEvent;
import com.dpwn.smartscanus.logging.L;
import com.dpwn.smartscanus.utils.PrefsConsts;
import com.dpwn.smartscanus.utils.ui.ExpandableHeightListViewList;


import org.apache.commons.lang3.StringUtils;

import roboguice.activity.RoboActivity;


/**
 * This Activity appears as a dialog. It lists any paired devices and
 * devices detected in the area after discovery. When a device is chosen
 * by the user, the MAC address of the device is sent back to the parent
 * Activity in the result Intent.
 */
public class DeviceListActivity extends RoboActivity {
    // Debugging
    private static final String TAG = DeviceListActivity.class.getSimpleName();

    public static final String RECONFIGURE_THIS_SCANNER = "Reconfigure this scanner";
    private static final String MAC_C4_7D_CC_CAPS = "C4:7D:CC:";
    private static final String MAC_C4_7D_CC_SMALL = "c4:7d:cc:";

    @Inject
    private Bus mBus;
    @Inject
    private SharedPreferences sharedPrefs;
    @Inject
    protected PrefUtils prefUtils;

    @Inject
    private ConnectionHandler connectionHandler;


    public static final String EXTRA_DEVICE_ADDRESS = "device_address";
    public static final int EXTRA_DEVICE_NOT_CONFIGURED = 100;
    private ExpandableHeightListViewList connectedDevicesListView;
    private TextView connectedDevicesTitle;
    private EditText connectedDevice;
    ArrayAdapter<String> mConnectedDeviceArrayAdapter;
    // Member fields
    private BluetoothAdapter mBtAdapter;
    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private BroadcastReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.device_list);

        // Set result CANCELED in case the user backs out
        setResult(Activity.RESULT_CANCELED);

        // Initialize the button to perform device discovery
        Button scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                doDiscovery();
            }
        });

        // Initialize array adapters. One for already paired devices and
        // one for newly discovered devices
        mConnectedDeviceArrayAdapter = new RemoveArrayAdapter(this, R.layout.device_list_item_connected_update);
        ArrayAdapter<String> mPairedDevicesArrayAdapter = new CustomArrayAdapter(this, R.layout.device_list_item);
        ArrayAdapter<String> mNewDevicesArrayAdapter = new CustomArrayAdapter(this, R.layout.device_list_item);

        connectedDevicesTitle = (TextView) findViewById(R.id.title_connected_devices);
        connectedDevice = (EditText) findViewById(R.id.et_connected_device);
        connectedDevicesListView = (ExpandableHeightListViewList) findViewById(R.id.lv_connected_devices);
        connectedDevicesListView.setAdapter(mConnectedDeviceArrayAdapter);

        if (connectionHandler.getRingscannerConnectionState() == BluetoothSPPConnection.STATE_CONNECTED) {
            String lastSelectedDeviceAddress = sharedPrefs.getString(PrefsConsts.LAST_CONNECTED_DEVICE, null);
            if (lastSelectedDeviceAddress != null) {
                String deviceName = sharedPrefs.getString(PrefsConsts.CONNECTED_DEVICE_NAME, null);
                if (StringUtils.isNotBlank(deviceName)) {
                    mConnectedDeviceArrayAdapter.add(deviceName);
                } else {
                    mConnectedDeviceArrayAdapter.add(lastSelectedDeviceAddress);
                }
                connectedDevicesListView.setAdapter(mConnectedDeviceArrayAdapter);
                connectedDevicesListView.setVisibility(View.VISIBLE);
                connectedDevicesTitle.setVisibility(View.VISIBLE);
            }
        }

        ExpandableHeightListViewList pairedListView = (ExpandableHeightListViewList) findViewById(R.id.lv_paired_devices);
        pairedListView.setExpanded(true);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // Find and set up the ListView for newly discovered devices

        ExpandableHeightListViewList newDevicesListView = (ExpandableHeightListViewList) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);
        newDevicesListView.setExpanded(true);


        mReceiver = new BluetoothDiscoveryReceiver(this, mNewDevicesArrayAdapter);
        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (!pairedDevices.isEmpty()) {
            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            pairedListView.setClickable(true);
            mPairedDevicesArrayAdapter.clear();
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else if (mConnectedDeviceArrayAdapter.isEmpty()) {
            pairedListView.setClickable(false);
            String noDevices = getResources().getText(R.string.none_paired).toString();
            mPairedDevicesArrayAdapter.add(noDevices);
        }

    }

    static class CustomArrayAdapter extends ArrayAdapter<String> {

        /**
         * Default constructor
         *
         * @param context
         * @param resource
         */
        public CustomArrayAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            if (view != null) {
                TextView tvDeviceName = (TextView) view.findViewById(R.id.tv_device_name);
                tvDeviceName.setError(null);
                tvDeviceName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                String data = getItem(position);
                if (data.contains(MAC_C4_7D_CC_CAPS) || data.contains(MAC_C4_7D_CC_SMALL)) {
                    if (data.contains(RECONFIGURE_THIS_SCANNER)) {
                        tvDeviceName.setError("");
                    }
                    tvDeviceName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_ring_scanner, 0);
                }
            }
            return view;
        }
    }

    static class RemoveArrayAdapter extends ArrayAdapter<String> {
        private final Activity context;

        public RemoveArrayAdapter(Activity context, int resource) {
            super(context, resource);
            this.context = context;
        }

        static class ViewHolder {
            protected EditText editText;
            protected Button button;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                LayoutInflater inflator = context.getLayoutInflater();
                view = inflator.inflate(R.layout.device_list_item_connected_update, null);
                final ViewHolder viewHolder = new ViewHolder();
                viewHolder.editText = (EditText) view.findViewById(R.id.edtConnected);
                viewHolder.button = (Button) view.findViewById(R.id.btnUpdateDeviceName);
                view.setTag(viewHolder);
                viewHolder.button.setOnClickListener(
                        new CompoundButton.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                DeviceListActivity activity = (DeviceListActivity) context;
                                String enteredDeviceName = viewHolder.editText.getText().toString();
                                if (StringUtils.isBlank(enteredDeviceName)) {
                                    final String currentDeviceName = activity.sharedPrefs.getString(PrefsConsts.CONNECTED_DEVICE_NAME,
                                            activity.sharedPrefs.getString(PrefsConsts.LAST_CONNECTED_DEVICE, null));

                                    CustomizedDialog m = new CustomizedDialog(activity, activity.mBus, activity.prefUtils)
                                            .setMessage("Bluetooth device name cannot be left blank. Please enter a valid name.")
                                            .setDrawable(R.drawable.ic_warning_red)
                                            .setPositiveButton(activity.getString(android.R.string.yes), new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    viewHolder.editText.setText(currentDeviceName);
                                                }
                                            });
                                    m.show();
                                    return;
                                }
                                activity.sharedPrefs.edit().putString(PrefsConsts.CONNECTED_DEVICE_NAME, enteredDeviceName).apply();
                                activity.finish();
                            }
                        }
                );
            }
            ViewHolder holder = (ViewHolder) view.getTag();
            String data = getItem(position);
            holder.editText.setText(data);
            return view;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mBus.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {
        L.d(TAG, "doDiscovery()");
        ExpandableHeightListViewList newDevicesListView = (ExpandableHeightListViewList) findViewById(R.id.new_devices);
        try {
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) newDevicesListView.getAdapter();
            if (adapter.getCount() == 1 && adapter.getItem(0).equalsIgnoreCase(getString(R.string.none_found))) {
                adapter.clear();
            }
        } catch (Exception e) {
            L.e(TAG, e.getMessage(), e);
        }
        // Indicate scanning in the title
        setProgressBarIndeterminateVisibility(true);
        Button scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setClickable(false);
        scanButton.setText(getString(R.string.scanning));
        findViewById(R.id.prg_scanning).setVisibility(View.VISIBLE);
        // If we're already discovering, disconnect it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
    }

    // The on-click listener for all devices in the ListViews
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            if (info.startsWith(getString(R.string.none_found)) ||
                    info.startsWith(getString(R.string.none_paired))) {
                return;
            }

            // Cancel discovery because it's costly and we're about o connect
            mBtAdapter.cancelDiscovery();

            String address = info.substring(info.length() - 17);

            Intent intent = new Intent();
            // Create the result Intent and include the MAC address
            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

            //if the device is not configured right it will have null as a name
            if (info.contains(RECONFIGURE_THIS_SCANNER)) {
                setResult(EXTRA_DEVICE_NOT_CONFIGURED, intent);
            } else {
                setResult(Activity.RESULT_OK, intent);
            }
            finish();
        }
    };

    /**
     * Receiver for updating the device list when a device is discovered
     */
    private static class BluetoothDiscoveryReceiver extends BroadcastReceiver {
        //Android SPP device class numbers
        public static final int SPP_DEVICE_CLASS = 1568;
        public static final int SPP_MAJOR_DEVICE_CLASS = 1536;

        private final ArrayAdapter<String> newDevicesArrayAdapter;
        private final Activity activity;

        /**
         * Receiver for updating the device list when a device is discovered
         *
         * @param activity current context
         * @param adapter  adapter for adding devices to.
         */
        public BluetoothDiscoveryReceiver(Activity activity, ArrayAdapter<String> adapter) {
            this.newDevicesArrayAdapter = adapter;
            this.activity = activity;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction(), deviceTmp;
            ExpandableHeightListViewList newDevicesListView = (ExpandableHeightListViewList) activity.findViewById(R.id.new_devices);
            Button scanButton = (Button) activity.findViewById(R.id.button_scan);
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Turn on sub-title for new devices
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    //Avoiding not found message.
                    activity.findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);
                    if (!newDevicesListView.isClickable()) {
                        newDevicesArrayAdapter.clear();
                    }

                    String name = device.getName();
                    String address = device.getAddress();

                    deviceTmp = getListItemText(name, address, device);

                    if (newDevicesArrayAdapter.getPosition(deviceTmp) == -1) {
                        newDevicesArrayAdapter.add(deviceTmp);
                        newDevicesListView.setClickable(true);
                    }

                }
                // When discovery is finished
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                activity.setProgressBarIndeterminateVisibility(false);
                scanButton.setClickable(true);
                scanButton.setText(activity.getString(R.string.button_scan));
                activity.findViewById(R.id.prg_scanning).setVisibility(View.GONE);
                if (newDevicesArrayAdapter.getCount() == 0) {
                    String noDevices = activity.getResources().getText(R.string.none_found).toString();
                    newDevicesArrayAdapter.add(noDevices);
                    newDevicesListView.setClickable(false);

                }
            }
        }

        private String getListItemText(String name, String address, BluetoothDevice device) {
            String itmText = "";
            if (startsWithRingScannerMac(address)) {
                if (!isRingScannerConfigured(device)) {
                    itmText = RECONFIGURE_THIS_SCANNER + "\n";
                } else if (name != null) {
                    itmText = name + "\n";
                }
            }
            itmText += address;
            return itmText;
        }

        private boolean startsWithRingScannerMac(String address) {
            return address.startsWith(MAC_C4_7D_CC_CAPS) || address.startsWith(MAC_C4_7D_CC_SMALL);
        }

        private boolean isRingScannerConfigured(BluetoothDevice device) {
            boolean ret = true;
            if (device.getBluetoothClass().getDeviceClass() != SPP_DEVICE_CLASS
                    && device.getBluetoothClass().getMajorDeviceClass() != SPP_MAJOR_DEVICE_CLASS) {
                ret = false;
            }
            return ret;
        }
    }

    /**
     * Subscribe method for Connection State change event for bluetooth device(Ringscanner)
     *
     * @param event event state
     */
    @Subscribe
    public void handleRingConnectionState(RingStateChangeEvent event) {
        L.d(TAG, "****onRingscanner State: " + event.getState());
        switch (event.getState()) {
            case BluetoothSPPConnection.STATE_CONNECTION_FAILED:
            case BluetoothSPPConnection.STATE_NONE:
                connectedDevicesListView.setVisibility(View.GONE);
                connectedDevicesTitle.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}
