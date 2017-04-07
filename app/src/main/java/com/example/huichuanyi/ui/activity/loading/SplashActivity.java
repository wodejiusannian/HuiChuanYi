package com.example.huichuanyi.ui.activity.loading;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.activity.MainActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.MyJson;
import com.example.huichuanyi.utils.MySharedPreferences;
import com.example.huichuanyi.utils.User;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class SplashActivity extends AppCompatActivity {
    private int isFirst = 0;
    private int user_id = 0;

    @ViewInject(R.id.iv_splash_ad)
    private SimpleDraweeView mSv;
    @ViewInject(R.id.tv_jump)
    private TextView jump;
    private int minute = 3;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            Toast.makeText(SplashActivity.this, "what" + what, Toast.LENGTH_SHORT).show();
            if (what == 1) {
                switch (isFirst) {
                    case 1:
                        loadingAD();
                        //startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        break;
                    case 0:
                        startActivity(new Intent(SplashActivity.this, LoadingActivity.class));
                        finish();
                        break;
                }
            }
            if (what == 2) {
                minute--;
                if (minute == 0) {
                    jumpMainActivity();
                }
                jump.setText(minute + "跳转");
                mHandler.sendEmptyMessageDelayed(2, 1000);
            }
            if (what == 3) {
                jumpMainActivity();
            }
        }
    };

    private Handler mHandler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            jumpMainActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        x.view().inject(this);
        initData();
        setData();
        setListener();
    }


    public void initData() {
        isFirst = MySharedPreferences.isFirst(this);
        user_id = new User(this).getUseId();
        if (user_id > 0) {
            isBuy365();
        }
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
        mHandler.sendEmptyMessageDelayed(1, 2000);
        //mHandler2.sendEmptyMessageDelayed(1, 5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeMessages(2);
            mHandler.removeMessages(3);
        }
    }

    private void loadingAD() {
        /*
        * 如果没有网络的情况下
        * */
        if (!isNetworkAvailable()) {
            mHandler.sendEmptyMessageAtTime(3, 1000);
            return; 
        }
        RequestParams params = new RequestParams("http://192.168.1.176:8081/HM/app/a/sys/constant/login/pidc.do");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (!CommonUtils.isEmpty(result)) {
                    mSv.setImageURI("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1769030056,810166357&fm=23&gp=0.jpg");
                    jump.setVisibility(View.VISIBLE);
                }
                mHandler.sendEmptyMessage(2);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(SplashActivity.this, "111", Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessage(2);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Event({R.id.tv_jump})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_jump:
                jumpMainActivity();
                break;
            default:
                break;
        }
    }

    /*
    * 跳转到主页面
    * */
    private void jumpMainActivity() {
        ActivityUtils.switchTo(this, MainActivity.class);
        finish();
    }

    /*
    * 判断是否购买365
    * */
    private void isBuy365() {
        RequestParams params = new RequestParams(NetConfig.IS_BUY_365);
        params.addBodyParameter("user_id", user_id + "");
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

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
