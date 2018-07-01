package com.example.huichuanyi.ui.activity.loading;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.ShopTag;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.sql.SQLCLODao;
import com.example.huichuanyi.ui.activity.MainActivity;
import com.example.huichuanyi.ui.activity.login.LoginByAuthCodeActivity;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

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
                    if (!CommonUtils.isEmpty(SharedPreferenceUtils.getUserData(SplashActivity.this, 1)) && !CommonUtils.isEmpty(SharedPreferenceUtils.getToken(SplashActivity.this))) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, LoginByAuthCodeActivity.class));
                    }
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
        initView();
        initData();
        setData();
        setListener();
    }

    public void initView() {
        RequestParams params = new RequestParams(NetConfig.IS_BUY_365);
        params.addBodyParameter("user_id", SharedPreferenceUtils.getUserData(this, 1));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                String ret = JsonUtils.getRet(result);
                if (TextUtils.equals("0", ret)) {
                    SharedPreferenceUtils.save365(SplashActivity.this, "365");
                } else {
                    try {
                        JSONObject object = new JSONObject(result);
                        JSONObject body = object.getJSONObject("body");
                        String price = body.getString("activity_price");
                        String activity = body.getString("activity_state");
                        SharedPreferenceUtils.saveActivity(SplashActivity.this, activity, price);
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
        haha();
        mHandler.sendEmptyMessageDelayed(1, 2000);
    }


    private void haha() {
        final SQLCLODao dao = new SQLCLODao(SplashActivity.this);
        List<ShopTag.BodyBean> bean = dao.selectSort();
        Log.e("TAG", "onSuccess: -----bean" + bean);
        if (bean != null && !(bean.size() > 0)) {
            Log.e("TAG", "onSuccess: -----" + "hahahah");
            RequestParams params = new RequestParams("http://hmyc365.net:8084/HM/stu/tag/fac/clothes/getClothesTag.do");
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("TAG", "onSuccess: -----" + result);
                    Gson gson = new Gson();
                    ShopTag shopTag = gson.fromJson(result, ShopTag.class);
                    List<ShopTag.BodyBean> body = shopTag.getBody();
                    for (ShopTag.BodyBean sh : body) {
                        dao.addCloTAG(sh);
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

}
