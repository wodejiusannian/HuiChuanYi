package com.example.huichuanyi.ui.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.PayUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

public class BuyPayActivity extends BaseActivity {
    @ViewInject(R.id.rg_pay)
    private RadioGroup rgPay;

    private int pay_type = 3;

    private PayUtils payUtils = PayUtils.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_pay);

    }

    @Override
    public void setListener() {
        rgPay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_ali_pay:
                        pay_type = 1;
                        break;
                    case R.id.rb_wechat_pay:
                        pay_type = 2;
                        break;
                    case R.id.rb_cmb_pay:
                        pay_type = 3;
                        break;

                    default:
                        break;
                }
            }
        });
    }

    @Event({R.id.btn_buy_pay})
    private void onEvent(View v) {
        switch (v.getId()) {
            case R.id.btn_buy_pay:
                switch (pay_type) {
                    case 1:
                        Toast.makeText(this, "支付宝支付", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(this, "微信支付", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        ActivityUtils.switchTo(this, YWTPayActivity.class);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        String money = intent.getStringExtra("money");
    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {

    }

    public void back(View view) {
        finish();
    }
}
