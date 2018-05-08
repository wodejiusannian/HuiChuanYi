package com.example.huichuanyi.common_view.holder;

import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
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
public class OrderFormSLWHolder extends BaseViewHolder<OrderFormSLW.BodyBean> {
    public OrderFormSLWHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(OrderFormSLW.BodyBean model, int position, MultiTypeAdapter adapter) {
        RelativeLayout orderFrom = (RelativeLayout) getView(R.id.rl_orderformslw_state);
        HorizontalScrollView scrollView = (HorizontalScrollView) getView(R.id.hs_ofderformslw_show);
        RelativeLayout relativeLayout = (RelativeLayout) getView(R.id.rl_orderfromslw_show);
        LinearLayout linearLayout = (LinearLayout) getView(R.id.ll_ofderformslw_show);
        if (model.getOrderInfo().size() > 1) {
            scrollView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        } else {
            scrollView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
        }
        orderFrom.setOnClickListener(adapter.getmOnclick());
    }
}
