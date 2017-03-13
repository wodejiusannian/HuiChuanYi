package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.ClosetAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.fragment.ChangHeFragment;
import com.example.huichuanyi.secondui.ClosetSortFragment;
import com.example.huichuanyi.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

public class MC_HomeActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImageViewBack;
    private ViewPager mViewPager;
    private ClosetAdapter mAdapter;
    private List<Fragment> mData;
    private List<String> mTitles;
    private TabLayout mTabLayout;
    private TextView mTextViewUp;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);
    }

    @Override
    public void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.iv_closet_main_back);
        mViewPager = (ViewPager) findViewById(R.id.vp_closet_mPager);
        mTabLayout = (TabLayout) findViewById(R.id.tb_closet_mTitle);
        mTextViewUp = (TextView) findViewById(R.id.tv_clothes_up);
        mTextView = (TextView) findViewById(R.id.tv_yichu);
    }

    @Override
    public void initData() {
        mData = new ArrayList<>();
        mTitles = new ArrayList<>();
        String yichu = getIntent().getStringExtra("yichu");
        mTextView.setText(yichu);
        mData.add(new ClosetSortFragment());
        mData.add(new ChangHeFragment());
        mTitles.add("按分类");
        mTitles.add("按场合");
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
        mTextViewUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_closet_main_back:
                finish();
                break;
            case R.id.tv_clothes_up:
                ActivityUtils.switchTo(this, LabelsActivity.class);
                break;
        }
    }

}
