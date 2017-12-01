package com.dpwn.smartscanus.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import com.dpwn.smartscanus.logging.L;

/**
 * This class does all the work for setting up and managing Bluetooth
 * connections with other devices. It has a thread that listens for
 * incoming connections, a thread for connecting with a device, and a
 * thread for performing data transmissions when connected.
 */
public class BluetoothSPPConnection {
    // Debugging
    private static final String TAG = BluetoothSPPConnection.class.getSimpleName();

    // UUID for standard UUID Serial Port Profile
    private static final UUID SPP_UUID_RINGSCANNER = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public static final String SPP_READ = "spp_read";
    public static final String MAC_ADDRESS = "mac_add";

    // handler messaging fields
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_DEVICE_NAME = 2;
    public static final int MESSAGE_TOAST = 4;
    public static final int MESSAGE_WRITE = 6;
    public static final int MESSAGE_SPP_READ = 7;

    // Member fields
    private Handler mHandler;
    private ConnectThread mConnectThread;
    private ReadThread readThread;
    private WriteThread writeThread;
    private volatile int mState;

    // Constants that indicate the current connection state
    // we're doing nothing
    public static final int STATE_NONE = 0;
    // now initiating an outgoing connection
    public static final int STATE_CONNECTING = 1;
    // now connected to a remote device
    public static final int STATE_CONNECTED = 2;
    // connection lost to remote device
    public static final int STATE_CONNECTION_FAILED = 3;

    // bluetooth fields
    private volatile BluetoothSocket btSocket;

    // Blocking queues for synchronisation between threads
    private LinkedBlockingQueue<byte[]> readQueue;
    private LinkedBlockingQueue<byte[]> writeQueue;
    private BluetoothAdapter btAdapter;
    private BluetoothDevice mBtDevice;

    /**
     * Prepares a new BluetoothChat session.
     *
     * @param handler A Handler to send messages back to the UI Activity
     */
    public BluetoothSPPConnection(Handler handler) {
        mState = STATE_NONE;
        mHandler = handler;
    }

    /**
     * Set the current state of the chat connection
     *
     * @param state An integer defining the current connection state
     */
    private synchronized void setState(int state, Bundle data) {
        L.d(TAG, "setState() " + mState + " -> " + state);
        mState = state;
        // Give the new state to the Handler so the UI Activity can update
        Message msg = mHandler.obtainMessage(MESSAGE_STATE_CHANGE, state, -1);

        if (data != null) {
            msg.setData(data);
        }
        msg.sendToTarget();
    }

    /**
     * Get the current state
     *
     * @return current state
     */
    public int getState() {
        return mState;
    }

    /**
     * Start the ConnectThread to initiate a connection to a remote device.
     */
    public synchronized void connect(BluetoothDevice device) {
        L.d(TAG, "connect to: " + device);


        // Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING && mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        // Start the thread to connect with the given device
        BlockingQueue<Boolean> connectQueue = new SynchronousQueue<Boolean>();
        readQueue = new LinkedBlockingQueue<byte[]>();
        writeQueue = new LinkedBlockingQueue<byte[]>();
        if (btAdapter == null) {
            btAdapter = BluetoothAdapter.getDefaultAdapter();
        }

        mBtDevice = btAdapter.getRemoteDevice(device.getAddress());
        Bundle data = new Bundle();
        data.putString(MAC_ADDRESS, mBtDevice.getAddress());

        try {
            setState(STATE_CONNECTING, null);
            mConnectThread = new ConnectThread(mBtDevice, connectQueue);
            mConnectThread.start();
            // start the blocking call to make the connection.
            // following call makes the connect() a blocking method
            Boolean isConnected = connectQueue.take();

            if (isConnected) {
                setState(STATE_CONNECTED, data);
                writeThread = new WriteThread(btSocket, writeQueue);
                readThread = new ReadThread(btSocket, readQueue);
                writeThread.start();
                readThread.start();
            } else {
                // no need to set state here since connect thread will set the state
                L.d(TAG, "Connection failed ---");
            }
        } catch (InterruptedException e) {
            L.e(TAG, "Connection failed #####");
            // no need to set state here since connect thread will set the state
        }
    }

