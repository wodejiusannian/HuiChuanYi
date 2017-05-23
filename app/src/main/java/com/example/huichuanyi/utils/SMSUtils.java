package com.example.huichuanyi.utils;

import com.example.huichuanyi.config.NetConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 小五 on 2017/4/13.
 */

public class SMSUtils implements UtilsInternet.XCallBack {
    private static final String TAG = "SMSUtils";
    private UtilsInternet net;
    private Map<String, String> map;
    private int isME = 1;

    public SMSOnResponse smsOnResponse;

    public SMSOnResponse2 smsOnResponse2;


    public SMSUtils() {
        net = UtilsInternet.getInstance();
        map = new HashMap<>();
    }


    public void smsSend(String phone, SMSOnResponse onResponse) {
        smsOnResponse = onResponse;
        map.put("phone", phone);
        net.post(NetConfig.SMS_SEND_URL, map, this);
    }



    @Override
    public void onResponse(String result) {
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
