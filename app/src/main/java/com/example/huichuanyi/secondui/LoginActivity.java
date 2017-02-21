package com.example.huichuanyi.secondui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.MyThirdData;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
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

public class LoginActivity extends BaseActivity implements View.OnClickListener, MyThirdData, UtilsInternet.XCallBack {
    private EditText mEditTextPhone, mEditTextPWD;
    private Button mButtonLogin;
    private User mUser;
    private ImageView mImageViewWechat, mImageViewQQ, mImageViewBack;
    private TextView mTextViewForgetPwd;
    private int flag = 0;
    private UtilsInternet internet = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

    }

    @Override
    public void initView() {
        mEditTextPhone = (EditText) findViewById(R.id.et_login_writephone);
        mEditTextPWD = (EditText) findViewById(R.id.et_login_pwd);
        mButtonLogin = (Button) findViewById(R.id.btn_login_login);
        mImageViewWechat = (ImageView) findViewById(R.id.tv_login2_wechat);
        mImageViewQQ = (ImageView) findViewById(R.id.tv_login2_qq);
        mImageViewBack = (ImageView) findViewById(R.id.iv_login2_back);
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
        mImageViewWechat.setOnClickListener(this);
        mImageViewQQ.setOnClickListener(this);
        mImageViewBack.setOnClickListener(this);
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
            case R.id.tv_login2_wechat:
                new Login(this).whileLogin(this, Wechat.NAME);
                break;
            case R.id.tv_login2_qq:
                new Login(this).whileLogin(this, QQ.NAME);
                break;
            case R.id.iv_login2_back:
                finish();
                break;
            case R.id.tv_login_forgetpwd:
                ActivityUtils.switchTo(this, ForgetPWDAcitity.class);
                break;
        }
    }

    @Override
    public void getData(String url, final String userId, String userName) {
        goLoginThird(url, userId, userName);
    }

    /*
    * 第三方登录
    * */
    private void goLoginThird(String url, String userId, String userName) {
        flag = 1;
        map.put("account", userId);
        map.put("photopath", url);
        map.put("username", userName);
        internet.post(NetConfig.THIRD_LOGIN, map, this);
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
        flag = 2;
        map.clear();
        map.put("user_id", userID + "");
        internet.post(NetConfig.IS_BUY_365, map, this);

    }

    @Override
    public void onResponse(String result) {
        switch (flag) {
            case 0:
                goLoginAfter(result);
                break;
            case 1:
                goLoginThirdAfter(result);
                break;
            case 2:
                isBuy365(result);
                break;
            case 3:
                mUser.writeUserId(user_id);
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                sendBroad();
                finish();
                break;
            default:
                break;
        }
    }

    /*
    * 是否购买365，购买或者未购买的操作
    * */
    private void isBuy365(String result) {
        String ret = MyJson.getRet(result);
        if (!TextUtils.equals("0", ret)) {
            MySharedPreferences.save365(LoginActivity.this, "365");
        }
        upRegistrationID();
    }

    private void upRegistrationID() {
        flag = 3;
        String rid = JPushInterface.getRegistrationID(getApplicationContext());
        Utils.Log(rid);
        map.put("registration_id", rid);
        internet.post(NetConfig.UP_J_PUSH_REGISTRATION_ID, map, this);
    }

    /*
    * 第三方登陆后，处理服务器返回的数据
    * */
    private void goLoginThirdAfter(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONArray list = object.getJSONArray("list");
            JSONObject jsonObject = list.getJSONObject(0);
            String id = jsonObject.getString("id");
            String phone_number = jsonObject.getString("phone_number");
            int b = 0;
            try {
                b = (int) Double.parseDouble(id);
                user_id = b;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (b > 0) {
                if (TextUtils.equals("null", phone_number)) {
                    afterLoginSuccess(b);
                    ActivityUtils.switchTo(LoginActivity.this, BoundActivity.class);
                    finish();
                    return;
                }
                afterLoginSuccess(b);
            } else if (b == 0) {
                Toast.makeText(LoginActivity.this, "登录失败，请重新登录", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public void sendBroad() {
        Intent intent = new Intent();
        intent.setAction("action.refreshFriend");
        sendBroadcast(intent);
    }
}
