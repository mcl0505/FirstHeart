package com.konglianyuyin.mx.utils;

import android.util.Log;

import com.konglianyuyin.mx.BuildConfig;

/**
 * Created by cxf on 2017/8/3.
 */

public class L {
    private final static String TAG = "log--->";

    public static void e(String s) {
        e(TAG, s);
    }

    public static void e(String tag, String s) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, s);
        }
    }
}
