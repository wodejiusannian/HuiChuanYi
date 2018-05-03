package com.example.huichuanyi.utils;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

// ┏┓　　　┏┓
// ┏┛┻━━━┛┻┓
// ┃　　　　　　　┃ 　
// ┃　　　━　　　┃
// ┃　┳┛　┗┳　┃
// ┃　　　　　　　┃
// ┃　　　┻　　　┃
// ┃　　　　　　　┃
// ┗━┓　　　┏━┛
// ┃　　　┃ 神兽保佑　　　　　　　　
// ┃　　　┃ 代码无BUG！
// ┃　　　┗━━━┓
// ┃　　　　　　　┣┓
// ┃　　　　　　　┏┛
// ┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
// ┗┻┛　┗┻┛
public class WebViewUtils {

    public interface WebOnResult {

        void onResultProgress(int progress);

        void onResultTitle(String title);

        void onResultUrl(String url);
    }

    private WebOnResult mWebOnResult;

    public WebViewUtils(WebOnResult mWebOnResult) {
        this.mWebOnResult = mWebOnResult;
    }

    public  void LoadingUrl(WebView webView, String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                //view.loadUrl(url);
                mWebOnResult.onResultUrl(url);
                return true;
            }
        });
        WebChromeClient wvcc = new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

                mWebOnResult.onResultTitle(title);

            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                mWebOnResult.onResultProgress(newProgress);

            }

        };
        webView.setWebChromeClient(wvcc);
        if (url != null) {
            webView.loadUrl(url);
        }
    }

}
