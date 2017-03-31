package com.example.huichuanyi.ui.activity.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.MyThirdData;
import com.example.huichuanyi.custom.MyProgressDialog;
import com.example.huichuanyi.share.Login;
import com.example.huichuanyi.ui.activity.login.LoginSelectActivity;
import com.example.huichuanyi.utils.ActivityUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class RegisterSelectActivity extends AppCompatActivity implements MyThirdData {
    private ProgressDialog dialog;
    @ViewInject(R.id.checkBox)
    private CheckBox checkBox;
    private boolean isJump = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_register);
        x.view().inject(this);
        initEvent();
    }

    private void initEvent() {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isJump = isChecked;
            }
        });
    }

    @Event({R.id.register_phone, R.id.register_we_chat, R.id.register_qq, R.id.go_login,R.id.go_service})
    private void onClick(View v) {
        if (isJump) {
            switch (v.getId()) {
                case R.id.register_phone:
                    startActivity(new Intent(this, RegisterPhoneActivity.class));
                    break;
                case R.id.register_we_chat:
                    progress();
                    new Login(this).whileLogin(this, Wechat.NAME);
                    break;
                case R.id.register_qq:
                    progress();
                    new Login(this).whileLogin(this, QQ.NAME);
                    break;
                case R.id.go_login:
                    ActivityUtils.switchTo(this, LoginSelectActivity.class);
                    finish();
                    break;
                case R.id.go_service:
                    ActivityUtils.switchTo(this,RegisterHMService.class);
                    break;
                default:
                    break;
            }
        }
    }

    public void back(View view) {
        finish();
    }

    public void progress() {
        dialog = new MyProgressDialog(this);
        dialog.show();
    }

    public void disprogress() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void getData(String url, String openid, String userName) {
        Log.i("TAG", "--------" + openid);
    }
}
