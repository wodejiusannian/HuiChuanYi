package com.example.huichuanyi.ui.activity.lanyang;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.custom.MySelfPayDialog;
import com.example.huichuanyi.ui.activity.RTCReportActivity;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.IsSuccess;
import com.example.huichuanyi.utils.RxBus;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsPay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import rx.functions.Action1;

public class RTCWebActivity extends BaseActivity implements IsSuccess {

    @BindView(R.id.wb_activity_rtc_content)
    WebView webContent;

    @BindView(R.id.tv_activity_rtc_title)
    TextView title;

    private UtilsPay mPay;

    String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtcweb);
    }

    @Override
    protected void setListener() {
        mPay = new UtilsPay(this);
        mPay.isSuccess(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String brand = intent.getStringExtra("brand");
        String click_url = intent.getStringExtra("click_url");
        click_url = click_url + "?user_id=" + SharedPreferenceUtils.getUserData(this, 1);
        title.setText(brand);
        loadingUrl(click_url);
    }

    @Override
    protected void setData() {
        RxBus.getDefault().toObservable(Integer.class)
                .subscribe(new Action1<Integer>() {
                               @Override
                               public void call(Integer userEvent) {
                                   if (userEvent == 1) {
                                       //微信支付成功
                                       onSuccessResult();
                                   } else {
                                       //微信支付失败
                                       Toast.makeText(RTCWebActivity.this, "微信支付失败", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                // TODO: 处理异常
                            }
                        });
    }


    public void back(View view) {
        finish();
    }


    private void loadingUrl(String url) {
        WebSettings webSettings = webContent.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("TAG", "shouldOverrideUrlLoading: ----" + url);

                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                if (url.contains("http://www.hmyc365.cn/rtc/order/getSign?order_id=")) {
                    final String[] split = url.split("=");
                    MySelfPayDialog dialog = new MySelfPayDialog(RTCWebActivity.this);
                    dialog.setOnNoListener("取消", null);
                    dialog.setOnYesListener("确定", new MySelfPayDialog.OnYesClickListener() {
                        @Override
                        public void onClick(String tag) {
                            onNetResultOrder(split[1], tag);
                        }
                    });
                    dialog.show();
                    return true;
                } else if (url.contains("http://www.hmyc365.cn/rtc/lookReport")) {
                    ActivityUtils.switchTo(RTCWebActivity.this, RTCReportActivity.class);
                    return true;
                } else {
                    view.loadUrl(url);
                    return true;
                }
            }
        });
        WebChromeClient wvcc = new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {


            }

        };
        webContent.setWebChromeClient(wvcc);
        if (url != null) {
            webContent.loadUrl(url);
        }
    }

    private void onNetResultOrder(String order_id, final String tag) {
        this.order_id = order_id;
        RequestParams pa = new RequestParams("http://hmyc365.net:8081/HM/app/rtc/pay/sign/getSign.do");
        pa.addBodyParameter("order_id", order_id);
        pa.addBodyParameter("user_id", SharedPreferenceUtils.getUserData(this, 1));
        pa.addBodyParameter("pay_type", tag);
        x.http().post(pa, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                switch (tag) {
                    //一网通支付
                    case "3":
                        Toast.makeText(RTCWebActivity.this, "本支付方式尚未开通，请使用微信或者支付宝付款", Toast.LENGTH_SHORT).show();
                        break;
                    //支付宝支付
                    case "1":
                        try {
                            JSONObject object = new JSONObject(result);
                            JSONObject body = object.getJSONObject("body");
                            String sign = body.getString("sign");
                            mPay.aliPay(sign);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    //微信支付
                    case "2":
                        mPay.weChatPay(result);
                        break;
                    default:
                        break;
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
        });
    }

    @Override
    public void isSuccess(int success) {
        switch (success) {
            case 9000:
                CommonUtils.Toast(this, "支付成功");
                onSuccessResult();
                break;
            case 9001:
                CommonUtils.Toast(this, "支付失败");
                finish();
                break;
            default:
                break;
        }
    }

    //在rec中支付完成后我们应该做的操作
    private void onSuccessResult() {
        RequestParams pa = new RequestParams("http://hmyc365.net:8081/HM/app/rtc/order/info/getOrderRtcUrl.do");
        pa.addBodyParameter("user_id", SharedPreferenceUtils.getUserData(this, 1));
        pa.addBodyParameter("order_id", order_id);
        x.http().post(pa, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject ob = new JSONObject(result);
                    JSONArray body = ob.getJSONArray("body");
                    final String rtc_url = body.getJSONObject(0).getString("rtc_url");
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            webContent.loadUrl(rtc_url);
                        }
                    });
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
        });
    }
}