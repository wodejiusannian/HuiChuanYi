package com.example.huichuanyi.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    public static String getCity(Context context){
        SharedPreferences mCity = context.getSharedPreferences("mCity", 1);
        String city = mCity.getString("city", "");
        return city;
    }
    public static void saveCity(Context mContext,String city){
        SharedPreferences mCity = mContext.getSharedPreferences("mCity", 1);
        SharedPreferences.Editor edit = mCity.edit();
        edit.putString("city",city);
        edit.commit();
    }
}
