package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.MyJson;
import com.example.huichuanyi.utils.MySharedPreferences;
import com.example.huichuanyi.utils.User;
import com.example.huichuanyi.utils.UtilsInternet;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

public class My_365Activity extends BaseActivity implements UtilsInternet.XCallBack {

    private UtilsInternet internet = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();


    private SimpleDraweeView userPhoto, studioLogo;

    private TextView userName, useDate, studioName, isOpen, studioInfo;
    private String user_id, m365;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String user_name = data.getString("user_name");
            String start_time = data.getString("start_time");
            String end_time = data.getString("end_time");
            String studio_name = data.getString("studio_name");
            String studio_introduction = data.getString("studio_introduction");
            String studio_photo = data.getString("studio_photo");
            studioLogo.setImageURI(studio_photo);
            userName.setText(user_name);
            useDate.setText(end_time + "到期");
            studioName.setText(studio_name);
            if (TextUtils.equals("365", m365)) {
                isOpen.setText("已开通");
            }
            studioInfo.setText(studio_introduction);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_365);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        userPhoto = (SimpleDraweeView) findViewById(R.id.sv_user_photo);
        studioLogo = (SimpleDraweeView) findViewById(R.id.sv_studio_logo);
        userName = (TextView) findViewById(R.id.tv_user_name);
        useDate = (TextView) findViewById(R.id.tv_uesr_date);
        studioName = (TextView) findViewById(R.id.tv_studio_name);
        isOpen = (TextView) findViewById(R.id.tv_studio_is_open);
        studioInfo = (TextView) findViewById(R.id.tv_365_studio_info);
    }

    @Override
    public void initData() {
        user_id = new User(this).getUseId() + "";
        String user_photo = getIntent().getStringExtra("userPhoto");
        userPhoto.setImageURI(user_photo);
        map.put("user_id", user_id);
        internet.post(NetConfig.IS_BUY_365, map, this);
    }

    @Override
    public void setData() {
        m365 = MySharedPreferences.get365(this);
    }

    @Override
    public void setListener() {

    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onResponse(String result) {
        String ret = MyJson.getRet(result);
        if (!TextUtils.equals("0", ret)) return;
        try {
            JSONObject object = new JSONObject(result);
            JSONObject body = object.getJSONObject("body");
            String start_time = body.getString("start_time");
            String studio_name = body.getString("studio_name");
            String price_365 = body.getString("price_365");
            String user_name = body.getString("user_name");
            String end_time = body.getString("end_time");
            String studio_photo = body.getString("studio_photo");
            String studio_id = body.getString("studio_id");
            String studio_introduction = body.getString("studio_introduction");
            Bundle bundle = new Bundle();
            bundle.putString("start_time", start_time);
            bundle.putString("studio_name", studio_name);
            bundle.putString("price_365", price_365);
            bundle.putString("user_name", user_name);
            bundle.putString("end_time", end_time);
            bundle.putString("studio_photo", studio_photo);
            bundle.putString("studio_id", studio_id);
            bundle.putString("studio_introduction", studio_introduction);
            Message msg = Message.obtain();
            msg.setData(bundle);
            mHandler.sendMessage(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
