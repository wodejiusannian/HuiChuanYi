package com.example.huichuanyi.fragment_second;

import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.ui_third.BuChaJiaActivity;
import com.example.huichuanyi.adapter.ProgressAdapter;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.modle.Progress;
import com.example.huichuanyi.secondui.PayOrderActivity;
import com.example.huichuanyi.secondui.ShenQingTuiKuanActivity;
import com.example.huichuanyi.ui_second.LiJiYuYueActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.User;
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

public class Progress_Order extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ProgressAdapter.OnOrderClickListener {
    private ListView mListView;
    private ProgressAdapter mAdapter;
    private List<Progress.ListBean> mData;
    private SwipeRefreshLayout mRefreshLayout;
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
        mAdapter = new ProgressAdapter(mData,getActivity());
        getData();
    }

    private void getData() {
        RequestParams params = new RequestParams(NetConfig.MY_ORDER_PROGRESS);
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
                //Toast.makeText(getActivity(), R.string.err_internet,Toast.LENGTH_SHORT).show();
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
        getData();
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onOrderClick(View view) {
        if (mData.size()>0){
            Map<String,Object> map = new HashMap<>();
            int position = (int) view.getTag();
            Progress.ListBean  mPosition2 = mData.get(position);
            String manager_photo = mPosition2.getManager_photo();
            String managername = mPosition2.getManagername();
            String managerid = mPosition2.getManagerid();
            String money = mPosition2.getMoney();
            String orderid = mPosition2.getId();
            String state = mPosition2.getState();
            String city = mPosition2.getCity();
            switch(view.getId()){
                case R.id.iv_progress_item_shenqingtuikuan:
                    map.put("managePhoto",manager_photo);
                    map.put("manageName",managername);
                    map.put("allMoney",money);
                    map.put("orderid",orderid);
                    map.put("state",mPosition2.getState());
                    ActivityUtils.switchTo(getActivity(),ShenQingTuiKuanActivity.class,map);
                    break;
                case R.id.iv_progress_item_zailaiyidian:
                    Map<String,Object> map1 = new HashMap<>();
                    map1.put("location",city);
                    map1.put("time",getNowTime());
                    ActivityUtils.switchTo(getActivity(),LiJiYuYueActivity.class,map1);
                    break;
                case R.id.iv_progress_item_daiqueren:
                    if (!TextUtils.equals("10",state)){
                        upQueRen(orderid,managerid,position);
                    }else{
                        Toast.makeText(getContext(), "等待工作室接单", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.iv_progress_item_quzhifu:
                    map.put("managerPhoto",manager_photo);
                    map.put("managerName",managername);
                    map.put("nowMoney",money);
                    map.put("orderid",orderid);
                    ActivityUtils.switchTo(getActivity(),PayOrderActivity.class,map);
                    break;
                case R.id.iv_progress_item_buchajia:
                    map.put("YetPay",money);
                    map.put("orderid",orderid);
                    map.put("manager_name",managername);
                    ActivityUtils.switchTo(getActivity(),BuChaJiaActivity.class,map);
                    break;
                default:

                    break;
            }
        }
    }

    private void upQueRen(String id, String managerid, final int position) {
        RequestParams params = new RequestParams(NetConfig.SURE_ORDER);
        params.addBodyParameter("userid",new User(getActivity()).getUseId()+"");
        params.addBodyParameter("orderid",id);
        params.addBodyParameter("managerid",managerid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.equals("1",result)){
                    mData.remove(position);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //Toast.makeText(getContext(), R.string.err_internet, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public String getNowTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        GregorianCalendar ca = new GregorianCalendar();
        int i = ca.get(GregorianCalendar.AM_PM);
        String nowTime = sdf.format(new Date());
        if(i==0) {
            return nowTime+" "+"上午";
        }else if(i==1) {
            return nowTime+" "+"下午";
        }
        return null;
    }
}
