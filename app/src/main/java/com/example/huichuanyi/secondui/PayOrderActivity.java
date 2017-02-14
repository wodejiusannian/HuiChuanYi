package com.example.huichuanyi.secondui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui_second.MyOrderActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.IsSuccess;
import com.example.huichuanyi.utils.User;
import com.example.huichuanyi.utils.UtilsInternet;
import com.example.huichuanyi.utils.UtilsPay;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class PayOrderActivity extends BaseActivity implements View.OnClickListener, UtilsInternet.XCallBack, IsSuccess {

    private ImageView mImageViewPhoto, mImageViewAliPayNormal, mImageViewAliPaySelect,
            mImageViewWeChatNormal, mImageViewWeChatSelect;
    private String managerPhoto, managerName, nowMoney, orderid;
    private TextView mTextViewName, mTextViewMoney, mTextViewNowMoney;
    private int AliPayOrWeChat = 1;
    private RelativeLayout mRelativeLayoutAliPay, mRelativeLayoutWeChat;
    private Button mButtonPay;
    private UtilsInternet instance;
    private UtilsPay mPay;

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
        mRelativeLayoutWeChat = (RelativeLayout) findViewById(R.id.rl_payorder_wechat);
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
        mPay = new UtilsPay(this);
    }

    @Override
    public void setData() {
        mPay.isSuccess(this);
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
        mRelativeLayoutWeChat.setOnClickListener(this);
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
                AliPayOrWeChat = 1;
                break;
            case R.id.rl_payorder_wechat:
                mImageViewAliPaySelect.setVisibility(View.GONE);
                mImageViewAliPayNormal.setVisibility(View.VISIBLE);
                mImageViewWeChatNormal.setVisibility(View.GONE);
                mImageViewWeChatSelect.setVisibility(View.VISIBLE);
                AliPayOrWeChat = 2;
                break;
            case R.id.bt_payorder_pay:
                postData();
                break;
        }
    }

    private void postData() {
        Map<String, String> map = new HashMap<>();
        switch (AliPayOrWeChat) {
            case 1:
                map.put("orderid", orderid);
                map.put("type", AliPayOrWeChat + "");
                instance.post(NetConfig.ALI_PAY, map, this);
                break;
            case 2:
                map.put("order_id", orderid);
                map.put("user_id", new User(this).getUseId() + "");
                instance.post(NetConfig.WE_CHAT_PAY, map, this);
                break;
            default:
                break;
        }
    }


    @Override
    public void onResponse(String result) {
        switch (AliPayOrWeChat) {
            case 1:
                mPay.aliPay(result);
                break;
            case 2:
                mPay.weChatPay(result);
                break;
            default:
                break;
        }
    }

    @Override
    public void isSuccess(int success) {
        switch (success) {
            case 9000:
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                mPay.showNotation();
                ActivityUtils.switchTo(this, MyOrderActivity.class);
                break;
            case 9001:
                Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public void back(View view) {
        finish();
    }
}
