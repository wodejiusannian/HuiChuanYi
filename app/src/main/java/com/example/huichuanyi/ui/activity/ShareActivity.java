package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.Location;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.MySharedPreferences;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class ShareActivity extends BaseActivity {
    private Map<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
    }

    @Override
    public void initView() {
        this.findViewById(R.id.act_btn_share_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareActivity();
            }
        });
    }

    private void shareActivity() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle("慧美衣橱");
        oks.setTitleUrl("http://hmyc365.net:8080/html/365/365.html");
        oks.setText("轻松生活来自慧美，让衣橱管理走进千万家");
        oks.setUrl("http://hmyc365.net:8080/html/365/365.html");
        oks.setImageUrl("http://hmyc365.net:8080/html/365/365.html");
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                map.put("location", MySharedPreferences.getBuyCity(ShareActivity.this));
                map.put("order_365", "365");
                Location.Location_type = 1;
                ActivityUtils.switchTo(ShareActivity.this, LiJiYuYueActivity.class, map);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(ShareActivity.this, "分享出错", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(ShareActivity.this, "分享取消", Toast.LENGTH_SHORT).show();
            }
        });
        oks.show(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {

    }

    public void back(View view) {
        finish();
    }
}
