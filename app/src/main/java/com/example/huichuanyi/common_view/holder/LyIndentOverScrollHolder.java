package com.example.huichuanyi.common_view.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.LyListIndentScroll;
import com.example.huichuanyi.utils.CommonUtils;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class LyIndentOverScrollHolder extends BaseViewHolder<LyListIndentScroll> {

    public LyIndentOverScrollHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(LyListIndentScroll model, int position, MultiTypeAdapter adapter) {
        LinearLayout view = (LinearLayout) getView(R.id.ll_item_indentscroll_pics);
        TextView moneys = (TextView) getView(R.id.tv_item_indentscroll_moneys);
        TextView counts = (TextView) getView(R.id.tv_item_indentscroll_counts);
        final TextView gopay = (TextView) getView(R.id.iv_item_indentscroll_quzhifu);
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
        counts.setText("x" + model.getNum());
        view.removeAllViews();
        String pic_url = model.getPic_url();
        if (!CommonUtils.isEmpty(pic_url)) {
            String[] split = pic_url.split(",");
            for (int i = 0; i < split.length; i++) {
                ImageView iv = new ImageView(view.getContext());
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(120, 120);
                params.setMargins(5, 5, 5, 5);
                iv.setLayoutParams(params);
                view.addView(iv);
                Glide.with(view.getContext()).load(split[i]).error(R.mipmap.stand).into(iv);
            }
        }
    }

}
