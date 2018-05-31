package com.example.huichuanyi.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Base64;

import com.example.huichuanyi.custom.CustomToast;

import java.io.UnsupportedEncodingException;
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

    public static String strDoubleString(String dd) {
        Double d = Double.parseDouble(dd);
        DecimalFormat df = new java.text.DecimalFormat("0.00");
        return df.format(d);
    }

    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    // 加密
    public static String getBase64(String str) {
        String result = "";
        if (str != null) {
            try {
                result = new String(Base64.encode(str.getBytes("utf-8"), Base64.NO_WRAP), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // 解密
    public static String getFromBase64(String str) {
        String result = "";
        if (str != null) {
            try {
                result = new String(Base64.decode(str, Base64.NO_WRAP), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
