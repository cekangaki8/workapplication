package com.dpwn.smartscanus.events;

/**
 * Created by fshamim on 16.04.14.
 */
public class RingStateChangeEvent {
    private final int state;

    /**
     * Creates the Ringstate Change event
     * @param state value should be one of the values specified in BluetoothSPPConnection class
     *              STATE_* constants.
     */
    public RingStateChangeEvent(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
