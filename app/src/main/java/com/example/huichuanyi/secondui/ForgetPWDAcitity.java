package com.example.huichuanyi.secondui;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ForgetPWDAcitity extends BaseActivity implements View.OnClickListener {
    private EditText mEditTextPhone,mEditTextAuthCode,mEditTextPwd;
    private Button mButtonSure,mButtonGetAuth;
    private String phone,authCode,pwd;
    private ImageView mImageViewBack;
    private boolean flag = true;
    private int time = 60 ;
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
                    Toast.makeText(ForgetPWDAcitity.this, "验证码获取成功", Toast.LENGTH_SHORT).show();
                    break;
                case GET_VERIFICATION_CODE_COMPLETE:
                    if(!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(pwd)) {
                        setDadaToMy();
                    }else{
                        Toast.makeText(ForgetPWDAcitity.this, "手机号和密码不能为空", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case RESULT_ERROR:
                    Toast.makeText(ForgetPWDAcitity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }


    };
    private Handler mHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;
            mButtonGetAuth.setText(time+"s后获取验证码");
            if(time==0) {
                mButtonGetAuth.setText("获取验证码");
                time = 60;
                flag = true;
                return;
            }
            mHandle.sendEmptyMessageDelayed(1,1000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwdacitity);
    }

    @Override
    public void initView() {
        mEditTextPhone = (EditText) findViewById(R.id.et_forget_pwd_writephone);
        mEditTextAuthCode = (EditText) findViewById(R.id.et_forget_pwd_auth_code);
        mEditTextPwd = (EditText) findViewById(R.id.et_forget_pwd_pwd);
        mButtonSure = (Button) findViewById(R.id.btn_forget_pwd_sure);
        mButtonGetAuth = (Button) findViewById(R.id.btn_forget_pwd_getAuth);
        mImageViewBack = (ImageView) findViewById(R.id.iv_fogetPwd_back);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        mButtonGetAuth.setOnClickListener(this);
        mButtonSure.setOnClickListener(this);
        mImageViewBack.setOnClickListener(this);
    }

    public void setDadaToMy() {
        RequestParams params = new RequestParams(NetConfig.UPDATE_USER_PWD);
        params.addBodyParameter("userid",phone);
        params.addBodyParameter("oldpwd","找回");
        params.addBodyParameter("newpwd",pwd);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if("1".equals(result)) {
                    Toast.makeText(ForgetPWDAcitity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(ForgetPWDAcitity.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(ForgetPWDAcitity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.btn_forget_pwd_getAuth:
                if(flag) {
                    phone = mEditTextPhone.getText().toString().trim();
                    if(!TextUtils.isEmpty(phone)&&phone.length()==11) {
                        SMSSDK.initSDK(this, "19168cd291b14", "ffddabe45b829578796641cdd99d6d76");
                        SMSSDK.registerEventHandler(eh);
                        SMSSDK.getVerificationCode("86", phone);
                        flag = false;
                        mHandle.sendEmptyMessageDelayed(1,1000);
                        Toast.makeText(ForgetPWDAcitity.this, "发送验证码到"+phone, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ForgetPWDAcitity.this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case  R.id.btn_forget_pwd_sure:
                if(!TextUtils.isEmpty(phone)&&phone.length()==11) {
                    authCode = mEditTextAuthCode.getText().toString().trim();
                    pwd = mEditTextPwd.getText().toString().trim();
                    if(!TextUtils.isEmpty(authCode)&&!TextUtils.isEmpty(pwd)) {
                        SMSSDK.submitVerificationCode("86", phone, authCode);
                    }else{
                        Toast.makeText(ForgetPWDAcitity.this, "验证码和密码不能为空", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ForgetPWDAcitity.this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_fogetPwd_back:
                finish();
                break;
        }
    }
}
