package com.example.huichuanyi.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.common_view.model.ItemHmMainStarStudio;
import com.example.huichuanyi.config.NetConfig;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.List;


public class HmMainItemStarStudioAdapter extends LoopPagerAdapter {

    private List<ItemHmMainStarStudio.DataBean> mBanner;
    private Context mContext;

    public HmMainItemStarStudioAdapter(RollPagerView viewPager, List<ItemHmMainStarStudio.DataBean> banner, Context context) {
        super(viewPager);
        mBanner = banner;
        mContext = context;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(mContext);
        Glide.with(mContext).load(NetConfig.BASE_NEW_URL + mBanner.get(position).pictureUrl).into(view);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getRealCount() {
        return mBanner == null ? 0 : mBanner.size();
    }

}
