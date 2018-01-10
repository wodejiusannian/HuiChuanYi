package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.HistotyZhenDuan;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.AsyncHttpUtils;
import com.example.huichuanyi.utils.HttpCallBack;
import com.example.huichuanyi.utils.HttpUtils;
import com.example.huichuanyi.utils.ItemDecoration;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class HistoryZhenDuanActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private MultiTypeAdapter adapter;

    private List<Visitable> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_zhen_duan);
    }

    @Override
    protected void setListener() {

    }

    @BindView(R.id.rl_history_zhenduan)
    RecyclerView content;

    @BindView(R.id.swipe_history_zhenduan)
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void initData() {
        adapter = new MultiTypeAdapter(mData);
        content.setLayoutManager(new LinearLayoutManager(this));
        content.setAdapter(adapter);
        content.addItemDecoration(new ItemDecoration(10));
        initNet();
    }

    @Override
    protected void setData() {
        refreshLayout.setOnRefreshListener(this);


    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onRefresh() {
        initNet();
    }

    public void initNet() {
        String userId = SharedPreferenceUtils.getUserData(this, 1);
        Map<String, String> map = new HashMap<>();
        map.put("user_id", userId);
        String json = HttpUtils.toJson(map);
        new AsyncHttpUtils(new HttpCallBack() {
            @Override
            public void onResponse(String result) {
                mData.clear();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONObject body = object.getJSONObject("body");
                    String picUrl = body.getString("picUrl");
                    JSONArray list = body.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject obj = list.getJSONObject(i);
                        HistotyZhenDuan histotyZhenDuan = new HistotyZhenDuan();
                        histotyZhenDuan.setPicUrl(picUrl);
                        histotyZhenDuan.setId(obj.getString("id"));
                        histotyZhenDuan.setSj_advice(obj.getString("sj_advice"));
                        histotyZhenDuan.setSw_advice(obj.getString("sw_advice"));
                        histotyZhenDuan.setXx_advice(obj.getString("xx_advice"));
                        histotyZhenDuan.setSj_zd(obj.getString("sj_zd"));
                        histotyZhenDuan.setSw_zd(obj.getString("sw_zd"));
                        histotyZhenDuan.setXx_zd(obj.getString("xx_zd"));
                        histotyZhenDuan.setTime(obj.getString("time"));
                        mData.add(histotyZhenDuan);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, this).execute("http://hmyc365.net/HM/bg/hmyc/vip/info/getYczdRec.do", json);
    }
}
