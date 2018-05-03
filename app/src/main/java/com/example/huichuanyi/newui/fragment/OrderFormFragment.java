package com.example.huichuanyi.newui.fragment;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base_2.BaseFragment;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.OrderFormOrder;
import com.example.huichuanyi.common_view.model.OrderFormSLW;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.newui.activity.OrderFormDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderFormFragment extends BaseFragment {


    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Override
    protected void setData() {
        super.setData();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        for (int i = 0; i < 30; i++) {
            if (i%2==0){
                mData.add(new OrderFormSLW());
            }else {
                mData.add(new OrderFormOrder());
            }
        }
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrderFormDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private MultiTypeAdapter adapter;

    private List<Visitable> mData = new ArrayList<>();

    @Override
    protected void initData() {
        super.initData();
        adapter = new MultiTypeAdapter(mData);
    }

    @Override
    protected int layoutInflaterId() {
        return R.layout.refresh_reycleview;
    }
}
