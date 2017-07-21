package com.example.huichuanyi.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.secondui.BoundActivity;
import com.example.huichuanyi.ui.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by 小五 on 2017/5/8.
 */

public class LoginControl implements PlatformActionListener, UtilsInternet.XCallBack {
    private static final String TAG = "LoginControl";

    private static LoginControl control;

    private UtilsInternet internet;

    private String number;

    private Activity mContext;

    private Map<String, String> map = new HashMap<>();

    private int internetFlag = 0;


    private int user_id;

    public static LoginControl getInstance(Activity context) {
        if (control == null) {
            synchronized (LoginControl.class) {
                if (control == null) {
                    return new LoginControl(context);
                }
            }
        }
        return control;
    }

    public LoginControl(Activity context) {
        internet = UtilsInternet.getInstance();
        mContext = context;
    }


    public void thirdLogin(String name) {
        Platform plat = ShareSDK.getPlatform(name);
        if (plat.isAuthValid()) {
            plat.removeAccount(true);
        }
        plat.setPlatformActionListener(this);
        plat.showUser(null);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        PlatformDb db = platform.getDb();
        //获取用户头像
        String userIcon = db.getUserIcon();
        //获取用户的id
        String userId = db.getUserId();
        //获取用的的名字
        String userName = db.getUserName();
        SharedPreferenceUtils.writeUserName(mContext, userName);
        SharedPreferenceUtils.writeUserPhoto(mContext, userIcon);
        map.put("user_pic", userIcon);
        map.put("photopath", userIcon);
        map.put("account", userId);
        map.put("username", userName);
        internet.post(NetConfig.THIRD_LOGIN, map, this);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }

    @Override
    public void onResponse(String result) {
        switch (internetFlag) {
            case 0:
                afterThirdLogin(result);
                break;
            case 1:
                afterThirdLogin(result);
                break;
            case 2:
                isBuy365(result);
                break;
            case 3:
                getAddress();
                break;
            case 4:
                try {
                    JSONObject object = new JSONObject(result);
                    String city = object.getString("city");
                    SharedPreferenceUtils.writeUserId(mContext, user_id + "");
                    SharedPreferenceUtils.saveCity(mContext, city);
                    getIMToken();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                try {
                    JSONObject j = new JSONObject(result);
                    JSONObject b = j.getJSONObject("body");
                    SharedPreferenceUtils.saveToken(mContext, b.getString("token"));
                    ActivityUtils.switchTo(mContext, MainActivity.class);
                    mContext.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    /*
    * 三方登陆后要做的操作
    * */
    private void afterThirdLogin(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONArray list = object.getJSONArray("list");
            JSONObject jsonObject = list.getJSONObject(0);
            String id = jsonObject.getString("id");
            String phone_number = jsonObject.getString("phone_number");
            try {
                user_id = (int) Double.parseDouble(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (user_id > 0) {
                if (TextUtils.equals(phone_number, "null")) {
                    SharedPreferenceUtils.writeUserId(mContext, user_id + "");
                    ActivityUtils.switchTo(mContext, BoundActivity.class);
                    Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                    mContext.finish();
                    return;
                }
                afterLoginSuccess(user_id);
            } else if (user_id == 0) {
                Toast.makeText(mContext, "登录失败，请重新登录", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /*
   * 登录成功所需要做的事情
   * */
    private void afterLoginSuccess(int userID) {
        internetFlag = 2;
        map.put("user_id", userID + "");
        internet.post(NetConfig.IS_BUY_365, map, this);
    }


    /*
     * 是否购买365，购买或者未购买的操作
     * */
    private void isBuy365(String result) {
        String ret = JsonUtils.getRet(result);
        if (TextUtils.equals("0", ret)) {
            SharedPreferenceUtils.save365(mContext, "365");
        } else {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject body = object.getJSONObject("body");
                String price = body.getString("activity_price");
                String activity = body.getString("activity_state");
                SharedPreferenceUtils.saveActivity(mContext, activity, price);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        upRegistrationID();
    }

    private void upRegistrationID() {
        internetFlag = 3;
        String rid = JPushInterface.getRegistrationID(mContext.getApplicationContext());
        map.put("registration_id", rid);
        internet.post(NetConfig.UP_J_PUSH_REGISTRATION_ID, map, this);
    }

    private void getAddress() {
        internetFlag = 4;
        map.put("userid", user_id + "");
        internet.post(NetConfig.GET_INFORMATION, map, this);
    }

    private void getIMToken() {
        internetFlag = 5;
        map.put("type", "1");
        internet.post("http://hmyc365.net:8084/HM/stu/im/rong/getTokenIM.do", map, this);
    }
}
