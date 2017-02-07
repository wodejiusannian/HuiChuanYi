package com.example.huichuanyi.secondui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.PersonAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.modle.City;
import com.example.huichuanyi.ui_third.ManageActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.MySharedPreferences;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActiveActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ListView mListView;
    private List<City.BodyBean> mData;
    private PersonAdapter mAdapter;
    private ImageView mImageViewBack;
    private TextView mTextViewActive;
    private String activeName;
    private SwipeRefreshLayout mRefreshLayout;
    private String active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active);
    }

    @Override
    public void initView() {
        mListView = (ListView) findViewById(R.id.lv_active_list);
        mImageViewBack = (ImageView) findViewById(R.id.iv_active_back);
        mTextViewActive = (TextView) findViewById(R.id.tv_active_active);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sf_active_refresh);
    }

    @Override
    public void initData() {
        active = getIntent().getStringExtra("active");
        activeName = getIntent().getStringExtra("activeName");
        mData = new ArrayList<>();
        mAdapter = new PersonAdapter(mData,this);
        getData();
    }

    @Override
    public void setData() {
        mListView.setAdapter(mAdapter);
        mTextViewActive.setText(activeName);
    }

    @Override
    public void setListener() {
        mImageViewBack.setOnClickListener(this);
        mListView.setOnItemClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.iv_active_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String,Object> map = new HashMap<>();
        map.put("managerid",mData.get(position).getGzs_id());
        ActivityUtils.switchTo(this, ManageActivity.class,map);
    }

    @Override
    public void onRefresh() {
        mData.clear();
        getData();
        mRefreshLayout.setRefreshing(false);
    }
    public void getData(){
        RequestParams params = new RequestParams(NetConfig.ACTIVE_FOUR);
        params.addBodyParameter("type",active);
        params.addBodyParameter("city", MySharedPreferences.getCity(this));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                City city = gson.fromJson(result, City.class);
                mData.addAll(city.getBody());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    };
}
