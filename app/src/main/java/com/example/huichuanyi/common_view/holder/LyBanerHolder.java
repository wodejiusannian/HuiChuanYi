package com.example.huichuanyi.common_view.holder;

import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.HomeLyAdapter;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.LyBanner;
import com.jude.rollviewpager.RollPagerView;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class LyBanerHolder extends BaseViewHolder<LyBanner> {

    public LyBanerHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final LyBanner model, int position, MultiTypeAdapter adapter) {
        final RollPagerView banner = (RollPagerView) getView(R.id.banner_item_ly);
        HomeLyAdapter homeAdapter = new HomeLyAdapter(banner, model.getBanner(), banner.getContext());
        banner.setAdapter(homeAdapter);
    }
}
