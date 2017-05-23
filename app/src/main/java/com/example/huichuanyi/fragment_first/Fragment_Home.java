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
import com.example.huichuanyi.ui.activity.HMWebActivity;
import com.example.huichuanyi.ui.activity.HomeDaPeiRiJiActivity;
import com.example.huichuanyi.ui.activity.HomeStatisticsActivity;
import com.example.huichuanyi.ui.activity.HomeVideoCoverActivity;
import com.example.huichuanyi.ui.activity.HomeWoDeYiChuActivity;
import com.example.huichuanyi.ui.activity.MyOrderActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.huichuanyi.R.id.iv_home_closet;


public class Fragment_Home extends BaseFragment implements OnItemClickListener, UtilsInternet.XCallBack {

    private static final int JUMP_TIME = 4000;

    @BindView(R.id.vp_home_banner)
    public RollPagerView mViewPager;

    private HomeAdapter mAdapter;

    private List<Banner> mBanners = new ArrayList<>();
    ;
    private UtilsInternet internet = UtilsInternet.getInstance();

    private String hm_adpage_share_url, hm_activity_name;

    private Map<String, String> map = new HashMap<>();

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
        if (map == null)
            map = new HashMap<>();
        map.put("banner_type", "1");
        internet.post(NetConfig.BANNER_URL, map, this);
    }

    @Override
    protected void setData() {
        super.setData();
    }

    private void initViewPager() {
        mAdapter = new HomeAdapter(mViewPager, mBanners, getActivity());
        mViewPager.setAdapter(mAdapter);
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

    @OnClick({R.id.iv_home_match, R.id.iv_home_info, R.id.iv_home_partner, R.id.iv_home_closet})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home_match:
                ActivityUtils.switchTo(getActivity(), HomeDaPeiRiJiActivity.class);
                break;
            case R.id.iv_home_info:
                // ActivityUtils.switchTo(getActivity(), HMStateActivity.class);
                ActivityUtils.switchTo(getActivity(), HomeStatisticsActivity.class);
                //ActivityUtils.switchTo(getActivity(), BuyPayActivity.class);
                break;
            case R.id.iv_home_partner:
                ActivityUtils.switchTo(getActivity(), HomeVideoCoverActivity.class);
                break;
            case iv_home_closet:
                ActivityUtils.switchTo(getActivity(), HomeWoDeYiChuActivity.class);
                break;
        }
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

}
