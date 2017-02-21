package com.example.huichuanyi.utils;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 小五 on 2017/2/20.
 */

public class Utils {
    private static final boolean flag = true;

    public static void Log(String acName) {
        if (flag) {
            Log.i("TAG", "----------------" + acName);
        }
    }

    public static void Toa(Activity activity, String acName) {
        if (flag) {
            Toast.makeText(activity, acName, Toast.LENGTH_SHORT).show();
        }
    }
}
