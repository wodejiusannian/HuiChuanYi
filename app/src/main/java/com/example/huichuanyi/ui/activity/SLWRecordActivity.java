package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.RefreshRecordAdapter;
import com.example.huichuanyi.bean.RecordRefresh;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class SLWRecordActivity extends BaseActivity implements UtilsInternet.XCallBack, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.fragment_list_view)
    ListView mShow;

    @BindView(R.id.sf_buy_record_refresh)
    SwipeRefreshLayout mReFresh;

    private UtilsInternet internet = UtilsInternet.getInstance();

    private Map<String, String> map = new HashMap<>();

    private String user_id;

    private List<RecordRefresh> mData;

    private RefreshRecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list);
    }


    @Override
    public void initData() {
        user_id = SharedPreferenceUtils.getUserData(this, 1);
        mData = new ArrayList<>();
        adapter = new RefreshRecordAdapter(mData, this);
        mShow.setAdapter(adapter);
    }

    @Override
    public void setData() {
        loadData();
    }

    @Override
    public void setListener() {
        mReFresh.setOnRefreshListener(this);
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onResponse(String result) {
        mData.clear();
        List<RecordRefresh.RefreshBean> a = null;
        String str = null;
        RecordRefresh rr = null;
        try {
            JSONObject object = new JSONObject(result);
            JSONArray array = object.getJSONArray("body");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String time = jsonObject.getString("recommendDate");
                if (!TextUtils.equals(str, time)) {
                    str = time;
                    a = new ArrayList<>();
                    rr = new RecordRefresh();
                    rr.setTime(time);
                    mData.add(rr);
                }
                RecordRefresh.RefreshBean bean = new RecordRefresh.RefreshBean();
                bean.setColor(jsonObject.getString("goodsColor"));
                bean.setName(jsonObject.getString("goodsName"));
                bean.setClothes_get(jsonObject.getString("goodsPicture"));
                bean.setPrice_dj(jsonObject.getString("goodsPrice"));
                bean.setSize_name(jsonObject.getString("goodsSize"));
                bean.setId(jsonObject.getString("id"));
                bean.setRecommend_id(jsonObject.getString("recommendUserName"));
                bean.setReason(jsonObject.getString("recommendReason"));
                bean.setDeleteStatus(jsonObject.getString("deleteStatus"));
                a.add(bean);
                rr.setList(a);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRefresh() {
        loadData();
        mReFresh.setRefreshing(false);
    }

    private void loadData() {
        map.put("buyUserId", user_id);
        internet.post(NetConfig.RECOMMEND_STORY, map, this);
    }
}
