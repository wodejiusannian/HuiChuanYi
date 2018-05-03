package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.PrivateRecommendModel;
import com.example.huichuanyi.custom.RoundImageView;

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
public class ShopCarAccurateHolder extends BaseViewHolder<PrivateRecommendModel> {

    public ShopCarAccurateHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(PrivateRecommendModel model, int position, final MultiTypeAdapter adapter) {
        RoundImageView cloPic = (RoundImageView) getView(R.id.image_view);
        RelativeLayout rl = (RelativeLayout) getView(R.id.rl_test);
        View.OnClickListener onClickListener = adapter.getmOnclick();
        if (onClickListener != null) {
            rl.setTag(position);
            rl.setOnClickListener(onClickListener);
        }
        Context context = cloPic.getContext();
        Glide.with(context).load(model.getClothes_get()).into(cloPic);
    }
}
