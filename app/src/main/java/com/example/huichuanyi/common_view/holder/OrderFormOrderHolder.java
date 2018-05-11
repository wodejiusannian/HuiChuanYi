package com.example.huichuanyi.common_view.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.OrderFormOrder;
import com.example.huichuanyi.custom.GlideCircleTransform;

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
public class OrderFormOrderHolder extends BaseViewHolder<OrderFormOrder.BodyBean> {
    public OrderFormOrderHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(OrderFormOrder.BodyBean model, int position, MultiTypeAdapter adapter) {
        RelativeLayout orderFrom = (RelativeLayout) getView(R.id.rl_orderformorder_state);
        ImageView photo = (ImageView) getView(R.id.iv_orderformstate_photo);
        TextView id = (TextView) getView(R.id.tv_orderformstate_id);
        TextView state = (TextView) getView(R.id.tv_orderformstate_state);
        TextView servicetype = (TextView) getView(R.id.tv_orderformstate_servicetype);
        TextView studioname = (TextView) getView(R.id.tv_orderformstate_studioname);
        TextView price = (TextView) getView(R.id.tv_orderformstate_price);
        TextView coupon = (TextView) getView(R.id.tv_orderformstate_coupon);
        TextView time = (TextView) getView(R.id.tv_orderformstate_time);
        TextView address = (TextView) getView(R.id.tv_orderformstate_address);
        TextView btn1 = (TextView) getView(R.id.tv_orderformstate_button1);
        TextView btn2 = (TextView) getView(R.id.tv_orderformstate_button2);
        TextView btn3 = (TextView) getView(R.id.tv_orderformstate_button3);
        btn1.setText("申请退款");
        btn2.setText("补差价");
        OrderFormOrder.BodyBean.OrderInfoBean infoBean = model.getOrderInfo().get(0);
        String type = infoBean.getDeleteStatus();
        switch (type) {
            case "0":
                btn1.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.GONE);
                btn3.setVisibility(View.VISIBLE);
                btn3.setText("待确认");
                state.setText("进行中");
                break;
            case "1":
                btn1.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.VISIBLE);
                btn3.setVisibility(View.VISIBLE);
                btn3.setText("确认完成");
                state.setText("进行中");
                break;
            case "2":
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);
                btn3.setVisibility(View.VISIBLE);
                if ("0".equals(infoBean.getEvaluateState())) {
                    btn3.setText("再来一单");
                    state.setText("订单已完成");
                } else {
                    btn3.setText("去评价");
                    state.setText("");
                }
                break;
            case "14":
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);
                btn3.setVisibility(View.VISIBLE);
                btn3.setText("待确认");
                state.setText("进行中");
                break;
            case "3":
            case "4":
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);
                btn3.setVisibility(View.VISIBLE);
                btn3.setText("审核中");
                state.setText("审核中");
                break;
            case "5":
            case "6":
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);
                btn3.setVisibility(View.VISIBLE);
                btn3.setText("再来一单");
                state.setText("订单取消");
                break;
            default:
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);
                btn3.setVisibility(View.VISIBLE);
                btn3.setText("再来一单");
                state.setText("订单取消");
                break;
        }
        id.setText("订单编号:" + model.getOrderId());
        OrderFormOrder.BodyBean.OrderInfoBean bean = model.getOrderInfo().get(0);
        studioname.setText(bean.getSellerUserName());
        price.setText(bean.getMoneyPay());
        String moneyDiscount = model.getMoneyDiscount();
        Double douMoneyDiscount = Double.parseDouble(moneyDiscount);
        if (douMoneyDiscount > 0) {
            coupon.setText("已优惠" + moneyDiscount);
        } else {
            coupon.setText("未使用优惠券及折扣");
        }
        time.setText(bean.getCompleteTime());
        address.setText(bean.getConsigneeAddress());
        String orderType = bean.getOrderType();
        if ("1".equals(orderType)) {
            servicetype.setText("上门除螨管理服务");
        } else {
            servicetype.setText("上门衣橱管理服务");
        }
        Glide.with(orderFrom.getContext()).load(bean.getGoodsPicture()).error(R.mipmap.hm_stand_cicle).transform(new GlideCircleTransform(orderFrom.getContext())).into(photo);
        orderFrom.setTag(position);
        btn1.setTag(position);
        btn1.setOnClickListener(adapter.getmOnclick());
        btn2.setTag(position);
        btn2.setOnClickListener(adapter.getmOnclick());
        btn3.setTag(position);
        btn3.setOnClickListener(adapter.getmOnclick());
        orderFrom.setOnClickListener(adapter.getmOnclick());
    }
}
