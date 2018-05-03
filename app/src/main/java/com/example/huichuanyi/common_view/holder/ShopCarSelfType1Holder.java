package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ShopCarType1Model;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class ShopCarSelfType1Holder extends BaseViewHolder<ShopCarType1Model> {

    public ShopCarSelfType1Holder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(ShopCarType1Model model, int position, MultiTypeAdapter adapter) {
        ImageView ivSelect = (ImageView) getView(R.id.iv_shopcartype1_select);
        ivSelect.setTag(position);
        ImageView selluserpic = (ImageView) getView(R.id.iv_shopcarself_selluserpic);
        Context context = selluserpic.getContext();
        Glide.with(context).load(model.sellerPicture).into(selluserpic);
        if (model.isCheck){
            ivSelect.setImageResource(R.mipmap.hm_shopcar_select);
        }else{
            ivSelect.setImageResource(R.mipmap.hm_shopcar_noselect);
        }
        ivSelect.setOnClickListener(adapter.getmOnclick());
    }
}