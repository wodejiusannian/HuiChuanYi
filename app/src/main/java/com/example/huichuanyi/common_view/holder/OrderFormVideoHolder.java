package com.example.huichuanyi.common_view.holder;

import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.OrderFormVideo;

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
public class OrderFormVideoHolder extends BaseViewHolder<OrderFormVideo.BodyBean> {
    public OrderFormVideoHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(OrderFormVideo.BodyBean model, int position, MultiTypeAdapter adapter) {

        TextView id = (TextView) getView(R.id.tv_orderformvideo_id);
        TextView title = (TextView) getView(R.id.tv_orderformvideo_title);
        TextView realMoney = (TextView) getView(R.id.tv_orderformvideo_realmoney);
        TextView conpon = (TextView) getView(R.id.tv_orderformvideo_conpon);
        TextView name = (TextView) getView(R.id.tv_orderformvideo_name);
        TextView time = (TextView) getView(R.id.tv_orderformvideo_time);
        TextView button = (TextView) getView(R.id.tv_orderformvideo_button3);
        button.setTag(position);
        button.setOnClickListener(adapter.getmOnclick());
        id.setText("订单编号：" + model.getOrderId());
        OrderFormVideo.BodyBean.OrderInfoBean infoBean = model.getOrderInfo().get(0);
        title.setText(infoBean.getGoodsName());
        realMoney.setText("¥" + infoBean.getMoneyPay());
        conpon.setText("已优惠" + (Double.parseDouble(infoBean.getMoneyTotal()) - Double.parseDouble(infoBean.getMoneyPay())) + "元");
        name.setText(infoBean.getGoodsIntroduction());
        time.setText(infoBean.getPayTime());
    }
}
