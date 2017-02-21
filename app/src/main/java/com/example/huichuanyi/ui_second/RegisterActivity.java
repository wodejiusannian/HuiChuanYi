package com.example.huichuanyi.ui_second;

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
import com.example.huichuanyi.secondui.AuthCodeActivity;
import com.example.huichuanyi.secondui.BoundActivity;
import com.example.huichuanyi.secondui.LoginActivity;
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

public class RegisterActivity extends BaseActivity implements View.OnClickListener, MyThirdData {
    private ImageView mImageViewBack, mImageViewWeChat, mImageViewQQ;
    private Button mButtonGet;
    private EditText mEditTextPhone;
    private String number;
    private TextView mTextViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    @Override
    public void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.iv_register_back);
        mButtonGet = (Button) findViewById(R.id.bt_register_getsms);
        mEditTextPhone = (EditText) findViewById(R.id.et_register_writephone);
        mTextViewLogin = (TextView) findViewById(R.id.tv_register_yetlogin);
        mImageViewWeChat = (ImageView) findViewById(R.id.iv_register_wechat);
        mImageViewQQ = (ImageView) findViewById(R.id.iv_register_qq);
    }

    @Override
    public void initData() {
    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        mImageViewBack.setOnClickListener(this);
        mButtonGet.setOnClickListener(this);
        mTextViewLogin.setOnClickListener(this);
        mImageViewWeChat.setOnClickListener(this);
        mImageViewQQ.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_register_back:
                finish();
                break;
            case R.id.bt_register_getsms:
                number = mEditTextPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(number)) {
                    if (number.length() == 11) {
                        RequestParams paramss = new RequestParams(NetConfig.IS_REGISTER);
                        paramss.addBodyParameter("phone", number);
                        x.http().post(paramss, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                if (TextUtils.equals("1", result)) {
                                    Toast.makeText(RegisterActivity.this, "该手机号已注册", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Map<String, Object> map = new HashMap<>();
                                map.put("phone", number);
                                ActivityUtils.switchTo(RegisterActivity.this, AuthCodeActivity.class, map);
                                finish();
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                Toast.makeText(RegisterActivity.this, "网络错误，请检查网络", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {

                            }

                            @Override
                            public void onFinished() {

                            }
                        });
                    } else {
                        Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_register_yetlogin:
                ActivityUtils.switchTo(this, LoginActivity.class);
                finish();
                break;
            case R.id.iv_register_wechat:
                new Login(this).whileLogin(this, Wechat.NAME);
                finish();
                break;
            case R.id.iv_register_qq:
                new Login(this).whileLogin(this, QQ.NAME);
                finish();
                break;
        }
    }

    @Override
    public void getData(String url, final String userId, String userName) {
        RequestParams params = new RequestParams(NetConfig.THIRD_LOGIN);
        params.addBodyParameter("photopath", url);
        params.addBodyParameter("account", userId);
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
                            new User(RegisterActivity.this).writeUserId(b);
                            ActivityUtils.switchTo(RegisterActivity.this, BoundActivity.class, map);
                            Toast.makeText(RegisterActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                        new User(RegisterActivity.this).writeUserId(b);
                        Toast.makeText(RegisterActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (b == 0) {
                        Toast.makeText(RegisterActivity.this, "登录失败，请重新登录", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(RegisterActivity.this, "网络链接错误，请检查网络", Toast.LENGTH_SHORT).show();
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
