package com.example.huichuanyi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.bean.Progress;
import com.example.huichuanyi.ui.activity.pay.YWTPayActivity;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.IsSuccess;
import com.example.huichuanyi.utils.PayUtils;
import com.example.huichuanyi.utils.UtilsPay;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

public class ClosingPriceActivity extends BaseActivity implements PayUtils.Sign, IsSuccess {

    @ViewInject(R.id.et_cp_count)
    private EditText cpCount;

    @ViewInject(R.id.tv_cp_kind)
    private TextView cpKind;

    @ViewInject(R.id.tv_cp_money)
    private TextView cpMoney;


    @ViewInject(R.id.rg_pay)
    private RadioGroup cpRg;

    private UtilsPay mPay;
    private double box_price, intPrice_baseNum1, intPrice_baseNum2, intPrice2, intPrice11, intPrice_raiseNum, intPrice_raisePrice;
    /*
    * 0 代表使用支付宝，1 代表使用微信，2 代表使用一网通
    * */
    private int a_w_c = 2;

    private PayUtils pay = PayUtils.getInstance();

    private String kind = null;
    private String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_closing);
    }

    @Override
    public void initView() {
        Intent in = getIntent();
        kind = in.getStringExtra("kind");
        Progress.ListBean bean = (Progress.ListBean) in.getSerializableExtra("bean");
        box_price = getDouble(bean.getBox_price());
        intPrice_baseNum1 = getDouble(bean.getPrice_baseNum1());
        intPrice_baseNum2 = getDouble(bean.getPrice_baseNum2());
        intPrice11 = getDouble(bean.getPrice_basePrice1());
        intPrice2 = getDouble(bean.getPrice_basePrice2());
        intPrice_raiseNum = getDouble(bean.getPrice_raiseNum());
        intPrice_raisePrice = getDouble(bean.getPrice_raisePrice());
        order_id = bean.getId();
        if (TextUtils.equals("5", kind)) {
            cpKind.setText("上门服务补差价");
        } else if (TextUtils.equals("6", kind)) {
            cpKind.setText("小蓝盒补差价");
            cpCount.setHint("请输入小蓝盒数量");
        }
    }

    @Override
    public void initData() {
        cpCount.addTextChangedListener(watcher);
        mPay = new UtilsPay(this);
    }

    @Override
    public void setData() {
        mPay.isSuccess(this);
    }

    @Override
    public void setListener() {
        cpRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_ali_pay:
                        a_w_c = 0;
                        break;
                    case R.id.rb_wechat_pay:
                        a_w_c = 1;
                        break;
                    case R.id.rb_cmb_pay:
                        a_w_c = 2;
                        break;
                    default:
                        break;
                }
            }
        });
    }


    public double getDouble(String str) {
        if (!CommonUtils.isEmpty(str))
            return Double.parseDouble(str);
        else
            return 0;
    }

    @Event(R.id.btn_cp_pay)
    private void onEvent(View v) {
        if (getDouble(cpMoney.getText().toString().trim()) > 1) {
            switch (v.getId()) {
                case R.id.btn_cp_pay:
                    switch (a_w_c) {
                        case 0:
                            String counts = cpCount.getText().toString().trim();
                            pay.aLiSign(kind, order_id, counts, counts, this);
                            break;
                        case 1:
                            String countss = cpCount.getText().toString().trim();
                            pay.weChatSign(kind, order_id, countss, countss, this);
                            break;
                        case 2:
                            Intent it = new Intent(this, YWTPayActivity.class);
                            it.putExtra("type", kind);
                            it.putExtra("order_id", order_id);
                            startActivity(it);
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        } else {
            Toast.makeText(this, "差价必须大于1", Toast.LENGTH_SHORT).show();
        }
    }

    public void back(View view) {
        if (view != null) {
            finish();
        }
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String co = cpCount.getText().toString().trim();
            double x = getDouble(co);
            if (TextUtils.equals("5", kind)) {
                if (!TextUtils.isEmpty(s)) {
                    if (x <= intPrice_baseNum1) {
                        cpMoney.setText("0");
                        return;
                    } else if (intPrice_baseNum1 < x && x <= intPrice_baseNum2) {
                        cpMoney.setText((intPrice2 - intPrice11) + "");
                        return;
                    } else {
                        int a = (int) ((x - intPrice_baseNum2) / intPrice_raiseNum);
                        if ((x - intPrice_baseNum2) % intPrice_raiseNum != 0) {
                            a = a + 1;
                        }
                        cpMoney.setText(((a * intPrice_raisePrice + intPrice2) - intPrice11) + "");
                        return;
                    }
                }
            } else if (TextUtils.equals("6", kind)) {
                cpMoney.setText(box_price * x + "");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void getSign(String sign) {
        switch (a_w_c) {
            case 0:
                try {
                    JSONObject object = new JSONObject(sign);
                    JSONObject body = object.getJSONObject("body");
                    String signs = body.getString("sign");
                    mPay.aliPay(signs);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                mPay.weChatPay(sign);
                break;
            default:
                break;
        }
    }

    @Override
    public void isSuccess(int success) {
        switch (success) {
            case 9000:
                CommonUtils.Toast(this, "支付成功");
            case 9001:
                CommonUtils.Toast(this, "支付失败");
                break;
            default:
                break;
        }
    }
}
