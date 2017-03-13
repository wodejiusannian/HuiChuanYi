package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.ClosetAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.fragment_second.Over_Indent;
import com.example.huichuanyi.fragment_second.Progress_Indent;

import java.util.ArrayList;
import java.util.List;

public class IndentActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImageViewBack;
    private TabLayout mIndentTab;
    private ViewPager mIndentPager;
    private List<Fragment> mData;
    private List<String> mTitles;
    private ClosetAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent);

    }

    @Override
    public void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.iv_indent_back);
        mIndentTab = (TabLayout) findViewById(R.id.tb_my_indent_mTitle);
        mIndentPager = (ViewPager) findViewById(R.id.vp_my_indent_mPager);
    }

    @Override
    public void initData() {
        mData = new ArrayList<>();
        mTitles = new ArrayList<>();
        mData.add(new Progress_Indent());
        mData.add(new Over_Indent());
        mTitles.add("未完成");
        mTitles.add("已完成");
        mAdapter = new ClosetAdapter(getSupportFragmentManager(), mData, mTitles);
    }

    @Override
    public void setData() {
        mIndentTab.setupWithViewPager(mIndentPager);
        mIndentPager.setAdapter(mAdapter);
    }

    @Override
    public void setListener() {
        mImageViewBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_indent_back:
                finish();
                break;
        }
    }
}
