package com.example.huichuanyi.ui.newpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.ShopcarShowAdapter;
import com.example.huichuanyi.common_view.model.ShopCarType4Model;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MyListView;
import com.example.huichuanyi.custom.MySelfPayDialog;
import com.example.huichuanyi.ui.activity.AddressListActivity;
import com.example.huichuanyi.ui.activity.MyOrderActivity;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.ActivityCacheUtils;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.AsyncHttpUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.HttpCallBack;
import com.example.huichuanyi.utils.IsSuccess;
import com.example.huichuanyi.utils.MUtilsInternet;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsPay;
import com.example.huichuanyi.wxapi.WXPayEntryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;


public class ShopCarOrderDetailsActivity extends BaseActivity implements IsSuccess {

    public void back(View view) {
        finish();
    }

    @BindViews({R.id.tv_order_details_customer_name, R.id.tv_order_details_customer_phone, R.id.tv_order_details_customer_address})
    TextView[] customerInfo;


    private MUtilsInternet net = MUtilsInternet.getInstance();

    private Map<String, String> map = new HashMap<>();


    @BindView(R.id.tv_order_details_what)
    TextView what;


    @BindViews({R.id.et_order_details_youhuiquan, R.id.et_order_details_request})
    EditText[] ets;

    @BindView(R.id.tv_order_studio_introduce_money)
    TextView tvMoney;

    @BindView(R.id.tv_moneys)
    TextView tvMoneys;

    @BindView(R.id.ll_is_have_coupon)
    LinearLayout isHaveCoupon;


    private UtilsPay mPay;


