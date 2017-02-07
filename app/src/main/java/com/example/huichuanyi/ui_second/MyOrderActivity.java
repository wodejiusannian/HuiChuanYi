package com.example.huichuanyi.ui_second;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.ClosetAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.fragment_second.Over_Order;
import com.example.huichuanyi.fragment_second.Progress_Order;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImageViewBack;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mData;
    private List<String> mTitles;
    private ClosetAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
    }

    @Override
    public void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.iv_myOrder_back);
        mTabLayout = (TabLayout) findViewById(R.id.tb_my_order_mTitle);
        mViewPager = (ViewPager) findViewById(R.id.vp_my_order_mPager);
    }

    @Override
    public void initData() {
        mData = new ArrayList<>();
        mTitles = new ArrayList<>();
        mData.add(new Progress_Order());
        mData.add(new Over_Order());
        mTitles.add("全部预约");
        mTitles.add("待评价");
        mAdapter = new ClosetAdapter(getSupportFragmentManager(),mData,mTitles);
    }

    @Override
    public void setData() {
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void setListener() {
        mImageViewBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.iv_myOrder_back:
                finish();
                break;
        }
    }

}
