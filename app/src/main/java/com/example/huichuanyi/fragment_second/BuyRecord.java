package com.example.huichuanyi.fragment_second;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.BuyRecordAdapter;
import com.example.huichuanyi.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小五 on 2017/2/8.
 */

public class BuyRecord extends BaseFragment {

    private ListView mShow;
    private List<String> mData;
    private BuyRecordAdapter mAdapter;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_list, null);
        getChildView(view);
        return view;
    }

    private void getChildView(View view) {
        mShow = (ListView) view.findViewById(R.id.fragment_list_view);
    }

    @Override
    protected void initData() {
        super.initData();
        mData = new ArrayList<>();
        mAdapter = new BuyRecordAdapter(getContext(), mData, R.layout.item_fragment_buy_record);
        mShow.setAdapter(mAdapter);
    }

    @Override
    protected void setData() {
        super.setData();
        for (int i = 0; i < 3; i++) {
            mData.add("轻奢" + i);
        }
        mAdapter.notifyDataSetChanged();
    }
}
