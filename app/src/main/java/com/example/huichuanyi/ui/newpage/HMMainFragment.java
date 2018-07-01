package com.example.huichuanyi.ui.newpage;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.GetCity;
import com.example.huichuanyi.base_2.BaseFragment;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ItemHmMainBanner;
import com.example.huichuanyi.common_view.model.ItemHmMainInformation;
import com.example.huichuanyi.common_view.model.ItemHmMainKind;
import com.example.huichuanyi.common_view.model.ItemHmMainRecommendStudio;
import com.example.huichuanyi.common_view.model.ItemHmMainStarStudio;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.MUtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by 小五 on 2018/6/26.
 */

public class HMMainFragment extends BaseFragment {

    @BindView(R.id.tv_hmmain_address)
    TextView address;

    @BindView(R.id.rv_hmmain_content)
    RecyclerView content;

    private MUtilsInternet net = MUtilsInternet.getInstance();

    private MultiTypeAdapter adapter;

    private List<Visitable> mData = new ArrayList<>();

    @OnClick({R.id.tv_hmmain_address})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_hmmain_address:
                Intent intent = getAppDetailSettingIntent();
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private GetCity getCity;

    @Override
    protected void initData() {
        super.initData();
        if (mData == null)
            mData = new ArrayList<>();
        if (adapter == null)
            adapter = new MultiTypeAdapter(mData);
        if (getCity == null)
            getCity = new GetCity(getContext());

        getCity.setGetCity(new GetCity.WillGetCity() {
            @Override
            public void getWillGetCity(final String province, final String city, String lat, String lng) {
                mCity = city;
                mHandler.sendEmptyMessage(1);
            }
        });
        getCity.startLocation();
    }

    private String mCity;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    address.setText(mCity);
                    getCity.stopLocation();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void setData() {
        super.setData();
        content.setLayoutManager(new LinearLayoutManager(getContext()));
        content.setAdapter(adapter);
    }

    private Map<String, String> map = new HashMap<>();


    private void getData() {
        map.put("cityName", "北京市");
        net.post(NetConfig.HM_MAIN_DATA, getContext(), map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                mData.clear();
                sfRefresh.setRefreshing(false);
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONObject body = obj.getJSONObject("body");
                    JSONArray banner5 = body.getJSONArray("banner5");
                    List<ItemHmMainBanner.Banner> listBanner5 = new ArrayList<ItemHmMainBanner.Banner>();
                    for (int i = 0; i < banner5.length(); i++) {
                        JSONObject jsonBanner5 = banner5.getJSONObject(i);
                        ItemHmMainBanner.Banner banner = new ItemHmMainBanner.Banner();
                        banner.bannerName = jsonBanner5.getString("bannerName");
                        banner.clickType = jsonBanner5.getString("clickType");
                        banner.clickUrl = jsonBanner5.getString("clickUrl");
                        //banner.pictureUrl = jsonBanner5.getString("pictureUrl");
                        banner.pictureUrl = "http://img0.imgtn.bdimg.com/it/u=2238863788,4147573056&fm=27&gp=0.jpg";
                        banner.shareUrl = jsonBanner5.getString("shareUrl");
                        listBanner5.add(banner);
                    }
                    mData.add(new ItemHmMainBanner(listBanner5));
                    JSONArray icon = body.getJSONArray("icon");
                    List<ItemHmMainKind.DataBean> listIcon = new ArrayList<>();
                    for (int i = 0; i < icon.length(); i++) {
                        JSONObject jsonIcon = icon.getJSONObject(i);
                        ItemHmMainKind.DataBean banner = new ItemHmMainKind.DataBean();
                        banner.clickUrl = jsonIcon.getString("clickUrl");
                        banner.id = jsonIcon.getString("id");
                        banner.name = jsonIcon.getString("name");
                        banner.pictureUrl = jsonIcon.getString("pictureUrl");
                        listIcon.add(banner);
                    }
                    mData.add(new ItemHmMainKind(listIcon));
                    JSONArray banner6 = body.getJSONArray("banner6");
                    List<ItemHmMainInformation.DataBean> listBanner6 = new ArrayList<>();
                    for (int i = 0; i < banner6.length(); i++) {
                        JSONObject jsonBanner6 = banner6.getJSONObject(i);
                        ItemHmMainInformation.DataBean banner = new ItemHmMainInformation.DataBean();
                        banner.bannerName = jsonBanner6.getString("bannerName");
                        banner.clickType = jsonBanner6.getString("clickType");
                        banner.clickUrl = jsonBanner6.getString("clickUrl");
                        banner.pictureUrl = jsonBanner6.getString("pictureUrl");
                        banner.shareUrl = jsonBanner6.getString("shareUrl");
                        listBanner6.add(banner);
                    }
                    mData.add(new ItemHmMainInformation(listBanner6));
                    JSONArray studio = body.getJSONArray("studio");
                    List<ItemHmMainRecommendStudio.DataBean> listStudio = new ArrayList<>();
                    for (int i = 0; i < studio.length(); i++) {
                        JSONObject jsonStudio = studio.getJSONObject(i);
                        ItemHmMainRecommendStudio.DataBean banner = new ItemHmMainRecommendStudio.DataBean();
                        banner.appExtensionIntro = jsonStudio.getString("appExtensionIntro");
                        banner.headPicture = jsonStudio.getString("headPicture");
                        banner.name = jsonStudio.getString("name");
                        banner.userId = jsonStudio.getString("userId");
                        listStudio.add(banner);
                    }
                    mData.add(new ItemHmMainRecommendStudio(listStudio));
                    JSONArray banner7 = body.getJSONArray("banner7");
                    List<ItemHmMainStarStudio.DataBean> listBanner7 = new ArrayList<>();
                    for (int i = 0; i < banner7.length(); i++) {
                        JSONObject jsonBanner7 = banner7.getJSONObject(i);
                        ItemHmMainStarStudio.DataBean banner = new ItemHmMainStarStudio.DataBean();
                        banner.bannerName = jsonBanner7.getString("bannerName");
                        banner.clickType = jsonBanner7.getString("clickType");
                        banner.clickUrl = jsonBanner7.getString("clickUrl");
                        //banner.pictureUrl = jsonBanner7.getString("pictureUrl");
                        banner.pictureUrl = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1063018429,974188825&fm=200&gp=0.jpg";
                        banner.shareUrl = jsonBanner7.getString("shareUrl");
                        listBanner7.add(banner);
                    }
                    mData.add(new ItemHmMainStarStudio(listBanner7));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void addData() {
        super.addData();
        getData();
    }

    @BindView(R.id.sfRefresh_hmmain)
    SwipeRefreshLayout sfRefresh;

    @Override
    protected void initEvent() {
        super.initEvent();
        sfRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int layoutInflaterId() {
        return R.layout.fragment_hmmain;
    }


    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    private Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", "com.example.huichuanyi", null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", "com.example.huichuanyi");
        }
        return localIntent;
    }
}
