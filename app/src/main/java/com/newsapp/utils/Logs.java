package com.newsapp.utils;

import android.util.Log;

/**
 *
 */
public class Logs {
    public static boolean LOG_SWITCH = true;
    public static String TAG = "tag";

    public static void i(String string) {
        if (LOG_SWITCH)
            Log.i(TAG, string);
    }

    public static void v(String string) {
        if (LOG_SWITCH) Log.v(TAG, string);
    }

    public static void e(String string) {
        if (LOG_SWITCH) Log.e(TAG, string);
    }
}
