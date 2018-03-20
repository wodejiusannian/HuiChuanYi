package com.example.huichuanyi.fragment_second;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.ProgressAdapter;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.bean.ServiceBean;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.emum.ServiceType;
import com.example.huichuanyi.secondui.PingJiaActivity;
import com.example.huichuanyi.secondui.ShenQingTuiKuanActivity;
import com.example.huichuanyi.ui.activity.ClosingPriceActivity;
import com.example.huichuanyi.ui.newpage.OrderStudioListActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.MUtilsInternet;
import com.example.huichuanyi.utils.ServiceSingleUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceAcarusKillFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ProgressAdapter.OnOrderClickListener {


    private ListView mListView;

    private ProgressAdapter mAdapter;

    private List<ServiceBean.BodyBean> mData;

    private SwipeRefreshLayout mRefreshLayout;

    private MUtilsInternet net = MUtilsInternet.getInstance();

    private Map<String, String> map = new HashMap<>();

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_progress, null);
        getChildView(view);
        return view;
    }

    private void getChildView(View view) {
        mListView = (ListView) view.findViewById(R.id.lv_fragment_progress_list);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sf_fragment_progress_refresh);
    }

    @Override
    protected void initData() {
        super.initData();
        mData = new ArrayList<>();
        mAdapter = new ProgressAdapter(mData, getActivity());
        map.put("token", "82D5FBD40259C743ADDEF14D0E22F347");
        map.put("buyUserId", SharedPreferenceUtils.getUserData(getContext(), 1));
        map.put("orderType", "1");
        map.put("deleteStatusPj", "0,1,2,3,4,5,6");
        initNet();
    }

    /*
    * 请求服务器获取数据
    * */
    private void initNet() {
        net.post(NetConfig.SERVICE_LIST, getContext(), map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    mData.clear();
                    Gson gson = new Gson();
                    ServiceBean progress = gson.fromJson(result, ServiceBean.class);
                    mData.addAll(progress.getBody());
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnOrderClick(this);
    }

    @Override
    protected void setData() {
        super.setData();
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        initNet();
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onOrderClick(View view) {
        int position = (int) view.getTag();
        ServiceBean.BodyBean bean = mData.get(position);
        switch (view.getId()) {
            case R.id.iv_progress_item_shenqingtuikuan:
                Intent in = new Intent(getActivity(), ShenQingTuiKuanActivity.class);
                Bundle bun = new Bundle();
                bun.putSerializable("bean", bean);
                in.putExtras(bun);
                startActivity(in);
                break;
            case R.id.iv_progress_item_zailaiyidian:
                String deleteStatus = bean.getDeleteStatus();
                if ("4".equals(deleteStatus) || "3".equals(deleteStatus) || "100".equals(deleteStatus) || "110".equals(deleteStatus)) {
                    return;
                }
                if ("0".equals(bean.getEvaluateState())) {
                    ServiceSingleUtils.getInstance().setServiceType(ServiceType.SERVICE_ACARUS_KILLING);
                    ActivityUtils.switchTo(getActivity(), OrderStudioListActivity.class);
                } else {
                    Intent in22 = new Intent(getActivity(), PingJiaActivity.class);
                    Bundle bun22 = new Bundle();
                    in22.putExtra("orderid", bean.getOrderId());
                    in22.putExtra("manager_id", bean.getSellerUserId());
                    in22.putExtra("user_name", bean.getBuyUserName());
                    in22.putExtra("manager_name", bean.getSellerUserName());
                    in22.putExtra("type", "1");
                    in22.putExtras(bun22);
                    startActivity(in22);
                }
                break;
            case R.id.iv_progress_item_daiqueren:
                String state = bean.getDeleteStatus();
                if (TextUtils.equals("0", state) || TextUtils.equals("10", state)) {
                    Toast.makeText(getContext(), "等待工作室接单", Toast.LENGTH_SHORT).show();
                } else {
                    String orderId = bean.getOrderId();
                    quRen2(orderId);
                }
                break;
            case R.id.iv_progress_item_buchajia:
                Intent in2 = new Intent(getActivity(), ClosingPriceActivity.class);
                Bundle bun2 = new Bundle();
                bun2.putSerializable("bean", bean);
                in2.putExtras(bun2);
                startActivity(in2);
                break;
            case R.id.iv_over_item_qupingjia:
                Intent in22 = new Intent(getActivity(), PingJiaActivity.class);
                Bundle bun22 = new Bundle();
                in22.putExtra("orderid", bean.getOrderId());
                in22.putExtra("manager_id", bean.getSellerUserId());
                in22.putExtra("user_name", bean.getBuyUserName());
                in22.putExtra("manager_name", bean.getSellerUserName());
                in22.putExtra("type", "1");
                in22.putExtras(bun22);
                startActivity(in22);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initNet();
    }

    private void quRen2(String orderId) {
        map.put("token", "82D5FBD40259C743ADDEF14D0E22F347");
        map.put("orderId", orderId);
        net.post(NetConfig.OVER_SERVICE_ACARUS_KILLING, getContext(), map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                String ret = JsonUtils.getRet(result);
                if ("0".equals(ret)) {
                    initNet();
                } else {
                    Toast.makeText(getContext(), "请重新尝试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
