package com.example.huichuanyi.utils;

import android.content.Context;

import com.example.huichuanyi.custom.CustomToast;

/**
 * Created by 小五 on 2017/3/24.
 */

public class ReminderUtils {
    public static void Toast(Context mContext, String msg) {
        CustomToast.showToast(mContext, msg);
    }
}
