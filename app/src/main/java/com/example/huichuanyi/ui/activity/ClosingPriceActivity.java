package com.example.huichuanyi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.bean.PayState;
import com.example.huichuanyi.bean.Progress;
import com.example.huichuanyi.custom.PayListView;
import com.example.huichuanyi.ui.activity.pay.CMBPayActivity;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.IsSuccess;
import com.example.huichuanyi.utils.PayUtilsCopy;
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
import java.util.List;

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

    @Override
    public void initView() {
        Intent in = getIntent();
        kind = in.getStringExtra("kind");
        Progress.ListBean bean = (Progress.ListBean) in.getSerializableExtra("bean");
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

    @Event(R.id.btn_cp_pay)
    private void onEvent(View v) {
        if (getDouble(mmCount) > 1) {
            switch (v.getId()) {
                case R.id.btn_cp_pay:
                    switch (a_w_c) {
                        case "1":
                            String counts = cpCount.getText().toString().trim();
                            pay.aLiSign("1", order_id, mmCount, this,kind);
                            break;
                        case "2":
                            pay.weChatSign("2", order_id, mmCount, this,kind);
                            break;
                        case "3":
                            RequestParams params;
                            if (TextUtils.equals("6", kind)) {
                                params = new RequestParams("http://hmyc365.net:8081/HM/app/doorToDoorService/pay/supplementaryPriceDifference/box/getSign.do");
                            } else {
                                params = new RequestParams("http://hmyc365.net:8081/HM/app/doorToDoorService/pay/supplementaryPriceDifference/clothes/getSign_new.do");
                            }
                            params.addBodyParameter("order_id", order_id);
                            params.addBodyParameter("pay_type", "3");
                            params.addBodyParameter("num", mmCount);
                            x.http().post(params, new Callback.CacheCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    Intent it = new Intent(ClosingPriceActivity.this, CMBPayActivity.class);
                                    it.putExtra("order_id", result);
                                    startActivity(it);
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


    private Handler handler = new Handler();

    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            RequestParams pa;
            if (TextUtils.equals("6", kind)) {
                pa = new RequestParams("http://hmyc365.net:8081/HM/app/doorToDoorService/order/price/getBcjBoxPrice.do");
                pa.addBodyParameter("box_num", mmCount);
            } else {
                pa = new RequestParams("http://hmyc365.net:8081/HM/app/doorToDoorService/order/price/getBcjCloPriceNew.do");
                pa.addBodyParameter("clothes_num", mmCount);
            }
            pa.addBodyParameter("order_id", order_id);
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
}
