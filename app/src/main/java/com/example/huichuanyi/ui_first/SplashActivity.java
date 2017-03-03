package com.example.huichuanyi.ui_first;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.MyLocationListener;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.MyJson;
import com.example.huichuanyi.utils.MySharedPreferences;
import com.example.huichuanyi.utils.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class SplashActivity extends AppCompatActivity {
    private int isFirst = 0;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener(this);
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (isFirst) {
                case 1:
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case 0:
                    Intent intent0 = new Intent(SplashActivity.this, LoadingActivity.class);
                    startActivity(intent0);
                    break;
            }
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        initView();
        initData();
        setData();
        setListener();
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
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
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        initLocation();
        mPreferences = getSharedPreferences("isFirst", 0);
        isFirst = mPreferences.getInt("isFirst", 0);
        mEditor = mPreferences.edit();
        mEditor.putInt("isFirst", 1);
        mEditor.commit();


    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        int span = 1000;
        option.setScanSpan(span);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);
        option.setIgnoreKillProcess(false);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
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
        mHandler.sendEmptyMessageDelayed(1, 3000);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }

}
