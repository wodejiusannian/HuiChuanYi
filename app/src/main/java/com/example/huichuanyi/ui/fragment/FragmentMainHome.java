package com.example.huichuanyi.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.GetCity;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.VpSwipeRefreshLayout;
import com.example.huichuanyi.ui.activity.HomeDaPeiRiJiActivity;
import com.example.huichuanyi.ui.activity.HomeStatisticsActivity;
import com.example.huichuanyi.ui.activity.LabelsActivity;
import com.example.huichuanyi.ui.activity.MC_HomeActivity;
import com.example.huichuanyi.ui.activity.MC_OldClothesActivity;
import com.example.huichuanyi.ui.activity.MC_TripAndElseActivity;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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

        goNet();

        mGetCity = new GetCity(getContext());
        mGetCity.startLocation();
        mGetCity.setGetCity(new GetCity.WillGetCity() {
            @Override
            public void getWillGetCity(String city, String lat, String lng) {
                if (!CommonUtils.isEmpty(city)) {
                    map.put("city", "保定");
                    map.put("province", "河北");
                    map.put("token", NetConfig.TOKEN);
                    goNet();
                    mGetCity.stopLocation();
                }
            }
        });
    }

    private void goNet() {
        internet.post(NetConfig.WEATHER_INFO, map, this);
    }

    @Override
    protected void setData() {
        super.setData();
        swipe.setOnRefreshListener(this);
    }

    private void initViewPager() {
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
                intent = new Intent(getActivity(), HomeDaPeiRiJiActivity.class);
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
                weatherInfo[8].setText(date);
                swipe.setRefreshing(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @BindViews({R.id.tv_mainhomeweather_province, R.id.tv_mainhomeweather_city, R.id.tv_mainhomeweather_open,
            R.id.tv_mainhomeweather_centigrade, R.id.tv_mainhomeweather_atmosphere, R.id.tv_mainhomeweather_clothes
            , R.id.tv_mainhomeweather_sport, R.id.tv_mainhomeweather_week, R.id.tv_mainhomeweather_date})
    TextView[] weatherInfo;

    @Override
    public void onRefresh() {
        goNet();
    }
}
