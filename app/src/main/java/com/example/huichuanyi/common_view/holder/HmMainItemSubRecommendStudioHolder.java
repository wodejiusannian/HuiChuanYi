package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ItemHmMainRecommendStudio;
import com.example.huichuanyi.custom.GlideCircleTransform;

/**
 * Created by 小五 on 2018/6/26.
 */

public class HmMainItemSubRecommendStudioHolder extends BaseViewHolder<ItemHmMainRecommendStudio.DataBean> {

    public HmMainItemSubRecommendStudioHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(ItemHmMainRecommendStudio.DataBean model, int position, MultiTypeAdapter adapter) {
        TextView level = (TextView) getView(R.id.tv_substudio_level);
        TextView name = (TextView) getView(R.id.iv_substudio_name);
        ImageView logo = (ImageView) getView(R.id.iv_substudio_logo);
        Context context = level.getContext();
        level.setText(model.appExtensionIntro);
        name.setText(model.name);
        Glide.with(context).load(model.headPicture).bitmapTransform(new GlideCircleTransform(context)).into(logo);
    }
}
