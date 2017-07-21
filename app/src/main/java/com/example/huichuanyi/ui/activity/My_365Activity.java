package com.example.huichuanyi.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class My_365Activity extends BaseActivity implements UtilsInternet.XCallBack, RongIM.UserInfoProvider {


    private SimpleDraweeView userPhoto, studioLogo;

    private TextView userName, useDate, studioName, isOpen, studioInfo;

    private String m365, studio_id, studio_name, studio_photo;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Toast.makeText(My_365Activity.this, "您好，您还没有开通365服务", Toast.LENGTH_SHORT).show();
                finish();
            } else if (msg.what == 2) {
                Toast.makeText(My_365Activity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Bundle data = msg.getData();
                String end_time = data.getString("end_time");
                String studio_name = data.getString("studio_name");
                String studio_introduction = data.getString("studio_introduction");
                String studio_photo = data.getString("studio_photo");
                studioLogo.setImageURI(studio_photo);
                useDate.setText(end_time + "到期");
                studioName.setText(studio_name);
                if (TextUtils.equals("365", m365)) {
                    isOpen.setText("已开通");
                } else {
                    Toast.makeText(My_365Activity.this, "您还没有开通365服务", Toast.LENGTH_SHORT).show();
                    finish();
                }
                studioInfo.setText(studio_introduction);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_365);

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
        userPhoto.setImageURI(SharedPreferenceUtils.getUserData(this, 3));
        userName.setText(SharedPreferenceUtils.getUserData(this, 2));
        RequestParams pa = new RequestParams(NetConfig.IS_BUY_365);
        pa.addBodyParameter("user_id", SharedPreferenceUtils.getUserData(this, 1));
        x.http().post(pa, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                String ret = JsonUtils.getRet(result);
                if (!TextUtils.equals("0", ret)) {
                    mHandler.sendEmptyMessage(1);
                    return;
                }
                try {
                    JSONObject object = new JSONObject(result);
                    JSONObject body = object.getJSONObject("body");
                    String start_time = body.getString("start_time");
                    studio_name = body.getString("studio_name");
                    String price_365 = body.getString("price_365");
                    studio_id = body.getString("studio_id");
                    String user_name = body.getString("user_name");
                    String end_time = body.getString("end_time");
                    studio_photo = body.getString("studio_photo");
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
            public void onError(Throwable ex, boolean isOnCallback) {
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

    @Override
    public void setData() {
        m365 = SharedPreferenceUtils.get365(this);
    }

    @Override
    public void setListener() {
        RongIM.setUserInfoProvider(this, true);
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onResponse(String result) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.btn_chat_with_manager:
                    Intent broadcast = new Intent("action.have.msg");
                    broadcast.putExtra("isRead", "yes");
                    sendOrderedBroadcast(broadcast, null);
                    RongIM im = RongIM.getInstance();
                    if (im != null && studio_id != null) {
                        im.startPrivateChat(My_365Activity.this, "hmgls_" + studio_id, studio_name);
                    }
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public UserInfo getUserInfo(String s) {
        if (s.split("_")[1].equals(SharedPreferenceUtils.getUserData(My_365Activity.this, 1))) {
            return new UserInfo(s, SharedPreferenceUtils.getUserData(My_365Activity.this, 2), Uri.parse(SharedPreferenceUtils.getUserData(My_365Activity.this, 3)));
        } else {
            return new UserInfo(s, userName.getText().toString(), Uri.parse(studio_photo));
        }
    }
}
