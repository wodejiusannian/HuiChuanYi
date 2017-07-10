package com.example.huichuanyi.ui.activity.video;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.VideoYouHuiQuanAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.bean.Video;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MySelfPayDialog;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.IsSuccess;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.ReminderUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.example.huichuanyi.utils.UtilsPay;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeVideoYouhuiquanActivity extends BaseActivity implements IsSuccess, UtilsInternet.XCallBack, MySelfPayDialog.OnNoClickListener, MySelfPayDialog.OnYesClickListener /*implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, MySelfDialog.OnYesClickListener*/ {

    @ViewInject(R.id.lv_youhuiquan)
    private ListView mListView;

    private List<Video.BodyBean> mList;

    private VideoYouHuiQuanAdapter adapter;

    @ViewInject(R.id.tv_money)
    private TextView money;

    @ViewInject(R.id.bt_quan_submit_twos)
    private Button mSubmit;
    private int isFlag = 1;

    private String city, order_id, promo_code;

    private String aliOrWeChat;

    private UtilsPay pay;
    private String user_id;
    private UtilsInternet net = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_video_youhuiquan);
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        mList = (List<Video.BodyBean>) intent.getSerializableExtra("list");
        String video_price = intent.getStringExtra("video_price");
        promo_code = intent.getStringExtra("promo_code");
        money.setText(video_price);
    }

    @Override
    public void initData() {
        adapter = new VideoYouHuiQuanAdapter(mList, this);
    }


    @Override
    public void setData() {
        mListView.setAdapter(adapter);
        user_id = SharedPreferenceUtils.getUserData(this, 1);
        pay = new UtilsPay(this);
        pay.isSuccess(this);
        String citys = SharedPreferenceUtils.getCity(this);
        if (CommonUtils.isEmpty(citys)) {
            city = "北京市";
        } else {
            city = citys;
        }
    }

    @Override
    public void setListener() {
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                getOrderId();
            }
        });
    }

    public void back(View view) {
        finish();
    }


    private void getOrderId() {
        map.put("user_id", user_id);
        map.put("promo_code", promo_code);
        map.put("user_city", city);
        map.put("video_id_pj", "1,2,3,4");
        net.post(NetConfig.GET_SHIPIN_ORDER_ID, map, this);
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

    @Override
    public void onResponse(String result) {
        switch (isFlag) {
            case 1:
                try {
                    String ret = JsonUtils.getRet(result);
                    if (TextUtils.equals("1015", ret)) {
                        ReminderUtils.Toast(this, "视频成功兑换");
                        finish();
                        return;
                    }
                    if (TextUtils.equals("0", ret)) {
                        isFlag = 2;
                        JSONObject object = new JSONObject(result);
                        JSONObject body = object.getJSONObject("body");
                        order_id = body.getString("order_id");
                        dismissLoading();
                        showDialog();
                    } else {
                        Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                isFlag = 1;
                String ret = JsonUtils.getRet(result);
                switch (aliOrWeChat) {

                    case "1":
                        try {
                            JSONObject object = new JSONObject(result);
                            JSONObject body = object.getJSONObject("body");
                            String sign = body.getString("sign");
                            pay.aliPay(sign);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "2":
                        pay.weChatPay(result);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    private void showDialog() {
        MySelfPayDialog mySelfPayDialog = new MySelfPayDialog(this);
        mySelfPayDialog.setOnNoListener("取消", this);
        mySelfPayDialog.setOnYesListener("确定", this);
        mySelfPayDialog.setTitle("选择支付");
        mySelfPayDialog.show();
    }

    @Override
    public void onClick() {
        isFlag = 1;
    }

    @Override
    public void onClick(String tag) {
        aliOrWeChat = tag;
        map.put("pay_type", tag + "");
        map.put("video_order_id", order_id);
        net.post(NetConfig.PAY_SHIPIN, map, this);
    }
}
