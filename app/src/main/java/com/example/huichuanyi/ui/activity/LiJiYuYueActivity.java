package com.example.huichuanyi.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.ClosetAdapter;
import com.example.huichuanyi.baidumap.GetCity;
import com.example.huichuanyi.baidumap.Location;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.ui.fragment.LiJiYuYueDefaultFragment;
import com.example.huichuanyi.ui.fragment.LiJiYuYueKPSFragment;
import com.example.huichuanyi.ui.fragment.LiJiYuYueMailsFragment;
import com.example.huichuanyi.utils.CommonUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class LiJiYuYueActivity extends BaseActivity {


    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> mData = new ArrayList<>();

    @ViewInject(R.id.tv_order_top)
    private TextView mTop;
    @ViewInject(R.id.tv_lijiyueyue_address)
    private TextView address;
    @ViewInject(R.id.tb_order_mTitle)
    private TabLayout mTabLayout;
    @ViewInject(R.id.vp_order_mPager)
    private ViewPager mViewPager;
    private ClosetAdapter mAdapter;

    private GetCity mGetCity;

    public static Activity instanceLiji;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            Location.mAddress = data.getString("location");
            if (!CommonUtils.isEmpty(Location.mAddress)) {
                address.setText(Location.mAddress);
                sendBroad();
                mGetCity.stopLocation();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        instanceLiji = this;
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        Location.mAddress = intent.getStringExtra("location");

        if (CommonUtils.isEmpty(Location.mAddress)) {
            mGetCity = new GetCity(getApplicationContext());
            mGetCity.startLocation();
            mGetCity.setGetCity(new GetCity.WillGetCity() {
                @Override
                public void getWillGetCity(String city, String lat, String lng) {
                    if (city != null) {
                        Location.lat = lat;
                        Location.lng = lng;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("location", city);
                    Message message = Message.obtain();
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                }
            });
        }
        Location.mTime = intent.getStringExtra("time");
        Location.mOrder_365 = intent.getStringExtra("order_365");
        if (TextUtils.equals("365", Location.mOrder_365)) {
            mTop.setText("选择工作室");
            address.setVisibility(View.GONE);
        }
        mTitles.add("默认排序");
        mTitles.add("评分最高");
        mTitles.add("销量最好");
        mData.add(new LiJiYuYueDefaultFragment());
        mData.add(new LiJiYuYueKPSFragment());
        mData.add(new LiJiYuYueMailsFragment());
        mAdapter = new ClosetAdapter(getSupportFragmentManager(), mData, mTitles);
    }


    @Override
    public void setData() {
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mAdapter);
    }


    public void back(View view) {
        finish();
    }


    @Event(R.id.tv_lijiyueyue_address)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_lijiyueyue_address:
                Intent intent = new Intent(new Intent(this,
                        LiJiYuYueStudioSelectCityActivity.class));
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGetCity != null)
            mGetCity.stopLocation();
    }


    public void sendBroad() {
        Intent intent = new Intent();
        intent.setAction("refreshstudio");
        sendBroadcast(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String mAdd = data.getStringExtra("address");
            if (!CommonUtils.isEmpty(mAdd)) {
                Location.mAddress = mAdd;
                address.setText(mAdd);
                sendBroad();
            }
        }
    }
}
