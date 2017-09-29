package com.example.huichuanyi.ui.activity.lanyang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.GetCity;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.LyCommendPeople;
import com.example.huichuanyi.common_view.model.LyTest;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.activity.LiJiYuYueStudioSelectCityActivity;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.ItemDecoration;
import com.example.huichuanyi.utils.UtilsInternet;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class LyCommendPeopleActivity extends BaseActivity implements UtilsInternet.XCallBack, GetCity.WillGetCity {

    private static final String TAG = "LyCommendPeople";

    @BindView(R.id.rv_ly_commendpeople_content)
    RecyclerView rvContent;

    @BindViews({R.id.tv_ly_commendpeople_city})
    TextView[] tvs;

    private MultiTypeAdapter adapter;

    private List<Visitable> mData = new ArrayList<>();

    private UtilsInternet net = UtilsInternet.getInstance();

    private Map<String, String> map = new HashMap<>();

    private GetCity mGetCity;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ly_commend_people);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        dialog = new ProgressDialog(this);
        dialog.show();
        mGetCity = new GetCity(this);
        mGetCity.setGetCity(this);
        mGetCity.startLocation();
        adapter = new MultiTypeAdapter(mData);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(adapter);
        rvContent.addItemDecoration(new ItemDecoration(10));
    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.tv_ly_commendpeople_city})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.tv_ly_commendpeople_city:
                Intent intent = new Intent(new Intent(this,
                        LiJiYuYueStudioSelectCityActivity.class));
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }
    }

    private void initNet() {
        if (!dialog.isShowing())
            dialog.show();
        net.post(NetConfig.LY_SHOP_STUDIO_NAME, map, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String mAdd = data.getStringExtra("address");
            if (!CommonUtils.isEmpty(mAdd)) {
                map.put("city", mAdd);
                initNet();
                tvs[0].setText(mAdd);
            }
        }
    }

    @Override
    public void onResponse(String result) {
        try {
            mData.clear();
            LyTest lyTest = new LyTest();
            lyTest.setStr("不选择");
            mData.add(lyTest);
            Gson gson = new Gson();
            LyCommendPeople lyCommendPeople = gson.fromJson(result, LyCommendPeople.class);
            mData.addAll(lyCommendPeople.getBody());
            if (dialog.isShowing())
                dialog.dismiss();
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "请重试", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getWillGetCity(final String city, String lat, String lng) {
        map.put("city", city);
        if (!CommonUtils.isEmpty(city)) {
            mGetCity.stopLocation();
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString("city", city);
            msg.setData(bundle);
            mHandler.sendMessage(msg);
        }
        initNet();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String city = bundle.getString("city");
            tvs[0].setText(city);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGetCity.stopLocation();
        if (dialog.isShowing())
            dialog.dismiss();
    }
}
