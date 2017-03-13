package com.example.huichuanyi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.ClosetAdapter;
import com.example.huichuanyi.baidumap.Location;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.fragment_second.Fragment_Default;
import com.example.huichuanyi.fragment_second.Fragment_KPS;
import com.example.huichuanyi.fragment_second.Fragment_Sales;

import java.util.ArrayList;
import java.util.List;

public class LiJiYuYueActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImageViewBack;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> mTitles;
    private List<Fragment> mData;
    private ClosetAdapter mAdapter;
    private TextView mTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
    }

    @Override
    public void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.iv_order_back);
        mTabLayout = (TabLayout) findViewById(R.id.tb_order_mTitle);
        mViewPager = (ViewPager) findViewById(R.id.vp_order_mPager);
        mTop = (TextView) findViewById(R.id.tv_order_top);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        Location.mAddress = intent.getStringExtra("location");
        Location.mTime = intent.getStringExtra("time");
        Location.mOrder_365 = intent.getStringExtra("order_365");
        if (TextUtils.equals("365", Location.mOrder_365)) {
            mTop.setText("选择工作室");
        }
        mTitles = new ArrayList<>();
        mData = new ArrayList<>();
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

    @Override
    public void setListener() {
        mImageViewBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_order_back:
                finish();
                break;
        }
    }
}
