package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.SlwTwoModel;
import com.example.huichuanyi.ui.SlwGoActivity;

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
public class SlwTwoHolder extends BaseViewHolder<SlwTwoModel> {

    public SlwTwoHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final SlwTwoModel model, int position, MultiTypeAdapter adapter) {
        ImageView pagerView = (ImageView) getView(R.id.roll_item_365_two_banner);
        final Context context = pagerView.getContext();
        Glide.with(context).load(model.problemPic).into(pagerView);
        pagerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(context, SlwGoActivity.class);
                inte.putExtra("title", model.urlTitle);
                inte.putExtra("picOnclickUrl", model.picOnclickUrl);
                context.startActivity(inte);
            }
        });
    }
}
