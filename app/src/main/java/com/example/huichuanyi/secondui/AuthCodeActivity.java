package com.example.huichuanyi.secondui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class AuthCodeActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImageViewBack;
    private EditText mEditTextAuth,mEditTextPWD;
    private Button mButtonRegister;
    private String phone;
    private Callback.Cancelable cancelable;
    private User mUser;
    private TextView mTextView;
    private int time = 60;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;
            mTextView.setText(time+"");
            if(time==0) {
                return;
            }
            mHandler.sendEmptyMessageDelayed(1,1000);
        }
    };
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
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    // 返回支持发送验证码的国家列表
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
                    Toast.makeText(AuthCodeActivity.this, "验证码获取成功", Toast.LENGTH_SHORT).show();
                    break;
                case GET_VERIFICATION_CODE_COMPLETE:
                    msmSuccess();
                    finish();
                    break;
                case RESULT_ERROR:
                    Toast.makeText(AuthCodeActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    public void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.iv_authcode_back);
        mEditTextAuth = (EditText) findViewById(R.id.et_authcode_auth);
        mEditTextPWD = (EditText) findViewById(R.id.et_authcode_pwd);
        mButtonRegister = (Button) findViewById(R.id.btn_authcode_register);
        mTextView = (TextView) findViewById(R.id.tv_auth_code_time);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        mUser = new User(this);
        Toast.makeText(AuthCodeActivity.this,"发送验证码到："+phone, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setData() {
        SMSSDK.initSDK(this, "19168cd291b14", "ffddabe45b829578796641cdd99d6d76");
        SMSSDK.registerEventHandler(eh);
        SMSSDK.getVerificationCode("86", phone);
    }

    @Override
    public void setListener() {
        mImageViewBack.setOnClickListener(this);
        mButtonRegister.setOnClickListener(this);
        mHandler.sendEmptyMessageDelayed(1,1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.iv_authcode_back:
                finish();
                break;
            case R.id.btn_authcode_register:
                String auth = mEditTextAuth.getText().toString().trim();
                String pwd = mEditTextPWD.getText().toString().trim();
                if(!TextUtils.isEmpty(auth)&&!TextUtils.isEmpty(pwd)) {
                    if(pwd.length()>=6&&pwd.length()<=12) {
                        SMSSDK.submitVerificationCode("86", phone, auth);
                    }else{
                        Toast.makeText(AuthCodeActivity.this, "亲，密码是6到12位", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AuthCodeActivity.this, "验证不要和密码不要为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消注册回调
        SMSSDK.unregisterEventHandler(eh);
        if (cancelable != null && !cancelable.isCancelled()) {
            cancelable.cancel();
        }
    }
    public void msmSuccess(){
        RequestParams params = new RequestParams(NetConfig.SEND_PASSWORD);
        params.addBodyParameter("pwd",mEditTextPWD.getText().toString().trim());
        params.addBodyParameter("phone",phone);
        cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                int b = -1;
                try {
                    b = (int)Double.parseDouble(result);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if(b>0) {
                    mUser.writeUserId(b);
                    Toast.makeText(AuthCodeActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                }else {
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
}
