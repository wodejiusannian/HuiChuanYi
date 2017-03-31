package com.example.huichuanyi.fragment_first;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.HomeAdapter;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.bean.Banner;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.secondui.AtMyAcitivty;
import com.example.huichuanyi.secondui.StatisticsActivity;
import com.example.huichuanyi.ui.activity.DaPeiRiJiActivity;
import com.example.huichuanyi.ui.activity.HuiMeiWeiKeActivity;
import com.example.huichuanyi.ui.activity.MyOrderActivity;
import com.example.huichuanyi.ui.activity.RegisterActivity;
import com.example.huichuanyi.ui.activity.WoDeYiChuActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.google.gson.Gson;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.huichuanyi.R.id.iv_home_closet;


public class Fragment_Home extends BaseFragment implements View.OnClickListener, OnItemClickListener, UtilsInternet.XCallBack {
    private static final int JUMP_TIME = 4000;
    @BindView(R.id.vp_home_banner)
    public RollPagerView mViewPager;
    private HomeAdapter mAdapter;
    private List<Banner.ListBean> mBanners = new ArrayList<>();
    ;
    private UtilsInternet internet = UtilsInternet.getInstance();

    @Override
    protected void initEvent() {
        super.initEvent();
        mViewPager.setOnItemClickListener(this);
    }

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        initViewPager();
        internet.get(NetConfig.BANNER_URL, null, this);
    }

    @Override
    protected void setData() {
        super.setData();
    }

    private void initViewPager() {
        mAdapter = new HomeAdapter(mViewPager, mBanners, getActivity());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setHintView(new ColorPointHintView(getActivity(), Color.parseColor("#ac0000"), Color.WHITE));
        mViewPager.setPlayDelay(JUMP_TIME);
    }

    @Override
    public void onItemClick(int position) {
        if (!getUser()) {
            ActivityUtils.switchTo(getActivity(), RegisterActivity.class);
            return;
        }
        switch (position) {
            case 0:
                ActivityUtils.switchTo(getActivity(), AtMyAcitivty.class);
                break;
            case 1:
                ActivityUtils.switchTo(getActivity(), MyOrderActivity.class);
                break;
            case 2:
                ActivityUtils.switchTo(getActivity(), DaPeiRiJiActivity.class);
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.iv_home_match, R.id.iv_home_info, R.id.iv_home_partner, R.id.iv_home_closet})
    public void onClick(View v) {
        if (!getUser()) {
            ActivityUtils.switchTo(getActivity(), RegisterActivity.class);
            return;
        }
        switch (v.getId()) {
            case R.id.iv_home_match:
                ActivityUtils.switchTo(getActivity(), DaPeiRiJiActivity.class);
                break;
            case R.id.iv_home_info:
                ActivityUtils.switchTo(getActivity(), StatisticsActivity.class);
                break;
            case R.id.iv_home_partner:
                ActivityUtils.switchTo(getActivity(), HuiMeiWeiKeActivity.class);
                break;
            case iv_home_closet:
                ActivityUtils.switchTo(getActivity(), WoDeYiChuActivity.class);
                break;
        }
    }

    @Override
    public void onResponse(String result) {
        if (!CommonUtils.isEmpty(result)) {
            Gson gson = new Gson();
            Banner banner = gson.fromJson(result, Banner.class);
            mBanners.addAll(banner.getList());
            mAdapter.notifyDataSetChanged();
        }
    }
}
