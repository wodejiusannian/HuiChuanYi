package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.PrivateRecommendModel;

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
public class PrivateRecommendHolder extends BaseViewHolder<PrivateRecommendModel> {

    public PrivateRecommendHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(PrivateRecommendModel model, int position, final MultiTypeAdapter adapter) {
        ImageView cloPic = (ImageView) getView(R.id.image_view);
        RelativeLayout rl = (RelativeLayout) getView(R.id.rl_test);
        View.OnClickListener onClickListener = adapter.getmOnclick();
        if (onClickListener != null) {
            rl.setTag(position);
            rl.setOnClickListener(onClickListener);
        }
        TextView name = (TextView) getView(R.id.text_view);
        TextView content = (TextView) getView(R.id.text_content);
        content.setText(model.getReason());
        Context context = cloPic.getContext();
        name.setText(model.getClothes_name());

        Glide.with(context).load(model.getClothes_get()).into(cloPic);
    }
}
