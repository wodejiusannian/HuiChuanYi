package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.LyMain;

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
public class LyMainHolder extends BaseViewHolder<LyMain.BodyBean> {

    public LyMainHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final LyMain.BodyBean model, int position, final MultiTypeAdapter adapter) {
        ImageView view = (ImageView) getView(R.id.iv_item_ly_main_sort);
        RelativeLayout test = (RelativeLayout) getView(R.id.rl_test);
        Context context = view.getContext();
        Glide.with(context).load(model.getPic_url()).into(view);
        test.setTag(position);
        test.setOnClickListener(adapter.getmOnclick());
    }
}
