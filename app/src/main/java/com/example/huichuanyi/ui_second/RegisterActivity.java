package com.example.huichuanyi.ui_second;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.MyThirdData;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.secondui.AuthCodeActivity;
import com.example.huichuanyi.secondui.BoundActivity;
import com.example.huichuanyi.secondui.LoginActivity;
import com.example.huichuanyi.share.Login;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.MyJson;
import com.example.huichuanyi.utils.MySharedPreferences;
import com.example.huichuanyi.utils.User;
import com.example.huichuanyi.utils.Utils;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, MyThirdData, UtilsInternet.XCallBack {
    private UtilsInternet internet = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();
    private RelativeLayout loginWeChat, loginQQ;
    private Button mButtonGet;
    private EditText mEditTextPhone;
    private String number;
    private TextView mTextViewLogin;
    private int internetFlag = 0;
    private int user_id;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    @Override
    public void initView() {
        mButtonGet = (Button) findViewById(R.id.bt_register_getsms);
        mEditTextPhone = (EditText) findViewById(R.id.et_register_writephone);
        mTextViewLogin = (TextView) findViewById(R.id.tv_register_yetlogin);
        loginWeChat = (RelativeLayout) findViewById(R.id.rl_login_wechat);
        loginQQ = (RelativeLayout) findViewById(R.id.rl_login_qq);
    }

    @Override
    public void initData() {
        mUser = new User(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        mButtonGet.setOnClickListener(this);
        mTextViewLogin.setOnClickListener(this);
        loginQQ.setOnClickListener(this);
        loginWeChat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register_getsms:
                internetFlag = 0;
                number = mEditTextPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(number) && number.length() == 11) {
                    map.clear();
                    map.put("phone", number);
                    internet.post(NetConfig.IS_REGISTER, map, this);
                } else {
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_login_wechat:
                internetFlag = 1;
                new Login(this).whileLogin(this, Wechat.NAME);
                finish();
                break;
            case R.id.rl_login_qq:
                internetFlag = 1;
                new Login(this).whileLogin(this, QQ.NAME);
                finish();
                break;
            case R.id.tv_register_yetlogin:
                ActivityUtils.switchTo(this, LoginActivity.class);
                finish();
                break;
        }
    }

    @Override
    public void getData(String url, final String userId, String userName) {
        map.clear();
        map.put("photopath", url);
        map.put("account", userId);
        map.put("username", userName);
        internet.post(NetConfig.THIRD_LOGIN, map, this);
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onResponse(String result) {
        switch (internetFlag) {
            case 0:
                isRegister(result);
                break;
            case 1:
                afterThirdLogin(result);
                break;
            case 2:
                Utils.Log(result);
                isBuy365(result);
                break;
            case 3:
                getAddress();
                break;
            case 4:
                try {
                    JSONObject object = new JSONObject(result);
                    String city = object.getString("city");
                    mUser.writeUserId(user_id);
                    MySharedPreferences.saveCity(RegisterActivity.this, city);
                    Toast.makeText(RegisterActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    sendBroad();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            default:
                break;
        }
    }

    /*
    * 是否已经注册
    * */
    private void isRegister(String result) {
        if (TextUtils.equals("1", result)) {
            Toast.makeText(RegisterActivity.this, "该手机号已注册", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("phone", number);
        ActivityUtils.switchTo(RegisterActivity.this, AuthCodeActivity.class, map);
        finish();
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
                    new User(RegisterActivity.this).writeUserId(user_id);
                    ActivityUtils.switchTo(RegisterActivity.this, BoundActivity.class);
                    sendBroad();
                    Toast.makeText(RegisterActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                afterLoginSuccess(user_id);
            } else if (user_id == 0) {
                Toast.makeText(RegisterActivity.this, "登录失败，请重新登录", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendBroad() {
        Intent intent = new Intent();
        intent.setAction("action.refreshFriend");
        sendBroadcast(intent);
    }


    /*
   * 登录成功所需要做的事情
   * */
    private void afterLoginSuccess(int userID) {
        internetFlag = 2;
        map.clear();
        map.put("user_id", userID + "");
        internet.post(NetConfig.IS_BUY_365, map, this);
    }


    /*
     * 是否购买365，购买或者未购买的操作
     * */
    private void isBuy365(String result) {
        String ret = MyJson.getRet(result);
        if (TextUtils.equals("0", ret)) {
            MySharedPreferences.save365(RegisterActivity.this, "365");
        } else {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject body = object.getJSONObject("body");
                String price = body.getString("activity_price");
                String activity = body.getString("activity_state");
                MySharedPreferences.saveActivity(RegisterActivity.this, activity, price);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        upRegistrationID();
    }

    private void upRegistrationID() {
        internetFlag = 3;
        String rid = JPushInterface.getRegistrationID(getApplicationContext());
        map.put("registration_id", rid);
        internet.post(NetConfig.UP_J_PUSH_REGISTRATION_ID, map, this);
    }

    private void getAddress() {
        internetFlag = 4;
        map.clear();
        map.put("userid", user_id + "");
        internet.post(NetConfig.GET_INFORMATION, map, this);
    }
}
