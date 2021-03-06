package com.example.huichuanyi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.bean.PayState;
import com.example.huichuanyi.common_view.model.OrderFormOrder;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.PayListView;
import com.example.huichuanyi.utils.AsyncHttpUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.HttpCallBack;
import com.example.huichuanyi.utils.IsSuccess;
import com.example.huichuanyi.utils.MUtilsInternet;
import com.example.huichuanyi.utils.PayUtilsCopy;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsPay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClosingPriceActivity extends BaseActivity implements PayUtilsCopy.Sign, IsSuccess, PayListView.PayState {

    @ViewInject(R.id.et_cp_count)
    private EditText cpCount;

    @ViewInject(R.id.tv_cp_kind)
    private TextView cpKind;

    @ViewInject(R.id.tv_cp_money)
    private TextView cpMoney;

    @ViewInject(R.id.include_pay_list)
    private PayListView payListView;

    private UtilsPay mPay;


    /*
    * 1 代表使用支付宝，2 代表使用微信，3 代表使用一网通
    * */
    private String a_w_c, mmCount;

    private PayUtilsCopy pay = PayUtilsCopy.getInstance();

    private String kind = null;

    private String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_closing);
    }

    private OrderFormOrder.BodyBean bean;

    private OrderFormOrder.BodyBean.OrderInfoBean orderInfoBean;

    @Override
    public void initView() {
        Intent in = getIntent();
        kind = in.getStringExtra("kind");
        bean = in.getParcelableExtra("bean");
        orderInfoBean = bean.getOrderInfo().get(0);
        order_id = bean.getOrderId();
        if ("1".equals(orderInfoBean.getOrderType())) {
            cpKind.setText("除螨服务补差价");
            cpCount.setHint("请输入蓝氧空气机材料使用数量");
        } else {
            if (TextUtils.equals("5", kind)) {
                cpKind.setText("上门服务补差价");
            } else if (TextUtils.equals("6", kind)) {
                cpKind.setText("小蓝盒补差价");
                cpCount.setHint("请输入小蓝盒数量");
            }
        }
    }


    @Override
    public void initData() {
        mPay = new UtilsPay(this);
    }

    @Override
    public void setData() {
        mPay.isSuccess(this);
    }

    @Override
    public void setListener() {
        cpCount.addTextChangedListener(watcher);
        initPayListView();
    }


    public double getDouble(String str) {
        if (!CommonUtils.isEmpty(str))
            return Double.parseDouble(str);
        else
            return 0;
    }

    private MUtilsInternet net = MUtilsInternet.getInstance();

    /*获取到补差价订单的签名*/
    private void goDoorGetPrice() {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", bean.getOrderId());
        map.put("buyUserId", SharedPreferenceUtils.getUserData(this, 1));
        map.put("buyUserName", SharedPreferenceUtils.getUserData(this, 2));
        map.put("sellerUserId", orderInfoBean.getSellerUserId());
        map.put("sellerUserGrade", orderInfoBean.getSellerUserGrade());
        map.put("sellerUserName", orderInfoBean.getSellerUserName());
        map.put("sellerCityName", orderInfoBean.getSellerCityName());
        map.put("sellerPhone", orderInfoBean.getSellerPhone());
        map.put("consigneeTime", orderInfoBean.getConsigneeTime());
        map.put("clothesNumber", mmCount);
        net.post(NetConfig.GOODOOR_CLOSERPRICE_GETSIGN, this, map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONObject body = obj.getJSONObject("body");
                    goPayID(body.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Event(R.id.btn_cp_pay)
    private void onEvent(View v) {
        if (getDouble(mmCount) > 1) {
            switch (v.getId()) {
                case R.id.btn_cp_pay:
                    //这个是除螨补差价接口
                    switch (a_w_c) {
                        case "1":
                            if ("1".equals(orderInfoBean.getOrderType())) {
                                acarusKill();
                            } else {
                                goDoorGetPrice();
                               /* String counts = cpCount.getText().toString().trim();
                                pay.aLiSign("1", bean.getOrderId(), mmCount, this, kind);*/
                            }
                            break;
                        case "2":
                            if ("1".equals(orderInfoBean.getOrderType())) {
                                acarusKill();
                            } else {
                                goDoorGetPrice();
                                // pay.weChatSign("2", bean.getOrderId(), mmCount, this, kind);
                            }
                            break;
                        case "3":
                            Toast.makeText(this, "一网通支付暂未开通", Toast.LENGTH_SHORT).show();
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

    private void acarusKill() {
        RequestParams p = new RequestParams(NetConfig.ORDER_CLOSE_MONEY_ACARUS_KILLING);
        p.addBodyParameter("token", "82D5FBD40259C743ADDEF14D0E22F347");
        p.addBodyParameter("orderId", bean.getOrderId());
        p.addBodyParameter("orderRemarkBuyer", "");
        p.addBodyParameter("buyUserId", SharedPreferenceUtils.getUserData(this, 1));
        p.addBodyParameter("buyUserName", orderInfoBean.getConsigneeName());
        p.addBodyParameter("orderNumber", mmCount);
        x.http().post(p, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONObject body = obj.getJSONObject("body");
                    String orderId = body.getString("orderIdBcj");
                    goPay(orderId);
                } catch (Exception e) {
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


    /*
    * 获取签名支付
    * */
    private void goPay(String orderIdBcj) {
        RequestParams p = new RequestParams(NetConfig.SIGN_CLOSE_MONEY_ACARUS_KILLING);
        p.addBodyParameter("token", "82D5FBD40259C743ADDEF14D0E22F347");
        p.addBodyParameter("orderIdBcj", orderIdBcj);
        p.addBodyParameter("payType", a_w_c);
        x.http().post(p, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                switch (a_w_c) {
                    case "1":
                        try {
                            JSONObject object = new JSONObject(result);
                            JSONObject body = object.getJSONObject("body");
                            String signs = body.getString("sign");
                            mPay.aliPay(signs);
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

    public void back(View view) {
        if (view != null) {
            finish();
        }
    }


    @Override
    public void getSign(String sign) {
        switch (a_w_c) {
            case "1":
                try {
                    JSONObject object = new JSONObject(sign);
                    JSONObject body = object.getJSONObject("body");
                    String signs = body.getString("sign");
                    mPay.aliPay(signs);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "2":
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
                break;
            case 9001:
                CommonUtils.Toast(this, "支付失败");
                break;
            default:
                break;
        }
    }

    private void initPayListView() {
        RequestParams params = new RequestParams("http://hmyc365.net:8081/HM/app/system/pay/getPaySort.do");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                List<PayState> data = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray arr = object.getJSONArray("body");
                    for (int i = 0; i < arr.length(); i++) {
                        PayState pa = new PayState();
                        JSONObject o1 = arr.getJSONObject(i);
                        if (i == 0) {
                            a_w_c = o1.getString("pay_type");
                        }
                        pa.type = o1.getString("pay_type");
                        pa.pic = o1.getString("pic_url");
                        data.add(pa);
                    }
                    payListView.setData(data);
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
        payListView.getPos(this);
    }

    @Override
    public void state(String p) {
        a_w_c = p;
    }

    private void acarusKillClose() {
        RequestParams pa = new RequestParams(NetConfig.PRICE_ACARUS_KILLING);
        pa.addBodyParameter("token", "82D5FBD40259C743ADDEF14D0E22F347");
        pa.addBodyParameter("priceType", "1");
        pa.addBodyParameter("city", orderInfoBean.getSellerCityName());
        x.http().post(pa, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject ob = new JSONObject(result);
                    JSONObject body = ob.getJSONObject("body");
                    int defaultNum = body.getInt("defaultNum");
                    int count = Integer.parseInt(cpCount.getText().toString());
                    int cha = count - defaultNum;
                    if (cha > 0) {
                        int raiseNum = body.getInt("raiseNum");
                        Double raisePrice = body.getDouble("raisePrice");
                        final Double m = (cha / raiseNum) * raisePrice;
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                cpMoney.setText(m + "");
                            }
                        });
                    } else {
                        cpMoney.setText("0");
                        Toast.makeText(ClosingPriceActivity.this, "数量应该大于" + defaultNum, Toast.LENGTH_SHORT).show();
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

    private Handler handler = new Handler();

    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            if ("1".equals(orderInfoBean.getOrderType())) {
                acarusKillClose();
            } else {
                RequestParams pa;
                if (TextUtils.equals("6", kind)) {
                    pa = new RequestParams("http://hmyc365.net:8081/HM/app/doorToDoorService/order/price/getBcjBoxPrice.do");
                    pa.addBodyParameter("box_num", mmCount);
                    pa.addBodyParameter("order_id", bean.getOrderId());
                } else {
                    String orderId = bean.getOrderId();
                    String sellerUserId = orderInfoBean.getSellerUserId();
                    String sellerUserGrade = orderInfoBean.getSellerUserGrade();
                    pa = new RequestParams(NetConfig.GODOOR_CLOSEPRICE_GETPRICE);
                    pa.addBodyParameter("orderId", orderId);
                    pa.addBodyParameter("sellerUserId", sellerUserId);
                    pa.addBodyParameter("clothesNumber", mmCount);
                    pa.addBodyParameter("sellerUserGrade", sellerUserGrade);
                }
                x.http().post(pa, new Callback.CacheCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject object = new JSONObject(result);
                            JSONObject body = object.getJSONObject("body");
                            final String price = body.getString("price");
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    cpMoney.setText(price);
                                }
                            });
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

            mmCount = s.toString();
            //延迟800ms，如果不再输入字符，则执行该线程的run方法
            handler.postDelayed(delayRun, 800);
        }
    };


    private void goPayID(String id) {
        try {
            JSONObject o1 = new JSONObject();
            o1.put("token", NetConfig.TOKEN);
            o1.put("payType", a_w_c);
            o1.put("concessionCode", "");
            o1.put("orderRemarkBuyer", "");
            o1.put("consigneeName", "");
            o1.put("consigneePhone", "");
            o1.put("consigneeAddress", "");
            JSONArray a = new JSONArray();
            JSONObject O2 = new JSONObject();
            O2.put("id", id);
            a.put(O2);
            o1.put("ids", a);
            new AsyncHttpUtils(new HttpCallBack() {
                @Override
                public void onResponse(String result) {
                    switch (a_w_c) {
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
    }
}
