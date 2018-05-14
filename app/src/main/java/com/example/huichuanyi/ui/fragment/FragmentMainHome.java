package com.example.huichuanyi.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.HomeAdapter;
import com.example.huichuanyi.baidumap.GetCity;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.bean.Banner;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.VpSwipeRefreshLayout;
import com.example.huichuanyi.secondui.AtMyAcitivty;
import com.example.huichuanyi.ui.activity.HMWebActivity;
import com.example.huichuanyi.ui.activity.HomeDaPeiRiJiActivity;
import com.example.huichuanyi.ui.activity.HomeStatisticsActivity;
import com.example.huichuanyi.ui.activity.LabelsActivity;
import com.example.huichuanyi.ui.activity.MC_HomeActivity;
import com.example.huichuanyi.ui.activity.MC_OldClothesActivity;
import com.example.huichuanyi.ui.activity.MC_TripAndElseActivity;
import com.example.huichuanyi.ui.activity.MyOrderActivity;
import com.example.huichuanyi.ui.newpage.HomeMeasureActivity;
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
import butterknife.BindViews;
import butterknife.OnClick;

import static com.example.huichuanyi.R.id.iv_main_recycle;


public class FragmentMainHome extends BaseFragment implements UtilsInternet.XCallBack, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.swipe)
    VpSwipeRefreshLayout swipe;

    private GetCity mGetCity;

    private UtilsInternet internet = UtilsInternet.getInstance();

    private Map<String, String> map = new HashMap<>();

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mainhome, null);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        initViewPager();
        if (map == null)
            map = new HashMap<>();
        map.put("banner_type", "1");
        map.put("bannerType", "2");
        mGetCity = new GetCity(getContext());
        mGetCity.startLocation();
        mGetCity.setGetCity(new GetCity.WillGetCity() {
            @Override
            public void getWillGetCity(String province, String city, String lat, String lng) {
                if (!CommonUtils.isEmpty(city)) {
                    city = city.substring(0, city.length() - 1);
                    province = province.substring(0, province.length() - 1);
                    map.put("city", city);
                    map.put("province", province);
                    map.put("token", NetConfig.TOKEN);
                    goNet();
                    mGetCity.stopLocation();
                }
            }
        });
    }

    private void goNet() {
        internet.post(NetConfig.WEATHER_INFO, map, this);
        internet.post(NetConfig.BANNER_TYPE, map, new UtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {

                try {
                    mBanner.clear();
                    JSONObject object = new JSONObject(result);
                    JSONArray banners = object.getJSONArray("body");
                    for (int i = 0; i < banners.length(); i++) {
                        JSONObject jsonObject = banners.getJSONObject(i);
                        Banner banner = new Banner();
                        banner.setShare_name(jsonObject.getString("bannerName"));
                        banner.setShare_url(jsonObject.getString("shareUrl"));
                        banner.setWeb_url(jsonObject.getString("clickUrl"));
                        banner.setType(jsonObject.getString("clickType"));
                        banner.setPic_url(NetConfig.BASE_NEW_URL + jsonObject.getString("pictureUrl"));
                        mBanner.add(banner);
                    }
                    if (mBanner.size() == 1) {
                        rvBanners.setHintView(new ColorPointHintView(getActivity(), Color.parseColor("#00ac0000"), Color.parseColor("#00ac0000")));
                    } else {
                        rvBanners.setHintView(new ColorPointHintView(getActivity(), Color.parseColor("#ac0000"), Color.WHITE));
                        rvBanners.setPlayDelay(4000);
                    }
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void setData() {
        super.setData();
        swipe.setOnRefreshListener(this);
    }

    private HomeAdapter mAdapter;

    private List<Banner> mBanner = new ArrayList<>();

    private void initViewPager() {
        mAdapter = new HomeAdapter(rvBanners, mBanner, getContext());
        rvBanners.setAdapter(mAdapter);
        rvBanners.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Banner banner = mBanner.get(position);
                String type = banner.getType();
                switch (type) {
                    case "1":
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
                    case "4":
                        ActivityUtils.switchTo(getActivity(), AtMyAcitivty.class);
                        break;
                    default:
                        break;
                }
            }
        });
    }


    @OnClick({R.id.iv_main_collocation, R.id.iv_main_measure, R.id.iv_main_statistics, iv_main_recycle,
            R.id.iv_main_traver, R.id.iv_main_home, R.id.iv_main_other, R.id.iv_main_up})
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_main_collocation:
                intent = new Intent(getActivity(), HomeDaPeiRiJiActivity.class);
                break;
            case R.id.iv_main_measure:
                intent = new Intent(getActivity(), HomeMeasureActivity.class);
                break;
            case R.id.iv_main_statistics:
                intent = new Intent(getActivity(), HomeStatisticsActivity.class);
                break;
            case iv_main_recycle:
                intent = new Intent(getActivity(), MC_OldClothesActivity.class);
                intent.putExtra("yichu", "旧衣回收");
                break;
            case R.id.iv_main_traver:
                intent = new Intent(getActivity(), MC_TripAndElseActivity.class);
                intent.putExtra("yichu", "出行衣橱");
                intent.putExtra("word_id", "2");
                break;
            case R.id.iv_main_home:
                intent = new Intent(getActivity(), MC_HomeActivity.class);
                intent.putExtra("yichu", "家庭衣橱");
                break;
            case R.id.iv_main_other:
                intent = new Intent(getActivity(), MC_TripAndElseActivity.class);
                intent.putExtra("yichu", "其他衣橱");
                intent.putExtra("word_id", "4");
                break;
            case R.id.iv_main_up:
                intent = new Intent(getActivity(), LabelsActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onResponse(String result) {
        if (!CommonUtils.isEmpty(result)) {
            try {
                JSONObject obj = new JSONObject(result);
                JSONArray ret = obj.getJSONArray("result");
                JSONObject dateWeather = ret.getJSONObject(0);
                String province = dateWeather.getString("province");
                String city = dateWeather.getString("city");
                String weather = dateWeather.getString("weather");
                String temperature = dateWeather.getString("temperature");
                String airCondition = dateWeather.getString("airCondition");
                String dressingIndex = dateWeather.getString("dressingIndex");
                String exerciseIndex = dateWeather.getString("exerciseIndex");
                String week = dateWeather.getString("week");
                String date = dateWeather.getString("date");
                weatherInfo[0].setText(province);
                weatherInfo[1].setText(city);
                weatherInfo[2].setText(weather);
                weatherInfo[3].setText(temperature);
                weatherInfo[4].setText(airCondition);
                weatherInfo[5].setText(dressingIndex);
                weatherInfo[6].setText(exerciseIndex);
                weatherInfo[7].setText(week);
                initBannner(weather);
                weatherInfo[8].setText(date);
                swipe.setRefreshing(false);
            } catch (JSONException e) {
                e.printStackTrace();
                swipe.setRefreshing(false);
            }
        }
    }

    private void initBannner(String weather) {
        Map map = new HashMap();
        map.put("weather", "晴");
        internet.post("http://hmyc365.net/admiral/common/weather/weatherPic.htm?", map, new UtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONObject body = obj.getJSONObject("body");
                    String picUrl = body.getString("picUrl");
                    Glide.with(getContext()).load(picUrl).error(R.mipmap.nonepic).into(banner);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @BindView(R.id.rv_mainhome_banners)
    RollPagerView rvBanners;


    @BindView(R.id.iv_mainhome_banner)
    ImageView banner;

    @BindViews({R.id.tv_mainhomeweather_province, R.id.tv_mainhomeweather_city, R.id.tv_mainhomeweather_open,
            R.id.tv_mainhomeweather_centigrade, R.id.tv_mainhomeweather_atmosphere, R.id.tv_mainhomeweather_clothes
            , R.id.tv_mainhomeweather_sport, R.id.tv_mainhomeweather_week, R.id.tv_mainhomeweather_date})
    TextView[] weatherInfo;

    @Override
    public void onRefresh() {
        goNet();
    }
}
