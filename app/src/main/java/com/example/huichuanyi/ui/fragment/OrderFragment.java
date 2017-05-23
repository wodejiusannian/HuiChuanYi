package com.example.huichuanyi.ui.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.HomeAdapter;
import com.example.huichuanyi.bean.Banner;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.secondui.AtMyAcitivty;
import com.example.huichuanyi.ui.activity.HMWebActivity;
import com.example.huichuanyi.ui.activity.HomeDaPeiRiJiActivity;
import com.example.huichuanyi.ui.activity.LiJiYuYueActivity;
import com.example.huichuanyi.ui.activity.MyOrderActivity;
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
public class OrderFragment extends BaseFragment implements UtilsInternet.XCallBack, OnItemClickListener {
    @ViewInject(R.id.tv_order_sure)
    private TextView orderSure;

    private static final int JUMP_TIME = 4000;

    @ViewInject(R.id.vp_order_banner)
    public RollPagerView mViewPager;


    private HomeAdapter mAdapter;

    private List<Banner> mBanners = new ArrayList<>();

    private UtilsInternet internet = UtilsInternet.getInstance();

    private String hm_adpage_share_url, hm_activity_name;

    private Map<String, String> map = new HashMap<>();

    @Override
    protected void initEvent() {
        super.initEvent();
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
        internet.post(NetConfig.BANNER_URL, map, this);
        internet.post(NetConfig.BANNER_URL, null, this);
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
                JSONObject object = new JSONObject(result);
                JSONObject body = object.getJSONObject("body");
                JSONArray banners = body.getJSONArray("banners");
                hm_adpage_share_url = body.getString("activity_share_url");
                hm_activity_name = body.getString("activity_name");
                for (int i = 0; i < banners.length(); i++) {
                    JSONObject jsonObject = banners.getJSONObject(i);
                    Banner banner = new Banner();
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
        Banner banner = mBanners.get(position);
        String type = banner.getType();
        switch (type) {
            case "2":
                Map<String, Object> map = new HashMap<>();
                String web_url = banner.getWeb_url();
                map.put("hm_adpage_webview_url", web_url);
                map.put("hm_activity_name", hm_activity_name);
                map.put("hm_adpage_share_url", hm_adpage_share_url);
                ActivityUtils.switchTo(getActivity(), HMWebActivity.class, map);
                break;
            case "5":
                ActivityUtils.switchTo(getActivity(), MyOrderActivity.class);
                break;
            case "3":
                ActivityUtils.switchTo(getActivity(), HomeDaPeiRiJiActivity.class);
                break;
            case "1":
                break;
            case "4":
                ActivityUtils.switchTo(getActivity(), AtMyAcitivty.class);
                break;
            default:
                break;
        }
    }
}