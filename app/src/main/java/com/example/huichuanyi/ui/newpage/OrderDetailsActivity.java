package com.example.huichuanyi.ui.newpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.City;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.GlideCircleTransform;
import com.example.huichuanyi.custom.MySelfPayDialog;
import com.example.huichuanyi.emum.ServiceType;
import com.example.huichuanyi.ui.activity.AddressListActivity;
import com.example.huichuanyi.ui.activity.LiJiYuYueWhatActivity;
import com.example.huichuanyi.ui.activity.MyOrderActivity;
import com.example.huichuanyi.ui.activity.pay.YWTPayActivity;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.ActivityCacheUtils;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.AsyncHttpUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.HttpCallBack;
import com.example.huichuanyi.utils.IsSuccess;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.MUtilsInternet;
import com.example.huichuanyi.utils.ServiceSingleUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsPay;
import com.example.huichuanyi.wxapi.WXPayEntryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;


public class OrderDetailsActivity extends BaseActivity implements IsSuccess {

    public void back(View view) {
        finish();
    }

    @BindViews({R.id.tv_order_details_customer_name, R.id.tv_order_details_customer_phone, R.id.tv_order_details_customer_address})
    TextView[] customerInfo;

    private String address_id;

    private MUtilsInternet net = MUtilsInternet.getInstance();

    private Map<String, String> map = new HashMap<>();

    @BindView(R.id.round_item_order_studio_logo)
    ImageView studioLogo;

    @BindViews({R.id.tv_item_order_studio_name, R.id.tv_item_order_studio_produce})
    TextView[] studioInfo;

    @BindView(R.id.iv_item_order_studio_level_small)
    ImageView studioSmallLevel;

    @BindView(R.id.iv_item_order_studio_level_big)
    ImageView studioBigLevel;

    @BindView(R.id.tv_order_details_count)
    TextView tvCount;

    @BindView(R.id.tv_qu)
    TextView tvQu;

    @BindView(R.id.tv_order_details_time)
    TextView tvTime;

    @BindView(R.id.tv_order_details_what)
    TextView what;

    @BindView(R.id.iv_order_details_what)
    ImageView ivWhat;

    @BindViews({R.id.et_order_details_youhuiquan, R.id.et_order_details_request})
    EditText[] ets;

    @BindView(R.id.tv_order_studio_introduce_money)
    TextView tvMoney;

    @BindView(R.id.tv_moneys)
    TextView tvMoneys;

    @BindView(R.id.ll_is_have_coupon)
    LinearLayout isHaveCoupon;

    private String addGrade = "0";

    private String order_id;

    private UtilsPay mPay;

    private String invitation_code;

