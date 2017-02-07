package com.example.huichuanyi.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.ClosetAdapter;
import com.example.huichuanyi.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class ChangHeFragment extends BaseFragment{
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<Fragment> mData;
    private List<String> mTitles;
    private ClosetAdapter mAdapter;
    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_fragment, null);
        getChildView(view);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        mData = new ArrayList<>();
        mTitles = new ArrayList<>();
        mData.add(new Fragment_changhe());
        mData.add(new Fragment_xiuxian());
        mData.add(new Fragment_shejiao());
        mTitles.add("商务");
        mTitles.add("休闲");
        mTitles.add("社交");
        mAdapter = new ClosetAdapter(getChildFragmentManager(),mData,mTitles);
    }

    @Override
    protected void setData() {
        super.setData();
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mAdapter);
    }

    private void getChildView(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tb_faragment_mTitle);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_fragment_fragment);
    }


}
