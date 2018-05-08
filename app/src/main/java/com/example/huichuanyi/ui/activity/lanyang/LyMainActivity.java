package com.example.huichuanyi.ui.activity.lanyang;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.LyMain;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.FiveSwipeRefreshLayout;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.ItemDecoration;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class LyMainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, UtilsInternet.XCallBack {

    @BindView(R.id.rv_langyang_content)
    RecyclerView rvContent;

    @BindView(R.id.sf_langyang_refrsh)
    FiveSwipeRefreshLayout sfRefresh;

    private MultiTypeAdapter adapter;

    private List<Visitable> mData = new ArrayList<>();

    private UtilsInternet internet = UtilsInternet.getInstance();

    private Map<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ly_main);

    }

    @Override
    public void setListener() {
        initNet();
    }


    @Override
    public void initData() {
        sfRefresh.setRefreshing(true);
        sfRefresh.setOnRefreshListener(this);
        adapter = new MultiTypeAdapter(mData);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(adapter);
        rvContent.addItemDecoration(new ItemDecoration(10));
    }

    @Override
    public void setData() {
        map.put("user_id", SharedPreferenceUtils.getUserData(this, 1));
    }

    @Override
    public void onRefresh() {
        initNet();
    }

    public void back(View view) {
        finish();
    }

    private void initNet() {
        internet.post(NetConfig.LY_MAIN_DATA, map, this);
    }

    @Override
    public void onResponse(String result) {
        mData.clear();
        if (sfRefresh.isRefreshing())
            sfRefresh.setRefreshing(false);
        Gson gson = new Gson();
        LyMain lyMain = gson.fromJson(result, LyMain.class);
        mData.addAll(lyMain.getBody());
        adapter.notifyDataSetChanged();
    }
}
