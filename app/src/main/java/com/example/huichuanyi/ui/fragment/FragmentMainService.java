package com.example.huichuanyi.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base_2.BaseFragment;
import com.example.huichuanyi.custom.VpSwipeRefreshLayout;
import com.example.huichuanyi.emum.ServiceType;
import com.example.huichuanyi.fragment_first.SinglePersonActivity;
import com.example.huichuanyi.ui.activity.HomeVideoCoverActivity;
import com.example.huichuanyi.ui.activity.lanyang.LyShopListActivity;
import com.example.huichuanyi.ui.activity.lanyang.RTCWebActivity;
import com.example.huichuanyi.ui.newpage.HMURLActivity;
import com.example.huichuanyi.ui.newpage.OrderStudioListActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.ServiceSingleUtils;
import com.example.huichuanyi.utils.WebViewUtils;

import butterknife.BindView;

/**
 * Created by 小五 on 2017/3/23.
 */
public class FragmentMainService extends BaseFragment {

    @BindView(R.id.webview_order_content)
    WebView webView;

    @BindView(R.id.swipe)
    VpSwipeRefreshLayout refreshLayout;

    @BindView(R.id.pb_mainservice_loading)
    ProgressBar loading;

    @Override
    protected int layoutInflaterId() {
        return R.layout.fragment_mainservice;
    }

    private static final String mUrl = "http://hmyc365.net/hmyc/file/app-service/html/index-v1.html";

    @Override
    protected void initData() {
        super.initData();
        mWebViewUtils = new WebViewUtils(new WebViewUtils.WebOnResult() {
            @Override
            public void onResultProgress(int progress) {
                if (100 == progress) {
                    loading.setVisibility(View.GONE);
                } else {
                    loading.setProgress(progress);
                }
            }

            @Override
            public void onResultTitle(String title) {

            }

            @Override
            public void onResultUrl(String url) {
                Log.e("TAG", "onResultUrl: -----" + url);
                if (url.contains("yuyue?a=wardrobe_management")) {
                    ServiceSingleUtils.getInstance().setServiceType(ServiceType.SERVICE_THE_DOOR);
                    ActivityUtils.switchTo(getActivity(), OrderStudioListActivity.class);
                } else if (url.contains("yuyue?a=mite_service")) {
                    ServiceSingleUtils.getInstance().setServiceType(ServiceType.SERVICE_ACARUS_KILLING);
                    ActivityUtils.switchTo(getActivity(), OrderStudioListActivity.class);
                } else if (url.contains("yuyue?a=micro_Lesson")) {
                    ActivityUtils.switchTo(getActivity(), HomeVideoCoverActivity.class);
                } else if (url.contains("#yuyue?supplierId=")) {
                    String supplierId = getInsideString(url, "#yuyue?supplierId=", "&brand");
                    String brand = getInsideString(url, "brand=", "&clickUrl=");
                    if ("RTC".equals(brand)) {
                        Intent intent = new Intent(getContext(), RTCWebActivity.class);
                        intent.putExtra("brand", "RTC");
                        intent.putExtra("click_url", url.substring(url.indexOf("clickUrl=") + "clickUrl=".length(), url.length()));
                        startActivity(intent);
                    } else {
                        Intent in = new Intent(getActivity(), LyShopListActivity.class);
                        in.putExtra("supplier_id", supplierId);
                        in.putExtra("brand", brand);
                        startActivity(in);
                    }
                } else if (url.contains("yuyue?a=my_manager")) {
                    Intent in = new Intent(getActivity(), SinglePersonActivity.class);
                    startActivity(in);
                } else if (url.contains("yuyue?a=banner_click")) {
                    String clickType = url.substring(url.indexOf("clickType=") + "clickType=".length(), url.length());
                    if ("1".equals(clickType)) {
                        Intent intent = new Intent(getContext(), HMURLActivity.class);
                        intent.putExtra("title", "慧美衣橱");
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                }
            }
        });
        mWebViewUtils.LoadingUrl(webView, mUrl);
    }

    private WebViewUtils mWebViewUtils;

    public String getInsideString(String str, String strStart, String strEnd) {
        if (str.indexOf(strStart) < 0) {
            return "";
        }
        if (str.indexOf(strEnd) < 0) {
            return "";
        }
        return str.substring(str.indexOf(strStart) + strStart.length(), str.indexOf(strEnd));
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loading.setVisibility(View.VISIBLE);
                refreshLayout.setRefreshing(false);
                mWebViewUtils.LoadingUrl(webView, mUrl);
            }
        });
    }
}