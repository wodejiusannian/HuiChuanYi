package com.example.huichuanyi.common_view.holder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.LyCommendPeople;

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
public class LyCommendPeopleHolder extends BaseViewHolder<LyCommendPeople.BodyBean> {

    public LyCommendPeopleHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final LyCommendPeople.BodyBean model, int position, MultiTypeAdapter adapter) {
        RelativeLayout root = (RelativeLayout) getView(R.id.ll_item_lycommendpeople_root);
        final Context context = root.getContext();
        ImageView photo = (ImageView) getView(R.id.iv_item_lycommendpeople_photo);
        TextView name = (TextView) getView(R.id.tv_item_lycommendpeople_name);
        TextView introduce = (TextView) getView(R.id.tv_item_lycommendpeople_introduce);
        Glide.with(context).load(model.getPic_url()).error(R.mipmap.stand).into(photo);
        name.setText(model.getName_gzs());
        introduce.setText(model.getName_fzr());
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity con = (Activity) context;
                Intent intent = new Intent();
                intent.putExtra("id", model);
                con.setResult(100, intent);
                con.finish();
            }
        });
    }
}
