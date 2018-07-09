package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.LyShopList;
import com.example.huichuanyi.ui.activity.lanyang.LyShopDetailsActivity;

// ┏┓　　　┏┓
// ┏┛┻━━━┛┻┓
// ┃　　　　　　　┃ 　
// ┃　　　━　　　┃
// ┃　┳┛　┗┳　┃
// ┃　　　　　　　┃
// ┃　　　┻　　　┃
// ┃　　　　　　　┃
// ┗━┓　　　┏━┛
// ┃　　　┃ 神兽保佑　　　　　　　　
// ┃　　　┃ 代码无BUG！
// ┃　　　┗━━━┓
// ┃　　　　　　　┣┓
// ┃　　　　　　　┏┛
// ┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
// ┗┻┛　┗┻┛
public class LyListShopHolder extends BaseViewHolder<LyShopList.BodyBean> {
    public LyListShopHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final LyShopList.BodyBean model, int position, MultiTypeAdapter adapter) {
        final LinearLayout root = (LinearLayout) getView(R.id.ll_item_shop_list);
        ImageView iv = (ImageView) getView(R.id.iv_item_shoplist_pic);
        TextView name = (TextView) getView(R.id.tv_item_shoplist_name);
        TextView price = (TextView) getView(R.id.iv_item_shoplist_price);
        Glide.with(iv.getContext()).load(model.getPic_url()).into(iv);
        name.setText(model.getName());
        price.setText(model.getPrice());
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = root.getContext();
                Intent intent = new Intent(context, LyShopDetailsActivity.class);
                intent.putExtra("goods_id", model.getGoods_id()+"");
                intent.putExtra("sellerPicture", model.getPic_url());
                context.startActivity(intent);
            }
        });
    }
}
