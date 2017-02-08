package com.example.huichuanyi.ui_third;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.ClosetAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.fragment_second.BuyRecord;
import com.example.huichuanyi.fragment_second.RefreshRecord;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends BaseActivity {
    private TabLayout mTabLayout;
    private ViewPager mShow;
    private ClosetAdapter mAdapter;
    private List<Fragment> mData;
    private List<String> mTitles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
    }

    @Override
    public void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tb_record_mTitle);
        mShow = (ViewPager) findViewById(R.id.vp_record_show);
    }

    @Override
    public void initData() {
        mData = new ArrayList<>();
        mTitles = new ArrayList<>();
        mData.add(new BuyRecord());
        mData.add(new RefreshRecord());
        mTitles.add("购买记录");
        mTitles.add("更新记录");
        mAdapter = new ClosetAdapter(getSupportFragmentManager(),mData,mTitles);
    }

    @Override
    public void setData() {
        mTabLayout.setupWithViewPager(mShow);
        mShow.setAdapter(mAdapter);
    }

    @Override
    public void setListener() {

    }

    public void back(View view){
        finish();
    }

}
