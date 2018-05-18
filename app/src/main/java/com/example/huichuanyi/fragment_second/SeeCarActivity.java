package com.example.huichuanyi.fragment_second;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.SeeCarAdapter;
import com.example.huichuanyi.bean.SeeCar;
import com.example.huichuanyi.common_view.model.OrderFormSLW;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.GlideRoundTransform;
import com.example.huichuanyi.custom.MyListView;
import com.example.huichuanyi.utils.MUtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SeeCarActivity extends com.example.huichuanyi.ui.base.BaseActivity {
    private MUtilsInternet internet = MUtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();

    private SeeCarAdapter mAdapter;
    private List<SeeCar> mData = new ArrayList<>();

    @BindView(R.id.lv_see_car)
    MyListView mShow;
    @BindView(R.id.sf_see_car)
    SwipeRefreshLayout mRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_car);
    }


    @OnClick(R.id.act_way_phone)
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.act_way_phone:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + bean.getWayPhone()));
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    OrderFormSLW.BodyBean.OrderInfoBean bean;


    private void initNet() {
        internet.post(NetConfig.SEECAR_LIST, this, map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    mRefresh.setRefreshing(false);
                    mData.clear();
                    JSONObject object = new JSONObject(result);
                    JSONArray traces = object.getJSONArray("Traces");
                    for (int i = 0; i < traces.length(); i++) {
                        JSONObject obj = traces.getJSONObject(i);
                        SeeCar seeCar = new SeeCar();
                        seeCar.time = obj.getString("AcceptTime");
                        seeCar.address = obj.getString("AcceptStation");
                        mData.add(seeCar);
                    }
                    Collections.reverse(mData);
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void initData() {
        mAdapter = new SeeCarAdapter(this, mData, R.layout.item_see_car);
        mShow.setAdapter(mAdapter);
        mShow = (MyListView) findViewById(R.id.lv_see_car);
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.sf_see_car);
        Intent intent = getIntent();
        bean = intent.getParcelableExtra("bean");
        ImageView viewById = (ImageView) this.findViewById(R.id.sv_see_car_clothes_photo);
        Glide.with(this).load(bean.getGoodsPicture()).error(R.mipmap.stand).transform(new GlideRoundTransform(this)).into(viewById);
        map.put("wayNo", bean.getWayNo());
        map.put("wayCode", bean.getWayCode());
        TextView mWay_name = (TextView) this.findViewById(R.id.act_way_name);
        TextView mWay_no = (TextView) this.findViewById(R.id.act_way_no);
        TextView mWay_phone = (TextView) this.findViewById(R.id.act_way_phone);
        TextView name = (TextView) this.findViewById(R.id.tv_clothes_name);
        name.setText(bean.getGoodsName());
        mWay_name.setText("承运公司:" + bean.getWayCompany());
        mWay_phone.setText("联系电话:" + bean.getWayPhone());
        mWay_no.setText("订单编号:" + bean.getWayNo());
        initNet();
    }


    @Override
    public void setData() {
    }

    @Override
    public void setListener() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initNet();
            }
        });
    }

    public void back(View view) {
        finish();
    }

}
