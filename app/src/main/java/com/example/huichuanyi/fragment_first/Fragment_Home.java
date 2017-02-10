package com.example.huichuanyi.fragment_first;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.HomeAdapter;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.modle.Banner;
import com.example.huichuanyi.secondui.AtMyAcitivty;
import com.example.huichuanyi.secondui.StatisticsActivity;
import com.example.huichuanyi.ui_second.DaPeiRiJiActivity;
import com.example.huichuanyi.ui_second.HuiMeiWeiKeActivity;
import com.example.huichuanyi.ui_second.MyOrderActivity;
import com.example.huichuanyi.ui_second.RegisterActivity;
import com.example.huichuanyi.ui_second.WoDeYiChuActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.User;
import com.google.gson.Gson;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Home extends BaseFragment implements View.OnClickListener, OnItemClickListener {
    private static final int JUMP_TIME = 4000;
    private RollPagerView mViewPager;
    private HomeAdapter mAdapter;
    private View view;
    private User mUser;
    private ImageView mImageViewMatch,
            mImageViewInfo,
            mImageViewPartner,
            mImageViewCloset;
    private List<Banner.ListBean> mBanners;
    private Callback.Cancelable cancelable;

    @Override
    protected View initView() {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        initChildView(view);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        mBanners = new ArrayList<>();
        mUser = new User(getActivity());
        initViewPager();
        RequestParams getParams = new RequestParams(NetConfig.BANNER_ONE);
        cancelable = x.http().get(getParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Banner banner = gson.fromJson(result, Banner.class);
                mBanners.addAll(banner.getList());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }


            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    protected void setData() {
        super.setData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mImageViewCloset.setOnClickListener(this);
        mImageViewInfo.setOnClickListener(this);
        mImageViewMatch.setOnClickListener(this);
        mImageViewPartner.setOnClickListener(this);
        mViewPager.setOnItemClickListener(this);
    }

    private void initViewPager() {
        mAdapter = new HomeAdapter(mViewPager, mBanners, getActivity());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setHintView(new ColorPointHintView(getActivity(), Color.parseColor("#ac0000"), Color.WHITE));
        mViewPager.setPlayDelay(JUMP_TIME);
    }

    @Override
    public void onClick(View v) {
        int useId = mUser.getUseId();
        switch (v.getId()) {
            case R.id.iv_home_match:
                if (useId > 0) {
                    ActivityUtils.switchTo(getActivity(), DaPeiRiJiActivity.class);
                } else {
                    ActivityUtils.switchTo(getActivity(), RegisterActivity.class);
                }
                break;
            case R.id.iv_home_info:
                if (useId > 0) {
                    ActivityUtils.switchTo(getActivity(), StatisticsActivity.class);
                } else {
                    ActivityUtils.switchTo(getActivity(), RegisterActivity.class);
                }
                break;
            case R.id.iv_home_partner:
                if (useId > 0) {
                    ActivityUtils.switchTo(getActivity(), HuiMeiWeiKeActivity.class);
                } else {
                    ActivityUtils.switchTo(getActivity(), RegisterActivity.class);
                }
                break;
            case R.id.iv_home_closet:
                if (useId > 0) {
                    ActivityUtils.switchTo(getActivity(), WoDeYiChuActivity.class);
                } else {
                    ActivityUtils.switchTo(getActivity(), RegisterActivity.class);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cancelable != null && !cancelable.isCancelled()) {
            cancelable.cancel();
        }
    }

    private void initChildView(View view) {
        mViewPager = (RollPagerView) view.findViewById(R.id.vp_home_banner);
        mImageViewMatch = (ImageView) view.findViewById(R.id.iv_home_match);
        mImageViewInfo = (ImageView) view.findViewById(R.id.iv_home_info);
        mImageViewPartner = (ImageView) view.findViewById(R.id.iv_home_partner);
        mImageViewCloset = (ImageView) view.findViewById(R.id.iv_home_closet);
    }

    @Override
    public void onItemClick(int position) {
        int useId = new User(getActivity()).getUseId();
        if (useId == 0) {
            ActivityUtils.switchTo(getActivity(), RegisterActivity.class);
            return;
        }
        if (position == 0) {
            ActivityUtils.switchTo(getActivity(), AtMyAcitivty.class);
        }
        if (position == 1) {
            ActivityUtils.switchTo(getActivity(), MyOrderActivity.class);
        }
        if (position == 2) {
            ActivityUtils.switchTo(getActivity(), DaPeiRiJiActivity.class);
        }
    }
}
