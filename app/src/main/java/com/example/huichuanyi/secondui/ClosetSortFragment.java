package com.example.huichuanyi.secondui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.Event5Adapter;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.utils.ItemDecoration;
import com.example.huichuanyi.ui_four.MySortActivity;
import com.example.huichuanyi.utils.ActivityUtils;

import java.util.HashMap;
import java.util.Map;

public class ClosetSortFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView mRecycleView;
    private int[] arr = {R.mipmap.shangyi,R.mipmap.kuzi,
            R.mipmap.qunzi,R.mipmap.xiezi,R.mipmap.bao,R.mipmap.peishi,R.mipmap.jiajufu};
    private String[] arrZhongLei = {"","上衣","裤子","裙子","鞋子","包","配饰","家居服"};
    private Event5Adapter mAdapter;
    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_closet_sort, null);
        getChildView(view);
        return view;
    }

    private void getChildView(View view) {
        mRecycleView = (RecyclerView) view.findViewById(R.id.rv_sort_show);
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter = new Event5Adapter(getActivity(),arr);

    }

    @Override
    protected void setData() {
        super.setData();
        GridLayoutManager manager = new GridLayoutManager(getActivity(),3);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addItemDecoration(new ItemDecoration(20));
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int tag = (int)v.getTag();
        tag = tag+1;
        Map<String,Object> map = new HashMap();
        map.put("tag",tag+"");
        map.put("zhonglei",arrZhongLei[tag]);
        map.put("yichuzhonglei","11");
        ActivityUtils.switchTo(getActivity(),MySortActivity.class,map);
    }
}
