package com.example.huichuanyi.ui.fragment.old;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.HomeAdapter;
import com.example.huichuanyi.baidumap.GetCity;
import com.example.huichuanyi.bean.Banner;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.activity.LiJiYuYueActivity;
import com.example.huichuanyi.ui.base.BaseFragment;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 小五 on 2017/3/23.
 */
@ContentView(R.layout.fragment_order_copy)
public class OrderFragment extends BaseFragment implements UtilsInternet.XCallBack, OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.tv_order_sure)
    private TextView orderSure;

    private static final int JUMP_TIME = 4000;

    @ViewInject(R.id.vp_order_banner)
    public RollPagerView mViewPager;

    @ViewInject(R.id.swipe)
    private SwipeRefreshLayout swipe;


    private HomeAdapter mAdapter;

    private List<Banner> mBanners = new ArrayList<>();

    private UtilsInternet internet = UtilsInternet.getInstance();


    private Map<String, String> map = new HashMap<>();

    private GetCity mGetCity;
    @Override
    protected void initEvent() {
        super.initEvent();
        swipe.setOnRefreshListener(this);
        orderSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.switchTo(getActivity(), LiJiYuYueActivity.class);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        initViewPager();
        if (map == null)
            map = new HashMap<>();

        map.put("banner_type", "2");
        goNet();

        mGetCity = new GetCity(getContext());
        mGetCity.startLocation();
        mGetCity.setGetCity(new GetCity.WillGetCity() {
            @Override
            public void getWillGetCity(String province,String city, String lat, String lng) {
                if (!CommonUtils.isEmpty(city)) {
                    map.put("city", city);
                    goNet();
                    mGetCity.stopLocation();
                }
            }
        });
    }

    private void goNet() {
        internet.post(NetConfig.BANNER_URL, map, this);
    }

    private void initViewPager() {
        if (mBanners == null)
            mBanners = new ArrayList<>();

        mAdapter = new HomeAdapter(mViewPager, mBanners, getActivity());
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    protected void setData() {
        super.setData();
        mViewPager.setOnItemClickListener(this);
    }


    @Override
    public void onResponse(String result) {
        if (!CommonUtils.isEmpty(result)) {
            try {
                mBanners.clear();
                swipe.setRefreshing(false);
                JSONObject object = new JSONObject(result);
                JSONObject body = object.getJSONObject("body");
                JSONArray banners = body.getJSONArray("banners");
                for (int i = 0; i < banners.length(); i++) {
                    JSONObject jsonObject = banners.getJSONObject(i);
                    Banner banner = new Banner();
                    banner.setShare_name(jsonObject.getString("share_name"));
                    banner.setShare_url(jsonObject.getString("share_url"));
                    banner.setWeb_url(jsonObject.getString("web_url"));
                    banner.setType(jsonObject.getString("type"));
                    banner.setPic_url(jsonObject.getString("pic_url"));
                    mBanners.add(banner);
                }
                if (mBanners.size() == 1) {
                    mViewPager.setHintView(new ColorPointHintView(getActivity(), Color.parseColor("#00ac0000"), Color.parseColor("#00ac0000")));
                } else {
                    mViewPager.setHintView(new ColorPointHintView(getActivity(), Color.parseColor("#ac0000"), Color.WHITE));
                    mViewPager.setPlayDelay(JUMP_TIME);
                }
                mAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onRefresh() {
        goNet();
        handler.sendEmptyMessageDelayed(0, 5000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (swipe.isRefreshing())
                        swipe.setRefreshing(false);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }
}