    private Handler handler = new Handler();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 1001) {
            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");
            String city = data.getStringExtra("city");
            String address = data.getStringExtra("selector_address");
            customerInfo[0].setText(name);
            customerInfo[1].setText(phone);
            customerInfo[2].setText(city + address);
        }
    }

    private String payTag;

    @BindView(R.id.iv_order_details_what)
    ImageView ivWhat;

    @OnClick({R.id.rl_order_details_address, R.id.rl_order_details_pay_what, R.id.tv_order_studio_introduce_go_order})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.rl_order_details_address:
                Intent intent = new Intent(this, AddressListActivity.class);
                startActivityForResult(intent, 1000);
                break;
            case R.id.rl_order_details_pay_what:
                MySelfPayDialog dialog = new MySelfPayDialog(this);
                dialog.setOnNoListener("取消", null);
                dialog.setOnYesListener("确定", new MySelfPayDialog.OnYesClickListener() {
                    @Override
                    public void onClick(String tag) {
                        payTag = tag;
                        ivWhat.setVisibility(View.GONE);
                        switch (tag) {
                            case "1":
                                what.setText("支付宝支付");
                                break;
                            case "2":
                                what.setText("微信支付");
                                break;
                            case "3":
                                what.setText("一网通支付");
                                break;
                            default:
                                break;
                        }
                    }
                });
                dialog.show();
                break;
            case R.id.tv_order_studio_introduce_go_order:
                if (!CommonUtils.isEmpty(payTag)) {
                    try {
                        JSONObject o1 = new JSONObject();
                        o1.put("token", NetConfig.TOKEN);
                        o1.put("payType", payTag);
                        o1.put("concessionCode", ets[0].getText().toString());
                        o1.put("orderRemarkBuyer", ets[1].getText().toString());
                        o1.put("consigneeName", customerInfo[0].getText().toString());
                        o1.put("consigneePhone", customerInfo[1].getText().toString());
                        o1.put("consigneeAddress", customerInfo[2].getText().toString());
                        JSONArray a = new JSONArray();
                        for (ShopCarType4Model shopCarType4Model : arrays) {
                            JSONObject O2 = new JSONObject();
                            O2.put("id", shopCarType4Model.id);
                            a.put(O2);
                        }
                        o1.put("ids", a);
                        new AsyncHttpUtils(new HttpCallBack() {
                            @Override
                            public void onResponse(String result) {
                                switch (payTag) {
                                    case "0":
                                        Toast.makeText(ShopCarOrderDetailsActivity.this, "亲，此次消费还没有开通一网通支付哦", Toast.LENGTH_SHORT).show();
                                        break;
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
                                    case "2":
                                        mPay.weChatPay(result);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }, this).execute(NetConfig.SHOPCAR_SING_SHOP, o1.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "请选择支付方式", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    @BindView(R.id.tv_delete_money)
    TextView deleteMoney;


    private String mmoney;


    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            RequestParams pa = new RequestParams("http://hmyc365.net/admiral/common/concession/code/getPrice.htm");
            pa.addBodyParameter("token", "82D5FBD40259C743ADDEF14D0E22F347");
            pa.addBodyParameter("concessionCode", invitation_code);
            x.http().post(pa, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject object = new JSONObject(result);
                        String ret = object.getString("ret");
                        if ("0".equals(ret)) {
                            JSONObject body = object.getJSONObject("body");
                            final String price = body.getString("concessionMoney");
                            final String deleteStatus = body.getString("deleteStatus");
                            double v = Double.parseDouble(price);
                            double v1 = Double.parseDouble(mmoney);
                            final double v2 = v1 - v;
                            if (v2 < 0 || !"0".equals(deleteStatus)) {
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        deleteMoney.setText("不可用");
                                        tvMoney.setText("¥" + mmoney);
                                        tvMoneys.setText("¥" + mmoney);
                                    }
                                });
                            } else {
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        tvMoney.setText("¥" + v2);
                                        tvMoneys.setText("¥" + v2);
                                        deleteMoney.setText("- ¥" + price);
                                    }
                                });
                            }
                        } else {
                            tvMoney.setText("¥" + mmoney);
                            tvMoneys.setText("¥" + mmoney);
                            deleteMoney.setText("不可用");
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
            });
        }
    };

    TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (delayRun != null) {
                //每次editText有变化的时候，则移除上次发出的延迟线程
                handler.removeCallbacks(delayRun);
            }
            invitation_code = s.toString();
            //延迟800ms，如果不再输入字符，则执行该线程的run方法
            handler.postDelayed(delayRun, 800);
        }
    };

    private String invitation_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordershopcar_details);
        ActivityCacheUtils.addActivity(this);
    }

    @Override
    protected void setListener() {

        ets[0].addTextChangedListener(watcher);
        wxPay = new WXPayEntryActivity();
        wxPay.wxPayIsSuccess(new WXPayEntryActivity.CallBackCode() {
            @Override
            public void onCallBack(int errCode) {
                if (0 == errCode) {
                    CommonUtils.Toast(ShopCarOrderDetailsActivity.this, "支付成功");
                    mPay.showNotation();
                    ActivityUtils.switchTo(ShopCarOrderDetailsActivity.this, MyOrderActivity.class);
                } else {
                    CommonUtils.Toast(ShopCarOrderDetailsActivity.this, "支付失败");
                }
            }
        });
    }

    @BindView(R.id.mlw_show)
    MyListView show;

    WXPayEntryActivity wxPay;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wxPay.wxPayIsSuccess(null);
    }


    @Override
    protected void initData() {
        map.put("user_id", SharedPreferenceUtils.getUserData(this, 1));
        net.post(NetConfig.GET_PERSON_ADDRESS, this, map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray body = object.getJSONArray("body");
                    JSONObject jsonObject = body.getJSONObject(0);
                    String city = jsonObject.getString("receive_city");
                    String name = jsonObject.getString("receive_name");
                    String phone = jsonObject.getString("receive_phone");
                    String address = jsonObject.getString("receive_address");
                    customerInfo[0].setText(name);
                    customerInfo[1].setText(phone);
                    customerInfo[2].setText(city + address);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        mPay = new UtilsPay(this);
        mPay.isSuccess(this);
    }

    private List<ShopCarType4Model> arrays = new ArrayList<>();

    @Override
    protected void setData() {
        List<ShopCarType4Model> array = getIntent().getParcelableArrayListExtra("shoplist");
        arrays.addAll(array);
        double money = 0;
        for (ShopCarType4Model visitable : array) {
            String strGoodsPrice3 = visitable.goodsPrice;
            int intOrderNumber3 = visitable.orderNumber;
            double douGoodsPrice3 = Double.parseDouble(strGoodsPrice3);
            money += douGoodsPrice3 * intOrderNumber3;
        }
        mmoney = money + "";
        DecimalFormat df = new DecimalFormat("0.##");
        tvMoney.setText("¥" + df.format(money));
        tvMoneys.setText("¥" + df.format(money));
        if (array.size() > 3) {
            array = array.subList(0, 2);
        }
        ShopcarShowAdapter adapter = new ShopcarShowAdapter(array, this);
        show.setAdapter(adapter);
    }


    @Override
    public void isSuccess(int success) {
        switch (success) {
            case 9000:
                CommonUtils.Toast(this, "支付成功");
                mPay.showNotation();
                ActivityUtils.switchTo(this, MyOrderActivity.class);
                break;
            case 9001:
                CommonUtils.Toast(this, "支付失败");
                break;
            default:
                break;
        }
    }
}
