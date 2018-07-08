package com.example.huichuanyi.newui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.OrderFormOrder;
import com.example.huichuanyi.common_view.model.OrderFormSLW;
import com.example.huichuanyi.common_view.model.OrderFormVideo;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.fragment_second.SeeCarActivity;
import com.example.huichuanyi.secondui.GoBackMoneyActivity;
import com.example.huichuanyi.secondui.PingJiaActivity;
import com.example.huichuanyi.ui.activity.ClosingPriceActivity;
import com.example.huichuanyi.ui.activity.HomeVideoCoverActivity;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.ui.newpage.OrderStudioListActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.ItemDecorationUtils;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.MUtilsInternet;
import com.example.huichuanyi.utils.ServiceSingleUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.example.huichuanyi.emum.ServiceType.SERVICE_ACARUS_KILLING;
import static com.example.huichuanyi.emum.ServiceType.SERVICE_THE_DOOR;

public class OrderFormAllActivity extends BaseActivity {
    public void back(View view) {
        finish();
    }

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.refresh_view)
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void setData() {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new ItemDecorationUtils(0, 5, 0, 5));
        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                Visitable visitable = mData.get(pos);
                if (visitable instanceof OrderFormOrder.BodyBean) {
                    OrderFormOrder.BodyBean bean = (OrderFormOrder.BodyBean) visitable;
                    switch (v.getId()) {
                        case R.id.tv_orderformstate_button1:
                            Intent intent1 = new Intent(OrderFormAllActivity.this, GoBackMoneyActivity.class);
                            intent1.putExtra("bean", bean);
                            startActivity(intent1);
                            break;
                        case R.id.tv_orderformstate_button2:
                            Intent intent2 = new Intent(OrderFormAllActivity.this, ClosingPriceActivity.class);
                            intent2.putExtra("kind", "5");
                            intent2.putExtra("bean", bean);
                            startActivity(intent2);
                            break;
                        case R.id.tv_orderformstate_button3:
                            OrderFormOrder.BodyBean.OrderInfoBean infoBean = bean.getOrderInfo().get(0);
                            String type = infoBean.getDeleteStatus();
                            if (!"3".equals(type) && !"4".equals(type)) {
                                switch (type) {
                                    case "1":
                                        //服饰订单和黑科技确认收货
                                        sureGet(infoBean.getId(), pos);
                                        break;
                                    case "0":
                                    case "14":
                                        //去确认
                                        Toast.makeText(OrderFormAllActivity.this, "工作室还未确认接单", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "2":
                                        if ("0".equals(infoBean.getEvaluateState())) {
                                            goAgain(infoBean.getOrderType());
                                        } else {
                                            //去评价
                                            Intent intent3 = new Intent(OrderFormAllActivity.this, PingJiaActivity.class);
                                            intent3.putExtra("bean", bean);
                                            startActivity(intent3);
                                        }
                                        break;
                                    case "5":
                                    case "6":
                                    case "7":
                                        goAgain(infoBean.getOrderType());
                                        break;
                                    default:
                                        goAgain(infoBean.getOrderType());
                                        break;
                                }
                            }
                            break;
                        default:
                            Intent intent = new Intent(OrderFormAllActivity.this, OrderFormDetailsActivity.class);
                            intent.putExtra("bean", bean);
                            startActivity(intent);
                            break;
                    }
                } else if (visitable instanceof OrderFormSLW.BodyBean) {
                    //服饰订单和黑科技中的数据处理
                    OrderFormSLW.BodyBean bean = (OrderFormSLW.BodyBean) visitable;
                    switch (v.getId()) {
                        case R.id.tv_orderformslw_button2:
                            OrderFormSLW.BodyBean.OrderInfoBean infoBean1 = bean.getOrderInfo().get(0);
                            Intent intent = new Intent(OrderFormAllActivity.this, SeeCarActivity.class);
                            intent.putExtra("bean", infoBean1);
                            startActivity(intent);
                            break;
                        case R.id.tv_orderformslw_button3:
                            OrderFormSLW.BodyBean.OrderInfoBean infoBean = bean.getOrderInfo().get(0);
                            String type = infoBean.getDeleteStatus();
                            if (!"3".equals(type) && !"4".equals(type)) {
                                switch (type) {
                                    case "1":
                                        //服饰订单和黑科技确认收货
                                        sureGet(infoBean.getId(), pos);
                                        break;
                                    default:
                                        Intent intent5 = new Intent(OrderFormAllActivity.this, OrderFormDetailsActivity.class);
                                        intent5.putExtra("orderTypePj", orderTypePj);
                                        intent5.putExtra("bean", bean);
                                        startActivity(intent5);
                                        break;
                                }
                            }
                            break;
                        default:
                            Intent intent5 = new Intent(OrderFormAllActivity.this, OrderFormDetailsActivity.class);
                            intent5.putExtra("orderTypePj", orderTypePj);
                            intent5.putExtra("bean", bean);
                            startActivity(intent5);
                            break;
                    }
                }else {
                    Intent in = new Intent(OrderFormAllActivity.this, HomeVideoCoverActivity.class);
                    startActivity(in);
                    finish();
                }
            }
        });
    }

    private void sureGet(String id, final int position) {
        Map<String, String> map = new HashMap<>();
        map.put("buyUserId", SharedPreferenceUtils.getUserData(this, 1));
        map.put("idPj", id);
        net.post(NetConfig.ORDER_SUREGET, this, map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                String ret = JsonUtils.getRet(result);
                if ("0".equals(ret)) {
                    mData.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(OrderFormAllActivity.this, "确认收货成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderFormAllActivity.this, "请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goAgain(String type) {
        if ("1".equals(type)) {
            ServiceSingleUtils.getInstance().setServiceType(SERVICE_ACARUS_KILLING);
            ActivityUtils.switchTo(OrderFormAllActivity.this, OrderStudioListActivity.class);
        } else {
            ServiceSingleUtils.getInstance().setServiceType(SERVICE_THE_DOOR);
            ActivityUtils.switchTo(OrderFormAllActivity.this, OrderStudioListActivity.class);
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refresh_reycleviewall);
    }


    private MultiTypeAdapter adapter;

    private List<Visitable> mData = new ArrayList<>();

    @Override
    protected void setListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initNet();
            }
        });
    }

    @Override
    protected void initData() {
        adapter = new MultiTypeAdapter(mData);
        orderTypePj = getIntent().getStringExtra("orderTypePj");
        deleteStatusPj = getIntent().getStringExtra("deleteStatusPj");
    }

    private String orderTypePj;

    private String deleteStatusPj;


    private MUtilsInternet net = MUtilsInternet.getInstance();

    private void initNet() {
        Map<String, String> map = new HashMap<>();
        map.put("buyUserId", SharedPreferenceUtils.getUserData(this, 1));
        map.put("orderTypePj", orderTypePj);
        map.put("deleteStatusPj", deleteStatusPj);
        net.post(NetConfig.MINEORDER_LIST, this, map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                  /*  if (orderTypePj.contains("1")) {
                        Gson gson = new Gson();
                        OrderFormOrder orderFormOrder = gson.fromJson(result, OrderFormOrder.class);
                        mData.addAll(orderFormOrder.getBody());
                        adapter.notifyDataSetChanged();
                    } else {
                        Gson gson = new Gson();
                        OrderFormSLW orderFormOrder = gson.fromJson(result, OrderFormSLW.class);
                        mData.addAll(orderFormOrder.getBody());
                    }*/
                    mData.clear();
                    refreshLayout.setRefreshing(false);
                    JSONObject object = new JSONObject(result);
                    JSONArray bodyArray = object.getJSONArray("body");
                    for (int i = 0; i < bodyArray.length(); i++) {
                        JSONObject obj = bodyArray.getJSONObject(i);
                        JSONArray orderInfoArray = obj.getJSONArray("orderInfo");
                        JSONObject objInfo = orderInfoArray.getJSONObject(0);
                        String concessionCode = obj.getString("concessionCode");
                        String discountExplain = obj.getString("discountExplain");
                        String moneyDiscount = obj.getString("moneyDiscount");
                        String orderId = obj.getString("orderId");
                        String orderType = objInfo.getString("orderType");
                        if ("1".equals(orderType) || "2".equals(orderType) || "3".equals(orderType) || "4".equals(orderType)) {
                            OrderFormOrder.BodyBean bean = new OrderFormOrder.BodyBean();
                            bean.setOrderId(orderId);
                            bean.setConcessionCode(concessionCode);
                            bean.setDiscountExplain(discountExplain);
                            bean.setMoneyDiscount(moneyDiscount);
                            List<OrderFormOrder.BodyBean.OrderInfoBean> orderInfoBeen = new ArrayList<OrderFormOrder.BodyBean.OrderInfoBean>();
                            for (int j = 0; j < orderInfoArray.length(); j++) {
                                JSONObject objj = orderInfoArray.getJSONObject(j);
                                OrderFormOrder.BodyBean.OrderInfoBean bean1 = new OrderFormOrder.BodyBean.OrderInfoBean();
                                bean1.setAcceptTime(objj.getString("acceptTime"));
                                bean1.setApplyRefuseTime(objj.getString("applyRefuseTime"));
                                bean1.setCompleteTime(objj.getString("completeTime"));
                                bean1.setConsigneeAddress(objj.getString("consigneeAddress"));
                                bean1.setConsigneeName(objj.getString("consigneeName"));
                                bean1.setConsigneePhone(objj.getString("consigneePhone"));
                                bean1.setConsigneeTime(objj.getString("consigneeTime"));
                                bean1.setDeleteStatus(objj.getString("deleteStatus"));
                                bean1.setEvaluateAverage(objj.getString("evaluateAverage"));
                                bean1.setEvaluateContent(objj.getString("evaluateContent"));
                                bean1.setEvaluateState(objj.getString("evaluateState"));
                                bean1.setEvaluateTime(objj.getString("evaluateTime"));
                                bean1.setGoodsColor(objj.getString("goodsColor"));
                                bean1.setGoodsIntroduction(objj.getString("goodsIntroduction"));
                                bean1.setGoodsName(objj.getString("goodsName"));
                                bean1.setGoodsPicture(objj.getString("goodsPicture"));
                                bean1.setGoodsPrice(objj.getString("goodsPrice"));
                                bean1.setGoodsSize(objj.getString("goodsSize"));
                                bean1.setId(objj.getString("id"));
                                bean1.setMoneyPay(objj.getString("moneyPay"));
                                bean1.setMoneyTotal(objj.getString("moneyTotal"));
                                bean1.setOrderNumber(objj.getString("orderNumber"));
                                bean1.setOrderRemarkBuyer(objj.getString("orderRemarkBuyer"));
                                bean1.setOrderType(objj.getString("orderType"));
                                bean1.setPayTime(objj.getString("payTime"));
                                bean1.setPayType(objj.getString("payType"));
                                bean1.setRecommendDate(objj.getString("recommendDate"));
                                bean1.setRecommendUserId(objj.getString("recommendUserId"));
                                bean1.setRecommendUserName(objj.getString("recommendUserName"));
                                bean1.setRefundReason(objj.getString("refundReason"));
                                bean1.setRefuseTime(objj.getString("refuseTime"));
                                bean1.setSellerCityName(objj.getString("sellerCityName"));
                                bean1.setSellerPhone(objj.getString("sellerPhone"));
                                bean1.setSellerPicture(objj.getString("sellerPicture"));
                                bean1.setSellerUserGrade(objj.getString("sellerUserGrade"));
                                bean1.setSellerUserId(objj.getString("sellerUserId"));
                                bean1.setSellerUserName(objj.getString("sellerUserName"));
                                bean1.setWayCode(objj.getString("wayCode"));
                                bean1.setWayCompany(objj.getString("wayCompany"));
                                bean1.setWayNo(objj.getString("wayNo"));
                                bean1.setWayPhone(objj.getString("wayPhone"));
                                orderInfoBeen.add(bean1);
                            }
                            bean.setOrderInfo(orderInfoBeen);
                            mData.add(bean);
                        } else if ("5".equals(orderType)) {
                            OrderFormVideo.BodyBean bean = new OrderFormVideo.BodyBean();
                            bean.setOrderId(orderId);
                            bean.setConcessionCode(concessionCode);
                            bean.setDiscountExplain(discountExplain);
                            bean.setMoneyDiscount(moneyDiscount);
                            List<OrderFormVideo.BodyBean.OrderInfoBean> orderInfoBeen = new ArrayList<OrderFormVideo.BodyBean.OrderInfoBean>();
                            for (int j = 0; j < orderInfoArray.length(); j++) {
                                JSONObject objj = orderInfoArray.getJSONObject(j);
                                OrderFormVideo.BodyBean.OrderInfoBean bean1 = new OrderFormVideo.BodyBean.OrderInfoBean();
                                bean1.setAcceptTime(objj.getString("acceptTime"));
                                bean1.setApplyRefuseTime(objj.getString("applyRefuseTime"));
                                bean1.setCompleteTime(objj.getString("completeTime"));
                                bean1.setConsigneeAddress(objj.getString("consigneeAddress"));
                                bean1.setConsigneeName(objj.getString("consigneeName"));
                                bean1.setConsigneePhone(objj.getString("consigneePhone"));
                                bean1.setConsigneeTime(objj.getString("consigneeTime"));
                                bean1.setDeleteStatus(objj.getString("deleteStatus"));
                                bean1.setEvaluateAverage(objj.getString("evaluateAverage"));
                                bean1.setEvaluateContent(objj.getString("evaluateContent"));
                                bean1.setEvaluateState(objj.getString("evaluateState"));
                                bean1.setEvaluateTime(objj.getString("evaluateTime"));
                                bean1.setGoodsColor(objj.getString("goodsColor"));
                                bean1.setGoodsIntroduction(objj.getString("goodsIntroduction"));
                                bean1.setGoodsName(objj.getString("goodsName"));
                                bean1.setGoodsPicture(objj.getString("goodsPicture"));
                                bean1.setGoodsPrice(objj.getString("goodsPrice"));
                                bean1.setGoodsSize(objj.getString("goodsSize"));
                                bean1.setId(objj.getString("id"));
                                bean1.setMoneyPay(objj.getString("moneyPay"));
                                bean1.setMoneyTotal(objj.getString("moneyTotal"));
                                bean1.setOrderNumber(objj.getString("orderNumber"));
                                bean1.setOrderRemarkBuyer(objj.getString("orderRemarkBuyer"));
                                bean1.setOrderType(objj.getString("orderType"));
                                bean1.setPayTime(objj.getString("payTime"));
                                bean1.setPayType(objj.getString("payType"));
                                bean1.setRecommendDate(objj.getString("recommendDate"));
                                bean1.setRecommendUserId(objj.getString("recommendUserId"));
                                bean1.setRecommendUserName(objj.getString("recommendUserName"));
                                bean1.setRefundReason(objj.getString("refundReason"));
                                bean1.setRefuseTime(objj.getString("refuseTime"));
                                bean1.setSellerCityName(objj.getString("sellerCityName"));
                                bean1.setSellerPhone(objj.getString("sellerPhone"));
                                bean1.setSellerPicture(objj.getString("sellerPicture"));
                                bean1.setSellerUserGrade(objj.getString("sellerUserGrade"));
                                bean1.setSellerUserId(objj.getString("sellerUserId"));
                                bean1.setSellerUserName(objj.getString("sellerUserName"));
                                bean1.setWayCode(objj.getString("wayCode"));
                                bean1.setWayNo(objj.getString("wayNo"));
                                orderInfoBeen.add(bean1);
                            }
                            bean.setOrderInfo(orderInfoBeen);
                            mData.add(bean);
                        } else {
                            OrderFormSLW.BodyBean bean = new OrderFormSLW.BodyBean();
                            bean.setOrderId(orderId);
                            bean.setConcessionCode(concessionCode);
                            bean.setDiscountExplain(discountExplain);
                            bean.setMoneyDiscount(moneyDiscount);
                            List<OrderFormSLW.BodyBean.OrderInfoBean> orderInfoBeen = new ArrayList<OrderFormSLW.BodyBean.OrderInfoBean>();
                            for (int j = 0; j < orderInfoArray.length(); j++) {
                                JSONObject objj = orderInfoArray.getJSONObject(j);
                                OrderFormSLW.BodyBean.OrderInfoBean bean1 = new OrderFormSLW.BodyBean.OrderInfoBean();
                                bean1.setAcceptTime(objj.getString("acceptTime"));
                                bean1.setApplyRefuseTime(objj.getString("applyRefuseTime"));
                                bean1.setCompleteTime(objj.getString("completeTime"));
                                bean1.setConsigneeAddress(objj.getString("consigneeAddress"));
                                bean1.setConsigneeName(objj.getString("consigneeName"));
                                bean1.setConsigneePhone(objj.getString("consigneePhone"));
                                bean1.setConsigneeTime(objj.getString("consigneeTime"));
                                bean1.setDeleteStatus(objj.getString("deleteStatus"));
                                bean1.setEvaluateAverage(objj.getString("evaluateAverage"));
                                bean1.setEvaluateContent(objj.getString("evaluateContent"));
                                bean1.setEvaluateState(objj.getString("evaluateState"));
                                bean1.setEvaluateTime(objj.getString("evaluateTime"));
                                bean1.setGoodsColor(objj.getString("goodsColor"));
                                bean1.setGoodsIntroduction(objj.getString("goodsIntroduction"));
                                bean1.setGoodsName(objj.getString("goodsName"));
                                bean1.setGoodsPicture(objj.getString("goodsPicture"));
                                bean1.setGoodsPrice(objj.getString("goodsPrice"));
                                bean1.setGoodsSize(objj.getString("goodsSize"));
                                bean1.setId(objj.getString("id"));
                                bean1.setMoneyPay(objj.getString("moneyPay"));
                                bean1.setMoneyTotal(objj.getString("moneyTotal"));
                                bean1.setOrderNumber(objj.getString("orderNumber"));
                                bean1.setOrderRemarkBuyer(objj.getString("orderRemarkBuyer"));
                                bean1.setOrderType(objj.getString("orderType"));
                                bean1.setPayTime(objj.getString("payTime"));
                                bean1.setPayType(objj.getString("payType"));
                                bean1.setRecommendDate(objj.getString("recommendDate"));
                                bean1.setRecommendUserId(objj.getString("recommendUserId"));
                                bean1.setRecommendUserName(objj.getString("recommendUserName"));
                                bean1.setRefundReason(objj.getString("refundReason"));
                                bean1.setRefuseTime(objj.getString("refuseTime"));
                                bean1.setSellerCityName(objj.getString("sellerCityName"));
                                bean1.setSellerPhone(objj.getString("sellerPhone"));
                                bean1.setSellerPicture(objj.getString("sellerPicture"));
                                bean1.setSellerUserName(objj.getString("sellerUserName"));
                                bean1.setWayCode(objj.getString("wayCode"));
                                bean1.setWayCompany(objj.getString("wayCompany"));
                                bean1.setWayNo(objj.getString("wayNo"));
                                bean1.setWayPhone(objj.getString("wayPhone"));
                                orderInfoBeen.add(bean1);
                            }
                            bean.setOrderInfo(orderInfoBeen);
                            mData.add(bean);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    ;

    @Override
    public void onResume() {
        super.onResume();
        initNet();
    }
}
