package com.example.huichuanyi.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.BtnChangeAdapter6;
import com.example.huichuanyi.adapter.BtnChangeAdapter_1;
import com.example.huichuanyi.adapter.BtnChangeAdapter_4_1;
import com.example.huichuanyi.adapter.BtnChangeAdapter_5_1;
import com.example.huichuanyi.adapter.MC_MyClothesAdapter2;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.bean.Label;
import com.example.huichuanyi.bean.MyClothess;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.ItemDecoration;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MySortActivity extends BaseActivity implements View.OnClickListener, BtnChangeAdapter_1.GetOcc, CompoundButton.OnCheckedChangeListener, BtnChangeAdapter_4_1.GetStylesssss, BtnChangeAdapter6.GetOccc, BtnChangeAdapter_5_1.GetMaterial, SwipeRefreshLayout.OnRefreshListener {
    private ImageView mBack, mFilter;
    private List<MyClothess.BodyBean.ClothesInfoBean> mData;
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private RecyclerView mOccasion, mStyle, mMaterial, mSeason, mContent;
    private TextView mShowOccasion, mShowStyle, mShowMaterial, mShowSeason, mTitle;
    private String[] occasion = {"商务", "休闲", "社交"};
    private List<Label.CloStyleBean> mStyles;
    private List<Label.CloQualityBean> mMaterials;
    private List<String> mSeasons;
    private String zhonglei, jsons, tag, user_id, style, season, mmaterial, mOcc, yichuzhonglei;
    private BtnChangeAdapter_4_1 adapterStyle;
    private BtnChangeAdapter_5_1 adapterMaterial;
    private BtnChangeAdapter6 adapterSeason;
    private MC_MyClothesAdapter2 mAdapter;
    private ToggleButton mBtnStyle, mBtnMaterial, mBtnSeason;
    private SwipeRefreshLayout mFresh;
    private Button btnSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sort);

    }


    @Override
    public void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mBack = (ImageView) findViewById(R.id.iv_sort_back);
        mFilter = (ImageView) findViewById(R.id.iv_sort_filter);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        mOccasion = (RecyclerView) findViewById(R.id.rv_drawer_occasion);
        mShowOccasion = (TextView) findViewById(R.id.tv_drawer_show_occasion);
        mStyle = (RecyclerView) findViewById(R.id.rv_drawer_style);
        mBtnStyle = (ToggleButton) findViewById(R.id.tg_drawer_style);
        mShowStyle = (TextView) findViewById(R.id.tv_drawer_show_style);
        mMaterial = (RecyclerView) findViewById(R.id.rv_drawer_material);
        mShowMaterial = (TextView) findViewById(R.id.tv_drawer_show_material);
        mBtnMaterial = (ToggleButton) findViewById(R.id.tg_drawer_material);
        mSeason = (RecyclerView) findViewById(R.id.rv_drawer_season);
        mShowSeason = (TextView) findViewById(R.id.tv_drawer_show_season);
        mBtnSeason = (ToggleButton) findViewById(R.id.tg_drawer_season);
        mTitle = (TextView) findViewById(R.id.tv_toolbar_title);
        mContent = (RecyclerView) findViewById(R.id.rv_drawer_content);
        mFresh = (SwipeRefreshLayout) findViewById(R.id.sf_drawer_refresh);
        btnSure = (Button) findViewById(R.id.btn_drawer_sure);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        zhonglei = intent.getStringExtra("zhonglei");
        tag = intent.getStringExtra("tag");
        yichuzhonglei = intent.getStringExtra("yichuzhonglei");
        user_id = SharedPreferenceUtils.getUserData(this,1);
        SharedPreferences hqSysCloTag = getSharedPreferences("hqSysCloTag", 0);
        jsons = hqSysCloTag.getString("hqSysCloTag", "");
        mStyles = new ArrayList<>();
        mMaterials = new ArrayList<>();
        mSeasons = new ArrayList<>();
        mData = new ArrayList<>();
        mAdapter = new MC_MyClothesAdapter2(this, mData);
        initOccasion();
        initStyle();
        initMaterial();
        initSeason();
    }

    //场合的数据
    private void initOccasion() {
        BtnChangeAdapter_1 adapterOcc = new BtnChangeAdapter_1(this, occasion);
        GridLayoutManager mManager = new GridLayoutManager(this, 3);
        mOccasion.setLayoutManager(mManager);
        mOccasion.setAdapter(adapterOcc);
        mOccasion.addItemDecoration(new ItemDecoration(10));
        adapterOcc.setGetOcc(this);
    }

    private void initStyle() {
        adapterStyle = new BtnChangeAdapter_4_1(this, mStyles);
        GridLayoutManager mManager = new GridLayoutManager(this, 3);
        mStyle.setLayoutManager(mManager);
        mStyle.setAdapter(adapterStyle);
        mStyle.addItemDecoration(new ItemDecoration(10));
        getStyles(3);
        adapterStyle.setGetStyle(this);
    }

    private void initMaterial() {
        adapterMaterial = new BtnChangeAdapter_5_1(this, mMaterials);
        GridLayoutManager mManager = new GridLayoutManager(this, 3);
        mMaterial.setLayoutManager(mManager);
        mMaterial.setAdapter(adapterMaterial);
        mMaterial.addItemDecoration(new ItemDecoration(10));
        getMaterials(3);
        adapterMaterial.setGetMaterial(this);
    }

    //获取和处理季节的方法
    private void initSeason() {
        mSeasons.add("春秋");
        mSeasons.add("夏");
        mSeasons.add("冬");
        adapterSeason = new BtnChangeAdapter6(this, mSeasons);
        GridLayoutManager mManager = new GridLayoutManager(this, 3);
        mSeason.setLayoutManager(mManager);
        mSeason.setAdapter(adapterSeason);
        mSeason.addItemDecoration(new ItemDecoration(10));
        adapterSeason.setGetOcc(this);
    }

    //材质数据的获取
    private void getMaterials(int j) {
        mMaterials.clear();
        try {
            JSONObject object = new JSONObject(jsons);
            JSONObject body = object.getJSONObject("body");
            JSONArray cloQuality = body.getJSONArray("cloQuality");
            for (int i = 0; i < cloQuality.length(); i++) {
                if (j == 3 && mMaterials.size() == 3) {
                    break;
                }
                JSONObject object1 = cloQuality.getJSONObject(i);
                Label.CloQualityBean bean = new Label.CloQualityBean();
                bean.setCloQuality_name(object1.getString("cloQuality_name"));
                mMaterials.add(bean);
            }
            adapterMaterial.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //款式数据的获取
    private void getStyles(int j) {
        mStyles.clear();
        try {
            JSONObject object = new JSONObject(jsons);
            JSONObject body = object.getJSONObject("body");
            JSONArray cloStyle = body.getJSONArray("cloStyle");
            for (int i = 0; i < cloStyle.length(); i++) {
                JSONObject arr = cloStyle.getJSONObject(i);
                if (j == 3 && mStyles.size() == 3) {
                    break;
                }
                if (TextUtils.equals(tag, arr.getString("cloType_id"))) {
                    Label.CloStyleBean style = new Label.CloStyleBean();
                    style.setCloStyle_name(arr.getString("cloStyle_name"));
                    style.setCloStyle_id(arr.getString("cloStyle_id"));
                    mStyles.add(style);
                }

            }
            adapterStyle.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setData() {
        mTitle.setText(zhonglei);
        GridLayoutManager mManager = new GridLayoutManager(this, 3);
        mContent.setLayoutManager(mManager);
        mContent.setAdapter(mAdapter);
        mContent.addItemDecoration(new ItemDecoration(10));
        getData("", "", "", "");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshFriend");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
    }

    @Override
    public void setListener() {
        mFilter.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mBtnStyle.setOnCheckedChangeListener(this);
        mBtnMaterial.setOnCheckedChangeListener(this);
        mBtnSeason.setOnCheckedChangeListener(this);
        mFresh.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
        btnSure.setOnClickListener(this);
        mShowMaterial.setOnClickListener(this);
        mShowOccasion.setOnClickListener(this);
        mShowStyle.setOnClickListener(this);
        mShowSeason.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_drawer_show_material:
                mShowMaterial.setVisibility(View.GONE);
                mmaterial = "";
                break;
            case R.id.tv_drawer_show_season:
                mShowSeason.setVisibility(View.GONE);
                season = "";
                break;
            case R.id.tv_drawer_show_style:
                mShowStyle.setVisibility(View.GONE);
                style = "";
                break;
            case R.id.tv_drawer_show_occasion:
                mShowOccasion.setVisibility(View.GONE);
                mOcc = "";
                break;
            case R.id.iv_sort_filter:
                mDrawer.openDrawer(Gravity.RIGHT);
                break;
            case R.id.iv_sort_back:
                finish();
                break;
            case R.id.iv_item_recycler_3:
                int position = (int) v.getTag();
                Intent intent = new Intent(this, WDYCPicActivity.class);
                intent.putExtra("mList", (Serializable) mData);
                intent.putExtra("position", position);
                intent.putExtra("yichuzhonglei", yichuzhonglei);
                startActivity(intent);
                break;
            case R.id.btn_drawer_sure:
                if (TextUtils.isEmpty(mmaterial) && TextUtils.isEmpty(season)
                        && TextUtils.isEmpty(style) && TextUtils.isEmpty(mOcc)) {
                    Toast.makeText(MySortActivity.this, "请选择筛选条件", Toast.LENGTH_SHORT).show();
                } else {
                    getData(mOcc, style, season, mmaterial);
                    mDrawer.closeDrawer(Gravity.RIGHT);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void getOcc(String occ, int pos) {
        mOcc = occ;
        mShowOccasion.setVisibility(View.VISIBLE);
        mShowOccasion.setText(occ);
    }

    @Override
    public void getStylesssss(String id, String name, int pos) {
        style = id;
        mShowStyle.setVisibility(View.VISIBLE);
        mShowStyle.setText(name);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.tg_drawer_style:
                if (isChecked) {
                    getStyles(2);
                } else {
                    getStyles(3);
                }
                break;
            case R.id.tg_drawer_material:
                if (isChecked) {
                    getMaterials(2);
                } else {
                    getMaterials(3);
                }
                break;
            case R.id.tg_drawer_season:
                if (isChecked) {
                    mSeasons.add("四季");
                } else {
                    mSeasons.remove(mSeasons.size() - 1);
                }
                adapterSeason.notifyDataSetChanged();
                break;
            default:

                break;
        }
    }

    @Override
    public void getOccc(String occc) {
        season = occc;
        mShowSeason.setVisibility(View.VISIBLE);
        mShowSeason.setText(occc);
    }

    @Override
    public void getMaterial(String material) {
        mmaterial = material;
        mShowMaterial.setVisibility(View.VISIBLE);
        mShowMaterial.setText(material);
    }

    @Override
    public void onRefresh() {
        getData("", "", "", "");
        mFresh.setRefreshing(false);
    }

    public void getData(String occ, String style, String season, String material) {
        RequestParams params = new RequestParams(NetConfig.CHA_KAN_YI_FU);
        params.addBodyParameter("user_id", user_id);
        params.addBodyParameter("clothes_wardrobeId", "1");
        params.addBodyParameter("clothes_typeId", tag);
        params.addBodyParameter("clothes_situation", occ);
        params.addBodyParameter("clothes_styleId", style);
        params.addBodyParameter("clothes_season", season);
        params.addBodyParameter("clothes_caizhi", material);
        params.addBodyParameter("clothes_move", "1");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                String ret = JsonUtils.getRet(result);
                mData.clear();
                if (TextUtils.equals("0", ret)) {
                    Gson gson = new Gson();
                    MyClothess myClothess = gson.fromJson(result, MyClothess.class);
                    mData.addAll(myClothess.getBody().getClothesInfo());
                }
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


    // broadcast receiver
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshFriend")) {
                getData("", "", "", "");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mRefreshBroadcastReceiver);
    }

}
