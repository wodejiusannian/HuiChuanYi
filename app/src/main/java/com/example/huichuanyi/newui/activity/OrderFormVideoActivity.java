package com.example.huichuanyi.newui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.OrderFormVideo;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.ui.activity.HomeVideoCoverActivity;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.MUtilsInternet;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.example.huichuanyi.config.NetConfig.MINEORDER_LIST;

public class OrderFormVideoActivity extends BaseActivity {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.refresh_view)
    SwipeRefreshLayout refreshLayout;

    private List<Visitable> mData = new ArrayList<>();

    private MultiTypeAdapter adapter;

    private MUtilsInternet net = MUtilsInternet.getInstance();

    public void back(View view) {
        finish();
    }

    @Override
    protected void setListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initNet();
            }
        });
        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void initData() {
        map.put("buyUserId", SharedPreferenceUtils.getUserData(this, 1));
        map.put("orderTypePj", "5");
        map.put("deleteStatusPj", "-1,0,1,3,4,5,6");
        initNet();
    }

    private void initNet() {
        net.post(MINEORDER_LIST, this, map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                mData.clear();
                refreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                OrderFormVideo orderFromVideo = gson.fromJson(result, OrderFormVideo.class);
                mData.addAll(orderFromVideo.getBody());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private Map<String, String> map = new HashMap<>();

    @Override
    protected void setData() {
        adapter = new MultiTypeAdapter(mData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form_video);
    }
}
