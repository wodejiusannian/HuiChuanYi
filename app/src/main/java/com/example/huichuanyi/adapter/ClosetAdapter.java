package com.example.huichuanyi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ClosetAdapter extends FragmentPagerAdapter {
    private List<Fragment> mData;
    private List<String>mTitle;
    public ClosetAdapter(FragmentManager fm , List<Fragment> data,List<String> titles) {
        super(fm);
        mData = data;
        mTitle = titles;
    }

    @Override
    public int getCount() {
        return mData.size()==0?0:mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }


}
