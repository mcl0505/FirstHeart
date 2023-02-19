package com.konglianyuyin.mx.utils;

import android.util.Log;



/**
 * LogUtils
 */

public class LogUtils {
    public static void i(String tag, String msg) {
        if (Constant.LOG_ENABLE) {
            Log.i(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (Constant.LOG_ENABLE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (Constant.LOG_ENABLE) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (Constant.LOG_ENABLE) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (Constant.LOG_ENABLE) {
            Log.e(tag, msg);
        }
    }
}
