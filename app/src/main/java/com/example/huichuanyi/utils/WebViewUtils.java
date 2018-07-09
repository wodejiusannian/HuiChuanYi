package com.example.huichuanyi.utils;

import android.graphics.Bitmap;
import android.util.Log;
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

        void onResultUrl(String title, String url);
    }

    private WebOnResult mWebOnResult;

    public WebViewUtils(WebOnResult mWebOnResult) {
        this.mWebOnResult = mWebOnResult;
    }

    public void LoadingUrl(WebView webView, String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //view.loadUrl(url);
                mWebOnResult.onResultUrl(view.getTitle(), url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mWebOnResult.onResultUrl(view.getTitle(), url);
                super.onPageFinished(view, url);
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

    public void LoadingUrl2(WebView webView, String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //view.loadUrl(url);
                mWebOnResult.onResultUrl(url, url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mWebOnResult.onResultUrl(view.getTitle(), url);
                super.onPageFinished(view, url);
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
