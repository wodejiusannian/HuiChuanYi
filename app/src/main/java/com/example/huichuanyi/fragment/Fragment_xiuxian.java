package com.example.huichuanyi.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.MC_MyClothesAdapter;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.ItemDecoration;
import com.example.huichuanyi.bean.MyClothess;
import com.example.huichuanyi.ui.activity.WDYCPicActivity;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_xiuxian extends BaseFragment implements View.OnClickListener, UtilsInternet.XCallBack {
    private RecyclerView mRecycleView;
    private MC_MyClothesAdapter mAdapter;
    private List<MyClothess.BodyBean.ClothesInfoBean> mData;
    private String user_id;
    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.changhe, null);
        getChildView(view);
        return view ;
    }

    @Override
    protected void initData() {
        super.initData();
        user_id = SharedPreferenceUtils.getUserData(getContext(),1);
        mData = new ArrayList<>();
        mAdapter = new MC_MyClothesAdapter(getActivity(),mData);
        getData();
    }

    @Override
    protected void setData() {
        super.setData();
        GridLayoutManager mLayout = new GridLayoutManager(getActivity(),3);
        mRecycleView.setLayoutManager(mLayout);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addItemDecoration(new ItemDecoration(15));
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdapter.setOnItemClickListener(this);
    }

    private void getData() {
        Map map = new HashMap();
        map.put("user_id",user_id);
        map.put("clothes_wardrobeId","");
        map.put("clothes_typeId","");
        map.put("clothes_situation","休闲");
        map.put("clothes_styleId","");
        map.put("clothes_caizhi","");
        map.put("clothes_move","1");
        UtilsInternet.getInstance().post(NetConfig.GET_CLOTHES_CHA,map,this);
    }

    private void getChildView(View view) {
        mRecycleView = (RecyclerView) view.findViewById(R.id.rv_changhe_changhe);
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        Intent intent = new Intent(getActivity(), WDYCPicActivity.class);
        intent.putExtra("mList", (Serializable) mData);
        intent.putExtra("position",position);
        intent.putExtra("yichuzhonglei","11");
        startActivity(intent);
    }

    @Override
    public void onResponse(String result) {
        String ret = JsonUtils.getRet(result);
        mData.clear();
        if (TextUtils.equals("0",ret)){
            Gson gson = new Gson();
            MyClothess myClothess = gson.fromJson(result, MyClothess.class);
            mData.addAll(myClothess.getBody().getClothesInfo());
        }
        mAdapter.notifyDataSetChanged();
    }
}
