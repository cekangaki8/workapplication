package com.dpwn.smartscanus.logging;


import com.orhanobut.logger.Logger;

/**
 * Logging helper class
 * Created by fshamim on 04.09.14.
 */
public class L {
    //prevent this class from instantiating
    private L() {
    }

    private static final String TAG = "GBM";

    /**
     * Info log
     *
     * @param tag log tag
     * @param msg log msg
     */
    public static void i(String tag, String msg) {
        Logger.i(L.TAG + "." + tag, msg);
    }

    /**
     * Error log
     *
     * @param tag log tag
     * @param msg log msg
     * @param e   exception to log
     */
    public static void i(String tag, String msg, Exception e) {
        Logger.i(L.TAG + "." + tag, msg);
    }

    /**
     * Debug log
     *
     * @param tag log tag
     * @param msg log msg
     */
    public static void d(String tag, String msg) {
        Logger.d(L.TAG + "." + tag, msg);
    }

    /**
     * Error log
     *
     * @param tag log tag
     * @param msg log msg
     * @param e   exception to log
     */
    public static void d(String tag, String msg, Exception e) {
        Logger.d(L.TAG + "." + tag, msg);
        Logger.e(e);
    }

    /**
     * Warning log
     *
     * @param tag log tag
     * @param msg log msg
     */
    public static void w(String tag, String msg) {
        Logger.w(L.TAG + "." + tag, msg);
    }

    /**
     * Error log
     *
     * @param tag log tag
     * @param msg log msg
     * @param e   exception to log
     */
    public static void w(String tag, String msg, Exception e) {
        Logger.d(L.TAG + "." + tag, msg);
        Logger.e(e);
    }

    /**
     * Error log
     *
     * @param tag log tag
     * @param msg log msg
     * @param e   exception to log
     */
    public static void e(String tag, String msg, Exception e) {
        Logger.e(L.TAG + "." + tag, msg, e);
    }

    /**
     * Error log
     *
     * @param tag log tag
     * @param msg log msg
     */
    public static void e(String tag, String msg) {
        Logger.e(L.TAG + "." + tag, msg);
    }

    /**
     * Verbose log
     *
     * @param tag log tag
     * @param msg log msg
     */
    public static void v(String tag, String msg) {
        Logger.v(L.TAG + "." + tag, msg);
    }

    /**
     * Info log
     *
     * @param msg log msg
     */
    public static void i(String msg) {
        Logger.i(TAG, msg);
    }

    /**
     * Debug log
     *
     * @param msg log msg
     */
    public static void d(String msg) {
        Logger.d(TAG, msg);
    }

    /**
     * Warning log
     *
     * @param msg log msg
     */
    public static void w(String msg) {
        Logger.w(TAG, msg);
    }

    /**
     * Error log
     *
     * @param msg log msg
     */
    public static void e(String msg) {
        Logger.e(TAG, msg);
    }

    /**
     * Verbose log
     *
     * @param msg log msg
     */
    public static void v(String msg) {
        Logger.v(TAG, msg);
    }
}
