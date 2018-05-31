package com.example.huichuanyi.secondui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.bean.PayState;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.PayListView;
import com.example.huichuanyi.newui.activity.OrderFormActivity;
import com.example.huichuanyi.ui.activity.Item_DetailsActivity;
import com.example.huichuanyi.ui.activity.My_365Activity;
import com.example.huichuanyi.ui.activity.SLWWriteInfoActivity;
import com.example.huichuanyi.ui.activity.pay.YWTPayActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonStatic;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.IsSuccess;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.example.huichuanyi.utils.UtilsPay;
import com.facebook.drawee.view.SimpleDraweeView;

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

public class PayOrderActivity extends BaseActivity implements UtilsInternet.XCallBack, IsSuccess, PayListView.PayState {
    @ViewInject(R.id.iv_payorder_photo)
    private SimpleDraweeView studioLogo;

    @ViewInject(R.id.tv_payorder_name)
    private TextView mTextViewName;

    @ViewInject(R.id.tv_payorder_allmoney)
    private TextView mTextViewMoney;

    @ViewInject(R.id.tv_payorder_nowMoney)
    private TextView mTextViewNowMoney;

    @ViewInject(R.id.tv_num)
    private TextView mNum;

    @ViewInject(R.id.include_pay_list)
    private PayListView payListView;
/*
    @ViewInject(R.id.rg_pay)
    private RadioGroup mRg;*/

    private String AliPayOrWeChat;

    private String managerPhoto, managerName, nowMoney, order_id, type, invitation_code;

    private UtilsInternet instance = UtilsInternet.getInstance();

    private UtilsPay mPay;

    private String user_id;


    public static PayOrderActivity payOrderActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);
        payOrderActivity = this;
    }


    @Override
    public void initData() {
        user_id = SharedPreferenceUtils.getUserData(this, 1);
        Intent intent = getIntent();
        managerPhoto = intent.getStringExtra("managerPhoto");
        managerName = intent.getStringExtra("managerName");
        nowMoney = intent.getStringExtra("nowMoney");
        order_id = intent.getStringExtra("orderid");
        invitation_code = intent.getStringExtra("invitation_code");
        type = intent.getStringExtra("type");
        String studio_id = intent.getStringExtra("studio_id");
        String city = intent.getStringExtra("city");
        String count = intent.getStringExtra("count");
        getMoney(studio_id, count, city);
        String num = intent.getStringExtra("num");
        mNum.setText(num);
        CommonStatic.wechatType = type;
        mPay = new UtilsPay(this);
    }


    @Override
    public void setData() {
        mPay.isSuccess(this);
        if (!TextUtils.isEmpty(managerPhoto) && managerPhoto.length() > 5) {
            studioLogo.setImageURI(managerPhoto);
        }
        mTextViewName.setText(managerName);
        mTextViewMoney.setText(nowMoney);
        mTextViewNowMoney.setText(nowMoney);
    }

    private void getMoney(String stu, String count, String city) {
        RequestParams pa = new RequestParams(NetConfig.GO_DOOR_MONEY);
        pa.addBodyParameter("studio_id", stu);
        pa.addBodyParameter("studio_city", city);
        pa.addBodyParameter("clothes_num", count);
        pa.addBodyParameter("invitation_code", invitation_code);
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
                            mTextViewMoney.setText(price);
                            mTextViewNowMoney.setText(price);
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

    @Override
    public void setListener() {


       /* mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_ali_pay:
                        AliPayOrWeChat = 1;
                        break;
                    case R.id.rb_wechat_pay:
                        AliPayOrWeChat = 2;
                        break;
                    case R.id.rb_cmb_pay:
                        AliPayOrWeChat = 3;
                        break;

                    default:
                        break;
                }
            }
        });*/
    }

    @Event(R.id.bt_payorder_pay)
    private void onEvent(View v) {
        switch (v.getId()) {
            case R.id.bt_payorder_pay:
                if (TextUtils.equals("3", AliPayOrWeChat)) {
                    /*Toast.makeText(payOrderActivity, "暂未开通", Toast.LENGTH_SHORT).show();
                    return;*/
                    Intent it = new Intent(this, YWTPayActivity.class);
                    it.putExtra("type", type);
                    it.putExtra("order_id", order_id);
                    startActivity(it);
                } else {
                    postData();
                }
                break;
        }
    }

    private void postData() {
        Map<String, String> map = new HashMap<>();
        if ("1".equals(type)) {
            map.put("service_order_id", order_id);
            map.put("pay_type", AliPayOrWeChat + "");
            instance.post(NetConfig.GO_DOOR_PAY, map, this);
            return;
        }
        switch (AliPayOrWeChat) {
            case "1":
                map.put("order_id", order_id);
                map.put("type", type);
                map.put("user_id", user_id);
                instance.post(NetConfig.ALI_PAY, map, this);
                break;
            case "2":
                map.put("order_id", order_id);
                map.put("user_id", user_id);
                map.put("type", type);
                instance.post(NetConfig.WE_CHAT_PAY, map, this);
                break;
            default:
                break;
        }
    }


    @Override
    public void onResponse(String result) {
        switch (AliPayOrWeChat) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        payOrderActivity = null;
    }

    @Override
    public void isSuccess(int success) {
        switch (success) {
            case 9000:
                CommonUtils.Toast(this, "支付成功");
                switch (type) {
                    case "1":
                        //mPay.showNotation();
                        closeActivity();
                        Intent orderIntent = new Intent(this, OrderFormActivity.class);
                        orderIntent.putExtra("title", "预约订单");
                        orderIntent.putExtra("orderTypePj", "1,2,3,4");
                        startActivity(orderIntent);
                        break;
                    case "2":
                        SharedPreferenceUtils.save365(this, "365");
                        sendBroad();
                        ActivityUtils.switchTo(this, My_365Activity.class);
                        break;
                    case "3":
                        closeActivity();
                        break;
                    default:
                        break;
                }

                break;
            case 9001:
                CommonUtils.Toast(this, "支付失败");
                break;
            default:
                break;
        }
    }

    public void back(View view) {
        finish();
    }

    private void closeActivity() {
        if (PayOrderActivity.payOrderActivity != null) {
            PayOrderActivity.payOrderActivity.finish();
            PayOrderActivity.payOrderActivity = null;
        }
        if (SLWWriteInfoActivity.write_orderActivity != null) {
            SLWWriteInfoActivity.write_orderActivity.finish();
            SLWWriteInfoActivity.write_orderActivity = null;
        }
        if (Item_DetailsActivity.item_detailsActivity != null) {
            Item_DetailsActivity.item_detailsActivity.finish();
            Item_DetailsActivity.item_detailsActivity = null;
        }
    }

    public void sendBroad() {
        Intent intent = new Intent();
        intent.setAction("action.refreshFriend");
        sendBroadcast(intent);
    }

    @Override
    public void initView() {
        initPayListView();
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
                            AliPayOrWeChat = o1.getString("pay_type");
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
        AliPayOrWeChat = p;
    }
}
