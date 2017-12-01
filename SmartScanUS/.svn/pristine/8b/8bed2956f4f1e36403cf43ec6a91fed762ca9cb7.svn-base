package com.dpwn.smartscanus.main;

import android.os.Handler;
import android.os.Looper;

import com.google.inject.Provider;
import com.squareup.otto.Bus;

/**
 * Main Thread Bus Provider for Roboguice injection system.
 * Created by fshamim on 04.09.14.
 */
public class MainThreadBusProvider implements Provider<Bus> {
    private static Bus bus;

    @Override
    public Bus get() {
        if (bus == null) {
            bus = new ThreadManagedBus("MAINBUS");
        }
        return bus;
    }

    /**
     * This Bus takes care of switching between the main thread and the background thread when
     * posting. Post event will be automatically
     */
    private static class ThreadManagedBus extends Bus{
        private final Handler mHandler = new Handler(Looper.getMainLooper());
        private Bus thisBus;

        /**
         * ThreadManagedBus needed for auto switching between ui and background thread.
         * @param busName name of the bus
         */
        public ThreadManagedBus(String busName){
            super(busName);
            thisBus = this;
        }

        @Override
        public void post(final Object event) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                super.post(event);
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        thisBus.post(event);
                    }
                });
            }
        }
    }
}
