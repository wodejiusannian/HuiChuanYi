package com.example.huichuanyi.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base_2.BaseFragment;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.ui.activity.lanyang.LyDetailsWebActivity;
import com.example.huichuanyi.utils.AsyncHttpUtils;
import com.example.huichuanyi.utils.HttpCallBack;
import com.example.huichuanyi.utils.HttpUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.rong.imkit.RongIM;

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
public class SlwFragmentGuidance extends BaseFragment {
    @Override
    protected int layoutInflaterId() {
        return R.layout.fragment_slw_private_guidance_manager;
    }

    @BindView(R.id.wb_test_one)
    WebView one;

    @BindView(R.id.wb_test_two)
    WebView two;

    @BindView(R.id.scrollview_guidance_content)
    ScrollView scrollView;

    @BindView(R.id.tv_test_zero)
    TextView zero;

    private Handler handler = new Handler();

    private int userEvent;

    private String studio_name, studio_id;

    private double x1, x2;
    private double y1, y2;

    String url;

    String url2;


    @BindView(R.id.refrsh)
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void initData() {
        super.initData();
        userEvent = getArguments().getInt("userEvent");
        studio_name = getArguments().getString("studio_name");
        studio_id = getArguments().getString("studio_id");
        String s = SharedPreferenceUtils.getUserData(getContext(), 1);
        url = "http://hmyc365.net/file/html/app/liangTi/index.html?user_id=%s";
        url = String.format(url, s);
        url2 = "http://hmyc365.net:8082/file/hm/html/report/index.html?user_id=%s";
        url2 = String.format(url2, s);
        loadindUrl(one, url);
        loadindUrl(two, url2);

    }

    private void loadindUrl(WebView web, String url) {
        WebSettings webSettings = web.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if ("http://www.huimei.com/connect/manager".equals(url)) {
                    showStudioDialog(2);
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
                    if (10082 == userEvent) {
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                scrollToPosition(0, zero.getHeight() + one.getHeight());
                            }
                        });
                    }
                } else {
                }

            }

        };
        web.setWebChromeClient(wvcc);
        if (url != null) {
            web.loadUrl(url);
        }
    }

    /**
     * 监听 双击
     */
    private long time = 0;

    @Override
    protected void setData() {
        super.setData();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                loadindUrl(one, url);
                loadindUrl(two, url2);
            }
        });
        one.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = one.getX();
                        y1 = one.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        double oneX = one.getX();
                        double oneY = one.getY();
                        double sqrt = Math.sqrt(Math.abs(oneX - x1) + Math.abs(oneY - y1));
                        if (sqrt < 20) {
                            if ((System.currentTimeMillis() - time > 1000)) {
                                time = System.currentTimeMillis();
                            } else {
                                jumpNext(url);
                            }
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        two.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x2 = one.getX();
                        y2 = one.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        double oneX = one.getX();
                        double oneY = one.getY();
                        double sqrt = Math.sqrt(Math.abs(oneX - x2) + Math.abs(oneY - y2));
                        if (sqrt < 20) {
                            if ((System.currentTimeMillis() - time > 1000)) {
                                time = System.currentTimeMillis();
                            } else {
                                jumpNext(url2);
                            }
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void jumpNext(String details_page) {
        Intent intent = new Intent(getContext(), LyDetailsWebActivity.class);
        intent.putExtra("details_page", details_page);
        startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void scrollToPosition(int x, int y) {
        ObjectAnimator xTranslate = ObjectAnimator.ofInt(scrollView, "scrollX", x);
        ObjectAnimator yTranslate = ObjectAnimator.ofInt(scrollView, "scrollY", y);
        AnimatorSet animators = new AnimatorSet();
        animators.setDuration(1000L);
        animators.playTogether(xTranslate, yTranslate);
        animators.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                // TODO Auto-generated method stub

            }
        });
        animators.start();
    }

    public void showStudioDialog(int userEvent) {
        Map map = new HashMap();
        map.put("studio_id", studio_id);
        map.put("user_id", SharedPreferenceUtils.getUserData(getContext(), 1));
        map.put("demandType", userEvent + "");
        String json = HttpUtils.toJson(map);
        new AsyncHttpUtils(new HttpCallBack() {
            @Override
            public void onResponse(String result) {
                String msg = "提示：已向 %s 工作室发送短信通知，您还可以选择咨询 %s 工作室！";
                msg = String.format(msg, studio_name, studio_name);
                MySelfDialog mDialog = new MySelfDialog(getActivity());
                mDialog.setMessage(msg);
                mDialog.setOnNoListener("取消", null);
                mDialog.setOnYesListener("去联系", new MySelfDialog.OnYesClickListener() {
                    @Override
                    public void onClick() {
                        chat();
                    }
                });
                mDialog.show();
            }
        }, getActivity()).execute("http://hmyc365.net/HM/bg/hmyc/vip/info/noticeStudio.do", json);
    }

    private void chat() {
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
