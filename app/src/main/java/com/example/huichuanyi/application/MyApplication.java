package com.example.huichuanyi.application;

import android.app.Application;

import com.example.huichuanyi.config.SystemParams;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application{

        @Override
        public void onCreate() {
            super.onCreate();
            JPushInterface.setDebugMode(true);
            JPushInterface.init(this);
            x.Ext.init(this);
            SystemParams.init(this);
        }




}
