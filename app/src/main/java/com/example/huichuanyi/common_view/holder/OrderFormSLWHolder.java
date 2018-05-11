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
import com.example.huichuanyi.common_view.model.OrderFormSLW;
import com.example.huichuanyi.custom.GlideRoundTransform;

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
        Context context = orderFrom.getContext();
        TextView id = (TextView) getView(R.id.tv_orderformslw_id);
        TextView state = (TextView) getView(R.id.tv_orderformslw_state);
        TextView servicetype = (TextView) getView(R.id.tv_orderformslw_servicetype);
        TextView size = (TextView) getView(R.id.tv_orderformslw_size);
        TextView color = (TextView) getView(R.id.tv_orderformslw_color);
        TextView price = (TextView) getView(R.id.tv_orderformslw_price);
        TextView coupon = (TextView) getView(R.id.tv_orderformslw_coupon);
        TextView time = (TextView) getView(R.id.tv_orderformslw_time);
        TextView address = (TextView) getView(R.id.tv_orderformslw_address);
        id.setText("订单编号：" + model.getOrderId());
        TextView btn1 = (TextView) getView(R.id.tv_orderformslw_button1);
        TextView btn2 = (TextView) getView(R.id.tv_orderformslw_button2);
        TextView btn3 = (TextView) getView(R.id.tv_orderformslw_button3);
        btn1.setText("申请退款");
        btn2.setText("查看物流");
        OrderFormSLW.BodyBean.OrderInfoBean infoBean = model.getOrderInfo().get(0);
        address.setText(infoBean.getConsigneeAddress());
        time.setText(infoBean.getCompleteTime());
        String moneyDiscount = model.getMoneyDiscount();
        Double douMoneyDiscount = Double.parseDouble(moneyDiscount);
        price.setText(infoBean.getMoneyPay());
        servicetype.setText(infoBean.getGoodsName());
        color.setText(infoBean.getGoodsColor());
        size.setText(infoBean.getGoodsSize());
        if (douMoneyDiscount > 0) {
            coupon.setText("已优惠" + moneyDiscount);
        } else {
            coupon.setText("未使用优惠券及折扣");
        }
        String type = infoBean.getDeleteStatus();
        switch (type) {
            case "0":
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);
                btn3.setVisibility(View.VISIBLE);
                btn3.setText("待确认");
                state.setText("待发货");
                break;
            case "1":
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.VISIBLE);
                btn3.setVisibility(View.VISIBLE);
                btn3.setText("确认收货");
                state.setText("已发货");
                break;
            case "2":
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);
                btn3.setVisibility(View.VISIBLE);
                btn3.setText("再来一单");
                state.setText("订单已完成");
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
        if (model.getOrderInfo().size() > 1) {
            scrollView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
            linearLayout.removeAllViews();
            List<OrderFormSLW.BodyBean.OrderInfoBean> list = model.getOrderInfo();
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
        btn2.setTag(position);
        btn2.setOnClickListener(adapter.getmOnclick());
        btn3.setTag(position);
        btn3.setOnClickListener(adapter.getmOnclick());
        orderFrom.setTag(position);
        orderFrom.setOnClickListener(adapter.getmOnclick());
    }
}
