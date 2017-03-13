package com.example.huichuanyi.secondui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.CustomToast;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.MyJson;
import com.example.huichuanyi.utils.MySharedPreferences;
import com.example.huichuanyi.utils.User;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends BaseActivity implements View.OnClickListener/*, MyThirdData*/, UtilsInternet.XCallBack {
    private UtilsInternet internet = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();

    private EditText mEditTextPhone, mEditTextPWD;
    private TextView mTextViewForgetPwd;
    private Button mButtonLogin;
    private User mUser;
    private int user_id;

    private int internetFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    public void initView() {
        mEditTextPhone = (EditText) findViewById(R.id.et_login_writephone);
        mEditTextPWD = (EditText) findViewById(R.id.et_login_pwd);
        mButtonLogin = (Button) findViewById(R.id.btn_login_login);
        mTextViewForgetPwd = (TextView) findViewById(R.id.tv_login_forgetpwd);
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
        mButtonLogin.setOnClickListener(this);
        mTextViewForgetPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_login:
                String phone = mEditTextPhone.getText().toString().trim();
                String pwd = mEditTextPWD.getText().toString().trim();
                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pwd)) {
                    goLogin(phone, pwd);
                } else {
                    Toast.makeText(LoginActivity.this, "手机号和密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_login_forgetpwd:
                ActivityUtils.switchTo(this, ForgetPWDAcitity.class);
                break;
        }
    }

    /*
    * 手机用户去登陆
    * */
    private void goLogin(String phone, String pwd) {
        map.put("type", "1");
        map.put("phone", phone);
        map.put("pwd", pwd);
        internet.post(NetConfig.SEND_LOGIN, map, this);
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
     * 处理服务器返回的数据
     * */
    private void goLoginAfter(String result) {
        int b = 0;
        try {
            b = (int) Double.parseDouble(result);
            user_id = b;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (b > 0) {
            afterLoginSuccess(b);
        } else if (b == 0) {
            Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
        } else if (b == -1) {
            Toast.makeText(LoginActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * 是否购买365，购买或者未购买的操作
     * */
    private void isBuy365(String result) {
        String ret = MyJson.getRet(result);
        if (TextUtils.equals("0", ret)) {
            MySharedPreferences.save365(LoginActivity.this, "365");
        } else {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject body = object.getJSONObject("body");
                String price = body.getString("activity_price");
                String activity = body.getString("activity_state");
                MySharedPreferences.saveActivity(LoginActivity.this, activity, price);
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

    public void sendBroad() {
        Intent intent = new Intent();
        intent.setAction("action.refreshFriend");
        sendBroadcast(intent);
    }

    @Override
    public void onResponse(String result) {

        switch (internetFlag) {
            case 0:
                goLoginAfter(result);
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
                    mUser.writeUserId(user_id);
                    MySharedPreferences.saveBuyCity(LoginActivity.this, city);
                    CustomToast.showToast(this, "登录成功");
                    sendBroad();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }

    private void getAddress() {
        internetFlag = 4;
        map.clear();
        map.put("userid", user_id + "");
        internet.post(NetConfig.GET_INFORMATION, map, this);
    }


    public void back(View view) {
        finish();
    }


}
