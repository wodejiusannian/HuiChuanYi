package com.example.huichuanyi.newui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base_2.BaseFragment;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.OrderFormOrder;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.newui.activity.OrderFormDetailsActivity;
import com.example.huichuanyi.utils.MUtilsInternet;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

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
                if ("0".equals(orderType)) {
                   int pos =  (int) v.getTag();
                    Visitable visitable = mData.get(pos);
                    if (visitable instanceof OrderFormOrder.BodyBean) {
                        OrderFormOrder.BodyBean bean = (OrderFormOrder.BodyBean) visitable;
                        Intent intent = new Intent(getContext(), OrderFormDetailsActivity.class);
                        intent.putExtra("bean", bean);
                        startActivity(intent);
                    }
                }
            }
        });
        initNet();
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
                    Gson gson = new Gson();
                    OrderFormOrder orderFormOrder = gson.fromJson(result, OrderFormOrder.class);
                    mData.addAll(orderFormOrder.getBody());
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static final String TAG = "OrderFromFragment";
    ;
}
