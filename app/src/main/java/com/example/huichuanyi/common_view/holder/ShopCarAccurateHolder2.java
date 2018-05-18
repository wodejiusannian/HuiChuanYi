package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.PrivateRecommendModel2;
import com.example.huichuanyi.custom.RoundImageView;
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
public class ShopCarAccurateHolder2 extends BaseViewHolder<PrivateRecommendModel2> {

    public ShopCarAccurateHolder2(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(PrivateRecommendModel2 model, int position, final MultiTypeAdapter adapter) {
        RoundImageView cloPic = (RoundImageView) getView(R.id.image_view);
        TextView title = (TextView) getView(R.id.tv_shocaraccuratetantan_title);
        TextView color = (TextView) getView(R.id.tv_shocaraccuratetantan_color);
        TextView introduce = (TextView) getView(R.id.tv_shocaraccuratetantan_introduce);
        TextView time = (TextView) getView(R.id.tv_shocaraccuratetantan_time);
        RelativeLayout rl = (RelativeLayout) getView(R.id.rl_test);
        title.setText(model.getClothes_name());
        color.setText(model.getColor_name());
        introduce.setText(model.getReason());
        time.setText(model.getRecommend_time());
        View.OnClickListener onClickListener = adapter.getmOnclick();
        if (onClickListener != null) {
            rl.setTag(position);
            rl.setOnClickListener(onClickListener);
        }

        if (CommonUtils.isEmpty(model.getId())) {
            time.setVisibility(View.GONE);
        } else {
            time.setVisibility(View.VISIBLE);
        }
        Context context = cloPic.getContext();
        Glide.with(context).load(model.getClothes_get()).into(cloPic);
    }
}
