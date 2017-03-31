package com.example.huichuanyi.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.activity.MainActivity;
import com.example.huichuanyi.ui.activity.register.RegisterPhoneActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.ReminderUtils;
import com.example.huichuanyi.utils.UtilsInternet;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

public class LoginByPhoneActivity extends AppCompatActivity implements UtilsInternet.XCallBack {
    private UtilsInternet net = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();
    private Map<String, Object> mapValue = new HashMap<>();
    private int internetFlag = 0;

    @ViewInject(R.id.et_phone)
    private EditText etPhone;
    @ViewInject(R.id.et_pwd)
    private EditText etPWD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_by_phone);
        x.view().inject(this);
    }


    @Event({R.id.tv_login, R.id.tv_forget_pwd})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_forget_pwd:
                mapValue.put("RorF", 1);
                ActivityUtils.switchTo(this, RegisterPhoneActivity.class, mapValue);
                break;
            default:
                break;
        }
    }

    /*
    * 去登陆
    * */
    private void login() {
        String phone = etPhone.getText().toString().trim().replace(" ", "");
        String pwd = etPWD.getText().toString().trim().replace(" ", "");
        if (CommonUtils.isEmpty(phone) || CommonUtils.isEmpty(pwd)) {
            ReminderUtils.Toast(this, "手机号和密码不能为空");
            return;
        }
        map.put("type", "1");
        map.put("phone", phone);
        map.put("pwd", pwd);
        net.post(NetConfig.SEND_LOGIN, map, this);
    }

    /*
    * 登录成功或者失败后的回调
    * */
    @Override
    public void onResponse(String result) {
        int user_id = stringToInt(result);
        if (user_id > 0) {
            goLogin();

        } else if (user_id == 0) {
            Toast.makeText(LoginByPhoneActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
        } else if (user_id == -1) {
            Toast.makeText(LoginByPhoneActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
        }
        //Log.i("TAG", "result" + result);
    }


    /*
    * 登录成功，将UserId保存，跳转到MainActivity，发送广播，关闭其他的页面
    * */
    private void goLogin() {
        ActivityUtils.switchTo(this, MainActivity.class);
        sendFinish();
        finish();
    }

    /*
    * 发送关闭其他页面的广播
    * */
    private void sendFinish() {
        Intent intent = new Intent();
        intent.setAction("iwantfinish");
        sendBroadcast(intent);
    }

    private int stringToInt(String str) {
        return Integer.parseInt(str);
    }

    public void back(View view) {
        finish();
    }

}
