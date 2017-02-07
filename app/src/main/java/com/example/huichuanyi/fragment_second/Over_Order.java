package com.example.huichuanyi.fragment_second;

import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.OverAdapter;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.modle.Progress;
import com.example.huichuanyi.ui_four.MyOrderDetailsActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.User;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Over_Order extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private ListView mListView;
    private OverAdapter mAdapter;
    private List<Progress.ListBean> mData;
    private SwipeRefreshLayout mLayout;
    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_over, null);
        getChildView(view);
        return view;
    }

    private void getChildView(View view) {
        mListView = (ListView) view.findViewById(R.id.lv_fragment_over_list);
        mLayout = (SwipeRefreshLayout) view.findViewById(R.id.sf_fragment_over_refresh);
    }

    @Override
    protected void initData() {
        super.initData();
        mData = new ArrayList<>();
        mAdapter = new OverAdapter(mData,getActivity());
        getData();

    }

    private void getData() {
        RequestParams params = new RequestParams(NetConfig.MY_ORDER_OVER);
        params.addBodyParameter("userid",new User(getActivity()).getUseId()+"");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(TextUtils.equals("0",result)) {
                    return;
                }
                mData.clear();
                Gson gson = new Gson();
                Progress progress = gson.fromJson(result, Progress.class);
                mData.addAll(progress.getList());
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
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mLayout.setOnRefreshListener(this);
        mListView.setOnItemClickListener(this);
    }

    @Override
    protected void setData() {
        super.setData();
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        getData();
        mLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String,Object> map = new HashMap<>();
        map.put("orderid",mData.get(position).getId());
        ActivityUtils.switchTo(getActivity(), MyOrderDetailsActivity.class,map);
    }
}
