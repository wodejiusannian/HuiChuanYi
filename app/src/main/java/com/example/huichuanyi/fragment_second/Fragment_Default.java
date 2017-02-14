package com.example.huichuanyi.fragment_second;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.PersonAdapter;
import com.example.huichuanyi.baidumap.Location;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.modle.City;
import com.example.huichuanyi.ui_third.ManageActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.MyJson;
import com.example.huichuanyi.utils.UtilsInternet;
import com.example.huichuanyi.utils.Utils_Data;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_Default extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, UtilsInternet.XCallBack {
    private ListView mListViewDefault;
    private PersonAdapter pAdapter;
    private List<City.BodyBean> mCity;
    private SwipeRefreshLayout mRefresh;
    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_reuser_order,null);
        getChildView(view);
        return view;
    }

    private void getChildView(View view) {
        mListViewDefault = (ListView) view.findViewById(R.id.lv_reuse_order);
        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.sf_reuse_refresh);
    }

    @Override
    protected void initData() {
        super.initData();
        mCity = new ArrayList<>();
        pAdapter = new PersonAdapter(mCity,getActivity());
        AccordingToAddress(Location.mAddress);
    }

    @Override
    protected void setData() {
        super.setData();
        mListViewDefault.setAdapter(pAdapter);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mRefresh.setOnRefreshListener(this);
        mListViewDefault.setOnItemClickListener(this);
    }

    public void AccordingToAddress(String city){
        Map<String,String> map = new HashMap<>();
        map.put("city_name",city);
        map.put("sort_type","0");
        String dataObject = Utils_Data.getDataObject(map);
        Map<String,String> maps = new HashMap<>();
        maps.put("data",dataObject);
        UtilsInternet.getInstance().post(NetConfig.ADDRESS_URL,maps,this);
    }



    @Override
    public void onRefresh() {
        AccordingToAddress(Location.mAddress);
        mRefresh.setRefreshing(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String,Object> map = new HashMap<>();
        City.BodyBean listBean = mCity.get(position);
        String mId = listBean.getGzs_id();
        String city = listBean.getGzs_city();
        String service = listBean.getGzs_fuwu();
        String price1 = listBean.getPrice_basePrice1();
        String price2 = listBean.getPrice_basePrice2();
        String price_baseNum1 = listBean.getPrice_baseNum1();
        String price_baseNum2 = listBean.getPrice_baseNum2();
        String price_raiseNum = listBean.getPrice_raiseNum();
        String price_raisePrice = listBean.getPrice_raisePrice();
        if(TextUtils.equals("已开通",service)) {
            if(!TextUtils.isEmpty(mId)&&!TextUtils.isEmpty(city)) {
                map.put("managerid",mId);
                map.put("city",city);
                map.put("price1",price1);
                map.put("price2",price2);
                map.put("price_baseNum1",price_baseNum1);
                map.put("price_baseNum2",price_baseNum2);
                map.put("price_raiseNum",price_raiseNum);
                map.put("price_raisePrice",price_raisePrice);
                ActivityUtils.switchTo(getActivity(), ManageActivity.class,map);
                getActivity().finish();
            }
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("提示").setMessage("该工作室忙,请选择其他工作室").
                    setIcon(android.R.drawable.btn_star_big_off).
                    setPositiveButton("确定",null).create().show();
        }
    }

    @Override
    public void onResponse(String result) {
        String s = MyJson.getRet(result);
        if (TextUtils.equals("0",s)){
            mCity.clear();
            Gson gson = new Gson();
            City city = gson.fromJson(result, City.class);
            mCity.addAll(city.getBody());
            pAdapter.notifyDataSetChanged();
        }
    }
}
