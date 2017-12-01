package com.dpwn.smartscanus.events;

import android.os.Message;

/**
 * Created by fshamim on 16.04.14.
 */
public class RingReadEvent {

    private final Message msg;

    /**
     * Create Ring Read event with a message
     * @param msg contains the bytes read from the ringscanner
     */
    public RingReadEvent(final Message msg) {
        this.msg = msg;
    }

    public Message getMsg() {
        return msg;
    }
}
