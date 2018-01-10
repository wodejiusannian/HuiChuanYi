package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.HistotyZhenDuan;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.custom.RoundImageView;
import com.example.huichuanyi.ui.activity.ZhenDuanActivity;

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
public class HistoryHolder extends BaseViewHolder<HistotyZhenDuan> {

    public HistoryHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final HistotyZhenDuan model, int position, MultiTypeAdapter adapter) {
        RelativeLayout history = (RelativeLayout) getView(R.id.rl_history_zhenduan);
        TextView date = (TextView) getView(R.id.tv_history_date);
        TextView count = (TextView) getView(R.id.tv_history_position);
        RoundImageView iv = (RoundImageView) getView(R.id.rv_history_pic);
        date.setText(model.getTime());
        position = position + 1;
        count.setText("第" + position + "次");
        final Context context = date.getContext();
        Glide.with(context).load(model.getPicUrl()).into(iv);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, ZhenDuanActivity.class);
                Intent intent = in.putExtra("id", model.getId());
                context.startActivity(in);
            }
        });
    }
}
