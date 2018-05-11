package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.HomeAdapter;
import com.example.huichuanyi.bean.Banner;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ShopCarTopModel;
import com.example.huichuanyi.ui.activity.HMWebActivity;
import com.jude.rollviewpager.OnItemClickListener;
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
    public void setUpView(final ShopCarTopModel model, int position, MultiTypeAdapter adapter) {
        final RollPagerView pager = (RollPagerView) getView(R.id.rv_item_shopcar_ad);
        HomeAdapter adapter1 = new HomeAdapter(pager, model.mData, pager.getContext());
        pager.setAdapter(adapter1);
        if (model.mData.size() == 1) {
            pager.setHintView(new ColorPointHintView(pager.getContext(), Color.parseColor("#00ac0000"), Color.parseColor("#00ac0000")));
        } else {
            pager.setHintView(new ColorPointHintView(pager.getContext(), Color.parseColor("#ac0000"), Color.WHITE));
            pager.setPlayDelay(4000);
        }

        pager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Banner banner = model.mData.get(position);
                String type = banner.getType();
                switch (type) {
                    case "1":
                        Context context = pager.getContext();
                        Intent intent = new Intent(context, HMWebActivity.class);
                        String web_url = banner.getWeb_url();
                        String share_name = banner.getShare_name();
                        String share_url = banner.getShare_url();
                        intent.putExtra("hm_adpage_webview_url", web_url);
                        intent.putExtra("hm_activity_name", share_name);
                        intent.putExtra("hm_adpage_share_url", share_url);
                        context.startActivity(intent);
                        break;
                }
            }
        });
    }
}