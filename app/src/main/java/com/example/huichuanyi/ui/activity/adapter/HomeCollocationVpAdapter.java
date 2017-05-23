package com.example.huichuanyi.ui.activity.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.huichuanyi.R;

import java.util.List;

/**
 * Created by 小五 on 2017/4/24.
 */

public class HomeCollocationVpAdapter extends PagerAdapter {
    private static final String TAG = "HomeCollocationVpAdapter";
    private List<Integer> mData;

    public HomeCollocationVpAdapter(List<Integer> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size() == 0 ? 0 : mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup Container, int position, Object object) {
        ((ViewPager) Container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_vp_collocation, container, false);
        view.setTag(position);
        container.addView(view);
        bind(position, view);
        return view;
    }

    private void bind(int pos, View view) {
        ImageView person = (ImageView) view.findViewById(R.id.iv_person);
        person.setImageResource(mData.get(pos));
    }

}
