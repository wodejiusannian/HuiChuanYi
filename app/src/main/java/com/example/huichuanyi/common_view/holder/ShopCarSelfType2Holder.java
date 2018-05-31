package com.example.huichuanyi.common_view.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ShopCarType2Model;
import com.example.huichuanyi.utils.CommonUtils;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class ShopCarSelfType2Holder extends BaseViewHolder<ShopCarType2Model> {

    public ShopCarSelfType2Holder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(ShopCarType2Model model, int position, MultiTypeAdapter adapter) {
        ImageView ivSelect = (ImageView) getView(R.id.iv_shopcartype2_select);
        TextView shoptitle = (TextView) getView(R.id.tv_shocarself_shoptitle);
        TextView shopspecification = (TextView) getView(R.id.tv_shocarself_shopspecification);
        TextView shopintroduce = (TextView) getView(R.id.tv_shocarself_shopintroduce);
        TextView shopprice = (TextView) getView(R.id.tv_shocarself_shopprice);
        ImageView shoppic = (ImageView) getView(R.id.iv_shopcarselftype2_shoppic);
        TextView shopcount = (TextView) getView(R.id.tv_shocarself_count);
        TextView delete = (TextView) getView(R.id.tv_shocarself_delete);
        TextView add = (TextView) getView(R.id.tv_shocarself_add);
        TextView countShow = (TextView) getView(R.id.tv_count);
        LinearLayout count = (LinearLayout) getView(R.id.ll_shopcartype1_count);
        shopcount.setText(model.orderNumber + "");
        shoptitle.setText(model.goodsName);
        shopspecification.setText(model.goodsSize);
        shopintroduce.setText(model.goodsColor);
        shopprice.setText("¥" + CommonUtils.strDoubleString(model.goodsPrice));
        Glide.with(shoppic.getContext()).load(model.goodsPicture).into(shoppic);
        ivSelect.setTag(position);
        if (model.isCheck) {
            ivSelect.setImageResource(R.mipmap.hm_shopcar_select);
        } else {
            ivSelect.setImageResource(R.mipmap.hm_shopcar_noselect);
        }
        delete.setTag(position);
        add.setTag(position);
        delete.setOnClickListener(adapter.getmOnclick());
        add.setOnClickListener(adapter.getmOnclick());
        ivSelect.setOnClickListener(adapter.getmOnclick());
        if (model.isShow && !"5".equals(model.orderType)) {
            count.setVisibility(View.VISIBLE);
            countShow.setVisibility(View.GONE);
        } else {
            count.setVisibility(View.GONE);
            countShow.setVisibility(View.VISIBLE);
            countShow.setText("x" + model.orderNumber);
        }
    }
}