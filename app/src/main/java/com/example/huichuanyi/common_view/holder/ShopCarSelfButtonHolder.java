package com.example.huichuanyi.common_view.holder;

import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ShopCarButtonModel;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class ShopCarSelfButtonHolder extends BaseViewHolder<ShopCarButtonModel> {

    public ShopCarSelfButtonHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(ShopCarButtonModel model, int position, MultiTypeAdapter adapter) {
        TextView textView = (TextView) getView(R.id.tv_mainshopcar_accurate);
        textView.setOnClickListener(adapter.getmOnclick());
    }


}