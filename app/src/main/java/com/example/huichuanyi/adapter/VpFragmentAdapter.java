package com.example.huichuanyi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class VpFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mData;

    public VpFragmentAdapter(FragmentManager fm, List<Fragment> data) {
        super(fm);
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size() == 0 ? 0 : mData.size();
    }


    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }


}
