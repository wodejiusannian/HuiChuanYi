package com.example.huichuanyi.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by 小五 on 2017/1/18.
 */
public class MyViewPagerPicsAdapter extends PagerAdapter{

    private List<ImageView> mImages;
    public MyViewPagerPicsAdapter(List<ImageView> mImages) {
        this.mImages = mImages;
    }


    @Override
    public int getCount() {
        return mImages.size()==0?0:mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup Container, int position, Object object) {
        ((ViewPager) Container).removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mImages.get(position), 0);
        return mImages.get(position);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
