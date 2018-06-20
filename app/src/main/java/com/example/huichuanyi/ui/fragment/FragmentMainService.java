package com.example.huichuanyi.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base_2.BaseFragment;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.VpSwipeRefreshLayout;
import com.example.huichuanyi.emum.ServiceType;
import com.example.huichuanyi.fragment_first.SinglePersonActivity;
import com.example.huichuanyi.ui.activity.HomeVideoActivity;
import com.example.huichuanyi.ui.activity.HomeVideoCoverActivity;
import com.example.huichuanyi.ui.activity.lanyang.LyShopListActivity;
import com.example.huichuanyi.ui.activity.lanyang.RTCWebActivity;
import com.example.huichuanyi.ui.newpage.HMURL2Activity;
import com.example.huichuanyi.ui.newpage.HMURLActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.ServiceSingleUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.WebViewUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import io.rong.imkit.RongIM;

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

    private static String mUrl = "http://hmyc365.net/hmyc/file/app-service/html/index-v1.html?token=%s&userId=%s";

    @Override
    protected void initData() {
        super.initData();
        mUrl = String.format(mUrl, NetConfig.TOKEN, SharedPreferenceUtils.getUserData(getContext(), 1));
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
            public void onResultUrl(String url, String u) {
                if (url.contains("yuyue?a=wardrobe_management")) {
                    ServiceSingleUtils.getInstance().setServiceType(ServiceType.SERVICE_THE_DOOR);
                    Intent intent = new Intent(getContext(), HMURL2Activity.class);
                    intent.putExtra("url", "http://hmyc365.net/hmyc/file/app/app-reservation-service/order.html");
                    intent.putExtra("title", "上门衣橱管理服务");
                    startActivity(intent);
                } else if (url.contains("yuyue?a=mite_service")) {
                    ServiceSingleUtils.getInstance().setServiceType(ServiceType.SERVICE_ACARUS_KILLING);
                    Intent intent = new Intent(getContext(), HMURL2Activity.class);
                    intent.putExtra("title", "上门除螨服务");
                    intent.putExtra("url", "http://hmyc365.net/hmyc/file/app/app-reservation-service/acarus.html");
                    startActivity(intent);
                } else if (url.contains("yuyue?a=micro_Lesson")) {
                    if (url.contains("Big_Cafe_class")) {
                        Intent intent = new Intent(getContext(), HomeVideoActivity.class);
                        intent.putExtra("id", "2");
                        startActivity(intent);
                    } else if (url.contains("HM_About_info")) {
                        ActivityUtils.switchTo(getActivity(), HomeVideoCoverActivity.class);
                    } else if (url.contains("HM_grand_meeting")) {
                        Toast.makeText(getContext(), getContext().getResources().getString(R.string.videonocontenttip), Toast.LENGTH_SHORT).show();
                    }
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
                        in.putExtra("title", brand.toString());
                        startActivity(in);
                    }
                } else if (url.contains("yuyue?a=my_manager") || url.contains("#yuyue?a=365state")) {
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
                } else if (url.contains("yuyue?a=chat_with_manager")) {
                    RequestParams params = new RequestParams(NetConfig.IS_BUY_365);
                    params.addBodyParameter("user_id", SharedPreferenceUtils.getUserData(getContext(), 1));
                    x.http().post(params, new Callback.CacheCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject object = new JSONObject(result);
                                JSONObject body = object.getJSONObject("body");
                                String studio_name = body.getString("studio_name");
                                String studio_id = body.getString("studio_id");
                                RongIM im = RongIM.getInstance();
                                if (im != null && studio_id != null) {
                                    im.startPrivateChat(getContext(), "hmgls_" + studio_id, studio_name);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {

                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }

                        @Override
                        public boolean onCache(String result) {
                            return false;
                        }
                    });
                }
            }
        });
        mWebViewUtils.LoadingUrl2(webView, mUrl);
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
                mWebViewUtils.LoadingUrl2(webView, mUrl);
            }
        });
    }
}