package com.example.huichuanyi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.Location;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.bean.City;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.secondui.PayOrderActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.Utils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Buy_365Activity extends BaseActivity implements UtilsInternet.XCallBack, View.OnClickListener {
    private UtilsInternet instance = UtilsInternet.getInstance();
    private TextView mCount, mStudioName, mPay, mWillPay;
    private Button mBtnPay;
    private Map<String, String> map = new HashMap<>();
    private SimpleDraweeView mStudioLogo;
    Map<String, Object> jumpMap = new HashMap<>();
    private String studioId, studioLogo, studioName, user_id;
    private int flag = 0;
    private String nowMoney = "365";
    private String is_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_365);
    }

    @Override
    public void initView() {
        mCount = (TextView) findViewById(R.id.tv_buy_365_count);
        mStudioLogo = (SimpleDraweeView) findViewById(R.id.sv_buy_365_photo);
        mStudioName = (TextView) findViewById(R.id.tv_buy_365_studio_name);
        mPay = (TextView) findViewById(R.id.tv_365_pay);
        mBtnPay = (Button) findViewById(R.id.btn_365_pay);
        mWillPay = (TextView) findViewById(R.id.act_tv_buy_365_will_price);
    }

    @Override
    public void initData() {
        user_id = SharedPreferenceUtils.getUserData(this,1);
        Intent intent = getIntent();
        City.BodyBean bodyBean = (City.BodyBean) intent.getSerializableExtra("bodyBean");
        studioId = bodyBean.getId();
        studioLogo = bodyBean.getPhoto_get();
        studioName = bodyBean.getName();
        //studioId = intent.getStringExtra("studioId");
        //studioLogo = intent.getStringExtra("studioLogo");
        //studioName = intent.getStringExtra("studioName");
        mStudioName.setText(studioName);
        mStudioLogo.setImageURI(studioLogo);
        map.put("id", studioId);
        map.put("type", "1");
        instance.post(NetConfig.GET_STUDIO_BUY_COUNT, map, this);
    }

    @Override
    public void setData() {

        if (Location.Location_type == 1) {
            nowMoney = SharedPreferenceUtils.getActivityPrice(this);
            mWillPay.setText("¥" + nowMoney);
            is_share = "Y";
        }
    }

    @Override
    public void setListener() {
        mPay.setOnClickListener(this);
        mBtnPay.setOnClickListener(this);
    }

    @Override
    public void onResponse(String result) {
        Utils.Log(result);

        switch (flag) {
            case 0:
                try {
                    JSONObject object = new JSONObject(result);
                    JSONObject body = object.getJSONObject("body");
                    String num = body.getString("num");
                    mCount.setText(num);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                dismissLoading();
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONObject body = obj.getJSONObject("body");
                    String id = body.getString("id");
                    Utils.Log(id);
                    jumpMap.put("managerPhoto", studioLogo);
                    jumpMap.put("orderid", studioId);
                    jumpMap.put("managerName", studioName);
                    jumpMap.put("nowMoney", nowMoney);
                    jumpMap.put("type", "2");
                    jumpMap.put("orderid", id);
                    ActivityUtils.switchTo(Buy_365Activity.this, PayOrderActivity.class, jumpMap);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_365_pay:
                goOpen();
                break;
            case R.id.btn_365_pay:
                goOpen();
                break;
            default:
                break;
        }
    }

    /*
    * 去开通
    * */
    private void goOpen() {
        showLoading();
        flag = 1;
        map.clear();
        map.put("user_id", user_id);
        map.put("studio_id", studioId);
        map.put("is_share", is_share);
        instance.post(NetConfig.SUBMIT_ORDER_365, map, this);
    }
}
