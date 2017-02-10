package com.example.huichuanyi.fragment_first;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.MyPartnerAdapter;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.bean.CardItem;
import com.example.huichuanyi.ui_third.RecordActivity;
import com.example.huichuanyi.ui_third.Write_OrderActivity;
import com.example.huichuanyi.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;


public class Fragment_365 extends BaseFragment implements View.OnClickListener {
    public static boolean isPay = false;
    private ViewPager mPics;
    private MyPartnerAdapter mAdapter;
    private TextView mRecord;
    private List<CardItem> mData;
    private LinearLayout mPay, mNoPay;
    private Button mAdd, mWill;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_365, null);
        getChildView(view);
        return view;
    }

    private void getChildView(View view) {
        mPics = (ViewPager) view.findViewById(R.id.vp_365_pics);
        mRecord = (TextView) view.findViewById(R.id.tv_365_record);
        mNoPay = (LinearLayout) view.findViewById(R.id.ll_no_pay_money);
        mPay = (LinearLayout) view.findViewById(R.id.ll_yet_pay_money);
        mAdd = (Button) view.findViewById(R.id.btn_365_add);
        mWill = (Button) view.findViewById(R.id.btn_will_pay);
        isYetPay();
    }

    private void isYetPay() {
        if (!isPay) {
            mPay.setVisibility(View.VISIBLE);
            mRecord.setVisibility(View.VISIBLE);
            mNoPay.setVisibility(View.GONE);
        } else {
            mRecord.setVisibility(View.GONE);
            mNoPay.setVisibility(View.VISIBLE);
            mPay.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        mData = new ArrayList<>();
        mAdapter = new MyPartnerAdapter(mData);
        mPics.setPageMargin(50);
        mPics.setOffscreenPageLimit(3);
        mPics.setAdapter(mAdapter);
    }

    @Override
    protected void setData() {
        super.setData();
        for (int i = 0; i < 3; i++) {
            CardItem cardItem = new CardItem();
            cardItem.setPicStyle("轻奢" + i);
            cardItem.setPicContent("我就是商品" + i + "的介绍");
            cardItem.setPicName("刘亦菲喜欢的" + i);
            mData.add(cardItem);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdapter.setOnItemClickListener(this);
        mRecord.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        mWill.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_365_record:
                ActivityUtils.switchTo(getActivity(), RecordActivity.class);
                break;
            case R.id.btn_365_add:
                isPay = true;
                isYetPay();
                break;
            case R.id.btn_will_pay:
                isPay = false;
                isYetPay();
                break;
            default:
                int tag = (int) v.getTag();
                ActivityUtils.switchTo(getActivity(), Write_OrderActivity.class);
                break;
        }
    }


}
