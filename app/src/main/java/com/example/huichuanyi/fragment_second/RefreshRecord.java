package com.example.huichuanyi.fragment_second;

import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.RefreshRecordAdapter;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.bean.RecordRefresh;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.User;
import com.example.huichuanyi.utils.Utils;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 小五 on 2017/2/8.
 */

public class RefreshRecord extends BaseFragment implements UtilsInternet.XCallBack, SwipeRefreshLayout.OnRefreshListener {
    private ListView mShow;
    private List<RecordRefresh> mData;
    private RefreshRecordAdapter adapter;
    private SwipeRefreshLayout mReFresh;
    private UtilsInternet internet = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();
    private String user_id;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_list, null);
        getChildView(view);
        return view;
    }

    private void getChildView(View view) {
        mShow = (ListView) view.findViewById(R.id.fragment_list_view);
        mReFresh = (SwipeRefreshLayout) view.findViewById(R.id.sf_buy_record_refresh);
    }

    @Override
    protected void initData() {
        super.initData();
        user_id = new User(getContext()).getUseId() + "";
        mData = new ArrayList<>();
        adapter = new RefreshRecordAdapter(getContext(), mData, R.layout.item_fragment_refresh_record);
        mShow.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mReFresh.setOnRefreshListener(this);
    }

    @Override
    protected void setData() {
        super.setData();
        loadData();
    }

    @Override
    public void onResponse(String result) {
        Utils.Log(result);
        mData.clear();
        List<RecordRefresh.RefreshBean> a = null;
        String str = null;
        RecordRefresh rr = null;
        try {
            JSONObject object = new JSONObject(result);
            JSONArray array = object.getJSONArray("body");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String time = jsonObject.getString("recommend_time");
                if (!TextUtils.equals(str, time)) {
                    str = time;
                    a = new ArrayList<>();
                    rr = new RecordRefresh();
                    mData.add(rr);
                }
                RecordRefresh.RefreshBean bean = new RecordRefresh.RefreshBean();
                bean.setClothes_get(jsonObject.getString("clothes_get"));
                a.add(bean);
                rr.setList(a);
            }
            Log.i("TAG", "-------" + mData.size());
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
        map.put("user_id", user_id);
        internet.post(NetConfig.GET_REFRESH_RECORD, map, this);
    }
}
