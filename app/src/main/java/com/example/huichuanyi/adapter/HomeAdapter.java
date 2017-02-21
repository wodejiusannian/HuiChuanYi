package com.example.huichuanyi.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.huichuanyi.bean.Banner;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;


public class HomeAdapter extends LoopPagerAdapter {

    private List<Banner.ListBean> mBanner;
    private Context mContext;
    public HomeAdapter(RollPagerView viewPager,List<Banner.ListBean> banner,Context context)
    {
        super(viewPager);
        mBanner = banner;
        mContext = context;
    }

    @Override
    public View getView(ViewGroup container, int position)
    {
        ImageView view = new ImageView(container.getContext());
        Picasso.with(mContext).load(mBanner.get(position).getPhotopath()).into(view);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getRealCount()
    {
        return mBanner==null?0:mBanner.size();
    }

}
