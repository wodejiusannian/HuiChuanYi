package com.example.huichuanyi.newui.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.OrderFormShowAdapter;
import com.example.huichuanyi.common_view.model.OrderFormOrder;
import com.example.huichuanyi.common_view.model.OrderFormSLW;
import com.example.huichuanyi.custom.GlideCircleTransform;
import com.example.huichuanyi.ui.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;

public class OrderFormDetailsActivity extends BaseActivity {

    @Override
    protected void setListener() {

    }

    @BindView(R.id.rl_orderdetails_info)
    RelativeLayout info;

    @BindView(R.id.sc)
    ScrollView scrollView;

    @Override
    protected void initData() {
        Parcelable bean = getIntent().getParcelableExtra("bean");
        if (bean instanceof OrderFormOrder.BodyBean) {
            OrderFormOrder.BodyBean bodyBean = ((OrderFormOrder.BodyBean) bean);
            formInfo[0].setText("订单编号:" + bodyBean.getOrderId());
            OrderFormOrder.BodyBean.OrderInfoBean infoBean = bodyBean.getOrderInfo().get(0);
            String orderType = infoBean.getOrderType();
            if ("1".equals(orderType)) {
                formInfo[2].setText("上门除螨管理服务");
            } else {
                formInfo[2].setText("上门衣橱管理服务");
            }
            formInfo[3].setText(infoBean.getSellerUserName());
            formInfo[4].setText(infoBean.getMoneyTotal());
            if (Double.parseDouble(bodyBean.getMoneyDiscount()) > 0) {
                formInfo[5].setText("已优惠" + bodyBean.getMoneyDiscount());
            }
            formInfo[6].setText(infoBean.getMoneyTotal());
            formInfo[7].setText("已优惠" + bodyBean.getMoneyDiscount());
            formInfo[8].setText(infoBean.getMoneyPay());
            formInfo[9].setText("订单编号:" + bodyBean.getOrderId());
            formInfo[10].setText("下单时间:" + infoBean.getEvaluateTime());
            formInfo[11].setText("预约时间:" + infoBean.getConsigneeTime());
            formInfo[12].setText("服务地址:" + infoBean.getConsigneeAddress());
            formInfo[13].setText("客户姓名:" + infoBean.getConsigneeName());
            formInfo[14].setText("客户电话:" + infoBean.getConsigneePhone());
            Glide.with(this).load(infoBean.getGoodsPicture()).transform(new GlideCircleTransform(this)).error(R.mipmap.hm_stand_cicle).into(iv);
        } else {
            OrderFormSLW.BodyBean bodyBean = (OrderFormSLW.BodyBean) bean;
            List<OrderFormSLW.BodyBean.OrderInfoBean> orderInfoBeen = bodyBean.getOrderInfo();
            OrderFormSLW.BodyBean.OrderInfoBean infoBean = orderInfoBeen.get(0);
            if (orderInfoBeen.size() == 1) {
                Glide.with(this).load(infoBean.getGoodsPicture()).transform(new GlideCircleTransform(this)).error(R.mipmap.hm_stand_cicle).into(iv);
            } else {
                ListView listView = (ListView) this.findViewById(R.id.lv_orderfrom_show);
                info.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                if (orderInfoBeen.size() < 3) {
                    listView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, orderInfoBeen.size() * 150));
                } else {
                    listView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 450));
                }
                listView.setAdapter(new OrderFormShowAdapter(orderInfoBeen, this));
                listView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            scrollView.requestDisallowInterceptTouchEvent(false);
                        } else {
                            scrollView.requestDisallowInterceptTouchEvent(true);//屏蔽父控件的拦截事件
                        }
                        return false;
                    }
                });
            }
            formInfo[0].setText("订单编号:" + bodyBean.getOrderId());
            formInfo[2].setText(infoBean.getGoodsName());
            formInfo[3].setText(infoBean.getSellerUserName());
            formInfo[4].setText(infoBean.getMoneyTotal());
            if (Double.parseDouble(bodyBean.getMoneyDiscount()) > 0) {
                formInfo[5].setText("已优惠" + bodyBean.getMoneyDiscount());
            }
            formInfo[6].setText(infoBean.getMoneyTotal());
            formInfo[7].setText("已优惠" + bodyBean.getMoneyDiscount());
            formInfo[8].setText(infoBean.getMoneyPay());
            formInfo[9].setText("订单编号:" + bodyBean.getOrderId());
            formInfo[10].setText("下单时间:" + infoBean.getEvaluateTime());
            formInfo[11].setText("推荐工作室:" + infoBean.getSellerUserName());
            formInfo[12].setText("服务地址:" + infoBean.getConsigneeAddress());
            formInfo[13].setText("客户姓名:" + infoBean.getConsigneeName());
            formInfo[14].setText("客户电话:" + infoBean.getConsigneePhone());
        }
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form_details);
    }

    public void back(View view) {
        finish();
    }

    @BindViews({R.id.tv_orderformdetails_id, R.id.tv_orderformdetails_state, R.id.tv_orderformdetails_servicetype,
            R.id.tv_orderformdetails_managername, R.id.tv_orderformdetails_price, R.id.tv_orderformdetails_coupon,
            R.id.tv_orderformdetails_allmoney, R.id.tv_orderformdetails_youhui, R.id.tv_orderformdetails_realmoney, R.id.tv_orderformdetails_id2,
            R.id.tv_orderformdetails_time, R.id.tv_orderformdetails_recommend, R.id.tv_orderformdetails_address,
            R.id.tv_orderformdetails_name, R.id.tv_orderformdetails_phone})
    TextView[] formInfo;

    @BindView(R.id.iv_orderformdetails_photo)
    ImageView iv;
}
