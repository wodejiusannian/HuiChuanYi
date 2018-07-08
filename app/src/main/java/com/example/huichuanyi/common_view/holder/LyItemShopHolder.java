package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.LyItemShop;
import com.example.huichuanyi.ui.activity.lanyang.LyShopDetailsActivity;

import java.util.List;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class LyItemShopHolder extends BaseViewHolder<LyItemShop> {

    public LyItemShopHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final LyItemShop model, final int position, MultiTypeAdapter adapter) {
        final ImageView te = (ImageView) getView(R.id.tv_item_shop_test);
        List<LyItemShop.item_2> banner = model.getBanner();
        for (LyItemShop.item_2 item_2 : banner) {
            Glide.with(te.getContext()).load(item_2.getPic_url()).into(te);
        }
        te.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = te.getContext();
                Intent intent = new Intent(context, LyShopDetailsActivity.class);
                String id = model.getBanner().get(0).getId();
                intent.putExtra("goods_id", id);
                context.startActivity(intent);
            }
        });
    }
}
