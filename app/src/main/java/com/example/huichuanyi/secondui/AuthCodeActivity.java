package com.example.huichuanyi.secondui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.ReminderUtils;
import com.example.huichuanyi.utils.SMSUtils;
import com.example.huichuanyi.utils.User;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class AuthCodeActivity extends BaseActivity implements View.OnClickListener, SMSUtils.SMSOnResponse2, SMSUtils.SMSOnResponse {
    private EditText mEditTextAuth, mEditTextPWD;
    private Button mButtonRegister;
    private String phone, type;
    private Callback.Cancelable cancelable;
    private User mUser;
    private TextView mTextView;
    private int time = 60;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;
            mTextView.setText(time + "");
            if (time == 0) {
                return;
            }
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }
    };
    private SMSUtils smsUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authcode);

    }

    @Override
    public void initView() {
        mEditTextAuth = (EditText) findViewById(R.id.et_authcode_auth);
        mEditTextPWD = (EditText) findViewById(R.id.et_authcode_pwd);
        mButtonRegister = (Button) findViewById(R.id.btn_authcode_register);
        mTextView = (TextView) findViewById(R.id.tv_auth_code_time);
    }

    @Override
    public void initData() {
        smsUtils = new SMSUtils();
        smsUtils.setSMSCode(this);
        smsUtils.setSMSSend(this);
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        mUser = new User(this);
        smsUtils.smsSend(phone);
    }

    @Override
    public void setData() {
    }

    @Override
    public void setListener() {
        mButtonRegister.setOnClickListener(this);
        mHandler.sendEmptyMessageDelayed(1, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_authcode_register:
                String auth = mEditTextAuth.getText().toString().trim();
                String pwd = mEditTextPWD.getText().toString().trim();
                if (!TextUtils.isEmpty(auth) && !TextUtils.isEmpty(pwd)) {
                    if (pwd.length() >= 6 && pwd.length() <= 12) {
                        smsUtils.smsSendCode(type, auth, phone);
                    } else {
                        Toast.makeText(AuthCodeActivity.this, "亲，密码是6到12位", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AuthCodeActivity.this, "验证不要和密码不要为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cancelable != null && !cancelable.isCancelled()) {
            cancelable.cancel();
        }
    }

    public void msmSuccess() {
        RequestParams params = new RequestParams(NetConfig.SEND_PASSWORD);
        params.addBodyParameter("pwd", mEditTextPWD.getText().toString().trim());
        params.addBodyParameter("phone", phone);
        cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                int b = -1;
                try {
                    b = (int) Double.parseDouble(result);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if (b > 0) {
                    mUser.writeUserId(b);
                    Toast.makeText(AuthCodeActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AuthCodeActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(AuthCodeActivity.this, "亲，请检查网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(AuthCodeActivity.this, "取消了访问", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onResultCode(String resultCode) {
        if (TextUtils.equals("200", resultCode)) {
            msmSuccess();
        } else {
            ReminderUtils.Toast(this, "验证码错误");
        }

    }

    @Override
    public void onSuccess(String send_type) {
        if (!TextUtils.equals("349", send_type)) {
            ReminderUtils.Toast(this, "获取验证码成功");
            type = send_type;
        } else {
            ReminderUtils.Toast(this, "获取验证码失败");
        }
    }

}