    /**
     * disconnect any existing sockets and shutdown all threads
     */
    public synchronized void disconnect() {
        L.d(TAG, "disconnect");

        if (readThread != null) {
            readThread.cancel();
            readThread = null;
        }

        if (writeThread != null) {
            writeThread.cancel();
            writeThread = null;
        }

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        mBtDevice = null;
    }


    /**
     * Blocking read for the connected bluetooth socket.
     *
     * @return data that being read from the socket
     */
    public byte[] read() throws IOException {
        byte[] b = null;
        while (b == null) {
            b = readQueue.poll();
        }
        return b;
    }

    /**
     * Write to the ConnectedThread in an unsynchronized manner
     *
     * @param data The bytes to write
     * @see ConnectThread#write(byte[])
     */
    public boolean write(byte[] data) {
        boolean ret = false;
        try {
            if (data != null) {
                writeQueue.put(data);
                ret = true;
            }
        } catch (InterruptedException e) {
            L.e(TAG, "write error on queue", e);
            connectionLost();
        }
        return ret;
    }

    /**
     * Indicate that the connection attempt failed and notify the UI Activity.
     */
    private synchronized void connectionFailed() {
        setState(STATE_CONNECTION_FAILED, null);
    }

    /**
     * Indicate that the connection was lost and notify the UI Activity.
     */
    private synchronized void connectionLost() {
        setState(STATE_CONNECTION_FAILED, null);
    }

    private void sendReadToHandler(byte[] data) {
        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(MESSAGE_SPP_READ);
        Bundle bundle = new Bundle();
        bundle.putByteArray(SPP_READ, data);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    public BluetoothDevice getBtDevice() {
        return mBtDevice;
    }

    /**
     * Device was disconnected without the knowledge of this class.
     */
    public void deviceDisconnected() {
        disconnect();
        setState(STATE_NONE, null);
    }

    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread {
        private BluetoothSocket mBsocket;
        private final BluetoothDevice mBDevice;
        private BlockingQueue<Boolean> mConnectQueue;

        /**
         * A Thread to facilitate connecting to a bluetooth socket.
         *
         * @param device       bluetooth device to connect to.
         * @param connectQueue connection queue for synchronization btw. threads.
         */
        public ConnectThread(BluetoothDevice device,
                             BlockingQueue<Boolean> connectQueue) {
            mBDevice = device;
            BluetoothSocket tmp = null;
            this.mConnectQueue = connectQueue;
            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                tmp = device.createRfcommSocketToServiceRecord(SPP_UUID_RINGSCANNER);
            } catch (Exception e) {
                L.e(TAG, "create() failed", e);
            }
            mBsocket = tmp;
        }

        /**
         * Close and initiate the current socket
         */
        public void cancel() {
            try {
                if (mBsocket != null) {
                    mBsocket.close();
                }
            } catch (IOException e) {
                //unpair the device if the last connection was failed and it was paired
                unpair(mBDevice);
                L.e(TAG, "close() of connect socket failed", e);
            } finally {
                mBsocket = null;
            }
        }

        private void relayConnectionSuccess() {
            try {
                // notify calling thread that connection succeeded
                mConnectQueue.put(Boolean.TRUE);
            } catch (InterruptedException e) {
                L.e(TAG, "ConnectThread is error ", e);
            }
            L.d(TAG, "Connection success -- is connected to "
                            + mBDevice.getName()
            );
            // allow main thread to read connected
            yield();
        }

        private void relyConnectionFailure(IOException e) {
            try {
                // notify calling thread that connection failed
                mConnectQueue.put(Boolean.FALSE);
                connectionFailed();
                L.e(TAG, "ConnectFailure: ", e);
            } catch (InterruptedException e1) {
                L.e(TAG, "ConnectThread is error ", e1);
            }

            if (mBsocket != null) {
                cancel();
            }
        }

        @Override
        public void run() {
            setName("ConnectThread");
            L.i(TAG, "on Run ConnectThread");
            // Make a connection to the BluetoothSocket
            // This is a blocking call and will only return on a
            // successful connection or an exception
            try {
                mBsocket.connect();
            } catch (IOException e) {
                relyConnectionFailure(e);
                return;
            }
            btSocket = mBsocket;
            relayConnectionSuccess();
        }
    }

