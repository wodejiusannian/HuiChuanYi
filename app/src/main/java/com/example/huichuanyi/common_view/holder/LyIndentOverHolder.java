package com.example.huichuanyi.common_view.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.LyListIndent;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class LyIndentOverHolder extends BaseViewHolder<LyListIndent> {

    public LyIndentOverHolder(View itemView) {
        super(itemView);
    }


    @Override
    public void setUpView(final LyListIndent model, final int position, MultiTypeAdapter adapter) {
        ImageView view = (ImageView) getView(R.id.iv_listindent_pic);
        TextView name = (TextView) getView(R.id.tv_item_listindent_name);
        TextView counts = (TextView) getView(R.id.tv_item_listindent_counts);
        TextView money = (TextView) getView(R.id.tv_item_listindent_money);
        TextView moneys = (TextView) getView(R.id.tv_item_listindent_moneys);
        final ImageView gopay = (ImageView) getView(R.id.iv_over_item_qupingjia);
        gopay.setTag(position);
        gopay.setOnClickListener(adapter.getmOnclick());
        name.setText(model.getName());
        money.setText("¥" + model.getMoney_one());
        counts.setText("x" + model.getNum());
        moneys.setText("共计¥" + model.getMoney_pay());
        Glide.with(view.getContext()).load(model.getPic_url()).error(R.mipmap.stand).into(view);
    }

    public interface GetPos {
        void getpos(int p);
    }
}
