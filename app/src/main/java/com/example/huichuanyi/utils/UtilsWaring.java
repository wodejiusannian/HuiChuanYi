package com.example.huichuanyi.utils;

import android.content.Context;

import com.example.huichuanyi.custom.CustomToast;

/**
 * Created by 小五 on 2017/3/3.
 */

public class UtilsWaring {
    public static void Toast(Context mContext, String msg) {
        CustomToast.showToast(mContext, msg);
    }
}
