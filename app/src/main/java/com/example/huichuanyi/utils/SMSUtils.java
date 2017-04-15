package com.example.huichuanyi.utils;

import android.util.Log;

import com.example.huichuanyi.config.NetConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 小五 on 2017/4/13.
 */

public class SMSUtils implements UtilsInternet.XCallBack {
    private UtilsInternet net;
    private Map<String, String> map;
    private int isME = 1;

    public SMSOnResponse smsOnResponse;

    public SMSOnResponse2 smsOnResponse2;

    public void setSMSSend(SMSOnResponse onResponse) {
        smsOnResponse = onResponse;
    }

    public void setSMSCode(SMSOnResponse2 smsCode) {
        smsOnResponse2 = smsCode;
    }

    public SMSUtils() {
        net = UtilsInternet.getInstance();
        map = new HashMap<>();
    }


    public void smsSend(String phone) {
        isME = 1;
        map.put("phone", phone);
        net.post(NetConfig.SMS_SEND_URL, map, this);
    }

    public void smsSendCode(String send_type, String code, String phone) {
        isME = 2;
        map.put("phone", phone);
        map.put("send_type", send_type);
        map.put("code", code);
        net.post(NetConfig.SMS_VERIFY_URL, map, this);
    }

    @Override
    public void onResponse(String result) {
        Log.i("TAG", "------" + result);
        switch (isME) {
            case 1:
                try {
                    JSONObject object = new JSONObject(result);
                    String send_type = object.getString("send_type");
                    smsOnResponse.onSuccess(send_type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    JSONObject object = new JSONObject(result);
                    String resultCode = object.getString("result");
                    smsOnResponse2.onResultCode(resultCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    public interface SMSOnResponse {
        void onSuccess(String send_type);
    }

    public interface SMSOnResponse2 {
        void onResultCode(String resultCode);
    }
}
