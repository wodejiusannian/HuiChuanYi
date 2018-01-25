package com.example.huichuanyi.fragment_first;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.HomeAdapter;
import com.example.huichuanyi.baidumap.GetCity;
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
import com.example.huichuanyi.ui.activity.lanyang.LyMainActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.huichuanyi.R.id.iv_home_closet;


public class Fragment_Home extends BaseFragment implements OnItemClickListener, UtilsInternet.XCallBack, SwipeRefreshLayout.OnRefreshListener {

    private static final int JUMP_TIME = 4000;

    private static final String TAG = "Fragment_Home";

    @BindView(R.id.vp_home_banner)
    public RollPagerView mViewPager;

    private GetCity mGetCity;

    @BindView(R.id.swipe)
    public SwipeRefreshLayout swipe;

    @BindView(R.id.iv_hm_usth)
    ImageView iv;
    private HomeAdapter mAdapter;

    private List<Banner> mBanners = new ArrayList<>();
    ;
    private UtilsInternet internet = UtilsInternet.getInstance();


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

        goNet();

        mGetCity = new GetCity(getContext());
        mGetCity.startLocation();
        mGetCity.setGetCity(new GetCity.WillGetCity() {
            @Override
            public void getWillGetCity(String city, String lat, String lng) {
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

    @Override
    protected void setData() {
        super.setData();
        swipe.setOnRefreshListener(this);
        getButton();
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
                String share_name = banner.getShare_name();
                String share_url = banner.getShare_url();
                map.put("hm_adpage_webview_url", web_url);
                map.put("hm_activity_name", share_name);
                map.put("hm_adpage_share_url", share_url);
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

    @OnClick({R.id.iv_home_match, R.id.iv_home_info, R.id.iv_home_partner, R.id.iv_home_closet, R.id.iv_hm_usth})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home_match:
                ActivityUtils.switchTo(getActivity(), HomeDaPeiRiJiActivity.class);
                //ActivityUtils.switchTo(getActivity(), BoundActivity.class);
                break;
            case R.id.iv_home_info:
                ActivityUtils.switchTo(getActivity(), HomeStatisticsActivity.class);
                break;
            case R.id.iv_home_partner:
                ActivityUtils.switchTo(getActivity(), HomeVideoCoverActivity.class);
                break;
            case iv_home_closet:
                ActivityUtils.switchTo(getActivity(), HomeWoDeYiChuActivity.class);
                break;
            case R.id.iv_hm_usth:
                ActivityUtils.switchTo(getActivity(), LyMainActivity.class);
                break;
        }
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
    public void onRefresh() {
        goNet();
        handler.sendEmptyMessageDelayed(0, 5000);
        getButton();
    }

    private void getButton() {
        RequestParams pa = new RequestParams(NetConfig.GET_BUTTON);
        x.http().post(pa, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONObject body = object.getJSONObject("body");
                    final String url = body.getString("url");
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(getContext()).load(url).error(R.mipmap.test).into(iv);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
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

}
