package com.example.huichuanyi.ui.activity.custom;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.huichuanyi.ui.activity.IndentActivity;
import com.example.huichuanyi.ui.activity.MainActivity;
import com.example.huichuanyi.ui.activity.MyOrderActivity;
import com.example.huichuanyi.ui.activity.SLWRecordActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonStatic;

import cmb.pb.util.CMBKeyboardFunc;

/**
 * Created by ycw on 2017/3/17.
 */
public class YWTWebViewClient extends WebViewClient {


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        // 使用当前的WebView加载页面
        CMBKeyboardFunc kbFunc = new CMBKeyboardFunc((Activity) view.getContext());
        if (kbFunc.HandleUrlCall(view, request.getUrl().toString()) == false) {
            return super.shouldOverrideUrlLoading(view, view.getUrl());
        } else {
            return true;
        }
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // 使用当前的WebView加载页面
        CMBKeyboardFunc kbFunc = new CMBKeyboardFunc((Activity) view.getContext());
        if (url.contains("http://www.hmyc365.cn/")) {
            Activity context = (Activity) view.getContext();
            switch (CommonStatic.wechatType) {
                case "1":
                    ActivityUtils.switchTo(context, MyOrderActivity.class);
                    break;
                case "2":
                    ActivityUtils.switchTo(context, MainActivity.class);
                    break;
                case "3":
                    ActivityUtils.switchTo(context, SLWRecordActivity.class);
                    break;
                case "4":
                    ActivityUtils.switchTo(context, IndentActivity.class);
                    break;
                default:
                    break;
            }
            context.finish();
        }
        if (kbFunc.HandleUrlCall(view, url) == false) {
            return super.shouldOverrideUrlLoading(view, url);
        } else {
            return true;
        }
    }


}
