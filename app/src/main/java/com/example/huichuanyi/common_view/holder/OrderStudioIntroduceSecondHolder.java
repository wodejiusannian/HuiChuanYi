package com.example.huichuanyi.common_view.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.OrderStudioIntroduceSecondModel;

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
public class OrderStudioIntroduceSecondHolder extends BaseViewHolder<OrderStudioIntroduceSecondModel> {

    public OrderStudioIntroduceSecondHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(OrderStudioIntroduceSecondModel model, int position, final MultiTypeAdapter adapter) {
        LinearLayout second = (LinearLayout) getView(R.id.rl_order_studio_introduce_second);
        second.setTag(position);
        TextView text = (TextView) getView(R.id.tv_order_studio_introduce_second);
        text.setText(model.text);
        second.setOnClickListener(adapter.getmOnclick());
    }
}