    private void unpair(BluetoothDevice device) {
        try {
            Method m = device.getClass()
                    .getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
        } catch (Exception e) {
            L.e(TAG, e.getMessage(), e);
        }
    }

    /**
     * Reader Thread which after initializing listens to the socket input streams
     * and puts it in the Queue
     */
    private class ReadThread extends Thread {
        private InputStream is;
        boolean running = true;
        LinkedBlockingQueue<byte[]> readQueue;

        /**
         * Helper reader thread
         *
         * @param socket    Bluetooth socket to listen to.
         * @param readQueue queue for the putting the read bytes into.
         */
        public ReadThread(BluetoothSocket socket,
                          LinkedBlockingQueue<byte[]> readQueue) {
            try {
                is = socket.getInputStream();
                L.d(TAG, "socket is connected to: "
                        + socket.getRemoteDevice().getName());
                this.readQueue = readQueue;
            } catch (IOException e) {
                L.e(TAG, "ReadThread is error ", e);

            }
        }

        /**
         * interuppt this thread and also clear the queue
         */
        public void cancel() {
            running = false;
            readQueue.clear();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    L.e(TAG, "failed to close Input Stream", e);
                }
            }
            interrupt();
        }

        @Override
        public void run() {
            setName("Reader Thread");
            byte[] buffer = new byte[128];
            int bytesRead;
            running = true;
            while (running) {
                try {
                    bytesRead = is.read(buffer);
                    if (bytesRead != -1) {
                        L.d(TAG, "In: " + Arrays.toString(buffer));
                        sendReadToHandler(buffer);
                        //read queue not needed any more
                        buffer = new byte[128];
                    }
                } catch (IOException e) {
                    L.e(TAG, "ReadThread queue error " + e);
                    running = false;
                }
            }
        }
    }

    private class WriteThread extends Thread {
        private OutputStream os;
        private boolean running = true;
        LinkedBlockingQueue<byte[]> writeQueue;

        /**
         * Writer thread for writing into the connected bluetooth socket
         *
         * @param socket     Bluetooth socket to write to.
         * @param writeQueue writing Queue from where this thread takes the bytes.
         */
        public WriteThread(BluetoothSocket socket,
                           LinkedBlockingQueue<byte[]> writeQueue) {
            try {
                os = socket.getOutputStream();
                this.writeQueue = writeQueue;
            } catch (IOException e) {
                L.e(TAG, "WriteThread Output Stream error " + e);
            }
        }

        /**
         * interrupts the current thread and clears the write queue
         */
        public void cancel() {
            running = false;
            writeQueue.clear();
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    L.e(TAG, "failed to close Output Stream", e);
                }
            }
            interrupt();
        }

        @Override
        public void run() {
            setName("writer thread");
            running = true;
            while (running) {
                try {
                    byte[] data;
                    data = writeQueue.take();
                    os.write(data);
                } catch (InterruptedException e) {
                    L.e(TAG, "WriteThread write error " + e);
                    running = false;
                } catch (IOException e) {
                    L.e(TAG, "WriteThread write error " + e);
                    running = false;
                    disconnect();
                    // no need to send message instead we use the BluetoothReceiver class.
                    mState = STATE_NONE;
                }
            }
        }
    }
}
