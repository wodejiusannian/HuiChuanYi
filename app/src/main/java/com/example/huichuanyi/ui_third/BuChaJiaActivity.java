package com.example.huichuanyi.ui_third;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.IsSuccess;
import com.example.huichuanyi.utils.UtilsInternet;
import com.example.huichuanyi.utils.UtilsPay;

import java.util.HashMap;
import java.util.Map;

public class BuChaJiaActivity extends BaseActivity implements TextWatcher, View.OnClickListener, UtilsInternet.XCallBack, IsSuccess {
    private EditText mEditTextYiFuLiang;
    private TextView mTextViewYingBuChaJia;
    private String YetPay, orderid, manager_name;
    private int nowMoney;
    private Button mButton;
    private int AliPayOrWechat = 1;
    private ImageView
            mImageViewAlipayNormal, mImageViewAlipaySelect,
            mImageViewWechatNormal, mImageViewWechatSelect;
    private RelativeLayout mRelativeLayoutAliPay, mRelativeLayoutWechat;
    private UtilsInternet instance;
    private UtilsPay mPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bu_cha_jia);
    }

    @Override
    public void initView() {
        mButton = (Button) findViewById(R.id.btn_pay_sure);
        mEditTextYiFuLiang = (EditText) findViewById(R.id.shi_ji_yifu);
        mTextViewYingBuChaJia = (TextView) findViewById(R.id.ying_bu_chajia);
        mImageViewAlipayNormal = (ImageView) findViewById(R.id.iv_buchajia_alipayNomal);
        mImageViewAlipaySelect = (ImageView) findViewById(R.id.iv_buchajia_alipaySelect);
        mImageViewWechatNormal = (ImageView) findViewById(R.id.iv_buchajia_wechatNomal);
        mImageViewWechatSelect = (ImageView) findViewById(R.id.iv_buchajia_wechatSelect);
        mRelativeLayoutAliPay = (RelativeLayout) findViewById(R.id.rl_buchajia_alipay);
        mRelativeLayoutWechat = (RelativeLayout) findViewById(R.id.rl_buchajia_wechat);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        YetPay = intent.getStringExtra("YetPay");
        orderid = intent.getStringExtra("orderid");
        manager_name = intent.getStringExtra("manager_name");
        instance = UtilsInternet.getInstance();
        mPay = new UtilsPay(this);
    }

    @Override
    public void setData() {
        mPay.isSuccess(this);
    }

    @Override
    public void setListener() {
        mEditTextYiFuLiang.addTextChangedListener(this);
        mRelativeLayoutAliPay.setOnClickListener(this);
        mRelativeLayoutWechat.setOnClickListener(this);
        mButton.setOnClickListener(this);
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
        Map<String, String> map = new HashMap<>();

        switch (v.getId()) {
            case R.id.btn_pay_sure:
                switch (AliPayOrWechat) {
                    case 1:
                        map.put("orderid", orderid);
                        map.put("type", "1");
                        map.put("money", nowMoney + "");
                        map.put("manager_name", manager_name);
                        map.put("remarks", "");
                        instance.post(NetConfig.BU_CHA_JIA, map, this);
                        break;
                    case 2:
                        Toast.makeText(this, "传输入到我们服务器，回去签名信息", Toast.LENGTH_SHORT).show();
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
            case R.id.rl_buchajia_wechat:
                mImageViewAlipaySelect.setVisibility(View.GONE);
                mImageViewAlipayNormal.setVisibility(View.VISIBLE);
                mImageViewWechatNormal.setVisibility(View.GONE);
                mImageViewWechatSelect.setVisibility(View.VISIBLE);
                AliPayOrWechat = 2;
                break;
        }
    }


    @Override
    public void onResponse(String result) {
        switch (AliPayOrWechat) {
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
