package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ItemHmShopCarRecommendShop;

/**
 * Created by 小五 on 2018/6/27.
 */

public class HmShopCarRecommendShopHolder extends BaseViewHolder<ItemHmShopCarRecommendShop> {
    public HmShopCarRecommendShopHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(ItemHmShopCarRecommendShop model, int position, MultiTypeAdapter adapter) {
        LinearLayout recommendShop = (LinearLayout) getView(R.id.ll_recommendshop);
        ImageView recommendShopPic = (ImageView) getView(R.id.iv_recommendshoppic);
        TextView recommendShopName = (TextView) getView(R.id.tv_recommendshopname);
        TextView recommendShopReason = (TextView) getView(R.id.tv_recommendshopreason);
        TextView recommendShopPrice = (TextView) getView(R.id.tv_recommendshopprice);
        ImageView select = (ImageView) getView(R.id.iv_shopcarrecommendshop_select);
        recommendShop.setTag(position);
        recommendShop.setOnClickListener(adapter.getmOnclick());
        select.setTag(position);
        select.setOnClickListener(adapter.getmOnclick());
        Context context = recommendShop.getContext();
        Glide.with(context).load(model.getGoodsPicture()).into(recommendShopPic);
        recommendShopName.setText("服饰名：" + model.getGoodsName());
        recommendShopReason.setText("推荐理由：" + model.getRecommendReason());
        recommendShopPrice.setText("¥ " + model.getGoodsPrice());
        if (model.isBuy()) {
            select.setImageResource(R.mipmap.hm_shopcar_select);
        } else {
            select.setImageResource(R.mipmap.hm_shopcar_noselect);
        }
    }
}
