package com.example.huichuanyi.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.common_view.model.ItemHmMainBanner;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.List;


public class HmMainItemBannerAdapter extends LoopPagerAdapter {

    private List<ItemHmMainBanner.Banner> mBanner;
    private Context mContext;

    public HmMainItemBannerAdapter(RollPagerView viewPager, List<ItemHmMainBanner.Banner> banner, Context context) {
        super(viewPager);
        mBanner = banner;
        mContext = context;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(mContext);
        Glide.with(mContext).load(mBanner.get(position).pictureUrl).into(view);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getRealCount() {
        return mBanner == null ? 0 : mBanner.size();
    }

}
