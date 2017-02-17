package com.example.huichuanyi.secondui;

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
import com.example.huichuanyi.modle.MessageEvent;
import com.example.huichuanyi.share.Login;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import de.greenrobot.event.EventBus;

public class LoginActivity extends BaseActivity implements View.OnClickListener, MyThirdData {
    private EditText mEditTextPhone, mEditTextPWD;
    private Button mButtonLogin;
    private User mUser;
    private ImageView mImageViewWechat, mImageViewQQ, mImageViewBack;
    private TextView mTextViewForgetPwd;

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
                    RequestParams params = new RequestParams(NetConfig.SEND_LOGIN);
                    params.addBodyParameter("type", "1");
                    params.addBodyParameter("phone", phone);
                    params.addBodyParameter("pwd", pwd);
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            int b = 0;
                            try {
                                b = (int) Double.parseDouble(result);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            if (b > 0) {
                                mUser.writeUserId(b);
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new MessageEvent("11"));
                                finish();
                            } else if (b == 0) {
                                Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                            } else if (b == -1) {
                                Toast.makeText(LoginActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                            }
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
                    });
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
        RequestParams params = new RequestParams(NetConfig.THIRD_LOGIN);
        params.addBodyParameter("account", userId);
        params.addBodyParameter("photopath", url);
        params.addBodyParameter("username", userName);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray list = object.getJSONArray("list");
                    JSONObject jsonObject = list.getJSONObject(0);
                    String id = jsonObject.getString("id");
                    String phone_number = jsonObject.getString("phone_number");
                    int b = 0;
                    try {
                        b = (int) Double.parseDouble(id);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    if (b > 0) {
                        if (TextUtils.equals(phone_number, "null")) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("userid", userId);
                            new User(LoginActivity.this).writeUserId(b);
                            EventBus.getDefault().post(new MessageEvent("11"));
                            ActivityUtils.switchTo(LoginActivity.this, BoundActivity.class, map);
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                        new User(LoginActivity.this).writeUserId(b);
                        EventBus.getDefault().post(new MessageEvent("11"));
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (b == 0) {
                        Toast.makeText(LoginActivity.this, "登录失败，请重新登录", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        });
    }
}
