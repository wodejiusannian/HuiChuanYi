package com.example.huichuanyi.ui.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.ui.activity.custom.YWTWebView;
import com.example.huichuanyi.utils.PayUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;

public class YWTPayActivity extends BaseActivity implements PayUtils.Sign {
    @ViewInject(R.id.ywt_pay)
    private YWTWebView ywtWebView;
    private PayUtils payUtils = PayUtils.getInstance();
    private static final String payUrl = "https://netpay.cmbchina.com/netpayment/BaseHttp.dll?MB_EUserPay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ywtpay);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        String order_id = intent.getStringExtra("order_id");
        String type = intent.getStringExtra("type");
        payUtils.payYWT(order_id, type, this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {

    }

    @Override
    public void getSign(String sign) {
        try {
            JSONObject object = new JSONObject(sign);
            JSONObject body = object.getJSONObject("body");
            String mSign = body.getString("sign");
            String jsondata = "jsonRequestData=" + mSign;
            ywtWebView.postUrl(payUrl, jsondata.getBytes());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void back(View view) {
        if (ywtWebView.canGoBack()) {
            ywtWebView.goBack();
        } else {
            finish();
        }
    }

    /*
     * 监听返回键
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (ywtWebView.canGoBack()) {
                ywtWebView.goBack();
            } else {
                finish();
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
