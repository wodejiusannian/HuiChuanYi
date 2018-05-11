package com.example.huichuanyi.newui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base_2.BaseFragment;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.OrderFormOrder;
import com.example.huichuanyi.common_view.model.OrderFormSLW;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.fragment_second.SeeCarActivity;
import com.example.huichuanyi.newui.activity.OrderFormDetailsActivity;
import com.example.huichuanyi.secondui.PingJiaActivity;
import com.example.huichuanyi.secondui.ShenQingTuiKuanActivity;
import com.example.huichuanyi.ui.activity.ClosingPriceActivity;
import com.example.huichuanyi.ui.newpage.OrderStudioListActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.MUtilsInternet;
import com.example.huichuanyi.utils.ServiceSingleUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.example.huichuanyi.emum.ServiceType.SERVICE_ACARUS_KILLING;
import static com.example.huichuanyi.emum.ServiceType.SERVICE_THE_DOOR;

public class OrderFormFragment extends BaseFragment {


    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.refresh_view)
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void setData() {
        super.setData();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                Visitable visitable = mData.get(pos);
                if (visitable instanceof OrderFormOrder.BodyBean) {
                    OrderFormOrder.BodyBean bean = (OrderFormOrder.BodyBean) visitable;
                    switch (v.getId()) {
                        case R.id.tv_orderformstate_button1:
                            Intent intent1 = new Intent(getContext(), ShenQingTuiKuanActivity.class);
                            intent1.putExtra("bean", bean);
                            startActivity(intent1);
                            break;
                        case R.id.tv_orderformstate_button2:
                            Intent intent2 = new Intent(getContext(), ClosingPriceActivity.class);
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
                                        Toast.makeText(getContext(), "确认收货", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "0":
                                    case "14":
                                        //去确认
                                        Toast.makeText(getContext(), "工作室还未确认接单", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "2":
                                        if ("0".equals(infoBean.getEvaluateState())) {
                                            goAgain(infoBean.getOrderType());
                                        } else {
                                            //去评价
                                            Intent intent3 = new Intent(getContext(), PingJiaActivity.class);
                                            intent3.putExtra("orderid", bean.getOrderId());
                                            intent3.putExtra("type", infoBean.getOrderType());
                                            startActivity(intent3);
                                        }
                                        break;
                                    default:
                                        goAgain(infoBean.getOrderType());
                                        break;
                                }
                            }
                            break;
                        default:
                            Intent intent = new Intent(getContext(), OrderFormDetailsActivity.class);
                            intent.putExtra("bean", bean);
                            startActivity(intent);
                            break;
                    }
                } else if (visitable instanceof OrderFormSLW.BodyBean) {
                    OrderFormSLW.BodyBean bean = (OrderFormSLW.BodyBean) visitable;
                    switch (v.getId()) {
                        case R.id.tv_orderformslw_button1:
                            //这个按钮不显示
                            break;
                        case R.id.tv_orderformslw_button2:
                            OrderFormSLW.BodyBean.OrderInfoBean infoBean1 = bean.getOrderInfo().get(0);
                            Intent intent = new Intent(getActivity(), SeeCarActivity.class);
                            intent.putExtra("wayNo", infoBean1.getWayNo());
                            intent.putExtra("wayCode", infoBean1.getWayCode());
                            startActivity(intent);
                            break;
                        case R.id.tv_orderformslw_button3:
                            OrderFormSLW.BodyBean.OrderInfoBean infoBean = bean.getOrderInfo().get(0);
                            String type = infoBean.getDeleteStatus();
                            if (!"3".equals(type) && !"4".equals(type)) {
                                switch (type) {
                                    case "1":
                                        Toast.makeText(getContext(), "确认收货", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "0":
                                    case "14":
                                        //去确认
                                        Toast.makeText(getContext(), "工作室还未确认接单", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "2":
                                        if ("0".equals(infoBean.getEvaluateState())) {
                                            //goAgain(infoBean.getOrderType());
                                            //改成加购物车
                                        } else {
                                            //去评价
                                            Intent intent3 = new Intent(getContext(), PingJiaActivity.class);
                                            intent3.putExtra("orderid", bean.getOrderId());
                                            intent3.putExtra("type", infoBean.getOrderType());
                                            startActivity(intent3);
                                        }
                                        break;
                                    default:
                                        ////改成加购物车
                                        //goAgain(infoBean.getOrderType());
                                        break;
                                }
                            }
                            break;
                        default:
                            Intent intent5 = new Intent(getContext(), OrderFormDetailsActivity.class);
                            intent5.putExtra("bean", bean);
                            startActivity(intent5);
                            break;
                    }
                }
            }
        });
        initNet();
    }

    private void goAgain(String type) {
        if ("1".equals(type)) {
            ServiceSingleUtils.getInstance().setServiceType(SERVICE_ACARUS_KILLING);
            ActivityUtils.switchTo(getActivity(), OrderStudioListActivity.class);
        } else {
            ServiceSingleUtils.getInstance().setServiceType(SERVICE_THE_DOOR);
            ActivityUtils.switchTo(getActivity(), OrderStudioListActivity.class);
        }
        getActivity().finish();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initNet();
            }
        });
    }

    private MultiTypeAdapter adapter;

    private List<Visitable> mData = new ArrayList<>();

    @Override
    protected void initData() {
        super.initData();
        adapter = new MultiTypeAdapter(mData);
        Bundle bundle = getArguments();
        orderType = bundle.getString("orderType");
        deleteStatusPj = bundle.getString("deleteStatusPj");
    }

    private String orderType;

    private String deleteStatusPj;

    @Override
    protected int layoutInflaterId() {
        return R.layout.refresh_reycleview;
    }

    private MUtilsInternet net = MUtilsInternet.getInstance();

    private void initNet() {
        Map<String, String> map = new HashMap<>();
        map.put("buyUserId", SharedPreferenceUtils.getUserData(getContext(), 1));
        map.put("orderType", orderType);
        map.put("deleteStatusPj", deleteStatusPj);
        net.post(NetConfig.MINEORDER_LIST, getContext(), map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    mData.clear();
                    refreshLayout.setRefreshing(false);
                    if ("0".equals(orderType)) {
                        Gson gson = new Gson();
                        OrderFormOrder orderFormOrder = gson.fromJson(result, OrderFormOrder.class);
                        mData.addAll(orderFormOrder.getBody());
                        adapter.notifyDataSetChanged();
                    } else {
                        Gson gson = new Gson();
                        OrderFormSLW orderFormOrder = gson.fromJson(result, OrderFormSLW.class);
                        mData.addAll(orderFormOrder.getBody());
                        adapter.notifyDataSetChanged();
                    }
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
