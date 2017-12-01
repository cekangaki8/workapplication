package com.dpwn.smartscanus.kiosk;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.google.inject.Inject;

import com.dpwn.smartscanus.main.App;
import com.dpwn.smartscanus.utils.PrefUtils;
import roboguice.receiver.RoboBroadcastReceiver;

/**
 * Created by fshamim on 01.04.15.
 */
public class OnScreenOffReceiver extends RoboBroadcastReceiver {

    @Inject
    private PrefUtils prefUtils;

    @Override
    protected void handleReceive(Context context, Intent intent) {
        if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
            App ctx = (App) context.getApplicationContext();
            if (!prefUtils.isAdminMode()) {
                wakeUpDevice(ctx);
            }
        }
    }

    private void wakeUpDevice(App context) {
        PowerManager.WakeLock wakeLock = context.getWakeLock(); // get WakeLock reference via AppContext
        if (wakeLock.isHeld()) {
            wakeLock.release(); // release old wake lock
        }
        // create a new wake lock...
        wakeLock.acquire();
        // ... and release again
        wakeLock.release();
    }
}
