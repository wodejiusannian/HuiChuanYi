package com.example.huichuanyi.newui.activity;

import android.content.Intent;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.OrderFormFromShowAdapter;
import com.example.huichuanyi.adapter.OrderFormShowAdapter;
import com.example.huichuanyi.common_view.model.OrderFormOrder;
import com.example.huichuanyi.common_view.model.OrderFormSLW;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.GlideCircleTransform;
import com.example.huichuanyi.fragment_second.SeeCarActivity;
import com.example.huichuanyi.secondui.PingJiaActivity;
import com.example.huichuanyi.secondui.ShenQingTuiKuanActivity;
import com.example.huichuanyi.ui.activity.ClosingPriceActivity;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.ui.newpage.OrderStudioListActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.MUtilsInternet;
import com.example.huichuanyi.utils.ServiceSingleUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

import static android.R.attr.type;
import static com.example.huichuanyi.emum.ServiceType.SERVICE_ACARUS_KILLING;
import static com.example.huichuanyi.emum.ServiceType.SERVICE_THE_DOOR;

public class OrderFormDetailsActivity extends BaseActivity {

    @Override
    protected void setListener() {

    }

    @BindView(R.id.rl_orderdetails_info)
    RelativeLayout info;

    @BindView(R.id.sc)
    ScrollView scrollView;

    private Parcelable bean;

