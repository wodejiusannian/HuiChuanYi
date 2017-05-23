package com.example.huichuanyi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.share.Share;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

public class HMWebActivity extends BaseActivity {

    @ViewInject(R.id.wb_web_loading_html)
    private WebView mWeb;

    @ViewInject(R.id.pr_web_loading_hint)
    private ProgressBar mLoading;


    private String hm_adpage_share_url, hm_activity_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

    }


    @Override
    public void setListener() {

    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        String hm_adpage_webview_url = intent.getStringExtra("hm_adpage_webview_url");
        hm_adpage_share_url = intent.getStringExtra("hm_adpage_share_url");
        hm_activity_name = intent.getStringExtra("hm_activity_name");
        loadindUrl(hm_adpage_webview_url);
    }

    @Override
    public void initData() {
        showLoading();
    }

    @Override
    public void setData() {

    }

    @Event({R.id.iv_web_share})
    private void onEvent(View v) {
        switch (v.getId()) {
            case R.id.iv_web_share:
                Share.showShare(this, hm_adpage_share_url, hm_activity_name);
                break;
            default:
                break;
        }
    }

    public void back(View view) {
        finish();
    }

    private void loadindUrl(String url) {
        WebSettings webSettings = mWeb.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        mWeb.setWebViewClient(new WebViewClient() {
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
                    dismissLoading();
                } else {
                    mLoading.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    mLoading.setProgress(newProgress);//设置进度值
                }

            }

        };
        mWeb.setWebChromeClient(wvcc);
        if (url != null) {
            mWeb.loadUrl(url);
        }
    }
}
