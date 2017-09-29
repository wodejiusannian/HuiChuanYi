package com.example.huichuanyi.ui.activity.lanyang;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.LyBanner;
import com.example.huichuanyi.common_view.model.LyItemShop;
import com.example.huichuanyi.common_view.model.LyItemSort;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.FiveSwipeRefreshLayout;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LyMainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, UtilsInternet.XCallBack {

    private static final String TAG = "LyMainActivity";
    @BindView(R.id.rv_langyang_content)
    RecyclerView rvContent;

    @BindView(R.id.sf_langyang_refrsh)
    FiveSwipeRefreshLayout sfRefresh;

    private MultiTypeAdapter adapter;

    private List<Visitable> mData = new ArrayList<>();

    private UtilsInternet internet = UtilsInternet.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ly_main);

    }

    @Override
    public void setListener() {
        initNet();
    }


    @Override
    public void initData() {
        sfRefresh.setRefreshing(true);
        sfRefresh.setOnRefreshListener(this);
        adapter = new MultiTypeAdapter(mData);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(adapter);
    }

    @Override
    public void setData() {

    }

    @Override
    public void onRefresh() {
        initNet();
    }

    public void back(View view) {
        finish();
    }

    private void initNet() {
        internet.post(NetConfig.LY_MAIN_DATA, null, this);
    }

    @Override
    public void onResponse(String result) {
        try {
            mData.clear();
            JSONObject json = new JSONObject(result);
            JSONObject body = json.getJSONObject("body");
            JSONObject img = body.getJSONObject("img");
            JSONArray item_1 = img.getJSONArray("item_1");
            List<LyBanner.item_1> list = new ArrayList<>();
            for (int i = 0; i < item_1.length(); i++) {
                LyBanner.item_1 lb = new LyBanner.item_1();
                JSONObject item1 = item_1.getJSONObject(i);
                lb.setId(item1.getString("goods_id"));
                lb.setPic_url(item1.getString("pic_url"));
                list.add(lb);
            }
            LyBanner banner = new LyBanner();
            banner.setClothesInfo(list);
            JSONArray item_2 = img.getJSONArray("item_2");
            List<LyItemShop.item_2> list2 = new ArrayList<>();
            for (int i = 0; i < item_2.length(); i++) {
                LyItemShop.item_2 lb1 = new LyItemShop.item_2();
                JSONObject item1 = item_2.getJSONObject(i);
                lb1.setId(item1.getString("goods_id"));
                lb1.setPic_url(item1.getString("pic_url"));
                list2.add(lb1);
            }
            LyItemShop lyItemShop = new LyItemShop();
            lyItemShop.setClothesInfo(list2);
            JSONArray item_3 = img.getJSONArray("item_3");
            List<LyItemShop.item_2> list3 = new ArrayList<>();
            for (int i = 0; i < item_3.length(); i++) {
                LyItemShop.item_2 lb1 = new LyItemShop.item_2();
                JSONObject item1 = item_3.getJSONObject(i);
                lb1.setId(item1.getString("goods_id"));
                lb1.setPic_url(item1.getString("pic_url"));
                list3.add(lb1);
            }
            LyItemShop lyItemShop1 = new LyItemShop();
            lyItemShop1.setClothesInfo(list3);
            mData.add(banner);
            mData.add(lyItemShop);
            mData.add(lyItemShop1);
            JSONArray type = body.getJSONArray("type");
            for (int i = 0; i < type.length(); i++) {
                LyItemSort lyItemSort = new LyItemSort();
                JSONObject item3 = type.getJSONObject(i);
                lyItemSort.setId(item3.getString("type_id"));
                lyItemSort.setName(item3.getString("type_name"));
                lyItemSort.setPos(i);
                mData.add(lyItemSort);
            }
            if (sfRefresh.isRefreshing())
                sfRefresh.setRefreshing(false);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
