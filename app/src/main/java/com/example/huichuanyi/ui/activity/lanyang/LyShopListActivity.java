package com.example.huichuanyi.ui.activity.lanyang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.LyShopList;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.FiveSwipeRefreshLayout;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.ItemDecoration;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

// ┏┓　　　┏┓
// ┏┛┻━━━┛┻┓
// ┃　　　　　　　┃ 　
// ┃　　　━　　　┃
// ┃　┳┛　┗┳　┃
// ┃　　　　　　　┃
// ┃　　　┻　　　┃
// ┃　　　　　　　┃
// ┗━┓　　　┏━┛
// ┃　　　┃ 神兽保佑　　　　　　　　
// ┃　　　┃ 代码无BUG！
// ┃　　　┗━━━┓
// ┃　　　　　　　┣┓
// ┃　　　　　　　┏┛
// ┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
// ┗┻┛　┗┻┛
public class LyShopListActivity extends BaseActivity implements UtilsInternet.XCallBack, SwipeRefreshLayout.OnRefreshListener {
    /*@BindView(R.id.tb_ly_shoplist)
    TabLayout tb;

    @BindView(R.id.vp_ly_shoplist)
    ViewPager vp;

    private UtilsInternet net = UtilsInternet.getInstance();

    private ClosetAdapter adapter;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    private int pos;*/
    @BindView(R.id.rv_item_shop_list)
    RecyclerView rvContent;

    @BindView(R.id.fr_refresh)
    FiveSwipeRefreshLayout refreshLayout;


    @BindView(R.id.tv_main_ly_list_title)
    TextView mTitle;


    private List<Visitable> mData = new ArrayList<>();

    private MultiTypeAdapter adapter;

    private UtilsInternet net = UtilsInternet.getInstance();

    private Map<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ly_shop_list);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String supplier_id = intent.getStringExtra("supplier_id");
        String title = getIntent().getStringExtra("title");
        // mTitle.setText(title);
        map.put("supplier_id", supplier_id);
        map.put("user_id", SharedPreferenceUtils.getUserData(this, 1));
        adapter = new MultiTypeAdapter(mData);
        initNet();
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        rvContent.setLayoutManager(manager);
        rvContent.setAdapter(adapter);
        rvContent.addItemDecoration(new ItemDecoration(15));
      /*  adapter = new ClosetAdapter(getSupportFragmentManager(), fragments, titles);
        tb.setupWithViewPager(vp);
        vp.setAdapter(adapter);*/
    }

    @Override
    protected void setData() {
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(this);

        /*pos = getIntent().getIntExtra("pos", 0);
        initNet();*/
    }

    /*private void initNet() {
        net.post(NetConfig.LY_MAIN_DATA, null, this);
    }*/

    private void initNet() {
        net.post(NetConfig.LY_GOODS, map, this);
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onResponse(String result) {
        try {
            mData.clear();
            Gson gson = new Gson();
            LyShopList lyShopList = gson.fromJson(result, LyShopList.class);
            mData.addAll(lyShopList.getBody());
            adapter.notifyDataSetChanged();
            if (refreshLayout.isRefreshing())
                refreshLayout.setRefreshing(false);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "请重试", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRefresh() {
        initNet();
    }


    /*@Override
    public void onResponse(String result) {
        try {
            JSONObject json = new JSONObject(result);
            JSONObject body = json.getJSONObject("body");
            JSONArray type = body.getJSONArray("type");
            for (int i = 0; i < type.length(); i++) {
                JSONObject item3 = type.getJSONObject(i);
                titles.add(item3.getString("type_name"));
                LyShopListFragment lyShopListFragment = new LyShopListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", item3.getString("type_id"));
                lyShopListFragment.setArguments(bundle);
                fragments.add(lyShopListFragment);
            }

            adapter.notifyDataSetChanged();
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    vp.setCurrentItem(pos);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
}
