package com.example.huichuanyi.common_view.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ItemHmShopCarKind;

/**
 * Created by 小五 on 2018/6/27.
 */

public class HmShopCarKindHolder extends BaseViewHolder<ItemHmShopCarKind> {
    public HmShopCarKindHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(ItemHmShopCarKind model, int position, MultiTypeAdapter adapter) {
        LinearLayout llUpDown = (LinearLayout) getView(R.id.ll_hmshopcarkind_updown);
        ImageView upDown = (ImageView) getView(R.id.iv_hmshopcarkind_updown);
        if (position == 0) llUpDown.setVisibility(View.VISIBLE);else llUpDown.setVisibility(View.GONE);
        if (model.isUpOrDown()) upDown.setImageResource(R.mipmap.hm_shopcar_up);else upDown.setImageResource(R.mipmap.hm_shopcar_down);
        llUpDown.setTag(position);
        llUpDown.setOnClickListener(adapter.getmOnclick());
        upDown.setTag(position);
        upDown.setOnClickListener(adapter.getmOnclick());
        TextView kind = (TextView) getView(R.id.tv_hmshopcar_kind);
        kind.setText(model.getKind());
    }
}
