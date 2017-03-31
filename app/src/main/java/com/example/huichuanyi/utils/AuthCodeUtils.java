package com.example.huichuanyi.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by 小五 on 2017/3/24.
 */

public class AuthCodeUtils {
    private IsSuccessGetCode mIsSuccessGetCode;
    private static final int SUBMIT_VERIFICATION_CODE_COMPLETE = 1;
    private static final int GET_VERIFICATION_CODE_COMPLETE = 2;
    private static final int RESULT_ERROR = 3;
    private EventHandler eh = new EventHandler() {

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                // 回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    // 提交验证码正确成功
                    handler.sendEmptyMessage(GET_VERIFICATION_CODE_COMPLETE);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    // 获取验证码成功
                    handler.sendEmptyMessage(SUBMIT_VERIFICATION_CODE_COMPLETE);
                }
            } else if (result == SMSSDK.RESULT_ERROR) {// 错误情况
                Throwable throwable = (Throwable) data;
                throwable.printStackTrace();
                JSONObject object;
                try {
                    object = new JSONObject(throwable.getMessage());
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("status", object.optInt("status"));// 错误代码
                    bundle.putString("detail", object.optString("detail"));// 错误描述
                    msg.setData(bundle);
                    msg.what = RESULT_ERROR;
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    };

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SUBMIT_VERIFICATION_CODE_COMPLETE:
                    Toast.makeText(mContext, "验证码获取成功", Toast.LENGTH_SHORT).show();
                    break;
                case GET_VERIFICATION_CODE_COMPLETE:
                    mIsSuccessGetCode.isSuccessGetCode();
                    break;
                case RESULT_ERROR:
                    Toast.makeText(mContext, "验证码错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private Context mContext;

    public AuthCodeUtils(Context context) {
        this.mContext = context;
    }

    public void sendPhone(String phone) {
        SMSSDK.initSDK(mContext, "19168cd291b14", "ffddabe45b829578796641cdd99d6d76");
        SMSSDK.registerEventHandler(eh);
        SMSSDK.getVerificationCode("86", phone);
    }

    public void sendAuthCode(String phone, String auth) {
        SMSSDK.submitVerificationCode("86", phone, auth);
    }

    public void isSuccess(IsSuccessGetCode isSuccess) {
        mIsSuccessGetCode = isSuccess;
    }

    public void unRegisterEventHandler() {
        SMSSDK.unregisterEventHandler(eh);
    }

    ;

    public interface IsSuccessGetCode {
        void isSuccessGetCode();
    }
}
