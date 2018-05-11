package com.example.huichuanyi.fragment_second;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.huichuanyi.utils.MUtilsInternet;
import com.example.huichuanyi.utils.ServiceSingleUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ServiceGoDoorFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ProgressAdapter.OnOrderClickListener {


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
        map.put("orderType", "3");
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
        if (mData.size() > 0) {
            final Map<String, Object> map = new HashMap<>();
            int position = (int) view.getTag();
            final ServiceBean.BodyBean bean = mData.get(position);
            String orderid = bean.getOrderId();
            String managerid = bean.getSellerUserId();
            /*final String managername = mPosition2.getManagername();
            String managerid = mPosition2.getManagerid();
            final String money = mPosition2.getMoney();
            final String orderid = mPosition2.getId();
            String state = mPosition2.getState();
            String city = mPosition2.getCity();*/
            switch (view.getId()) {
                case R.id.iv_progress_item_shenqingtuikuan:
                    Intent in = new Intent(getActivity(), ShenQingTuiKuanActivity.class);
                    Bundle bun = new Bundle();
                    bun.putSerializable("bean", bean);
                    in.putExtras(bun);
                    startActivity(in);
                    break;
                case R.id.iv_progress_item_zailaiyidian:
                    ServiceSingleUtils.getInstance().setServiceType(ServiceType.SERVICE_THE_DOOR);
                    ActivityUtils.switchTo(getActivity(), OrderStudioListActivity.class);
                    break;
                case R.id.iv_progress_item_daiqueren:
                    String state = bean.getDeleteStatus();
                    if (!TextUtils.equals("10", state)) {
                        upQueRen(orderid, managerid, position);
                    } else {
                        Toast.makeText(getContext(), "等待工作室接单", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.iv_progress_item_buchajia:
                    goCp("5", bean);
                   /* BottomDialog bottomDialog = new BottomDialog(getContext());
                    bottomDialog.setTop("补上门服务差价", new BottomDialog.OnTop() {
                        @Override
                        public void onClick() {
                            goCp("5", mPosition2);
                        }
                    });
                    bottomDialog.setOnCenter("补收纳盒差价", new BottomDialog.OnCenter() {
                        @Override
                        public void onClick() {
                            goCp("6", mPosition2);
                        }
                    });
                    bottomDialog.show();*/
                    break;
                case R.id.iv_over_item_qupingjia:
                    Intent in22 = new Intent(getActivity(), PingJiaActivity.class);
                    Bundle bun22 = new Bundle();
                    in22.putExtra("orderid", orderid);
                    in22.putExtra("manager_id", bean.getSellerUserId());
                    in22.putExtra("user_name", bean.getBuyUserName());
                    in22.putExtra("manager_name", bean.getSellerUserName());
                    in22.putExtras(bun22);
                    startActivity(in22);
                    break;
                default:

                    break;
            }
        }
    }


    private void upQueRen(String id, String managerid, final int position) {
        RequestParams params = new RequestParams(NetConfig.SURE_ORDER);
        params.addBodyParameter("userid", SharedPreferenceUtils.getUserData(getContext(), 1));
        params.addBodyParameter("orderid", id);
        params.addBodyParameter("managerid", managerid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.equals("1", result)) {
                    initNet();
                }
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

    public String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        GregorianCalendar ca = new GregorianCalendar();
        int i = ca.get(GregorianCalendar.AM_PM);
        String nowTime = sdf.format(new Date());
        if (i == 0) {
            return nowTime + " " + "上午";
        } else if (i == 1) {
            return nowTime + " " + "下午";
        }
        return null;
    }

    /*
    * 跳入补差价页面
    * */
    private void goCp(String kind, ServiceBean.BodyBean bean) {
        Intent in = new Intent(getActivity(), ClosingPriceActivity.class);
        Bundle bun = new Bundle();
        bun.putSerializable("bean", bean);
        bun.putString("kind", kind);
        in.putExtras(bun);
        startActivity(in);
    }
}
