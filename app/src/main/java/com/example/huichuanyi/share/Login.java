package com.example.huichuanyi.share;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.huichuanyi.baidumap.MyThirdData;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

public class Login implements PlatformActionListener {
    public MyThirdData mThirdData;

    public Login(MyThirdData thirdData) {
        mThirdData = thirdData;
    }

    public Context mContext;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String userIcon = data.getString("userIcon");
            String userId = data.getString("userId");
            String userName = data.getString("userName");
            mThirdData.getData(userIcon, userId, userName);
        }
    };

    public void whileLogin(Context context, String name) {
        mContext = context;
        ShareSDK.initSDK(context);
        Platform plat = ShareSDK.getPlatform(name);
        if (plat.isAuthValid()) {
            plat.removeAccount(true);
        }
        plat.setPlatformActionListener(this);
        plat.showUser(null);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

        //获取用户头像
        String userIcon = platform.getDb().getUserIcon();
        //获取用户的id
        String userId = platform.getDb().getUserId();
        //获取用的的名字
        String userName = platform.getDb().getUserName();
        //使用接口传值
        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putString("userIcon", userIcon);
        bundle.putString("userId", userId);
        bundle.putString("userName", userName);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }

    public void remove(Context context, String name) {

    }
}
