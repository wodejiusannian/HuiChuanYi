package com.example.huichuanyi.fragment_second;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.BuyRecordAdapter;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.bean.RecordBuy;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.User;
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

public class BuyRecord extends BaseFragment implements UtilsInternet.XCallBack, SwipeRefreshLayout.OnRefreshListener {

    private ListView mShow;
    private List<RecordBuy> mData = new ArrayList<>();
    private UtilsInternet internet = UtilsInternet.getInstance();
    private BuyRecordAdapter mAdapter;
    private Map<String, String> map = new HashMap<>();
    private String user_id;
    private SwipeRefreshLayout mReFresh;

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
        mAdapter = new BuyRecordAdapter(getContext(), mData, R.layout.item_fragment_buy_record);
        mShow.setAdapter(mAdapter);
        user_id = new User(getContext()).getUseId() + "";
        map.put("user_id", user_id);
        loadData();
    }

    @Override
    protected void setData() {
        super.setData();
    }


    @Override
    protected void initEvent() {
        super.initEvent();
        mReFresh.setOnRefreshListener(this);
    }

    @Override
    public void onResponse(String result) {
        mData.clear();
        try {
            JSONObject object = new JSONObject(result);
            JSONArray body = object.getJSONArray("body");
            for (int i = 0; i < body.length(); i++) {
                JSONObject obj = body.getJSONObject(i);
                RecordBuy record = new RecordBuy();
                record.setClothes_get(obj.getString("clothes_get"));
                record.setNum(obj.getString("num"));
                record.setClothes_name(obj.getString("clothes_name"));
                record.setSize_name(obj.getString("size_name"));
                record.setWay_no(obj.getString("clothes_get"));
                record.setTotal_price(obj.getString("total_price"));
                record.setState(obj.getString("state"));
                record.setOrder_id(obj.getString("order_id"));
                record.setRemarks(obj.getString("remarks"));
                mData.add(record);
            }
            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        internet.post(NetConfig.GET_BUY_RECORD, map, this);
    }

    @Override
    public void onRefresh() {
        loadData();
        mReFresh.setRefreshing(false);
    }
}