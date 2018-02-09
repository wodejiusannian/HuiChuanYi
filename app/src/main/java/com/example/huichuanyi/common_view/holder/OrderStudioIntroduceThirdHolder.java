package com.example.huichuanyi.common_view.holder;

import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.OrderStudioIntroduceThirdModel;

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
public class OrderStudioIntroduceThirdHolder extends BaseViewHolder<OrderStudioIntroduceThirdModel> {

    public OrderStudioIntroduceThirdHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(OrderStudioIntroduceThirdModel model, int position, final MultiTypeAdapter adapter) {
        TextView pj = (TextView) getView(R.id.tv_order_studio_introduce_pj);
        TextView count = (TextView) getView(R.id.tv_order_studio_introduce_365);
        TextView orderer = (TextView) getView(R.id.tv_order_studio_introduce_orderer);
        pj.setText(model.pj);
        count.setText(model.count);
        orderer.setText(model.orderer);
    }
}
