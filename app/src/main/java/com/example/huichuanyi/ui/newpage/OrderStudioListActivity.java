package com.example.huichuanyi.ui.newpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.OrderStudioAdapter;
import com.example.huichuanyi.baidumap.GetCity;
import com.example.huichuanyi.ui.activity.LiJiYuYueStudioSelectCityActivity;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.ActivityCacheUtils;
import com.example.huichuanyi.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class OrderStudioListActivity extends BaseActivity {

    public void back(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ActivityCacheUtils.addActivity(this);
    }


    private List<Fragment> mData = new ArrayList<>();

    @BindView(R.id.tv_order_top)
    TextView mTop;

    @BindView(R.id.tv_lijiyueyue_address)
    TextView address;

    @BindView(R.id.tb_order_title)
    TabLayout mTabLayout;

    @BindView(R.id.vp_order_pager)
    ViewPager mViewPager;

    @BindView(R.id.ll_no_body)
    LinearLayout noBody;

    OrderStudioAdapter mAdapter;

    private GetCity mGetCity;

    private OnFresh mOnFresh1, mOnFresh2, mOnFresh3;

    private Integer[] types = {15, 32, 21};

    private ProgressDialog dialog;

    private String mLat, mLng;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (1 == what) {
                if (dialog.isShowing())
                    dialog.dismiss();
            } else {
                Bundle data = msg.getData();
                String mAdd = data.getString("location");
                if (!CommonUtils.isEmpty(mAdd)) {
                    address.setText(mAdd);
                    mGetCity.stopLocation();
                    noBody.setVisibility(View.GONE);
                    dialog.dismiss();
                }
            }
        }
    };

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("加载中......");
        dialog.setCancelable(false);
        dialog.show();
        mHandler.sendEmptyMessageDelayed(1, 5000);
        for (int i = 0; i < types.length; i++) {
            Bundle bundle = new Bundle();
            OrderStudioListFragment orderStudioListFragment = new OrderStudioListFragment();
            bundle.putInt("type", types[i]);
            orderStudioListFragment.setArguments(bundle);
            mData.add(orderStudioListFragment);
        }
        mAdapter = new OrderStudioAdapter(getSupportFragmentManager(), mData);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.mipmap.hm_studio_distance));
        mTabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.mipmap.hm_studio_unranking));
        mTabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.mipmap.hm_studio_unscore));
        loadingLocation();
    }

    /*
    * 去定位
    * */
    private void loadingLocation() {
        mGetCity = new GetCity(getApplicationContext());
        mGetCity.startLocation();
        mGetCity.setGetCity(new GetCity.WillGetCity() {
            @Override
            public void getWillGetCity(String city, String lat, String lng) {
                Bundle bundle = new Bundle();
                mLat = lat;
                mLng = lng;
                if (mOnFresh1 != null)
                    mOnFresh1.onFresh(city, mLat, mLng);
                if (mOnFresh2 != null)
                    mOnFresh2.onFresh(city, mLat, mLng);
                if (mOnFresh3 != null)
                    mOnFresh3.onFresh(city, mLat, mLng);
                bundle.putString("location", city);
                bundle.putString("lat", lat);
                bundle.putString("lng", lng);
                Message message = Message.obtain();
                message.setData(bundle);
                mHandler.sendMessage(message);
            }
        });
    }

    @Override
    protected void setData() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == mTabLayout.getTabAt(0)) {
                    mTabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.mipmap.hm_studio_distance));
                    mViewPager.setCurrentItem(0);
                } else if (tab == mTabLayout.getTabAt(1)) {
                    mTabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.mipmap.hm_studio_ranking));
                    mViewPager.setCurrentItem(1);
                } else if (tab == mTabLayout.getTabAt(2)) {
                    mTabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.mipmap.hm_studio_score));
                    mViewPager.setCurrentItem(2);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == mTabLayout.getTabAt(0)) {
                    mTabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.mipmap.hm_studio_undisacne));
                } else if (tab == mTabLayout.getTabAt(1)) {
                    mTabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.mipmap.hm_studio_unranking));
                } else if (tab == mTabLayout.getTabAt(2)) {
                    mTabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.mipmap.hm_studio_unscore));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @OnClick({R.id.tv_lijiyueyue_address})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_lijiyueyue_address:
                Intent intent = new Intent(new Intent(this, LiJiYuYueStudioSelectCityActivity.class));
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }
    }


    public void setOnFresh(OnFresh onFresh) {
        if (onFresh != null) {
            mOnFresh1 = onFresh;
        }
    }

    public void setOnFresh2(OnFresh onFresh) {
        if (onFresh != null) {
            mOnFresh2 = onFresh;
        }
    }

    public void setOnFresh3(OnFresh onFresh) {
        if (onFresh != null) {
            mOnFresh3 = onFresh;
        }
    }

    public interface OnFresh {
        void onFresh(String address, String lat, String lng);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String mAdd = data.getStringExtra("address");
            if (!CommonUtils.isEmpty(mAdd)) {
                address.setText(mAdd);
                if (mOnFresh1 != null)
                    mOnFresh1.onFresh(mAdd, mLat, mLng);
                if (mOnFresh2 != null)
                    mOnFresh2.onFresh(mAdd, mLat, mLng);
                if (mOnFresh3 != null)
                    mOnFresh3.onFresh(mAdd, mLat, mLng);
            }
        }
    }


}
