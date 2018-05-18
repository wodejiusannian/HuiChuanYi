package com.example.huichuanyi.ui.fragment.old;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base_2.BaseFragment;
import com.example.huichuanyi.emum.ServiceType;
import com.example.huichuanyi.ui.newpage.OrderStudioListActivity;
import com.example.huichuanyi.utils.ServiceSingleUtils;
import com.example.huichuanyi.utils.WebViewUtils;

import butterknife.BindView;
import butterknife.OnClick;

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
        return R.layout.fragment_mainservice;
    }

    private static final String mUrl = "http://hmyc365.net/hmyc/file/app-service/html/index.html";

    @Override
    protected void initData() {
        super.initData();
        mWebViewUtils = new WebViewUtils(new WebViewUtils.WebOnResult() {
            @Override
            public void onResultProgress(int progress) {

            }

            @Override
            public void onResultTitle(String title) {
                if ("hmyc365.net/hmyc/file/app-service/html/index.html#1".equals(title)) {
                    ServiceSingleUtils.getInstance().setServiceType(ServiceType.SERVICE_THE_DOOR);
                } else if ("hmyc365.net/hmyc/file/app-service/html/index.html#2".equals(title)) {
                    ServiceSingleUtils.getInstance().setServiceType(ServiceType.SERVICE_ACARUS_KILLING);
                }
            }

            @Override
            public void onResultUrl(String url,String u) {

            }
        });
        mWebViewUtils.LoadingUrl(webView, mUrl);
    }

    private WebViewUtils mWebViewUtils;

    @Override
    protected void initEvent() {
        super.initEvent();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                mWebViewUtils.LoadingUrl(webView, mUrl);
            }
        });
    }


    @OnClick({R.id.tv_order_sure})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_order_sure:
                startActivity(new Intent(getActivity(), OrderStudioListActivity.class));
                break;
            default:
                break;
        }
    }

}