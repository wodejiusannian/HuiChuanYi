package com.example.huichuanyi.utils;

import android.content.Context;

import com.example.huichuanyi.custom.CustomToast;

/**
 * Created by 小五 on 2017/3/8.
 */

public class CommonUtils {

    /*
    * 判断字符串是否为空
    * */
    public static boolean isEmpty(String str) {
        if (!str.isEmpty() || str.length() != 0 || str != null || !str.equals("") || !str.equalsIgnoreCase("null")) {
            return true;
        } else {
            return false;
        }
    }

    public static void Toast(Context mContext, String msg) {
        CustomToast.showToast(mContext, msg);
    }
}
