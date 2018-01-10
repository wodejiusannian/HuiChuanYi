package com.example.huichuanyi.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.huichuanyi.R;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

import butterknife.BindView;

public class SlwGoActivity extends BaseActivity {

    @BindView(R.id.wb_web_loading_html)
    WebView mWeb;

    @BindView(R.id.pr_web_loading_hint)
    ProgressBar mLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

    }


    @Override
    public void setListener() {

    }


    @Override
    public void initData() {
        String studio_name = getIntent().getStringExtra("studio_name");
        String studio_pic = getIntent().getStringExtra("studio_pic");
        String user_id = SharedPreferenceUtils.getUserData(this, 1);
        String user_name = SharedPreferenceUtils.getUserData(this, 2);
        String user_pic = SharedPreferenceUtils.getUserData(this, 3);
        String url = "http://hmyc365.net/file/html/app/vipServiceCount/index.html?user_id=%s&user_name=%s&user_pic=%s&studio_name=%s&studio_pic=%s";
        loadindUrl(String.format(url, user_id, user_name, user_pic, studio_name, studio_pic));
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
