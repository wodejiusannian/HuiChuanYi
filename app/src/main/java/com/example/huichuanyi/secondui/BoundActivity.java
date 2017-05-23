package com.example.huichuanyi.secondui;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.activity.MainActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class BoundActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEditTextPhone,mEditTextAuth;
    private Button mButtonGetMsm,mButtonSure;
    private ImageView mImageViewBack;
    private TextView mTextViewJump;
    private static final int SUBMIT_VERIFICATION_CODE_COMPLETE = 1;
    private static final int GET_VERIFICATION_CODE_COMPLETE = 2;
    private static final int RESULT_ERROR = 3;
    private String phone;
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
                    Toast.makeText(BoundActivity.this, "验证码获取成功", Toast.LENGTH_SHORT).show();
                    break;
                case GET_VERIFICATION_CODE_COMPLETE:
                    sendPhone();
                    finish();
                    break;
                case RESULT_ERROR:
                    Toast.makeText(BoundActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound);

    }

    @Override
    public void initView() {
        mEditTextPhone = (EditText) findViewById(R.id.et_bound_phone);
        mEditTextAuth = (EditText) findViewById(R.id.et_bound_auth);
        mButtonGetMsm = (Button) findViewById(R.id.bt_bound_get);
        mButtonSure = (Button) findViewById(R.id.bt_bound_sure);
        mImageViewBack = (ImageView) findViewById(R.id.iv_bound_back);
        mTextViewJump = (TextView) findViewById(R.id.tv_bound_jump);
    }

    @Override
    public void initData() {
    }

    @Override
    public void setData() {
    }

    @Override
    public void setListener() {
        mButtonGetMsm.setOnClickListener(this);
        mButtonSure.setOnClickListener(this);
        mImageViewBack.setOnClickListener(this);
        mTextViewJump.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case  R.id.bt_bound_get:
                phone = mEditTextPhone.getText().toString().trim();
                SMSSDK.initSDK(this, "19168cd291b14", "ffddabe45b829578796641cdd99d6d76");
                SMSSDK.registerEventHandler(eh);
                SMSSDK.getVerificationCode("86", phone);
                Toast.makeText(BoundActivity.this, "发送验证码到"+phone, Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_bound_sure:
                String auth = mEditTextAuth.getText().toString().trim();
                SMSSDK.submitVerificationCode("86", phone, auth);
                break;
            case R.id.iv_bound_back:
                ActivityUtils.switchTo(this, MainActivity.class);
                finish();
                break;
            case R.id.tv_bound_jump:
                ActivityUtils.switchTo(this, MainActivity.class);
                finish();
                break;
        }
    }
    public void sendPhone(){
        RequestParams params = new RequestParams(NetConfig.THIRD_BOUND);

        params.addBodyParameter("id", SharedPreferenceUtils.getUserData(this,1));

        params.addBodyParameter("phone",phone);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                int b = (int)Double.parseDouble(result);
                if(b==1) {
                    Toast.makeText(BoundActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                }else if(b==0) {
                    Toast.makeText(BoundActivity.this, "绑定失败", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(BoundActivity.this, "服务器错误，请重新绑定", Toast.LENGTH_SHORT).show();
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
