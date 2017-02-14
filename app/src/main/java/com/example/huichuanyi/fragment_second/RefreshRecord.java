package com.example.huichuanyi.fragment_second;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.RefreshRecordAdapter;
import com.example.huichuanyi.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小五 on 2017/2/8.
 */

public class RefreshRecord extends BaseFragment {
    private ListView mShow;
    private List<String> mData;
    private RefreshRecordAdapter adapter;

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
        adapter = new RefreshRecordAdapter(getContext(), mData, R.layout.item_fragment_refresh_record);
        mShow.setAdapter(adapter);
    }

    @Override
    protected void setData() {
        super.setData();
        for (int i = 0; i < 3; i++) {
            mData.add("更新记录" + i);
        }
        adapter.notifyDataSetChanged();
    }
}
