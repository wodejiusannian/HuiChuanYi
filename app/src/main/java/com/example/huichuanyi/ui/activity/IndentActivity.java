package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.ClosetAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.fragment_second.IndentFragment;
import com.example.huichuanyi.fragment_second.Progress_Indent;

import java.util.ArrayList;
import java.util.List;

public class IndentActivity extends BaseActivity {
    private TabLayout mIndentTab;
    private ViewPager mIndentPager;
    private List<Fragment> mData;
    private List<String> mTitles;
    private ClosetAdapter mAdapter;

    public void back(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent);

    }

    @Override
    public void initView() {
        mIndentTab = (TabLayout) findViewById(R.id.tb_my_indent_mTitle);
        mIndentPager = (ViewPager) findViewById(R.id.vp_my_indent_mPager);
    }

    @Override
    public void initData() {
        mData = new ArrayList<>();
        mTitles = new ArrayList<>();
        mData.add(new Progress_Indent());
        mData.add(new IndentFragment());
        mTitles.add("视频订单");
        mTitles.add("黑科技订单");
        mAdapter = new ClosetAdapter(getSupportFragmentManager(), mData, mTitles);
    }

    @Override
    public void setData() {
        mIndentTab.setupWithViewPager(mIndentPager);
        mIndentPager.setAdapter(mAdapter);
    }

    @Override
    public void setListener() {
    }


}
