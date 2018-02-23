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
import com.example.huichuanyi.ui.activity.AddressListActivity;
import com.example.huichuanyi.ui.activity.LiJiYuYueWhatActivity;
import com.example.huichuanyi.ui.activity.MyOrderActivity;
import com.example.huichuanyi.ui.activity.pay.YWTPayActivity;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.ActivityCacheUtils;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.IsSuccess;
import com.example.huichuanyi.utils.MUtilsInternet;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsPay;

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
                if (!CommonUtils.isEmpty(payTag))
                    if (CommonUtils.isEmpty(order_id))
                        goPay();
                    else
                        payGo();
                else {
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

    @BindView(R.id.tv_delete_money)
    TextView deleteMoney;

    private void goPay() {
        String invitation_code = ets[0].getText().toString();
        String time = tvTime.getText().toString().trim();
        String user_name = customerInfo[0].getText().toString().trim();
        String userCity = customerInfo[2].getText().toString().trim();
        String userPhone = customerInfo[1].getText().toString().trim();
        String clothes_num = tvCount.getText().toString();
        final String remarks = ets[1].getText().toString();
        String user_id = SharedPreferenceUtils.getUserData(this, 1);
        String studio_id = model.getId();
        //RequestParams pa = new RequestParams(NetConfig.UPLOADING_COM_DETAILS);
        if (CommonUtils.isEmpty(invitation_code)) {
            map.put("type", "0");
            //pa.addBodyParameter("type", "0");
        } else {
            map.put("type", "1");
            //pa.addBodyParameter("type", "1");
        }
        map.put("user_id", user_id);
        //pa.addBodyParameter("user_id", user_id);
        map.put("studio_id", studio_id);
        //pa.addBodyParameter("studio_id", studio_id);
        String s = clothes_num.split("-")[1];
        map.put("clothes_num", s.substring(0, s.length() - 3));
        //pa.addBodyParameter("clothes_num", clothes_num.split("-")[1]);
        map.put("address_id", address_id);
        //pa.addBodyParameter("address_id", address_id);
        map.put("shr_address", userCity);
        //pa.addBodyParameter("shr_address", userCity);
        map.put("shr_phone", userPhone.replace(" ", ""));
        //pa.addBodyParameter("shr_phone", userPhone.replace(" ", ""));
        map.put("shr_name", user_name);
        //pa.addBodyParameter("shr_name", user_name);
        map.put("invitation_code", invitation_code);
        //pa.addBodyParameter("invitation_code", invitation_code);
        map.put("order_date", time.substring(0, 10));
        //pa.addBodyParameter("order_date", time.substring(0, 10));
        map.put("order_time", time);
        //pa.addBodyParameter("order_time", time);
        map.put("remarks", remarks);
        //pa.addBodyParameter("remarks", remarks);
        String substring = time.substring(10, time.length());
        if (TextUtils.equals("上午", substring)) {
            map.put("order_date_tag", "AM_BUSY");
            //pa.addBodyParameter("order_date_tag", "AM_BUSY");
        } else {
            map.put("order_date_tag", "PM_BUSY");
            // pa.addBodyParameter("order_date_tag", "PM_BUSY");
        }
        //String url = "http://192.168.1.160:8081/HM/app/stu/order/service/order/addServiceOrder_new.do";
        net.post(NetConfig.UPLOADING_COM_DETAILS, this, map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    String ret = obj.getString("ret");
                    if ("0".equals(ret)) {
                        JSONObject body = obj.getJSONObject("body");
                        order_id = body.getString("order_id");
                        payGo();
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

    private String money;
    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
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
                        double v = Double.parseDouble(price);
                        double v1 = Double.parseDouble(money);
                        final double v2 = v1 - v;
                        if (v2 > 0) {
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    tvMoney.setText("¥" + price);
                                    tvMoneys.setText("¥" + price);
                                    deleteMoney.setText("- ¥" + v2);
                                }
                            });
                        } else {
                            deleteMoney.setText("不可用");
                            tvMoney.setText("¥" + money);
                            tvMoneys.setText("¥" + money);
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
        isHaveActive();

        ets[0].addTextChangedListener(watcher);
    }

    ;

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
        String count = intent.getStringExtra("count");
        String time = intent.getStringExtra("time");
        money = intent.getStringExtra("money");
        addGrade = intent.getStringExtra("addGrade");
        tvMoney.setText("¥" + money);
        tvCount.setText(count);
        tvTime.setText(time);
        tvMoneys.setText("¥" + money);
        String imgPath = model.getImgPath();
        String grade = model.getGrade();
        studioInfo[0].setText(model.getName());
        studioInfo[1].setText(model.getIntroduction());
        Glide.with(this).load(model.getPhoto_get()).transform(new GlideCircleTransform(this)).into(studioLogo);
        Glide.with(this).load(imgPath + grade + "_icon.png").into(studioSmallLevel);
        Glide.with(this).load(imgPath + grade + "_grade.png").into(studioBigLevel);

    }

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
