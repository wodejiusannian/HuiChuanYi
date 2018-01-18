package com.example.huichuanyi.secondui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.SMSTimeUtils;
import com.example.huichuanyi.custom.dialog.VerificationCodeDialog;
import com.example.huichuanyi.ui.activity.MainActivity;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;

public class BoundActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_bound_phone)
    EditText mEditTextPhone;
    @BindView(R.id.et_bound_auth)
    EditText mEditTextAuth;
    @BindView(R.id.bt_bound_get)
    TextView mButtonGetMsm;
    @BindView(R.id.bt_bound_sure)
    Button mButtonSure;
    @BindView(R.id.tv_bound_jump)
    TextView mTextViewJump;

    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound);

    }


    @Override
    public void initData() {

    }

    @Override
    public void setData() {
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void setListener() {
        mButtonGetMsm.setOnClickListener(this);
        mButtonSure.setOnClickListener(this);
        mTextViewJump.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_bound_get:
                phone = mEditTextPhone.getText().toString().trim().replace(" ", "");
                VerificationCodeDialog codeDialog = new VerificationCodeDialog(this);
                codeDialog.setPhone(phone, new VerificationCodeDialog.EditYes() {
                    @Override
                    public void getEdit() {
                        SMSTimeUtils smsTimeUtils = new SMSTimeUtils(mButtonGetMsm, 60000, 1000);
                        smsTimeUtils.start();
                    }
                });
                codeDialog.show();
                break;
            case R.id.bt_bound_sure:
                sendPhone();

                break;
            case R.id.tv_bound_jump:
                ActivityUtils.switchTo(this, MainActivity.class);
                finish();
                break;
        }
    }

    public void sendPhone() {
        RequestParams params = new RequestParams(NetConfig.THIRD_BOUND);

        params.addBodyParameter("id", SharedPreferenceUtils.getUserData(this, 1));

        params.addBodyParameter("phone", phone);

        params.addBodyParameter("code", mEditTextAuth.getText().toString().replace(" ", ""));

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                int b = (int) Double.parseDouble(result);
                if (b == 1) {
                    Toast.makeText(BoundActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                    ActivityUtils.switchTo(BoundActivity.this, MainActivity.class);
                    finish();
                } else if (b == 0) {
                    Toast.makeText(BoundActivity.this, "绑定失败", Toast.LENGTH_SHORT).show();
                } else {
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
