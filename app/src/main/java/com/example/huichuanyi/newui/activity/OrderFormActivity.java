package com.example.huichuanyi.newui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.ClosetAdapter;
import com.example.huichuanyi.newui.fragment.OrderFormFragment;
import com.example.huichuanyi.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderFormActivity extends BaseActivity {

    @BindView(R.id.tv_orderform_title)
    TextView mTitle;

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        mTitle.setText(title);
    }

    @BindView(R.id.tb_orderform_title)
    TabLayout tb;

    @BindView(R.id.vp_orderform_content)
    ViewPager pager;

    @Override
    protected void setData() {
        List<Fragment> mData = new ArrayList<>();
        List<String> mTitles = new ArrayList<>();
        ClosetAdapter adapter = new ClosetAdapter(getSupportFragmentManager(), mData, mTitles);
        pager.setAdapter(adapter);
        tb.setupWithViewPager(pager);
        for (int i = 0; i < 2; i++) {
            mData.add(new OrderFormFragment());
        }
        mTitles.add("未确认订单");
        mTitles.add("已完成订单");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);
    }

    public void back(View view) {
        finish();
    }
}
