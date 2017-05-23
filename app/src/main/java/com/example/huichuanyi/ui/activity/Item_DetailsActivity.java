package com.example.huichuanyi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.ActivityUtils;

import java.util.HashMap;
import java.util.Map;

public class Item_DetailsActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout mJump;
    private Map<String, Object> map = new HashMap<>();
    private WebView mShow;
    private String loadUrl;
    private ProgressBar mLoading;
    public static Item_DetailsActivity item_detailsActivity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        item_detailsActivity = this;
    }

    @Override
    public void initView() {
        mJump = (RelativeLayout) findViewById(R.id.rl_item_details_select);
        mShow = (WebView) findViewById(R.id.wb_item_show);
        mLoading = (ProgressBar) findViewById(R.id.pb_buy_loading);
    }

    @Override
    public void initData() {
        getUpActivityData();
        loadindUrl(loadUrl);
    }

    /*
    * 初始化webview
    * */


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
                ActivityUtils.switchTo(this, SLWWriteInfoActivity.class, map);
                break;
            default:
                break;
        }
    }


    private void getUpActivityData() {
        Intent intent = getIntent();
        String recommend_id = intent.getStringExtra("recommend_id");
        loadUrl = String.format(NetConfig.SHOP_DETAILS, recommend_id);
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
        map.put("recommend_id", recommend_id);
    }

    public void back(View view) {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        item_detailsActivity = null;
    }

    private void loadindUrl(String url) {
        WebSettings webSettings = mShow.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        mShow.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        WebChromeClient wvcc = new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress == 100) {
                    mLoading.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    mLoading.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    mLoading.setProgress(newProgress);//设置进度值
                }

            }

        };
        mShow.setWebChromeClient(wvcc);
        if (url != null) {
            mShow.loadUrl(url);
        }
    }
}
