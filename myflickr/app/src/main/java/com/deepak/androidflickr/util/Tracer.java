package com.deepak.androidflickr.util;

import android.util.Log;

import com.deepak.androidflickr.Config;

public class Tracer {

    private static final String TAG_ = Config.logger + Tracer.class.getSimpleName();

    public static void debug(String TAG, String message) {
        Log.d(TAG, message);
    }
}
