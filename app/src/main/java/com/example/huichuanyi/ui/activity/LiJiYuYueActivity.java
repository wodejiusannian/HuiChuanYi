package com.example.huichuanyi.ui.activity;

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
import com.example.huichuanyi.custom.EditDialog;
import com.example.huichuanyi.fragment_second.Fragment_Default;
import com.example.huichuanyi.fragment_second.Fragment_KPS;
import com.example.huichuanyi.fragment_second.Fragment_Sales;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class LiJiYuYueActivity extends BaseActivity implements EditDialog.EditYes {
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

    private OnRefreshAddress mOnRefreshAddress;
    private ClosetAdapter mAdapter;
    private GetCity mGetCity;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String location = data.getString("location");
            address.setText(location);
            mOnRefreshAddress.reFreshAddress(location);
            //Toast.makeText(LiJiYuYueActivity.this, "location" + location, Toast.LENGTH_SHORT).show();
            if (!CommonUtils.isEmpty(location)) {
                mGetCity.stopLocation();
            }
        }
    };

    public void setRefreshAddress(OnRefreshAddress onRefreshAddress) {
        mOnRefreshAddress = onRefreshAddress;
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initView() {
        // String city = MySharedPreferences.getCity(this);
    }

    @Override
    public void initData() {
        mGetCity = new GetCity(getApplicationContext());
        mGetCity.startLocation();
        mGetCity.setGetCity(new GetCity.WillGetCity() {
            @Override
            public void getWillGetCity(String city) {
                Bundle bundle = new Bundle();
                bundle.putString("location", city);
                Message message = Message.obtain();
                message.setData(bundle);
                mHandler.sendMessage(message);
              /*  mOnRefreshAddress.reFreshAddress(city);
                address.setText(city);
                if (!CommonUtils.isEmpty(city)) {
                    mGetCity.stopLocation();
                }*/
            }
        });
        Intent intent = getIntent();
        Location.mAddress = intent.getStringExtra("location");
        Location.mTime = intent.getStringExtra("time");
        Location.mOrder_365 = intent.getStringExtra("order_365");
        if (TextUtils.equals("365", Location.mOrder_365)) {
            mTop.setText("选择工作室");
            address.setVisibility(View.GONE);
        }
        mTitles.add("默认排序");
        mTitles.add("评分最高");
        mTitles.add("销量最好");
        mData.add(new Fragment_Default());
        mData.add(new Fragment_KPS());
        mData.add(new Fragment_Sales());
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

    private void initEditText() {
        EditDialog editDialog = new EditDialog(this);
        editDialog.setOnClickNo("取消");
        editDialog.setOnClickYes("确定", this);
        editDialog.show();
    }

    @Override
    public void getEdit(String city) {
        address.setText(city);
        mOnRefreshAddress.reFreshAddress(city);
    }


    public interface OnRefreshAddress {
        void reFreshAddress(String city);
    }

    @Event(R.id.tv_lijiyueyue_address)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_lijiyueyue_address:
                /*initEditText();*/
                ActivityUtils.switchTo(this, StudioSelectCityActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGetCity.stopLocation();
    }
}
