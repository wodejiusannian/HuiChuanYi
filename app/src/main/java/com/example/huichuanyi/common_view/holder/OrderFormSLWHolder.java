package com.example.huichuanyi.common_view.holder;

import android.view.View;
import android.widget.RelativeLayout;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.OrderFormSLW;

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
public class OrderFormSLWHolder extends BaseViewHolder<OrderFormSLW> {
    public OrderFormSLWHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(OrderFormSLW model, int position, MultiTypeAdapter adapter) {
        RelativeLayout orderFrom = (RelativeLayout) getView(R.id.rl_orderformslw_state);
        orderFrom.setOnClickListener(adapter.getmOnclick());
    }
}
