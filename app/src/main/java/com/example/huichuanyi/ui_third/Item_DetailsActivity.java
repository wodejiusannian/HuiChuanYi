package com.example.huichuanyi.ui_third;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.utils.ActivityUtils;

import java.util.HashMap;
import java.util.Map;

public class Item_DetailsActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout mJump;
    private Map<String, Object> map = new HashMap<>();
    private WebView mShow;
    private String test_url = "http://item.meilishuo.com/detail/1h3vzz8?ptp=1.zfrD1b.preferitem_mls.2.8O0Ij&acm=3.mce.2_4_1h3vzz8.37435.0.2PccHqbUZD8Vf.m_5168186";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

    }

    @Override
    public void initView() {
        mJump = (RelativeLayout) findViewById(R.id.rl_item_details_select);
        mShow = (WebView) findViewById(R.id.wb_item_show);
    }

    @Override
    public void initData() {
        initWebView();
        getUpActivityData();
    }

    /*
    * 初始化webview
    * */
    private void initWebView() {
        mShow.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        WebSettings webSettings = mShow.getSettings();
//支持js
        webSettings.setJavaScriptEnabled(true);
        //支持对网页缩放
        webSettings.setSupportZoom(true);
        //默认缩放模式
        mShow.setInitialScale(100);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        if (!TextUtils.isEmpty(test_url)) {
            mShow.loadUrl(test_url);
        }
    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        mJump.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_item_details_select:
                ActivityUtils.switchTo(this, Write_OrderActivity.class, map);
                break;
            default:
                break;
        }
    }


    private void getUpActivityData() {
        Intent intent = getIntent();
        String clothes_get = intent.getStringExtra("clothes_get");
        String color = intent.getStringExtra("color");
        String color_name = intent.getStringExtra("color_name");
        String id = intent.getStringExtra("id");
        String introduction = intent.getStringExtra("introduction");
        String name = intent.getStringExtra("name");
        String price_dj = intent.getStringExtra("price_dj");
        String reason = intent.getStringExtra("reason");
        String size_name = intent.getStringExtra("size_name");
        String type = intent.getStringExtra("type");
        map.put("clothes_get", clothes_get);
        map.put("color", color);
        map.put("color_name", color_name);
        map.put("id", id);
        map.put("introduction", introduction);
        map.put("name", name);
        map.put("price_dj", price_dj);
        map.put("reason", reason);
        map.put("size_name", size_name);
        map.put("type", type);
    }

    public void back(View view) {
        finish();
    }

}
