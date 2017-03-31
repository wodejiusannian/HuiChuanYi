package com.example.huichuanyi.ui.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.AuthCodeUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.ReminderUtils;
import com.example.huichuanyi.utils.UtilsInternet;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

public class RegisterGetPhoneCodeActivity extends AppCompatActivity implements AuthCodeUtils.IsSuccessGetCode, UtilsInternet.XCallBack {
    @ViewInject(R.id.tv_code_phone)
    private TextView mPhone;
    @ViewInject(R.id.write_pass_world)
    private EditText mWritePWD;
    @ViewInject(R.id.phone_next)
    private TextView mJump;
    @ViewInject(R.id.btn_get_again)
    private TextView getAgain;
    @ViewInject(R.id.write_phone_code)
    private TextView writeCode;
    private int rorf;
    private String phone;
    private boolean isCanJump = false;
    private boolean isCanGetAgain = false;
    private int time = 60;
    private AuthCodeUtils authCode;

    private UtilsInternet net = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();
    private Map<String, Object> value = new HashMap<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;
            getAgain.setText("获取验证码(" + time + ")");
            if (time == 0) {
                isCanGetAgain = true;
                getAgain.setText("再次获取验证码");
                return;
            }
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_get_phone_code);
        x.view().inject(this);
        initData();
        setListener();
    }

    private void setListener() {
        mWritePWD.addTextChangedListener(watcher);
        writeCode.addTextChangedListener(watcher2);
    }


    private void initData() {
        Intent intent = getIntent();
        rorf = intent.getIntExtra("RorF", 0);
        phone = intent.getStringExtra("phone");
        mPhone.setText(phone);
        authCode = new AuthCodeUtils(this);
        mHandler.sendEmptyMessageDelayed(1, 1000);
        authCode.sendPhone(phone);
        authCode.isSuccess(this);
    }


    @Event({R.id.phone_next, R.id.btn_get_again})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_next:
                next();
                break;
            case R.id.btn_get_again:
                if (isCanGetAgain) {
                    time = 60;
                    isCanGetAgain = false;
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                    authCode.sendPhone(phone);
                }
                break;
            default:
                break;
        }
    }

    private void next() {
        if (rorf == 1) {
            Toast.makeText(this, "我修改密码了", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(this, RegisterUpPhotoActivity.class));
            /*if (isCanJump) {
                String auth = writeCode.getText().toString().trim();
                authCode.sendAuthCode(phone, auth);
            }*/
        }

    }


    /*
    * 验证码正确进行跳转
    * */
    @Override
    public void isSuccessGetCode() {
        //验证码正确修改密码
        if (rorf == 1) {
            Toast.makeText(this, "我修改密码了,我是不是要登录界面", Toast.LENGTH_SHORT).show();
        } else {
            //验证码成功，去注册
            goRegister();
        }
    }

    //验证码成功，去注册
    private void goRegister() {
        String pwd = mWritePWD.getText().toString().trim().replace(" ", "");
        map.put("phone", phone);
        map.put("pwd", pwd);
        net.post(NetConfig.SEND_PASSWORD, map, this);
    }

    /*
    *  下一步按钮的改变
    * */
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
            String code = writeCode.getText().toString().trim().replace(" ", "");
            if (s.length() > 3 && !CommonUtils.isEmpty(code)) {
                isCanJump = true;
                mJump.setBackgroundResource(R.mipmap.hm_nextstep_selected);
            } else {
                isCanJump = false;
                mJump.setBackgroundResource(R.mipmap.hm_nextstep_unselected);
            }
        }
    };
    /*
        *  下一步按钮的改变
        * */
    private TextWatcher watcher2 = new TextWatcher() {

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
            String code = mWritePWD.getText().toString().trim().replace(" ", "");
            if (code.length() > 3 && !CommonUtils.isEmpty(s.toString())) {
                isCanJump = true;
                mJump.setBackgroundResource(R.mipmap.hm_nextstep_selected);
            } else {
                isCanJump = false;
                mJump.setBackgroundResource(R.mipmap.hm_nextstep_unselected);
            }
        }
    };

    public void back(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        authCode.unRegisterEventHandler();
    }

    @Override
    public void onResponse(String result) {
        int userId = stringToInt(result);
        if (userId > 0) {
            ReminderUtils.Toast(this, "注册成功");
            value.put("userId", userId);
            ActivityUtils.switchTo(this, RegisterUpPhotoActivity.class, value);
        } else {
            ReminderUtils.Toast(this, "注册失败");
        }
    }

    private int stringToInt(String str) {
        return Integer.parseInt(str);
    }
}
