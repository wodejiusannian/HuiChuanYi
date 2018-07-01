package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ItemHmShopCarShops2;

/**
 * Created by 小五 on 2018/6/26.
 */

public class HmShopCarShops2Holder extends BaseViewHolder<ItemHmShopCarShops2> {

    public HmShopCarShops2Holder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(ItemHmShopCarShops2 model, int position, MultiTypeAdapter adapter) {
        LinearLayout edit = (LinearLayout) getView(R.id.ll_shopcartype1_count);
        ImageView select = (ImageView) getView(R.id.iv_shopcarshops2_select);
        TextView delete = (TextView) getView(R.id.tv_shocarself_delete);
        TextView add = (TextView) getView(R.id.tv_shocarself_add);
        TextView count = (TextView) getView(R.id.tv_count);
        ImageView pic = (ImageView) getView(R.id.iv_shopcarselftype2_shoppic);
        TextView title = (TextView) getView(R.id.tv_shocarself_shoptitle);
        TextView information = (TextView) getView(R.id.tv_shocarself_shopspecification);
        TextView introduce = (TextView) getView(R.id.tv_shocarself_shopintroduce);
        TextView price = (TextView) getView(R.id.tv_shocarself_shopprice);
        Context context = edit.getContext();
        Glide.with(context).load(model.getGoodsPicture()).into(pic);
        title.setText(model.getGoodsName());
        information.setText(model.getGoodsColor());
        introduce.setText(model.getGoodsSize());
        price.setText("¥" + model.getGoodsPrice());
        count.setText(model.getCount() + "");
        select.setTag(position);
        select.setOnClickListener(adapter.getmOnclick());
        delete.setTag(position);
        delete.setOnClickListener(adapter.getmOnclick());
        add.setTag(position);
        add.setOnClickListener(adapter.getmOnclick());
        if (model.isEdit()){
            edit.setVisibility(View.VISIBLE);
        }else{
            edit.setVisibility(View.GONE);
        }
        if (model.isSelect()) {
            select.setImageResource(R.mipmap.hm_shopcar_select);
        } else {
            select.setImageResource(R.mipmap.hm_shopcar_noselect);
        }
    }
}
