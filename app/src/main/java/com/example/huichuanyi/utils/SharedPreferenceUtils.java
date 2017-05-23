package com.example.huichuanyi.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtils {

    public static String getCity(Context context) {
        SharedPreferences mCity = context.getSharedPreferences("mCity", 1);
        String city = mCity.getString("city", "");
        return city;
    }

    public static void saveCity(Context mContext, String city) {
        SharedPreferences mCity = mContext.getSharedPreferences("mCity", 1);
        SharedPreferences.Editor edit = mCity.edit();
        edit.putString("city", city);
        edit.commit();
    }

    public static void save365(Context mContext, String isBuy) {
        SharedPreferences mCity = mContext.getSharedPreferences("mCity", 1);
        SharedPreferences.Editor edit = mCity.edit();
        edit.putString("m365", isBuy);
        edit.commit();
    }

    public static void saveActivity(Context mContext, String activity, String price) {
        SharedPreferences mCity = mContext.getSharedPreferences("mCity", 1);
        SharedPreferences.Editor edit = mCity.edit();
        edit.putString("activity", activity);
        edit.putString("price", price);
        edit.commit();
    }

    public static String get365(Context mContext) {
        SharedPreferences mCity = mContext.getSharedPreferences("mCity", 1);
        String m365 = mCity.getString("m365", "");
        return m365;
    }

    public static String getActivity(Context mContext) {
        SharedPreferences mCity = mContext.getSharedPreferences("mCity", 1);
        String m365 = mCity.getString("activity", "");
        return m365;
    }

    public static String getBuyCity(Context mContext) {
        SharedPreferences mCity = mContext.getSharedPreferences("mCity", 1);
        String m365 = mCity.getString("buy_cuty", "");
        return m365;
    }

    public static void saveBuyCity(Context mContext, String buy_cuty) {
        SharedPreferences mCity = mContext.getSharedPreferences("mCity", 1);
        SharedPreferences.Editor edit = mCity.edit();
        edit.putString("buy_cuty", buy_cuty);
        edit.commit();
    }


    public static String getActivityPrice(Context mContext) {
        SharedPreferences mCity = mContext.getSharedPreferences("mCity", 1);
        String m365 = mCity.getString("price", "");
        return m365;
    }

    public static int isFirst(Context context) {
        SharedPreferences isFirst = context.getSharedPreferences("isFirst", 1);
        return isFirst.getInt("isFirst", 0);
    }

    public static void saveIsFirst(Context context) {
        SharedPreferences isFirsts = context.getSharedPreferences("isFirst", 1);
        SharedPreferences.Editor edit = isFirsts.edit();
        edit.putInt("isFirst", 1);
        edit.commit();
    }

    public static String getUserData(Context context, int type) {
        SharedPreferences user = context.getSharedPreferences("user", 1);
        if (1 == type) {
            return user.getString("user_id", "");
        } else if (2 == type) {
            return user.getString("name", "");
        } else if (3 == type) {
            return user.getString("photo_url", "");
        }
        return null;
    }

    public static void writeUserData(Context context, String name, String user_id, String photo_url) {
        SharedPreferences user = context.getSharedPreferences("user", 1);
        SharedPreferences.Editor edit = user.edit();
        edit.putString("name", name);

        edit.putString("user_id", user_id);

        edit.putString("photo_url", photo_url);
        edit.commit();
    }

    public static void writeUserName(Context context, String name) {
        SharedPreferences user = context.getSharedPreferences("user", 1);
        SharedPreferences.Editor edit = user.edit();
        edit.putString("name", name);
        edit.commit();
    }

    public static void writeUserPhoto(Context context, String photo_url) {
        SharedPreferences user = context.getSharedPreferences("user", 1);
        SharedPreferences.Editor edit = user.edit();
        edit.putString("photo_url", photo_url);
        edit.commit();
    }

    public static void writeUserId(Context context, String user_id) {
        SharedPreferences user = context.getSharedPreferences("user", 1);
        SharedPreferences.Editor edit = user.edit();
        edit.putString("user_id", user_id);
        edit.commit();
    }
}
