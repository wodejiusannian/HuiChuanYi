package com.example.huichuanyi.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.common_view.model.LyBanner;
import com.example.huichuanyi.ui.activity.lanyang.LyShopDetailsActivity;
import com.example.huichuanyi.utils.CommonUtils;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.List;


public class HomeLyAdapter extends LoopPagerAdapter {

    private List<LyBanner.item_1> mBanner;
    private Context mContext;

    public HomeLyAdapter(RollPagerView viewPager, List<LyBanner.item_1> banner, Context context) {
        super(viewPager);
        mBanner = banner;
        mContext = context;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        final ImageView view = new ImageView(mContext);
        final LyBanner.item_1 item_1 = mBanner.get(position);
        Glide.with(mContext).load(item_1.getPic_url()).into(view);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = view.getContext();
                Intent intent = new Intent(context, LyShopDetailsActivity.class);
                String id = item_1.getId();
                if (!CommonUtils.isEmpty(id))
                    intent.putExtra("goods_id", id);
                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public int getRealCount() {
        return mBanner == null ? 0 : mBanner.size();
    }

}
