package com.example.huichuanyi.common_view.holder;

import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ItemHmShopCarRecommend;

/**
 * Created by 小五 on 2018/6/27.
 */

public class HmShopCarRecommendHolder extends BaseViewHolder<ItemHmShopCarRecommend> {
    public HmShopCarRecommendHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(ItemHmShopCarRecommend model, int position, MultiTypeAdapter adapter) {
        TextView more = (TextView) getView(R.id.tv_more);
        more.setTag(position);
        more.setOnClickListener(adapter.getmOnclick());
    }
}
