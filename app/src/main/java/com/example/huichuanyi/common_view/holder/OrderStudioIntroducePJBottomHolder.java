package com.example.huichuanyi.common_view.holder;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.OrderStudioIntroducePJBottomModel;

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
public class OrderStudioIntroducePJBottomHolder extends BaseViewHolder<OrderStudioIntroducePJBottomModel> {

    public OrderStudioIntroducePJBottomHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(OrderStudioIntroducePJBottomModel model, int position, final MultiTypeAdapter adapter) {
        TextView tip = (TextView) getView(R.id.tv_order_studio_introduce_pj_top_tip);
        TextView name = (TextView) getView(R.id.tv_order_studio_introduce_pj_top_name);
        TextView content = (TextView) getView(R.id.rl_order_studio_introduce_pj_top_content);
        TextView date = (TextView) getView(R.id.tv_order_studio_introduce_pj_top_date);
        TextView count = (TextView) getView(R.id.tv_order_studio_introduce_pj_top_count);
        RatingBar ratingBar = (RatingBar) getView(R.id.rat_order_studio_introduce_pj_top_star);
        double star1 = Double.parseDouble(model.stars1);
        double star2 = Double.parseDouble(model.stars2);
        double star3 = Double.parseDouble(model.stars3);
        double v = (star1 + star2 + star3) / 3;
        ratingBar.setRating((float) v);
        tip.setText(model.user_name);
        String order_name = model.order_name;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < order_name.length(); i++) {
            if (i == 0)
                buffer.append(order_name.charAt(i));
            else
                buffer.append("*");
        }
        name.setText(buffer.toString());
        content.setText(model.content);
        date.setText(model.time);
        count.setText(model.number);
    }
}
