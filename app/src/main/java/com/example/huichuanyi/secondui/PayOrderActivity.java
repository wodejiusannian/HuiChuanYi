package com.example.huichuanyi.secondui;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.example.huichuanyi.utils.UtilsInternet;
import com.squareup.picasso.Picasso;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PayOrderActivity extends BaseActivity implements View.OnClickListener, UtilsInternet.XCallBack {

    private ImageView  mImageViewPhoto,mImageViewAliPayNormal, mImageViewAliPaySelect,
            mImageViewWeChatNormal, mImageViewWeChatSelect;
    private String managerPhoto, managerName, nowMoney, orderid;
    private TextView mTextViewName, mTextViewMoney, mTextViewNowMoney;
    private int AliPayOrWechat = 1;
    private RelativeLayout mRelativeLayoutAliPay, mRelativeLayoutWechat;
    private Button mButtonPay;
    private static final int SDK_PAY_FLAG = 1;
    private String WX_APPID = "wxee286f04e48c82a3";
    private UtilsInternet instance;
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
                        Toast.makeText(PayOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        showNotation();
                    } else {
                        Toast.makeText(PayOrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    ActivityUtils.switchTo(PayOrderActivity.this, MyOrderActivity.class);
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
        setContentView(R.layout.activity_pay_order);

    }

    @Override
    public void initView() {
        mImageViewPhoto = (ImageView) findViewById(R.id.iv_payorder_photo);
        mTextViewName = (TextView) findViewById(R.id.tv_payorder_name);
        mTextViewMoney = (TextView) findViewById(R.id.tv_payorder_allmoney);
        mTextViewNowMoney = (TextView) findViewById(R.id.tv_payorder_nowMoney);
        mImageViewAliPayNormal = (ImageView) findViewById(R.id.iv_payorder_alipayNomal);
        mImageViewAliPaySelect = (ImageView) findViewById(R.id.iv_payorder_alipaySelect);
        mImageViewWeChatNormal = (ImageView) findViewById(R.id.iv_payorder_wechatNomal);
        mImageViewWeChatSelect = (ImageView) findViewById(R.id.iv_payorder_wechatSelect);
        mRelativeLayoutAliPay = (RelativeLayout) findViewById(R.id.rl_payorder_alipay);
        mRelativeLayoutWechat = (RelativeLayout) findViewById(R.id.rl_payorder_wechat);
        mButtonPay = (Button) findViewById(R.id.bt_payorder_pay);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        managerPhoto = intent.getStringExtra("managerPhoto");
        managerName = intent.getStringExtra("managerName");
        nowMoney = intent.getStringExtra("nowMoney");
        orderid = intent.getStringExtra("orderid");
        instance = UtilsInternet.getInstance();
    }

    @Override
    public void setData() {
        if (!TextUtils.isEmpty(managerPhoto) && managerPhoto.length() > 5) {
            Picasso.with(this).load(managerPhoto).into(mImageViewPhoto);
        }
        mTextViewName.setText(managerName);
        mTextViewMoney.setText(nowMoney);
        mTextViewNowMoney.setText(nowMoney);
    }

    @Override
    public void setListener() {
        mRelativeLayoutAliPay.setOnClickListener(this);
        mRelativeLayoutWechat.setOnClickListener(this);
        mButtonPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_payorder_alipay:
                mImageViewAliPaySelect.setVisibility(View.VISIBLE);
                mImageViewAliPayNormal.setVisibility(View.GONE);
                mImageViewWeChatNormal.setVisibility(View.VISIBLE);
                mImageViewWeChatSelect.setVisibility(View.GONE);
                AliPayOrWechat = 1;
                break;
            case R.id.rl_payorder_wechat:
                mImageViewAliPaySelect.setVisibility(View.GONE);
                mImageViewAliPayNormal.setVisibility(View.VISIBLE);
                mImageViewWeChatNormal.setVisibility(View.GONE);
                mImageViewWeChatSelect.setVisibility(View.VISIBLE);
                AliPayOrWechat = 2;
                break;
            case R.id.bt_payorder_pay:
                Map<String,String> map = new HashMap<>();
                map.put("orderid",orderid);
                map.put("type",AliPayOrWechat+"");
                instance.post(NetConfig.ALIPAY_OR_WECHAT,map,this);
        }
    }


    @Override
    public void onResponse(String result) {
        switch(AliPayOrWechat){
            case 1:
                aliPay(result);
                break;
            case 2:
                weChatPay(result);
                break;
            default:
                break;
        }
    }

    //微信支付
    private void weChatPay(String result) {
        final IWXAPI mWxApi = WXAPIFactory.createWXAPI(this, WX_APPID, true);
        mWxApi.registerApp(WX_APPID);
        PayReq req = new PayReq();
        try {
            JSONObject object = new JSONObject(result);
            //应用ID
            req.appId = WX_APPID;
            //商户号
            String partnerId = object.getString("partnerId");
            req.partnerId = partnerId;
            //预支付交易会话ID
            String prepayId = object.getString("prepayId");
            req.prepayId = prepayId;
            //随机字符串
            String nonceStr = object.getString("nonceStr");
            req.nonceStr = nonceStr;
            //时间戳*
            String timeStamp = object.getString("timeStamp");
            req.timeStamp = timeStamp;
            //扩展字段
            req.packageValue = "Sign=WXPay";
            //签名*
            String sign = object.getString("sign");
            req.sign = sign;
            mWxApi.sendReq(req);
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }

    }

    //支付宝支付
    private void aliPay(final String get) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(PayOrderActivity.this);
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

    //支付成功，通知
    private void showNotation() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent3 = PendingIntent.getActivity(this, 0,
                new Intent(this, MyOrderActivity.class), 0);
        Notification notify3 = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.logo)
                .setContentTitle("温馨提示")
                .setContentText("支付成功，衣橱管理师会尽快和您联系")
                .setContentIntent(pendingIntent3).build();
        notify3.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(1, notify3);
    }

    public void back(View view){
        finish();
    }
}
