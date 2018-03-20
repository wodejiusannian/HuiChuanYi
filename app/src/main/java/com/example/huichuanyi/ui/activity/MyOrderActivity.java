package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.ClosetAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.fragment_second.ServiceAcarusKillFragment;
import com.example.huichuanyi.fragment_second.ServiceGoDoorFragment;
import com.example.huichuanyi.utils.ActivityCacheUtils;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends BaseActivity {

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
        mTabLayout = (TabLayout) findViewById(R.id.tb_my_order_mTitle);
        mViewPager = (ViewPager) findViewById(R.id.vp_my_order_mPager);
    }

    @Override
    public void initData() {
        mData = new ArrayList<>();
        mTitles = new ArrayList<>();
        mData.add(new ServiceGoDoorFragment());
        mData.add(new ServiceAcarusKillFragment());
        mTitles.add("上门服务");
        mTitles.add("除螨服务");
        mAdapter = new ClosetAdapter(getSupportFragmentManager(), mData, mTitles);
    }

    @Override
    public void setData() {
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mAdapter);
        ActivityCacheUtils.finishActivity();
    }

    @Override
    public void setListener() {
    }

    public void back(View view) {
        finish();
    }
}
