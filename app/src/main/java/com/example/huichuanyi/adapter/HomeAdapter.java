package com.example.huichuanyi.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.Banner;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.List;


public class HomeAdapter extends LoopPagerAdapter {

    private List<Banner> mBanner;
    private Context mContext;

    public HomeAdapter(RollPagerView viewPager, List<Banner> banner, Context context) {
        super(viewPager);
        mBanner = banner;
        mContext = context;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.vp_home_open, null);
        ImageView open = (ImageView) view.findViewById(R.id.iv_vphome_open);
        TextView weather = (TextView) view.findViewById(R.id.tv_vphome_weather);
        weather.setText(mBanner.get(position).getWeather());
        Glide.with(mContext).load(mBanner.get(position).getPic_url()).into(open);
        open.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getRealCount() {
        return mBanner == null ? 0 : mBanner.size();
    }

}
