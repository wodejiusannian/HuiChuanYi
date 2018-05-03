package com.example.huichuanyi.common_view.holder;

import android.view.View;
import android.widget.RelativeLayout;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.OrderFormOrder;

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
public class OrderFormOrderHolder extends BaseViewHolder<OrderFormOrder> {
    public OrderFormOrderHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(OrderFormOrder model, int position, MultiTypeAdapter adapter) {
        RelativeLayout orderFrom = (RelativeLayout) getView(R.id.rl_orderformorder_state);
        orderFrom.setOnClickListener(adapter.getmOnclick());

    }
}
