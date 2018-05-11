package com.example.huichuanyi.utils;

import android.util.Log;

import com.example.huichuanyi.config.NetConfig;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

// ┏┓　　　┏┓
// ┏┛┻━━━┛┻┓
// ┃　　　　　　　┃ 　
// ┃　　　━　　　┃
// ┃　┳┛　┗┳　┃
// ┃　　　　　　　┃
// ┃　　　┻　　　┃
// ┃　　　　　　　┃
// ┗━┓　　　┏━┛
// ┃　　　┃ 神兽保佑　　　　　　　　
// ┃　　　┃ 代码无BUG！
// ┃　　　┗━━━┓
// ┃　　　　　　　┣┓
// ┃　　　　　　　┏┛
// ┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
// ┗┻┛　┗┻┛
public class PhoneLoginUtils {
    private static final String TAG = "PhoneLoginUtils";

    private PhoneLoginUtils phoneLoginUtils;

    private PhoneLoginUtils() {

    }

    public PhoneLoginUtils getInstance() {
        if (phoneLoginUtils == null) {
            synchronized (PhoneLoginUtils.class) {
                phoneLoginUtils = new PhoneLoginUtils();
            }
        }
        return phoneLoginUtils;
    }

    public void login(String phone, String pwd) {
        RequestParams params = new RequestParams(NetConfig.LOGIN_THROUGH_PHONE);
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("pwd", pwd);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {

                return false;
            }
        });
    }
}
