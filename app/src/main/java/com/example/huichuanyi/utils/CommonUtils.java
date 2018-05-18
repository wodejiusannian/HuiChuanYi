package com.example.huichuanyi.utils;

import android.content.Context;
import android.text.TextUtils;

import com.example.huichuanyi.custom.CustomToast;

import java.text.DecimalFormat;

/**
 * Created by 小五 on 2017/3/8.
 */

public class CommonUtils {

    /*
    * 判断字符串是否为空
    * */
    public static boolean isEmpty(String str) {
        if (TextUtils.isEmpty(str) || str.length() == 0 || str == null || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }


    public static void Toast(Context mContext, String msg) {
        CustomToast.showToast(mContext, msg);
    }

    public static String area(String msg) {

        try {

            int s = 0, q = 0;

            for (int i = 0; i < msg.length(); i++) {
                String c = msg.charAt(i) + "";
                if ("市".equals(c)) {
                    s = i;
                    break;
                }
            }

            for (int i = 0; i < msg.length(); i++) {
                String c = msg.charAt(i) + "";
                if ("区".equals(c)) {
                    q = i;
                    break;
                }
            }
            return msg.substring(s + 1, q + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String strDoubleTwo(Double d) {
        DecimalFormat df = new java.text.DecimalFormat("0.00");
        return df.format(d);
    }
}
