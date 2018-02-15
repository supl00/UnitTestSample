package com.gazua.ddeokrok.coinman.common;

import android.util.Log;

/**
 * Created by kimju on 2018-01-22.
 */

public class Logger {

    private static final String TAG = "Gazua$";

    public static int d(String tag, String msg) {
        return Log.d(TAG.concat(tag), msg);
    }

    public static int e(String tag, String msg) {
        return Log.e(TAG.concat(tag), msg);
    }

    public static int i(String tag, String msg) {
        return Log.i(TAG.concat(tag), msg);
    }

    public static int v(String tag, String msg) {
        return Log.v(TAG.concat(tag), msg);
    }

    public static int w(String tag, String msg) {
        return Log.w(TAG.concat(tag), msg);
    }
}
