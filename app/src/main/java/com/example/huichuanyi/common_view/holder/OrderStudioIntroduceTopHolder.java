package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.OrderStudioIntroduceAdapter;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.OrderStudioIntroduceTopModel;
import com.example.huichuanyi.custom.GlideCircleTransform;
import com.jude.rollviewpager.RollPagerView;

import java.util.List;

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
public class OrderStudioIntroduceTopHolder extends BaseViewHolder<OrderStudioIntroduceTopModel> {

    public OrderStudioIntroduceTopHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(OrderStudioIntroduceTopModel model, int position, final MultiTypeAdapter adapter) {
        RollPagerView bg = (RollPagerView) getView(R.id.iv_order_studio_introduce_bg);
        ImageView logo = (ImageView) getView(R.id.iv_order_studio_introduce_logo);
        TextView name = (TextView) getView(R.id.tv_order_studio_introduce_name);
        TextView introduce = (TextView) getView(R.id.tv_order_studio_introduce_introduce);
        TextView realName = (TextView) getView(R.id.tv_order_studio_introduce_real_name);
        LinearLayout flag = (LinearLayout) getView(R.id.ll_order_studio_introduce_flag);
        Context context = bg.getContext();
        OrderStudioIntroduceAdapter ada = new OrderStudioIntroduceAdapter(bg, model.picList, context);
        bg.setAdapter(ada);
        bg.setPlayDelay(4000);
        flag.removeAllViews();
        List<String> txtList = model.txtList;
        for (int i = 0; i < txtList.size(); i++) {
            TextView textView = new TextView(context);
            textView.setBackgroundResource(R.drawable.shape_order_introduce_star_bg);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(11, 0, 0, 0);
            textView.setLayoutParams(lp);
            textView.setText(txtList.get(i));
            textView.setTextColor(Color.parseColor("#FF0707"));
            flag.addView(textView);
        }
        Glide.with(context).load(model.studioLogo).transform(new GlideCircleTransform(context)).error(R.mipmap.hm_stand_cicle).into(logo);
        name.setText(model.studioName);
        introduce.setText(model.studioIntroduce);
        realName.setText(model.studioRealName);
    }
}
