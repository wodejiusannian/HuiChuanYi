package com.example.huichuanyi.application;

import android.app.Application;

import com.example.huichuanyi.config.SystemParams;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application{

        @Override
        public void onCreate() {
            super.onCreate();
            JPushInterface.setDebugMode(true);
            JPushInterface.init(this);
            x.Ext.init(this);
            Fresco.initialize(this);
            SystemParams.init(this);
        }




}
