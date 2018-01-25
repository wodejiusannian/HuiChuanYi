package com.example.huichuanyi.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base_2.BaseFragment;
import com.example.huichuanyi.ui.activity.video.HMWebSlwActivity;
import com.example.huichuanyi.ui.newpage.OrderStudioActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static android.webkit.WebSettings.LOAD_NO_CACHE;

/**
 * Created by 小五 on 2017/3/23.
 */
public class Order2Fragment extends BaseFragment {

    @BindView(R.id.webview_order_content)
    WebView webView;

    @BindView(R.id.swipe)
    SwipeRefreshLayout refreshLayout;

    @Override
    protected int layoutInflaterId() {
        return R.layout.fragment_order_copys;
    }

    @Override
    protected void initData() {
        super.initData();
        loadindUrl(webView, "http://hmyc365.net/file/html/app/orderService/index.html");
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                loadindUrl(webView, "http://hmyc365.net/file/html/app/orderService/index.html");
            }
        });
    }

    private void loadindUrl(WebView web, String url) {
        WebSettings webSettings = web.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(LOAD_NO_CACHE);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                if ("http://www.huimei.com/problem/normal".equals(url)) {
                    Intent in = new Intent(getContext(), HMWebSlwActivity.class);
                    startActivity(in);
                    return true;
                }
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

                } else {

                }

            }

        };
        web.setWebChromeClient(wvcc);
        if (url != null) {
            web.loadUrl(url);
        }
    }

    @OnClick({R.id.tv_order_sure})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_order_sure:
                startActivity(new Intent(getActivity(), OrderStudioActivity.class));
                break;
            default:
                break;
        }
    }

}