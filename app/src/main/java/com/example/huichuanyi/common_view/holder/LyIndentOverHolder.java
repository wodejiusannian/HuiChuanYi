package com.example.huichuanyi.common_view.holder;

import android.text.TextUtils;
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
        TextView moneys = (TextView) getView(R.id.tv_item_listindent_moneys);
        final TextView gopay = (TextView) getView(R.id.iv_over_item_qupingjia);

        switch (model.getState()) {
            case "10":
                gopay.setText("待发货");
                moneys.setText("共计¥" + model.getMoney_pay());
                break;
            case "11":
                gopay.setText("已发货");
                moneys.setText("共计¥" + model.getMoney_pay());
                break;
            case "12":
                gopay.setText("已收货");
                moneys.setText("共计¥" + model.getMoney_pay());
                break;
            case "20":
                gopay.setText("去支付");
                moneys.setText("共计¥" + model.getMoney_one());
                break;
            case "40":
                gopay.setText("已关闭");
                moneys.setText("共计¥" + model.getMoney_pay());
                break;
            default:
                break;
        }
        if (TextUtils.equals("20", model.getState())) {
            gopay.setTag(position);
            gopay.setOnClickListener(adapter.getmOnclick());
        }
        name.setText(model.getName());
        counts.setText("x" + model.getNum());
        Glide.with(view.getContext()).load(model.getPic_url()).error(R.mipmap.stand).into(view);
    }

}
