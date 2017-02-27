package com.example.huichuanyi.utils;

import com.example.huichuanyi.config.NetConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 小五 on 2017/2/27.
 */

public class LoginUtils implements UtilsInternet.XCallBack {
    private UtilsInternet internet = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();

    public String loginByPhone(String phone, String pwd) {

        return null;
    }

    public void loginByThird(String imageUrl, String user_id, String userName) {
        map.put("photopath", imageUrl);
        map.put("account", user_id);
        map.put("username", userName);
        internet.post(NetConfig.THIRD_LOGIN, map, this);
    }

    @Override
    public void onResponse(String result) {

    }
}
