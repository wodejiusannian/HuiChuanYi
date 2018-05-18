package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.OrderFormOrder;
import com.example.huichuanyi.custom.GlideCircleTransform;
import com.example.huichuanyi.custom.GlideRoundTransform;
import com.example.huichuanyi.utils.CommonUtils;

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
        HorizontalScrollView scrollView = (HorizontalScrollView) getView(R.id.hs_ofderformorder_show);
        RelativeLayout relativeLayout = (RelativeLayout) getView(R.id.rl_orderfromorder_show);
        LinearLayout linearLayout = (LinearLayout) getView(R.id.ll_ofderformorder_show);
        TextView servicetype = (TextView) getView(R.id.tv_orderformstate_servicetype);
        TextView studioname = (TextView) getView(R.id.tv_orderformstate_studioname);
        TextView price = (TextView) getView(R.id.tv_orderformstate_price);
        TextView coupon = (TextView) getView(R.id.tv_orderformstate_coupon);
        TextView time = (TextView) getView(R.id.tv_orderformstate_time);
        TextView address = (TextView) getView(R.id.tv_orderformstate_address);
        TextView btn1 = (TextView) getView(R.id.tv_orderformstate_button1);
        TextView btn2 = (TextView) getView(R.id.tv_orderformstate_button2);
        TextView btn3 = (TextView) getView(R.id.tv_orderformstate_button3);
        ImageView iv = (ImageView) getView(R.id.iv_orderformstate_photo);
        btn1.setText("申请退款");
        btn2.setText("补差价");
        OrderFormOrder.BodyBean.OrderInfoBean infoBean = model.getOrderInfo().get(0);
        String type = infoBean.getDeleteStatus();
        Context context = orderFrom.getContext();
        Glide.with(context).load(infoBean.getGoodsPicture()).error(R.mipmap.stand).transform(new GlideCircleTransform(context)).into(iv);
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
        if (!CommonUtils.isEmpty(moneyDiscount)) {
            Double douMoneyDiscount = Double.parseDouble(moneyDiscount);
            if (douMoneyDiscount > 0) {
                coupon.setText("优惠" + moneyDiscount);
            } else {
                coupon.setText("未使用优惠券及折扣");
            }
        } else {
            coupon.setText("未使用优惠券及折扣");
        }
        time.setText("预约时间：" + bean.getConsigneeTime());
        address.setText("服务地址："+bean.getConsigneeAddress());
        String orderType = bean.getOrderType();
        if ("1".equals(orderType)) {
            servicetype.setText("上门除螨管理服务");
        } else {
            servicetype.setText("上门衣橱管理服务");
        }

        if (model.getOrderInfo().size() > 1) {
            scrollView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
            linearLayout.removeAllViews();
            List<OrderFormOrder.BodyBean.OrderInfoBean> list = model.getOrderInfo();
            for (int i = 0; i < list.size(); i++) {
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(180, 180));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                p.setMargins(5, 5, 5, 5);
                imageView.requestLayout();
                Glide.with(context).load(list.get(i).getGoodsPicture()).error(R.mipmap.stand).transform(new GlideRoundTransform(context)).into(imageView);
                linearLayout.addView(imageView);
            }
        } else {
            scrollView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
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
