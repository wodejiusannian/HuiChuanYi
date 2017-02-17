package com.example.huichuanyi.ui_third;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.secondui.PayOrderActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Buy_365Activity extends BaseActivity implements UtilsInternet.XCallBack, View.OnClickListener {
    private UtilsInternet instance = UtilsInternet.getInstance();
    private TextView mCount, mStudioName, mPay;
    private Button mBtnPay;
    private Map<String, String> map = new HashMap<>();
    private SimpleDraweeView mStudioLogo;
    Map<String, Object> jumpMap = new HashMap<>();
    private String studioId, studioLogo, studioName;

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
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        studioId = intent.getStringExtra("studioId");
        studioLogo = intent.getStringExtra("studioLogo");
        studioName = intent.getStringExtra("studioName");
        mStudioName.setText(studioName);
        mStudioLogo.setImageURI(studioLogo);
        map.put("id", studioId);
        map.put("type", "1");
        instance.post(NetConfig.GET_STUDIO_BUY_COUNT, map, this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        mPay.setOnClickListener(this);
        mBtnPay.setOnClickListener(this);
    }

    @Override
    public void onResponse(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject body = object.getJSONObject("body");
            String num = body.getString("num");
            mCount.setText(num);
        } catch (JSONException e) {
            e.printStackTrace();
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
        jumpMap.put("managerPhoto", studioLogo);
        jumpMap.put("orderid", studioId);
        jumpMap.put("managerName", studioName);
        jumpMap.put("nowMoney", "365");
        ActivityUtils.switchTo(Buy_365Activity.this, PayOrderActivity.class, jumpMap);
        finish();
    }
}