    @Override
    protected void initData() {
        bean = getIntent().getParcelableExtra("bean");
        String orderTypePj = getIntent().getStringExtra("orderTypePj");
        if (bean instanceof OrderFormOrder.BodyBean) {
            OrderFormOrder.BodyBean bodyBean = ((OrderFormOrder.BodyBean) bean);
            formInfo[0].setText("订单编号:" + bodyBean.getOrderId());
            List<OrderFormOrder.BodyBean.OrderInfoBean> beanList = bodyBean.getOrderInfo();
            OrderFormOrder.BodyBean.OrderInfoBean infoBean = beanList.get(0);
            String orderType = infoBean.getOrderType();
            if ("1".equals(orderType)) {
                formInfo[2].setText("上门除螨管理服务");
            } else {
                formInfo[2].setText("上门衣橱管理服务");
            }
            formInfo[3].setText(infoBean.getSellerUserName());
            formInfo[4].setText(infoBean.getMoneyTotal());
            if (Double.parseDouble(bodyBean.getMoneyDiscount()) > 0) {
                formInfo[5].setText("优惠" + bodyBean.getMoneyDiscount());
            }

            if (beanList.size() == 1) {
                formInfo[8].setText(infoBean.getMoneyPay());
                formInfo[6].setText(infoBean.getMoneyTotal());

                Glide.with(this).load(infoBean.getGoodsPicture()).transform(new GlideCircleTransform(this)).error(R.mipmap.hm_stand_cicle).into(iv);
            } else {
                ListView listView = (ListView) this.findViewById(R.id.lv_orderfrom_show);
                info.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                OrderFormFromShowAdapter adapter = new OrderFormFromShowAdapter(beanList, this);
                listView.setAdapter(adapter);
                View view = adapter.getView(0, null, listView);
                view.measure(0, 0);
                int height = view.getMeasuredHeight();
                if (beanList.size() < 3) {
                    listView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, beanList.size() * height));
                } else {
                    listView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3 * height));
                }
                float mo = 0;
                for (OrderFormOrder.BodyBean.OrderInfoBean bean : beanList) {
                    mo += Double.parseDouble(bean.getMoneyPay());
                }
                formInfo[8].setText(mo + "");
                formInfo[6].setText(mo + "");
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
            formInfo[7].setText("优惠" + bodyBean.getMoneyDiscount());
            formInfo[9].setText("订单编号:" + bodyBean.getOrderId());
            formInfo[10].setText("下单时间:" + infoBean.getPayTime());
            formInfo[11].setText("预约时间:" + infoBean.getConsigneeTime());
            formInfo[12].setText("服务地址:" + infoBean.getConsigneeAddress());
            formInfo[13].setText("客户姓名:" + infoBean.getConsigneeName());
            formInfo[14].setText("客户电话:" + infoBean.getConsigneePhone());
            formInfo[15].setText("管理师电话:" + infoBean.getSellerPhone());
            Glide.with(this).load(infoBean.getGoodsPicture()).transform(new GlideCircleTransform(this)).error(R.mipmap.hm_stand_cicle).into(iv);
            buttons[0].setText("申请退款");
            buttons[1].setText("补差价");
            String type = infoBean.getDeleteStatus();
            switch (type) {
                case "0":
                    buttons[0].setVisibility(View.VISIBLE);
                    buttons[1].setVisibility(View.GONE);
                    buttons[2].setVisibility(View.VISIBLE);
                    buttons[2].setText("待确认");
                    break;
                case "1":
                    buttons[0].setVisibility(View.VISIBLE);
                    buttons[1].setVisibility(View.VISIBLE);
                    buttons[2].setVisibility(View.VISIBLE);
                    buttons[2].setText("确认完成");
                    break;
                case "2":
                    buttons[0].setVisibility(View.GONE);
                    buttons[1].setVisibility(View.GONE);
                    buttons[2].setVisibility(View.VISIBLE);
                    if ("0".equals(infoBean.getEvaluateState())) {
                        buttons[2].setText("再来一单");
                    } else {
                        buttons[2].setText("去评价");
                    }
                    break;
                case "14":
                    buttons[0].setVisibility(View.GONE);
                    buttons[1].setVisibility(View.GONE);
                    buttons[2].setVisibility(View.VISIBLE);
                    buttons[2].setText("待确认");
                    break;
                case "3":
                case "4":
                    buttons[0].setVisibility(View.GONE);
                    buttons[1].setVisibility(View.GONE);
                    buttons[2].setVisibility(View.VISIBLE);
                    buttons[2].setText("审核中");
                    //state.setText("审核中");
                    break;
                case "5":
                case "6":
                    buttons[0].setVisibility(View.GONE);
                    buttons[1].setVisibility(View.GONE);
                    buttons[2].setVisibility(View.VISIBLE);
                    buttons[2].setText("再来一单");
                    //state.setText("订单取消");
                    break;
                default:
                    buttons[0].setVisibility(View.GONE);
                    buttons[1].setVisibility(View.GONE);
                    buttons[2].setVisibility(View.VISIBLE);
                    buttons[2].setText("再来一单");
                    //state.setText("订单取消");
                    break;
            }
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
                OrderFormShowAdapter adapter = new OrderFormShowAdapter(orderInfoBeen, this);
                listView.setAdapter(adapter);
                View view = adapter.getView(0, null, listView);
                view.measure(0, 0);
                int height = view.getMeasuredHeight();
                if (orderInfoBeen.size() < 3) {
                    listView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, orderInfoBeen.size() * height));
                } else {
                    listView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3 * height));
                }
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
            formInfo[3].setText(infoBean.getRecommendUserName());
            formInfo[4].setText(infoBean.getMoneyTotal());
            if (Double.parseDouble(bodyBean.getMoneyDiscount()) > 0) {
                formInfo[5].setText("优惠" + bodyBean.getMoneyDiscount());
            }
            float total = 0;
            for (int i = 0; i < orderInfoBeen.size(); i++) {
                total += Double.parseDouble(orderInfoBeen.get(i).getMoneyTotal());
            }
            formInfo[6].setText(total + "");
            formInfo[7].setText("优惠" + bodyBean.getMoneyDiscount());
            formInfo[8].setText(total + "");
            formInfo[9].setText("订单编号:" + bodyBean.getOrderId());
            formInfo[10].setText("下单时间:" + infoBean.getPayTime());
            if ("6".equals(orderTypePj)) {
                formInfo[11].setText("来源:" + infoBean.getSellerUserName());
            } else {
                formInfo[11].setText("推荐工作室:" + infoBean.getRecommendUserName());
            }
            formInfo[12].setText("服务地址:" + infoBean.getConsigneeAddress());
            formInfo[13].setText("客户姓名:" + infoBean.getConsigneeName());
            formInfo[14].setText("客户电话:" + infoBean.getConsigneePhone());
            String type = infoBean.getDeleteStatus();
            buttons[0].setText("申请退款");
            buttons[1].setText("查看物流");
            switch (type) {
                case "0":
                    buttons[0].setVisibility(View.GONE);
                    buttons[1].setVisibility(View.GONE);
                    buttons[2].setVisibility(View.VISIBLE);
                    //state.setText("进行中");
                    buttons[2].setText("待发货");
                    break;
                case "1":
                    buttons[0].setVisibility(View.GONE);
                    buttons[1].setVisibility(View.VISIBLE);
                    buttons[2].setVisibility(View.VISIBLE);
                    buttons[2].setText("确认收货");
                    //state.setText("已发货");
                    break;
                case "2":
                    buttons[0].setVisibility(View.GONE);
                    buttons[1].setVisibility(View.GONE);
                    buttons[2].setVisibility(View.VISIBLE);
                    buttons[2].setText("已完成");
                    // state.setText("订单已完成");
                    break;
                case "3":
                case "4":
                    buttons[0].setVisibility(View.GONE);
                    buttons[1].setVisibility(View.GONE);
                    buttons[2].setVisibility(View.VISIBLE);
                    buttons[2].setText("审核中");
                    // state.setText("审核中");
                    break;
                case "5":
                case "6":
                    buttons[0].setVisibility(View.GONE);
                    buttons[1].setVisibility(View.GONE);
                    buttons[2].setVisibility(View.VISIBLE);
                    buttons[2].setText("已完成");
                    // state.setText("订单取消");
                    break;
                case "7":
                    buttons[0].setVisibility(View.GONE);
                    buttons[1].setVisibility(View.GONE);
                    buttons[2].setVisibility(View.VISIBLE);
                    buttons[2].setText("已完成");
                    break;
                default:
                    buttons[0].setVisibility(View.GONE);
                    buttons[1].setVisibility(View.GONE);
                    buttons[2].setVisibility(View.VISIBLE);
                    buttons[2].setText("已完成");
                    // state.setText("订单取消");
                    break;
            }
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

    @OnClick({R.id.tv_orderformdetails_button1, R.id.tv_orderformdetails_button2, R.id.tv_orderformdetails_button3})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.tv_orderformdetails_button1:
                btn1OnClick();
                break;
            case R.id.tv_orderformdetails_button2:
                btn2OnClick();
                break;
            case R.id.tv_orderformdetails_button3:
                btn3OnClick();
                break;
            default:
                break;
        }
    }

    private void btn1OnClick() {
        Intent intent1 = new Intent(this, ShenQingTuiKuanActivity.class);
        intent1.putExtra("bean", bean);
        startActivity(intent1);
    }

    private void btn2OnClick() {
        if (bean instanceof OrderFormSLW.BodyBean) {
            //服饰订单和黑科技中的数据处理
            OrderFormSLW.BodyBean beans = (OrderFormSLW.BodyBean) bean;
            OrderFormSLW.BodyBean.OrderInfoBean infoBean1 = beans.getOrderInfo().get(0);
            Intent intent = new Intent(this, SeeCarActivity.class);
            intent.putExtra("bean", infoBean1);
            startActivity(intent);
        } else {
            Intent intent2 = new Intent(this, ClosingPriceActivity.class);
            intent2.putExtra("kind", "5");
            intent2.putExtra("bean", bean);
            startActivity(intent2);
        }
    }

    private void btn3OnClick() {
        if (bean instanceof OrderFormSLW.BodyBean) {
            OrderFormSLW.BodyBean beans = (OrderFormSLW.BodyBean) bean;
            OrderFormSLW.BodyBean.OrderInfoBean infoBean = beans.getOrderInfo().get(0);
            String type = infoBean.getDeleteStatus();
            if (!"3".equals(type) && !"4".equals(type)) {
                switch (type) {
                    case "1":
                        //服饰订单和黑科技确认收货
                        sureGet(infoBean.getId());
                        break;
                }
            }
        } else {
            OrderFormOrder.BodyBean beanss = (OrderFormOrder.BodyBean) bean;
            OrderFormOrder.BodyBean.OrderInfoBean infoBeans = beanss.getOrderInfo().get(0);
            String type2 = infoBeans.getDeleteStatus();
            if (!"3".equals(type) && !"4".equals(type)) {
                switch (type2) {
                    case "1":
                        //服饰订单和黑科技确认收货
                        sureGet(infoBeans.getId());
                        break;
                    case "0":
                    case "14":
                        //去确认
                        Toast.makeText(this, "工作室还未确认接单", Toast.LENGTH_SHORT).show();
                        break;
                    case "5":
                    case "6":
                    case "7":
                        goAgain(infoBeans.getOrderType());
                        break;
                    case "2":
                        if ("0".equals(infoBeans.getEvaluateState())) {
                            goAgain(infoBeans.getOrderType());
                        } else {
                            //去评价
                            Intent intent3 = new Intent(this, PingJiaActivity.class);
                            intent3.putExtra("bean", beanss);
                            startActivity(intent3);
                        }
                        break;
                }
            }
        }
    }

    private void goAgain(String type) {
        if ("1".equals(type)) {
            ServiceSingleUtils.getInstance().setServiceType(SERVICE_ACARUS_KILLING);
            ActivityUtils.switchTo(this, OrderStudioListActivity.class);
        } else {
            ServiceSingleUtils.getInstance().setServiceType(SERVICE_THE_DOOR);
            ActivityUtils.switchTo(this, OrderStudioListActivity.class);
        }
        finish();
    }

    private void sureGet(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("buyUserId", SharedPreferenceUtils.getUserData(this, 1));
        map.put("idPj", id);
        net.post(NetConfig.ORDER_SUREGET, this, map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                String ret = JsonUtils.getRet(result);
                if ("0".equals(ret)) {
                    Toast.makeText(OrderFormDetailsActivity.this, "确认收货成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderFormDetailsActivity.this, "请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void back(View view) {
        finish();
    }

    @BindViews({R.id.tv_orderformdetails_id, R.id.tv_orderformdetails_state, R.id.tv_orderformdetails_servicetype,
            R.id.tv_orderformdetails_managername, R.id.tv_orderformdetails_price, R.id.tv_orderformdetails_coupon,
            R.id.tv_orderformdetails_allmoney, R.id.tv_orderformdetails_youhui, R.id.tv_orderformdetails_realmoney, R.id.tv_orderformdetails_id2,
            R.id.tv_orderformdetails_time, R.id.tv_orderformdetails_recommend, R.id.tv_orderformdetails_address,
            R.id.tv_orderformdetails_name, R.id.tv_orderformdetails_phone, R.id.tv_orderformdetails_managerphone})
    TextView[] formInfo;

    @BindView(R.id.iv_orderformdetails_photo)
    ImageView iv;

    @BindViews({R.id.tv_orderformdetails_button1, R.id.tv_orderformdetails_button2, R.id.tv_orderformdetails_button3})
    TextView[] buttons;

    private MUtilsInternet net = MUtilsInternet.getInstance();
}
