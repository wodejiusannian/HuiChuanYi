package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.ClosetAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.fragment.Statistics_fragment_clothes;
import com.example.huichuanyi.fragment.Statistics_fragment_oca;
import com.example.huichuanyi.utils.ActivityUtils;

import org.xutils.view.annotation.Event;

import java.util.ArrayList;
import java.util.List;

public class HomeStatisticsActivity extends BaseActivity {
    private ViewPager mViewPager;
    private ClosetAdapter mAdapter;
    private List<Fragment> mData;
    private List<String> mTitles;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

    }

    @Override
    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_info_mPager);
        mTabLayout = (TabLayout) findViewById(R.id.tb_info_mTitle);
    }

    @Override
    public void initData() {
        mData = new ArrayList<>();
        mTitles = new ArrayList<>();
        mData.add(new Statistics_fragment_clothes());
        mData.add(new Statistics_fragment_oca());
        mTitles.add("按服饰分");
        mTitles.add("按场合分");
        mAdapter = new ClosetAdapter(getSupportFragmentManager(), mData, mTitles);
    }

    @Override
    public void setData() {
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void setListener() {
    }

    @Event({R.id.tv_statistics_info})
    private void onEvent(View v) {
        switch (v.getId()) {
            case R.id.tv_statistics_info:
                ActivityUtils.switchTo(this, HMStateActivity.class);
                break;
            default:
                break;
        }
    }

    public void back(View view) {
        finish();
    }
}
