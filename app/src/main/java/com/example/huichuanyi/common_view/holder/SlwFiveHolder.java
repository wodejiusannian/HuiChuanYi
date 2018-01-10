package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.SlwFiveModel;
import com.example.huichuanyi.custom.GlideCircleTransform;
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
public class SlwFiveHolder extends BaseViewHolder<SlwFiveModel> {

    public SlwFiveHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(SlwFiveModel model, int position, MultiTypeAdapter adapter) {
        RelativeLayout go = (RelativeLayout) getView(R.id.rl_go);
        ImageView chat = (ImageView) getView(R.id.iv_item_365_chat_manager);
        ImageView photo = (ImageView) getView(R.id.iv_item_365_photo);
        TextView count = (TextView) getView(R.id.tv_item_365_five_count);
        TextView name = (TextView) getView(R.id.tv_item_365_five_title);
        name.setText(model.studioName);
        count.setText("已服务" + model.studioNum + "次");
        final Context context = chat.getContext();
        Glide.with(context).load(model.studioPhotoUrl).transform(new GlideCircleTransform(context)).into(photo);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getDefault().post(10086111);
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getDefault().post(10086);
            }
        });

    }
}
