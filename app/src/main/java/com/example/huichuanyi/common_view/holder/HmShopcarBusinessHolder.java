package com.example.huichuanyi.common_view.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ItemHmShopCarBusiness;

/**
 * Created by 小五 on 2018/6/26.
 */

public class HmShopCarBusinessHolder extends BaseViewHolder<ItemHmShopCarBusiness> {

    public HmShopCarBusinessHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(ItemHmShopCarBusiness model, int position, MultiTypeAdapter adapter) {
        ImageView select = (ImageView) getView(R.id.iv_shopcarbusiness_select);
        TextView name = (TextView) getView(R.id.iv_shopcarself_sellusername);
        name.setText(model.getSellerUserName());
        select.setTag(position);
        select.setOnClickListener(adapter.getmOnclick());
        if (model.isSelect()) {
            select.setImageResource(R.mipmap.hm_shopcar_select);
        } else {
            select.setImageResource(R.mipmap.hm_shopcar_noselect);
        }
    }
}
