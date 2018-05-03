package com.example.huichuanyi.common_view.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ShopCarType0Model;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class ShopCarSelfType0Holder extends BaseViewHolder<ShopCarType0Model> {

    public ShopCarSelfType0Holder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(ShopCarType0Model model, int position, MultiTypeAdapter adapter) {
        ImageView ivSelect = (ImageView) getView(R.id.iv_shopcar_allselct);
        TextView manager = (TextView) getView(R.id.tv_shocarself_manager);
        manager.setText(model.strManager);
        ivSelect.setTag(position);
        manager.setTag(position);
        if (model.isCheck) {
            ivSelect.setImageResource(R.mipmap.hm_shopcar_select);
        } else {
            ivSelect.setImageResource(R.mipmap.hm_shopcar_noselect);
        }
        manager.setOnClickListener(adapter.getmOnclick());
        ivSelect.setOnClickListener(adapter.getmOnclick());
    }
}