package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

import org.xutils.view.annotation.ViewInject;

import static android.webkit.WebSettings.LOAD_NO_CACHE;

public class ZhenDuanActivity extends BaseActivity {

    @ViewInject(R.id.wb_style_loading_html)
    private WebView mWeb;

    @ViewInject(R.id.pr_style_loading_hint)
    private ProgressBar mLoading;

    @Override
    protected void onCreate(Bundle savedInstancestylee) {
        super.onCreate(savedInstancestylee);
        setContentView(R.layout.activity_web_style);

    }


    @Override
    public void setListener() {

    }

    @Override
    public void initView() {
        String s = SharedPreferenceUtils.getUserData(this, 1);
        String url = String.format(NetConfig.CLO_ZHENDUAN, s);
        loadindUrl(url);
    }

    @Override
    public void initData() {
        showLoading();
    }

    @Override
    public void setData() {

    }


    public void back(View view) {
        finish();
    }

    private void loadindUrl(String url) {
        WebSettings webSettings = mWeb.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(LOAD_NO_CACHE);
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
                    mWeb.setVisibility(View.VISIBLE);
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
