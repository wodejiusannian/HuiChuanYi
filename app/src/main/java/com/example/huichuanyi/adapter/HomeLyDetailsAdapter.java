package com.example.huichuanyi.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.huichuanyi.common_view.model.LyBanner;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;


public class HomeLyDetailsAdapter extends LoopPagerAdapter {

    private List<LyBanner.item_1> mBanner;
    private Context mContext;

    public HomeLyDetailsAdapter(RollPagerView viewPager, List<LyBanner.item_1> banner, Context context) {
        super(viewPager);
        mBanner = banner;
        mContext = context;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        final ImageView view = new ImageView(mContext);
        final LyBanner.item_1 item_1 = mBanner.get(position);
        Picasso.with(mContext).load(item_1.getPic_url()).into(view);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getRealCount() {
        return mBanner == null ? 0 : mBanner.size();
    }

}
