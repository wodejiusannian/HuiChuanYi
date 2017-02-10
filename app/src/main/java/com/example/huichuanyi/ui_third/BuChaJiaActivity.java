package com.example.huichuanyi.ui_third;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.huichuanyi.R;
import com.example.huichuanyi.alipay.PayResult;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui_second.MyOrderActivity;
import com.example.huichuanyi.utils.ActivityUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

public class BuChaJiaActivity extends BaseActivity implements TextWatcher, View.OnClickListener {
    private EditText mEditTextYiFuLiang;
    private TextView mTextViewYingBuChaJia;
    private String YetPay, orderid, manager_name;
    private int nowMoney;
    private Button mButton;
    private int AliPayOrWechat = 1;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private ImageView mImageViewBack,
            mImageViewAlipayNormal, mImageViewAlipaySelect,
            mImageViewWechatNormal, mImageViewWechatSelect;
    private RelativeLayout mRelativeLayoutAliPay, mRelativeLayoutWechat;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(BuChaJiaActivity.this, "支付成功", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(BuChaJiaActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    ActivityUtils.switchTo(BuChaJiaActivity.this, MyOrderActivity.class);
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bu_cha_jia);
    }

    @Override
    public void initView() {
        mButton = (Button) findViewById(R.id.btn_pay_sure);
        mImageViewBack = (ImageView) findViewById(R.id.iv_buchajia_back);
        mEditTextYiFuLiang = (EditText) findViewById(R.id.shi_ji_yifu);
        mTextViewYingBuChaJia = (TextView) findViewById(R.id.ying_bu_chajia);
        mImageViewAlipayNormal = (ImageView) findViewById(R.id.iv_buchajia_alipayNomal);
        mImageViewAlipaySelect = (ImageView) findViewById(R.id.iv_buchajia_alipaySelect);
        //mImageViewWechatNormal = (ImageView) findViewById(R.id.iv_buchajia_wechatNomal);
        //mImageViewWechatSelect = (ImageView) findViewById(R.id.iv_buchajia_wechatSelect);
        mRelativeLayoutAliPay = (RelativeLayout) findViewById(R.id.rl_buchajia_alipay);
        //mRelativeLayoutWechat = (RelativeLayout) findViewById(R.id.rl_buchajia_wechat);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        YetPay = intent.getStringExtra("YetPay");
        orderid = intent.getStringExtra("orderid");
        manager_name = intent.getStringExtra("manager_name");
    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        mEditTextYiFuLiang.addTextChangedListener(this);
        mButton.setOnClickListener(this);
        mRelativeLayoutAliPay.setOnClickListener(this);
        // mRelativeLayoutWechat.setOnClickListener(this);
        mImageViewBack.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s)) {
            Integer nowCount = getInt(s.toString());
            int anInt = getInt(YetPay);
            if (200 < nowCount && nowCount <= 500) {
                nowMoney = 698 - anInt;
                mTextViewYingBuChaJia.setText("应补差价" + nowMoney);
                return;
            } else if (500 < nowCount) {
                int a = (nowCount - 500) / 200;
                if (nowCount % 100 != 0) {
                    a = a + 1;
                }
                nowMoney = (a * 300 + 698) - anInt;
                mTextViewYingBuChaJia.setText("应补差价" + nowMoney);
            } else {
                mTextViewYingBuChaJia.setText("应补差价0元");
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public int getInt(String str) {
        return Integer.parseInt(str);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay_sure:
                switch (AliPayOrWechat) {
                    case 1:
                        RequestParams params = new RequestParams(NetConfig.BU_CHA_JIA);
                        params.addBodyParameter("orderid", orderid);
                        params.addBodyParameter("type", "1");
                        params.addBodyParameter("money", nowMoney + "");
                        params.addBodyParameter("manager_name", manager_name);
                        params.addBodyParameter("remarks", "");
                        x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(final String get) {
                                Runnable payRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        PayTask alipay = new PayTask(BuChaJiaActivity.this);
                                        Map<String, String> result = alipay.payV2(get, true);
                                        Message msg = new Message();
                                        msg.what = SDK_PAY_FLAG;
                                        msg.obj = result;
                                        mHandler.sendMessage(msg);
                                    }
                                };
                                Thread payThread = new Thread(payRunnable);
                                payThread.start();
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                Toast.makeText(BuChaJiaActivity.this, "网络连接问题，请检查网络", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {

                            }

                            @Override
                            public void onFinished() {

                            }
                        });

                        break;
                    case 2:
                        Toast.makeText(BuChaJiaActivity.this, "正在开发，敬请期待", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case R.id.rl_buchajia_alipay:
                mImageViewAlipaySelect.setVisibility(View.VISIBLE);
                mImageViewAlipayNormal.setVisibility(View.GONE);
                mImageViewWechatNormal.setVisibility(View.VISIBLE);
                mImageViewWechatSelect.setVisibility(View.GONE);
                AliPayOrWechat = 1;
                break;
            /*case R.id.rl_buchajia_wechat:
                mImageViewAlipaySelect.setVisibility(View.GONE);
                mImageViewAlipayNormal.setVisibility(View.VISIBLE);
                mImageViewWechatNormal.setVisibility(View.GONE);
                mImageViewWechatSelect.setVisibility(View.VISIBLE);
                AliPayOrWechat = 2;
                break;*/
            case R.id.iv_buchajia_back:
                finish();
                break;
        }
    }
}
