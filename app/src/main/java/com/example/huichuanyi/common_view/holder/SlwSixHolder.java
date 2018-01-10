package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.SlwSixModel;
import com.example.huichuanyi.custom.RoundImageView;
import com.example.huichuanyi.ui.activity.PrivateManagerActivity;
import com.example.huichuanyi.utils.RxBus;

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
public class SlwSixHolder extends BaseViewHolder<SlwSixModel> {

    public SlwSixHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(SlwSixModel model, int position, MultiTypeAdapter adapter) {
        RelativeLayout recommend = (RelativeLayout) getView(R.id.rl_item_365_six_recommend);
        final Context context = recommend.getContext();
        TextView title = (TextView) getView(R.id.tv_item_365_six_title);
        RoundImageView photo = (RoundImageView) getView(R.id.iv_item_365_six_photo);
        TextView reason = (TextView) getView(R.id.tv_item_365_six_content);
        TextView count = (TextView) getView(R.id.tv_item_365_three_recommend);
        count.setText("您已经享受" + model.rec_totalNum + "次服饰推荐");
        reason.setText(model.rec_reason);
        Glide.with(context).load(model.rec_cloPic).into(photo);
        title.setText("商品名称：" + model.rec_cloName);
        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getDefault().post(10000);
            }
        });
    }
}
