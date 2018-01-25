package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.Location;
import com.example.huichuanyi.bean.City;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.custom.GlideCircleTransform;
import com.example.huichuanyi.ui.activity.ManageActivity;
import com.example.huichuanyi.utils.CommonUtils;

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
public class OrderStudioTwoHolder extends BaseViewHolder<City.BodyBean> {

    public OrderStudioTwoHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final City.BodyBean model, int position, MultiTypeAdapter adapter) {
        RelativeLayout rl = (RelativeLayout) getView(R.id.rl_item_order_studio);
        ImageView logo = (ImageView) getView(R.id.round_item_order_studio_logo);
        ImageView level_small = (ImageView) getView(R.id.iv_item_order_studio_level_small);
        ImageView level_big = (ImageView) getView(R.id.iv_item_order_studio_level_big);
        TextView name = (TextView) getView(R.id.tv_item_order_studio_name);
        TextView area = (TextView) getView(R.id.tv_item_order_studio_area);
        TextView produce = (TextView) getView(R.id.tv_item_order_studio_produce);
        TextView distance = (TextView) getView(R.id.tv_item_order_studio_distance);
        TextView ranking = (TextView) getView(R.id.tv_item_order_studio_ranking);
        TextView score = (TextView) getView(R.id.tv_item_order_studio_score);
        final Context context = rl.getContext();
        Glide.with(context).load(model.getPhoto_get()).error(R.mipmap.hm_stand_cicle).transform(new GlideCircleTransform(context)).into(logo);
        String pf1 = model.getPf();
        double v1 = Double.parseDouble(pf1);
        String jl2 = String.format("%.1f", v1);
        score.setText(jl2 + "颗星");
        name.setText(model.getName());
        area.setText(model.getArea());
        String introduction = model.getIntroduction();
        if (CommonUtils.isEmpty(introduction)) {
            produce.setText("暂无简介");
        } else {
            produce.setText(introduction);
        }
        String jl = model.getJl();
        double v = Double.parseDouble(jl);
        String jll = String.format("%.1f", v);
        distance.setText(jll);
        ranking.setText(model.getXl() + "");
        String grade = model.getGrade();
        if ("1".equals(grade)) {
            level_small.setImageResource(R.mipmap.hm_studio_trainee_small);
            level_big.setImageResource(R.mipmap.hm_studio_trainee_big);
        } else if ("2".equals(grade)) {
            level_small.setImageResource(R.mipmap.hm_studio_authentication_small);
            level_big.setImageResource(R.mipmap.hm_studio_authentication_big);
        } else if ("3".equals(grade)) {
            level_small.setImageResource(R.mipmap.hm_studio_senior_small);
            level_big.setImageResource(R.mipmap.hm_studio_senior_big);
        } else {
            level_small.setImageResource(R.mipmap.hm_studio_high_senior_small);
            level_big.setImageResource(R.mipmap.hm_studio_high_senior_big);
        }
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ManageActivity.class);
                intent.putExtra("bodyBean", model);
                Location.mOrder_365 = "567";
                context.startActivity(intent);
            }
        });
    }
}
