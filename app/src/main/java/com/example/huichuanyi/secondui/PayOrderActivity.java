package com.example.huichuanyi.secondui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.activity.Item_DetailsActivity;
import com.example.huichuanyi.ui.activity.MyOrderActivity;
import com.example.huichuanyi.ui.activity.My_365Activity;
import com.example.huichuanyi.ui.activity.SLWWriteInfoActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonStatic;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.IsSuccess;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.example.huichuanyi.utils.UtilsPay;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

public class PayOrderActivity extends BaseActivity implements UtilsInternet.XCallBack, IsSuccess {
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

    @ViewInject(R.id.rg_pay)
    private RadioGroup mRg;

    private int AliPayOrWeChat = 1;

    private String managerPhoto, managerName, nowMoney, order_id, type;

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
        type = intent.getStringExtra("type");
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

    @Override
    public void setListener() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        });
    }

    @Event(R.id.bt_payorder_pay)
    private void onEvent(View v) {
        switch (v.getId()) {
            case R.id.bt_payorder_pay:
                if (AliPayOrWeChat == 3) {
                    Toast.makeText(payOrderActivity, "暂未开通", Toast.LENGTH_SHORT).show();
                    return;
                   /* Intent it = new Intent(this, YWTPayActivity.class);
                    it.putExtra("type", type);
                    it.putExtra("order_id", order_id);
                    startActivity(it);*/
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
            case 1:
                map.put("order_id", order_id);
                map.put("type", type);
                map.put("user_id", user_id);
                instance.post(NetConfig.ALI_PAY, map, this);
                break;
            case 2:
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
            case 1:
                try {
                    JSONObject object = new JSONObject(result);
                    JSONObject body = object.getJSONObject("body");
                    String sign = body.getString("sign");
                    mPay.aliPay(sign);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
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
                        mPay.showNotation();
                        closeActivity();
                        ActivityUtils.switchTo(this, MyOrderActivity.class);
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
    }

}
