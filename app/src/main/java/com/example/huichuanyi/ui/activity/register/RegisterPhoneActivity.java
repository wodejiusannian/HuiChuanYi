package com.example.huichuanyi.ui.activity.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.UtilsInternet;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

public class RegisterPhoneActivity extends AppCompatActivity implements UtilsInternet.XCallBack {
    @ViewInject(R.id.register_phone_write)
    private EditText mPhone;
    @ViewInject(R.id.register_phone_next)
    private TextView mJump;

    private UtilsInternet net = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();
    private Map<String, Object> value = new HashMap<>();
    private boolean isCanJump = false;
    private int rorf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_phone_register);
        x.view().inject(this);
        initData();
        setListener();
    }

    private void initData() {
        rorf = getIntent().getIntExtra("RorF", 0);
    }

    @Event({R.id.register_phone_next, R.id.register_phone_write})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_phone_next:
                getPhoneCode();
                break;
            case R.id.register_phone_write:

                break;
            default:
                break;
        }
    }

    private void setListener() {
        mPhone.addTextChangedListener(watcher);
    }

    private void getPhoneCode() {
        String phone = mPhone.getText().toString().trim().replace(" ", "");
        if (!CommonUtils.isEmpty(phone) && isCanJump) {
            map.put("phone", phone);
            value.put("phone", phone);
            value.put("RorF", rorf);
            if (rorf == 1) {
                ActivityUtils.switchTo(this, RegisterGetPhoneCodeActivity.class, value);
            } else {
                net.post(NetConfig.IS_REGISTER, map, this);
            }
        } else {
            Toast.makeText(this, "请正确填写手机号", Toast.LENGTH_SHORT).show();
        }
    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 11) {
                isCanJump = true;
                mJump.setBackgroundResource(R.mipmap.hm_nextstep_selected);
            } else {
                isCanJump = false;
                mJump.setBackgroundResource(R.mipmap.hm_nextstep_unselected);
            }
        }

    };

    @Override
    public void onResponse(String result) {
        if (TextUtils.equals("1", result)) {
            Toast.makeText(RegisterPhoneActivity.this, "该手机号已注册", Toast.LENGTH_SHORT).show();
            return;
        }
        ActivityUtils.switchTo(this, RegisterGetPhoneCodeActivity.class, value);
    }

    public void back(View view) {
        finish();
    }
}
