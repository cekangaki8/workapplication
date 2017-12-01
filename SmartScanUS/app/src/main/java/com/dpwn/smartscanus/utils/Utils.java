package com.dpwn.smartscanus.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.github.johnpersano.supertoasts.SuperToast;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.bluetooth.BluetoothSPPConnection;
import com.dpwn.smartscanus.events.RingReadEvent;
import com.dpwn.smartscanus.events.ShowToastEvent;
import com.dpwn.smartscanus.logging.L;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedString;

/**
 * Created by fshamim on 04.11.14.
 */
public class Utils {
    private static final String TAG = Utils.class.getSimpleName();
    private static Gson gson = new Gson();
    private static final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_POWER, KeyEvent.KEYCODE_WAKEUP, KeyEvent.KEYCODE_CAMERA));

    private Utils() {
    }

    /**
     * Extracts the barcode from Ring Read Event object
     *
     * @param event contains barcode
     * @return extracted barcode, null otherwise
     */
    public static String extractBarcode(RingReadEvent event) {
        Bundle data = event.getMsg().getData();
        String barcode = null;
        if (data != null) {
            byte[] readBuf = event.getMsg().getData().getByteArray(BluetoothSPPConnection.SPP_READ);
            if (readBuf != null) {
                try {
                    barcode = new String(readBuf, "UTF-8").trim();
                } catch (UnsupportedEncodingException e) {
                    L.e(TAG, "barcode decoding exception", e);
                }
            }
        } else {
            L.e(TAG, "Scanner read event data is null");
        }
        return barcode;
    }

    /**
     * Change the connection status of the ring scanner in the menu
     *
     * @param menu  menu containing the action
     * @param state of connection
     */
    public static void updateConnectionStatusIcon(Menu menu, int state) {
        L.d(TAG, "onUpdateStatus");
        if (menu != null) {
            MenuItem connectItem = menu.findItem(R.id.action_bt_connect);
            if (connectItem != null) {
                if (state == BluetoothSPPConnection.STATE_CONNECTED) {
                    connectItem.setIcon(R.drawable.ic_scanner_connected);
                } else {
                    connectItem.setIcon(R.drawable.ic_scanner_not_connected);
                }
            } else {
                L.e(TAG, "connectItem is null");
            }
        } else {
            L.e(TAG, "Menu is null");
        }
    }

    /**
     * do a deep copy of an object
     *
     * @param src   object to copy
     * @param clazz class of the object
     * @return a deep copy
     */
    public static <T extends Object> T deepCopy(T src, Class<T> clazz) {
        String json = gson.toJson(src);
        return gson.fromJson(json, clazz);
    }

    /**
     * do a deep copy on a list
     *
     * @param src   list to copy
     * @param clazz class of the embedded objects
     * @return a deep copy of the list
     */
    public static <T extends Object> List<T> deepCopyList(List<T> src, Class<T> clazz) {
        List<T> cloneList = new ArrayList<T>(src.size());
        for (T obj : src) {
            cloneList.add(deepCopy(obj, clazz));
        }
        return cloneList;
    }

    /**
     * forces the currently executing thread to sleep for the specified time duration.
     *
     * @param duration
     */
    public static void waitForDelay(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            L.d("Delay interrupted", e.getMessage(), e);
        }
    }

    public static RetrofitError createRetrofitError(int responseCode, String reason, Object jsonObjBody, Class clazz) {
        String url = "http://example.org";
        Response response = new Response(url, responseCode,
                reason,
                new ArrayList<Header>(),
                new TypedString(gson.toJson(jsonObjBody)));
        return RetrofitError.httpError("http://example.org", response,
                new GsonConverter(new Gson()),
                clazz);
    }

    public static SuperToast getSuperToast(Context context, ShowToastEvent event) {
        SuperToast toast = new SuperToast(context);
        toast.setAnimations(SuperToast.Animations.POPUP);
        toast.setText(event.getText());
        toast.setDuration(event.getDuration() == ShowToastEvent.ToastDuration.LONG ? SuperToast.Duration.LONG : SuperToast.Duration.SHORT);
        int textSize = SuperToast.TextSize.EXTRA_SMALL;
        switch (event.getTextSize()) {
            case SMALL:
                textSize = SuperToast.TextSize.SMALL;
                break;
            case MEDIUM:
                textSize = SuperToast.TextSize.MEDIUM;
                break;
            case LARGE:
                textSize = SuperToast.TextSize.LARGE;
                break;
        }
        toast.setTextSize(textSize);
        int color = SuperToast.Background.BLACK;
        switch (event.getColor()) {
            case RED:
                color = SuperToast.Background.RED;
                break;
            case GREEN:
                color = SuperToast.Background.GREEN;
                break;
            case BLUE:
                color = SuperToast.Background.BLUE;
                break;
            case PURPLE:
                color = SuperToast.Background.PURPLE;
                break;
            case ORANGE:
                color = SuperToast.Background.ORANGE;
                break;
            case GREY:
                color = SuperToast.Background.GRAY;
                break;
        }
        toast.setBackground(color);
        return toast;
    }

    public static boolean isKeyBlocked(int keyCode) {
        return blockedKeys.contains(keyCode);
    }

    public static boolean checkToAllowPowerDialog(KeyEvent event,boolean currentState){
        boolean showPowerDialog = currentState;
        if (event.getKeyCode() == KeyEvent.KEYCODE_POWER
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            showPowerDialog = event.getRepeatCount() < 5;
        } else if (event.getKeyCode() != KeyEvent.KEYCODE_POWER) {
            showPowerDialog = false;
        }
        return showPowerDialog;
    }
}
