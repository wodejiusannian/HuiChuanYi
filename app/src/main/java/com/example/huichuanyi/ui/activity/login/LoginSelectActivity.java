package com.example.huichuanyi.ui.activity.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.MyThirdData;
import com.example.huichuanyi.share.Login;
import com.example.huichuanyi.ui.activity.register.RegisterSelectActivity;
import com.example.huichuanyi.utils.ActivityUtils;

import org.xutils.view.annotation.Event;
import org.xutils.x;

import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginSelectActivity extends AppCompatActivity implements MyThirdData {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_copy_login);
        x.view().inject(this);
        initData();
        setData();
    }

    private void setData() {
    }

    private void initData() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("iwantfinish");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
    }

    @Event({R.id.login_phone, R.id.login_we_chat, R.id.login_qq,R.id.go_register})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_phone:
                startActivity(new Intent(this, LoginByPhoneActivity.class));
                break;
            case R.id.login_we_chat:
                new Login(this).whileLogin(this, Wechat.NAME);
                break;
            case R.id.login_qq:
                new Login(this).whileLogin(this, QQ.NAME);
                break;
            case R.id.go_register:
                ActivityUtils.switchTo(this, RegisterSelectActivity.class);
                finish();
                break;
            default:
                break;
        }
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void getData(String url, String openid, String userName) {
        Toast.makeText(this, "url" + url + "openid" + openid + "userName" + userName, Toast.LENGTH_SHORT).show();
    }

    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("iwantfinish")) {
                finish();
            }
        }
    };
}
