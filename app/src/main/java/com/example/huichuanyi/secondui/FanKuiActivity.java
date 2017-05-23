package com.example.huichuanyi.secondui;

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
import com.example.huichuanyi.utils.SharedPreferenceUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class FanKuiActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImageViewBack;
    private EditText mEditTextJianyi,mEditTextPhone;
    private Button mButtonTijiao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_kui);

    }

    @Override
    public void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.iv_fankui_back);
        mButtonTijiao = (Button) findViewById(R.id.btn_fankui_tijiao);
        mEditTextJianyi = (EditText) findViewById(R.id.et_fankui_jianyi);
        mEditTextPhone = (EditText) findViewById(R.id.et_fankui_phone);
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
        mButtonTijiao.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.iv_fankui_back:
                finish();
                break;
            case R.id.btn_fankui_tijiao:
                String fankui = mEditTextJianyi.getText().toString().trim();
                String phone = mEditTextPhone.getText().toString().trim();
                if(!TextUtils.isEmpty(fankui)) {
                    sendJianYi(fankui,phone);
                }else{
                    Toast.makeText(FanKuiActivity.this, "反馈不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public void sendJianYi(String fankui,String phone){
        RequestParams params = new RequestParams(NetConfig.SEND_FANKUI);
        params.addBodyParameter("userid", SharedPreferenceUtils.getUserData(this,1));
        params.addBodyParameter("content",fankui);
        params.addBodyParameter("phone",phone);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                int b = 0;
                try {
                    b = (int)Double.parseDouble(result);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if(b>0) {
                    Toast.makeText(FanKuiActivity.this, "反馈成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else if(b==0) {
                    Toast.makeText(FanKuiActivity.this, "反馈失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(FanKuiActivity.this, "网络连接失败，请检查网络", Toast.LENGTH_SHORT).show();
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
