package com.example.huichuanyi.common_view.holder;

import android.graphics.Color;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.HomeAdapter;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ShopCarTopModel;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class ShopCarSelfTopHolder extends BaseViewHolder<ShopCarTopModel> {

    public ShopCarSelfTopHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(ShopCarTopModel model, int position, MultiTypeAdapter adapter) {
        RollPagerView pager = (RollPagerView) getView(R.id.rv_item_shopcar_ad);
        HomeAdapter adapter1 = new HomeAdapter(pager, model.mData, pager.getContext());
        pager.setAdapter(adapter1);
        if (model.mData.size() == 1) {
            pager.setHintView(new ColorPointHintView(pager.getContext(), Color.parseColor("#00ac0000"), Color.parseColor("#00ac0000")));
        } else {
            pager.setHintView(new ColorPointHintView(pager.getContext(), Color.parseColor("#ac0000"), Color.WHITE));
            pager.setPlayDelay(4000);
        }
    }
}