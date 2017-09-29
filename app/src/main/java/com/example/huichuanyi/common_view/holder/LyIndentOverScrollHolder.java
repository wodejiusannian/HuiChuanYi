package com.example.huichuanyi.common_view.holder;

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

public class LyIndentOverScrollHolder extends BaseViewHolder<LyListIndentScroll>  {

    public LyIndentOverScrollHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(LyListIndentScroll model, int position, MultiTypeAdapter adapter) {
        LinearLayout view = (LinearLayout) getView(R.id.ll_item_indentscroll_pics);
        TextView moneys = (TextView) getView(R.id.tv_item_indentscroll_moneys);
        TextView counts = (TextView) getView(R.id.tv_item_indentscroll_counts);
        final ImageView gopay = (ImageView) getView(R.id.iv_item_indentscroll_quzhifu);
        gopay.setTag(position);
        gopay.setOnClickListener(adapter.getmOnclick());
        moneys.setText("共计¥" + model.getMoney_pay());
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
