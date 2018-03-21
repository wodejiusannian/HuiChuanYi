package com.example.huichuanyi.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.bean.MyClothess;

import java.util.List;

/**
 * Created by 小五 on 2017/1/18.
 */
public class MyViewPagerPicsAdapter extends PagerAdapter {

    private List<ImageView> mImages;
    private List<MyClothess.BodyBean.ClothesInfoBean> mData;
    private Context context;

    public MyViewPagerPicsAdapter(Context mContext, List<ImageView> mImages, List<MyClothess.BodyBean.ClothesInfoBean> data) {
        this.mImages = mImages;
        mData = data;
        context = mContext;
    }


    @Override
    public int getCount() {
        return mImages.size() == 0 ? 0 : mImages.size();
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
    public Object instantiateItem(ViewGroup container, int position) {
        int i = position % 4;
        ImageView imageView = mImages.get(i);
        Glide.with(context).load(mData.get(position).getClothes_pic()).into(imageView);
        container.addView(imageView, 0);
        return imageView;
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
