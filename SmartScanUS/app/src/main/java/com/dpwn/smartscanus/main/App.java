package com.dpwn.smartscanus.main;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.os.PowerManager;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;

import com.google.inject.Inject;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.dpwn.smartscanus.bluetooth.BluetoothSPPConnection;
import com.dpwn.smartscanus.events.RingReadEvent;
import com.dpwn.smartscanus.events.ShowToastEvent;
import com.dpwn.smartscanus.events.TTSEvent;
import com.dpwn.smartscanus.events.VibrateEvent;
import com.dpwn.smartscanus.kiosk.OnScreenOffReceiver;
import com.dpwn.smartscanus.logging.L;
import com.dpwn.smartscanus.security.PRNGFixes;
import com.dpwn.smartscanus.utils.PrefUtils;
import com.dpwn.smartscanus.utils.PrefsConsts;
import com.dpwn.smartscanus.utils.Utils;
import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;

/**
 * Main app class
 * Created by fshamim on 04.09.14.
 */
public class App extends Application implements TextToSpeech.OnInitListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private TextToSpeech mTts;

    private Map<Integer, String> barcodeMap = new HashMap<Integer, String>();

    @Inject
    private Bus bus;
    @Inject
    private SharedPreferences sharedPrefs;
    @Inject
    private Vibrator vibrator;
    @Inject
    private PrefUtils prefUtils;
    private PowerManager.WakeLock wakeLock;
    private OnScreenOffReceiver onScreenOffReceiver;


    @Override
    public void onCreate() {
        super.onCreate();
        L.d("onCreate");
        // apply security patch
        PRNGFixes.apply();
        //initialize prefs
        RoboGuice.setBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE,
                RoboGuice.newDefaultRoboModule(this), new MainModules(this));
        RoboInjector injector = RoboGuice.getInjector(this);
        injector.injectMembers(this);
        bus.register(this);
        mTts = new TextToSpeech(this,
                this  // TextToSpeech.OnInitListener
        );

        registerKioskModeScreenOffReceiver();
        prefUtils.initAdminMode();
        getSharedPreferences(PrefsConsts.CALABASH_PREFS, MODE_APPEND).registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        L.e("onTerminate");
        bus.unregister(this);
    }

    /**
     * Subscribes to ShowToastEvent on the Bus
     *
     * @param event event to be processed
     */
    @Subscribe
    public void showToast(ShowToastEvent event) {
        Utils.getSuperToast(this, event).show();
    }

    /**
     * Subscribes to TTSEvent on the Bus
     *
     * @param event event to be processed
     */
    @Subscribe
    public void textToSpeech(TTSEvent event) {
        if (sharedPrefs.getBoolean(PrefsConsts.TTS, true)) {
            // Speak and drop all pending entries in the playback queue.
            mTts.speak(event.getText(),
                    TextToSpeech.QUEUE_FLUSH,
                    null);
        }
    }

    /**
     * Subscribes to VibrateEvent on the Bus
     *
     * @param event event to be processed
     */
    @Subscribe
    public void vibrate(VibrateEvent event) {
        vibrator.vibrate(event.getDuration());
    }

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @SuppressWarnings("NullableProblems")
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "Background #" + mCount.getAndIncrement());
            thread.setPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
            return thread;
        }
    };

    private static final Executor BACKGROUND_EXECUTOR_POOL = new ThreadPoolExecutor(2, 64, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(10), THREAD_FACTORY);

    /**
     * Call to execute some code in background.
     *
     * @param runnable Runnable to execute in background.
     */
    public static void executeInBackground(Runnable runnable) {
        BACKGROUND_EXECUTOR_POOL.execute(runnable);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTts.setLanguage(Locale.UK);
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Lanuage data is missing or the language is not supported.
                L.e("Language is not available.");
            }

        } else {
            // Initialization failed.
            L.e("Could not initialize TextToSpeech.");
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        /**
         * Using SharedPreferences to throw a RingReadEvent
         */
        if (key.equals(PrefsConsts.CALABASH_PREF_STATUS) && (sharedPreferences.getInt(PrefsConsts.CALABASH_PREF_STATUS, 0) != 0)) {
            Message msg = new Message();
            Bundle b = new Bundle();
            String barcode = getBarcode(sharedPreferences.getInt(PrefsConsts.CALABASH_PREF_STATUS, 0));
            b.putByteArray(BluetoothSPPConnection.SPP_READ, barcode.getBytes());
            msg.setData(b);
            bus.post(new RingReadEvent(msg));

        }
    }

    private void registerKioskModeScreenOffReceiver() {
        // register screen off receiver
        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        onScreenOffReceiver = new OnScreenOffReceiver();
        registerReceiver(onScreenOffReceiver, filter);
    }

    public PowerManager.WakeLock getWakeLock() {
        if (wakeLock == null) {
            // lazy loading: first call, create wakeLock via PowerManager.
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "wakeup");
        }
        return wakeLock;
    }

    private String getBarcode(int barcode) {
        return barcodeMap.get(barcode);
    }

}
