package com.example.huichuanyi.ui.activity.loading;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.example.huichuanyi.R;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.activity.LoadingActivity;
import com.example.huichuanyi.ui.activity.MainActivity;
import com.example.huichuanyi.ui.activity.login.LoginSelectActivity;
import com.example.huichuanyi.ui.activity.register.RegisterSelectActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.MyJson;
import com.example.huichuanyi.utils.MySharedPreferences;
import com.example.huichuanyi.utils.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.x;

public class SplashActivity extends AppCompatActivity {
    private int isFirst = 0;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (isFirst) {
                case 1:
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    break;
                case 0:
                    startActivity(new Intent(SplashActivity.this, LoadingActivity.class));
                    break;
            }
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        x.view().inject(this);
        initView();
        initData();
        setData();
        setListener();
    }

    public void initView() {
        RequestParams params = new RequestParams(NetConfig.IS_BUY_365);
        params.addBodyParameter("user_id", new User(this).getUseId() + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                String ret = MyJson.getRet(result);
                if (TextUtils.equals("0", ret)) {
                    MySharedPreferences.save365(SplashActivity.this, "365");
                } else {
                    try {
                        JSONObject object = new JSONObject(result);
                        JSONObject body = object.getJSONObject("body");
                        String price = body.getString("activity_price");
                        String activity = body.getString("activity_state");
                        MySharedPreferences.saveActivity(SplashActivity.this, activity, price);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    public void initData() {
        mPreferences = getSharedPreferences("isFirst", 0);
        isFirst = mPreferences.getInt("isFirst", 0);
        mEditor = mPreferences.edit();
        mEditor.putInt("isFirst", 1);
        mEditor.commit();
    }


    public void setData() {
        SharedPreferences hqSysCloTag = getSharedPreferences("hqSysCloTag", 0);
        final SharedPreferences.Editor edit = hqSysCloTag.edit();
        RequestParams params = new RequestParams(NetConfig.LABEL_PATH);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.equals("0", result)) {
                    edit.putString("hqSysCloTag", result);
                    edit.commit();
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

    public void setListener() {
        //mHandler.sendEmptyMessageDelayed(1, 3000);
    }

    @Event({R.id.btn_splash_login, R.id.btn_splash_register})
    private void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.btn_splash_login:
                    ActivityUtils.switchTo(this, LoginSelectActivity.class);
                    break;
                case R.id.btn_splash_register:
                    ActivityUtils.switchTo(this, RegisterSelectActivity.class);
                    break;
                default:
                    break;
            }
        }
    }
}
