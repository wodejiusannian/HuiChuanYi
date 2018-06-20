package com.example.huichuanyi.common_view.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ShopCarType3Model;
import com.example.huichuanyi.utils.CommonUtils;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class ShopCarSelfType3Holder extends BaseViewHolder<ShopCarType3Model> {

    public ShopCarSelfType3Holder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(ShopCarType3Model model, int position, MultiTypeAdapter adapter) {
        ImageView ivSelect = (ImageView) getView(R.id.iv_shopcartype3_select);
        ivSelect.setTag(position);
        TextView shoptitle = (TextView) getView(R.id.tv_shocarselftype3_shoptitle);
        TextView shopspecification = (TextView) getView(R.id.tv_shocarselftype3_shopspecification);
        TextView shopintroduce = (TextView) getView(R.id.tv_shocarselftype3_shopintroduce);
        TextView shopprice = (TextView) getView(R.id.tv_shocarselftype3_shopprice);
        ImageView shoppic = (ImageView) getView(R.id.iv_shopcarself_shoppic);
        TextView shopcount = (TextView) getView(R.id.tv_shocarselftype3_count);
        TextView delete = (TextView) getView(R.id.tv_shocarselftype3_delete);
        TextView add = (TextView) getView(R.id.tv_shocarselftype3_add);
        LinearLayout count = (LinearLayout) getView(R.id.ll_shopcartype3_count);
        TextView countShow = (TextView) getView(R.id.tv_count);
        if (model.isShow && !"5".equals(model.orderType)) {
            count.setVisibility(View.VISIBLE);
            countShow.setVisibility(View.GONE);
        } else {
            count.setVisibility(View.GONE);
            countShow.setVisibility(View.VISIBLE);
            countShow.setText("x" + model.orderNumber);
        }
        RelativeLayout details = (RelativeLayout) getView(R.id.rl_shopcarselftype3_detials);
        details.setTag(position);
        details.setOnClickListener(adapter.getmOnclick());
        delete.setTag(position);
        add.setTag(position);
        shopcount.setText(model.orderNumber + "");
        shoptitle.setText(model.goodsName);
        shopspecification.setText(model.goodsSize);
        shopintroduce.setText(model.goodsColor);
        shopprice.setText("¥" + CommonUtils.strDoubleString(model.goodsPrice));
        Glide.with(shoppic.getContext()).load(model.goodsPicture).into(shoppic);
        if (model.isCheck) {
            ivSelect.setImageResource(R.mipmap.hm_shopcar_select);
        } else {
            ivSelect.setImageResource(R.mipmap.hm_shopcar_noselect);
        }
        delete.setOnClickListener(adapter.getmOnclick());
        add.setOnClickListener(adapter.getmOnclick());
        ivSelect.setOnClickListener(adapter.getmOnclick());
    }
}