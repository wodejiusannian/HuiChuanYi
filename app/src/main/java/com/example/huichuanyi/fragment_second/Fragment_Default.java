package com.example.huichuanyi.fragment_second;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.PersonAdapter;
import com.example.huichuanyi.baidumap.Location;
import com.example.huichuanyi.bean.City;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.activity.LiJiYuYueActivity;
import com.example.huichuanyi.ui.activity.ManageActivity;
import com.example.huichuanyi.ui.base.BaseFragment;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.MyJson;
import com.example.huichuanyi.utils.UtilsInternet;
import com.google.gson.Gson;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.fragment_reuser_order)
public class Fragment_Default extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, UtilsInternet.XCallBack {

    private PersonAdapter mAdapter;
    private List<City.BodyBean> mCity = new ArrayList<>();
    private Map<String, String> valueMap = new HashMap<>();
    private UtilsInternet internet = UtilsInternet.getInstance();

    @ViewInject(R.id.sf_reuse_refresh)
    private SwipeRefreshLayout mRefresh;
    @ViewInject(R.id.lv_reuse_order)
    private ListView mShow;

    private String getCity;

    @Override
    protected void initData() {
        super.initData();
        mAdapter = new PersonAdapter(getActivity(), mCity, R.layout.order_person);
        getCity = Location.mAddress;
        loadMore(getCity);
    }

    @Override
    protected void setData() {
        super.setData();
        mShow.setAdapter(mAdapter);
        LiJiYuYueActivity activity = (LiJiYuYueActivity) getActivity();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mRefresh.setOnRefreshListener(this);
        mShow.setOnItemClickListener(this);
    }


    @Override
    public void onRefresh() {
        loadMore(getCity);
        mRefresh.setRefreshing(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object> jumpMap = new HashMap<>();
        City.BodyBean listBean = mCity.get(position);
        String mId = listBean.getId();
        String city = listBean.getCity();
        String service = listBean.getService();
        String studioLogo = listBean.getPhoto_get();
        String name = listBean.getName();
       /* String price1 = listBean.getBase_price1();
        String price2 = listBean.getBase_price2();
        String price_baseNum1 = listBean.getBase_num1();
        String price_baseNum2 = listBean.getBase_num2();
        String price_raiseNum = listBean.getRaise_num();
        String price_raisePrice = listBean.getRaise_price();*/
        if (TextUtils.equals("已开通", service)) {
            if (TextUtils.equals("select_order", Location.mOrder_365)) {
                if (!TextUtils.isEmpty(mId) && !TextUtils.isEmpty(city)) {
                    /*jumpMap.put("studioId", mId);
                    jumpMap.put("city", city);
                    jumpMap.put("price1", price1);
                    jumpMap.put("price2", price2);
                    jumpMap.put("price_baseNum1", price_baseNum1);
                    jumpMap.put("price_baseNum2", price_baseNum2);
                    jumpMap.put("price_raiseNum", price_raiseNum);
                    jumpMap.put("price_raisePrice", price_raisePrice);*/
                    ActivityUtils.switchTo(getActivity(), ManageActivity.class, jumpMap);
                    //getActivity().finish();
                }
            } else {
                jumpMap.put("studioId", mId);
                jumpMap.put("studioLogo", studioLogo);
                jumpMap.put("studioName", name);
                ActivityUtils.switchTo(getActivity(), ManageActivity.class, jumpMap);
                //getActivity().finish();
            }
        } else {
            //工作室忙dialog提示
            showBusyDialog();
        }
    }


    //工作室忙的dialog
    private void showBusyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示").setMessage("该工作室忙,请选择其他工作室").
                setIcon(android.R.drawable.btn_star_big_off).
                setPositiveButton("确定", null).create().show();
    }

    //加载数据
    public void loadMore(String city) {
        valueMap.put("city", city);
        valueMap.put("type", "0");
        internet.post(NetConfig.GET_STUDIO_LIST, valueMap, this);
    }


    @Override
    public void onResponse(String result) {
        String s = MyJson.getRet(result);
        mCity.clear();
        if (TextUtils.equals("0", s)) {
            Gson gson = new Gson();
            City city = gson.fromJson(result, City.class);
            mCity.addAll(city.getBody());
        }
        mAdapter.notifyDataSetChanged();
    }


}