    private Handler handler = new Handler();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 1001) {
            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");
            String city = data.getStringExtra("city");
            String address = data.getStringExtra("selector_address");
            address_id = data.getStringExtra("address_id");
            customerInfo[0].setText(name);
            customerInfo[1].setText(phone);
            customerInfo[2].setText(city + address);
        }
    }

    private String payTag;


    @OnClick({R.id.rl_order_details_address, R.id.rl_order_details_pay_what, R.id.tv_order_studio_introduce_go_order, R.id.ll_what})
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
                    if (ServiceSingleUtils.getInstance().getServiceType() == ServiceType.SERVICE_ACARUS_KILLING) {
                        //除螨提交订单和获取签名支付
                        if (CommonUtils.isEmpty(order_id)) {
                            goAcarusKilling();
                        } else {
                            payGoAcaruskilling();
                        }
                    } else {
                        if (CommonUtils.isEmpty(order_id)) {
                            goPay();
                        } else {
                            payGo();
                        }
                    }
                } else {
                    Toast.makeText(this, "请选择支付方式", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_what:
                Intent in = new Intent(this, LiJiYuYueWhatActivity.class);
                in.putExtra("gra", addGrade);
                startActivity(in);
                break;
            default:
                break;
        }
    }

    /*
    * 提交除螨订单，获取订单号
    * */
    private void goAcarusKilling() {
        String user_name = customerInfo[0].getText().toString().trim();
        String userCity = customerInfo[2].getText().toString().trim();
        String userPhone = customerInfo[1].getText().toString().trim();
        final String remarks = ets[1].getText().toString();
        String user_id = SharedPreferenceUtils.getUserData(this, 1);
        String sellerUserId = model.getId();
        String sellerUserName = model.getName();
        String sellerPhone = model.getPhone();
        String sellerCityName = model.getCity();
        String consigneeTime = tvTime.getText().toString().trim();
        if (CommonUtils.isEmpty(user_name) || CommonUtils.isEmpty(userCity) || CommonUtils.isEmpty(userPhone)) {
            Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
            return;
        }
        map.put("token", "82D5FBD40259C743ADDEF14D0E22F347");
        map.put("orderRemarkBuyer", remarks);
        map.put("buyUserId", user_id);
        map.put("buyUserName", user_name);
        map.put("sellerUserId", sellerUserId);
        map.put("sellerUserName", sellerUserName);
        map.put("sellerCityName", sellerCityName);
        map.put("sellerPhone", sellerPhone);
        map.put("consigneeName", user_name);
        map.put("consigneePhone", userPhone);
        map.put("consigneeAddress", userCity);
        map.put("concessionCode", ets[0].getText().toString());
        map.put("orderNumber", orderNumber);
        map.put("consigneeTime", consigneeTime);
        net.post(NetConfig.ORDER_ACARUS_KILLING, this, map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    String ret = obj.getString("ret");
                    if ("0".equals(ret)) {
                        JSONObject body = obj.getJSONObject("body");
                        order_id = body.getString("orderId");
                        payGoAcaruskilling();
                    } else {
                        String msg = obj.getString("msg");
                        Toast.makeText(OrderDetailsActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String orderNumber;

    /*
    * 获取签名进行支付
    * */
    private void payGoAcaruskilling() {
        if (TextUtils.equals("3", payTag)) {
            Toast.makeText(this, "一网通支付暂未开通，敬请期待", Toast.LENGTH_SHORT).show();
        } else {
            switch (payTag) {
                case "1":
                    map.put("token", "82D5FBD40259C743ADDEF14D0E22F347");
                    map.put("orderId", order_id);
                    map.put("payType", "1");
                    net.post(NetConfig.PAY_SIGN_ACARUS_KILLING, this, map, new MUtilsInternet.XCallBack() {
                        @Override
                        public void onResponse(String result) {
                            try {
                                JSONObject object = new JSONObject(result);
                                JSONObject body = object.getJSONObject("body");
                                String sign = body.getString("sign");
                                mPay.aliPay(sign);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
                case "2":
                    map.put("token", "82D5FBD40259C743ADDEF14D0E22F347");
                    map.put("orderId", order_id);
                    map.put("payType", "2");
                    net.post(NetConfig.PAY_SIGN_ACARUS_KILLING, this, map, new MUtilsInternet.XCallBack() {
                        @Override
                        public void onResponse(String result) {
                            mPay.weChatPay(result);
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }

    @BindView(R.id.tv_delete_money)
    TextView deleteMoney;

    private void goPay() {
        if (!CommonUtils.isEmpty(payTag) || "3".equals(payTag)) {
            String invitation_code = ets[0].getText().toString();
            final String remarks = ets[1].getText().toString();
            String name = customerInfo[0].getText().toString();
            String phone = customerInfo[1].getText().toString();
            String address = customerInfo[2].getText().toString();
            if (CommonUtils.isEmpty(name) || CommonUtils.isEmpty(phone) || CommonUtils.isEmpty(address)) {
                Toast.makeText(this, "请填写完整个人信息", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject o1 = new JSONObject();
                o1.put("token", NetConfig.TOKEN);
                o1.put("payType", payTag);
                o1.put("concessionCode", invitation_code);
                o1.put("orderRemarkBuyer", remarks);
                o1.put("consigneeName", name);
                o1.put("consigneePhone", phone);
                o1.put("consigneeAddress", address);
                JSONArray a = new JSONArray();
                JSONObject O2 = new JSONObject();
                O2.put("id", id);
                a.put(O2);
                o1.put("ids", a);
                new AsyncHttpUtils(new HttpCallBack() {
                    @Override
                    public void onResponse(String result) {
                        String ret = JsonUtils.getRet(result);
                        if ("0".equals(ret)) {
                            switch (payTag) {
                                case "3":
                                    Toast.makeText(OrderDetailsActivity.this, "亲，此次消费还没有开通一网通支付哦", Toast.LENGTH_SHORT).show();
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
                        } else if ("1".equals(ret)) {
                            Toast.makeText(OrderDetailsActivity.this, "下单成功", Toast.LENGTH_SHORT).show();
                        } else if ("3011".equals(ret)) {
                            Toast.makeText(OrderDetailsActivity.this, "优惠码不能多订单合并支付", Toast.LENGTH_SHORT).show();
                        } else if ("3008".equals(ret)) {
                            Toast.makeText(OrderDetailsActivity.this, "请检查优惠码是否正确", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, this).execute(NetConfig.SHOPCAR_SING_SHOP, o1.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            if ("3".equals(payTag)) {
                Toast.makeText(this, "亲，此次消费还没有开通一网通支付哦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "请选择支付方式", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String money;
    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            if (ServiceSingleUtils.getInstance().getServiceType() == ServiceType.SERVICE_ACARUS_KILLING) {
                RequestParams pa = new RequestParams("http://hmyc365.net/admiral/common/concession/code/getPrice.htm");
                pa.addBodyParameter("token", "82D5FBD40259C743ADDEF14D0E22F347");
                pa.addBodyParameter("concessionCode", invitation_code);
                pa.addBodyParameter("orderType", "1");
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
                                double v1 = Double.parseDouble(money);
                                final double v2 = v1 - v;
                                if (v2 < 0 || !"0".equals(deleteStatus)) {
                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            deleteMoney.setText("不可用");
                                            tvMoney.setText("¥" + money);
                                            tvMoneys.setText("¥" + money);
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
                                tvMoney.setText("¥" + money);
                                tvMoneys.setText("¥" + money);
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
            } else {
                String s = tvCount.getText().toString().split("-")[1];
                String substring = s.substring(0, s.length() - 3);
                RequestParams pa = new RequestParams(NetConfig.GO_DOOR_MONEY);
                pa.addBodyParameter("studio_id", model.getId());
                pa.addBodyParameter("studio_city", model.getCity());
                pa.addBodyParameter("clothes_num", substring);
                pa.addBodyParameter("invitation_code", invitation_code);
                x.http().post(pa, new Callback.CacheCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject object = new JSONObject(result);
                            JSONObject body = object.getJSONObject("body");
                            final String price = body.getString("price");
                            final double v = Double.parseDouble(price);
                            final double v1 = Double.parseDouble(money);
                            final double v2 = v1 - v;
                            if (v2 > 0) {
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        tvMoney.setText("¥" + CommonUtils.strDoubleTwo(v));
                                        tvMoneys.setText("¥" + CommonUtils.strDoubleTwo(v));
                                        deleteMoney.setText("- ¥" + CommonUtils.strDoubleTwo(v2));
                                    }
                                });
                            } else {
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        deleteMoney.setText("不可用");
                                        tvMoney.setText("¥" + CommonUtils.strDoubleTwo(v1));
                                        tvMoneys.setText("¥" + CommonUtils.strDoubleTwo(v1));
                                    }
                                });
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

    private void payGo() {
        if (TextUtils.equals("3", payTag)) {
            Intent it = new Intent(this, YWTPayActivity.class);
            it.putExtra("type", "1");
            it.putExtra("order_id", order_id);
            startActivity(it);
        } else {
            switch (payTag) {
                case "1":
                    map.put("service_order_id", order_id);
                    map.put("pay_type", "1");
                    net.post("http://hmyc365.net:8081/HM/app/h/service/pay/getServicePayInfo.do", this, map, new MUtilsInternet.XCallBack() {
                        @Override
                        public void onResponse(String result) {
                            try {
                                JSONObject object = new JSONObject(result);
                                JSONObject body = object.getJSONObject("body");
                                String sign = body.getString("sign");
                                mPay.aliPay(sign);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
                case "2":
                    map.put("service_order_id", order_id);
                    map.put("pay_type", "2");
                    net.post("http://hmyc365.net:8081/HM/app/h/service/pay/getServicePayInfo.do", this, map, new MUtilsInternet.XCallBack() {
                        @Override
                        public void onResponse(String result) {
                            mPay.weChatPay(result);
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ActivityCacheUtils.addActivity(this);
        //AndroidBug5497Workaround.assistActivity(this);
    }

    @Override
    protected void setListener() {
        //if (ServiceSingleUtils.getInstance().getServiceType() != ServiceType.SERVICE_ACARUS_KILLING)
        isHaveActive();

        ets[0].addTextChangedListener(watcher);
        wxPay = new WXPayEntryActivity();
        wxPay.wxPayIsSuccess(new WXPayEntryActivity.CallBackCode() {
            @Override
            public void onCallBack(int errCode) {
                if (0 == errCode) {
                    CommonUtils.Toast(OrderDetailsActivity.this, "支付成功");
                    mPay.showNotation();
                    ActivityUtils.switchTo(OrderDetailsActivity.this, MyOrderActivity.class);
                } else {
                    CommonUtils.Toast(OrderDetailsActivity.this, "支付失败");
                }
            }
        });
    }

    WXPayEntryActivity wxPay;
    ;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wxPay.wxPayIsSuccess(null);
    }

    /*
     * 是否有团购活动
     * */
    private void isHaveActive() {
        RequestParams pa = new RequestParams("http://hmyc365.net:8081/HM/app/doorToDoorService/order/code/isGroupActivity.do");
        pa.addBodyParameter("user_id", SharedPreferenceUtils.getUserData(this, 1));
        pa.addBodyParameter("studio_id", model.getId());
        x.http().post(pa, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject ob = new JSONObject(result);
                    JSONObject body = ob.getJSONObject("body");
                    String isGroup = body.getString("isGroup");
                    if (TextUtils.equals("Y", isGroup)) {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                isHaveCoupon.setVisibility(View.VISIBLE);
                            }
                        });
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
                    address_id = jsonObject.getString("id");
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

    @Override
    protected void setData() {
        Intent intent = getIntent();
        model = (City.BodyBean) intent.getSerializableExtra("model");
        orderNumber = intent.getStringExtra("defaultNum");
        String count = intent.getStringExtra("count");
        String time = intent.getStringExtra("time");
        money = intent.getStringExtra("money");
        addGrade = intent.getStringExtra("addGrade");
        id = intent.getStringExtra("id");
        tvMoney.setText("¥" + money);
        tvCount.setText(count);
        tvTime.setText(time);
        tvMoneys.setText("¥" + money);
        if (ServiceSingleUtils.getInstance().getServiceType() == ServiceType.SERVICE_ACARUS_KILLING) {
            tvQu.setText("标准");
        }
        String imgPath = model.getImgPath();
        String grade = model.getGrade();
        studioInfo[0].setText(model.getName());
        studioInfo[1].setText(model.getIntroduction());
        Glide.with(this).load(model.getPhoto_get()).transform(new GlideCircleTransform(this)).into(studioLogo);
        Glide.with(this).load(imgPath + grade + "_icon.png").into(studioSmallLevel);
        Glide.with(this).load(imgPath + grade + "_grade.png").into(studioBigLevel);

    }

    private String id;
    private City.BodyBean model;

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
