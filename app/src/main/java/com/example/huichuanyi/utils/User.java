package com.example.huichuanyi.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class User {

    private Context mContext;
    private SharedPreferences user;
    public User(Context context){
        mContext = context;
        user = mContext.getSharedPreferences("user", 0);
    }
    public int getUseId(){
       int useid = user.getInt("userid", 0);
       return useid;
    }

    public void writeUserId(int userid){
        SharedPreferences.Editor edit = user.edit();
        edit.putInt("userid",userid);
        edit.commit();
    }

}
