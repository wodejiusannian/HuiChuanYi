package com.example.huichuanyi.share;

import android.content.Context;

import com.example.huichuanyi.baidumap.MyThirdData;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

public class Login implements PlatformActionListener {
    public MyThirdData mThirdData;
    public Login(MyThirdData thirdData){
        mThirdData = thirdData;
    }
    public  void whileLogin(Context context, String name){
        ShareSDK.initSDK(context);
        Platform plat= ShareSDK.getPlatform(name);
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
        mThirdData.getData(userIcon,userId,userName);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }

    public void remove(Context context,String name){

    }
}
