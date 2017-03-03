package com.example.huichuanyi.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

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
}
