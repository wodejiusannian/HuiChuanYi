package com.example.huichuanyi.utils;

import android.util.Log;

/**
 * Created by 小五 on 2017/5/9.
 */

public class LogUtils {

    public static boolean DEBUG = true;

    public static final void i(String tag, String log) {
        if (DEBUG)
            Log.i(tag, log);
    }

    public static final void d(String tag, String log) {
        if (DEBUG)
            Log.d(tag, log);
    }

    public static final void e(String tag, String log) {
        if (DEBUG)
            Log.e(tag, "---" + log);
    }

}
