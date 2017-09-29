package com.example.huichuanyi.ui.activity.lanyang;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.LyShopList;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.FiveSwipeRefreshLayout;
import com.example.huichuanyi.utils.ItemDecoration;
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
public class LyShopListFragment extends BaseFragment implements UtilsInternet.XCallBack, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_item_shop_list)
    RecyclerView rvContent;

    @BindView(R.id.fr_refresh)
    FiveSwipeRefreshLayout refreshLayout;

    private List<Visitable> mData = new ArrayList<>();

    private MultiTypeAdapter adapter;

    private UtilsInternet net = UtilsInternet.getInstance();

    private Map<String, String> map = new HashMap<>();

    @Override
    protected View initView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_ly_shop_list, null);
    }

    @Override
    protected void initData() {
        super.initData();
        String id = getArguments().getString("id");
        map.put("type_id", id);
        adapter = new MultiTypeAdapter(mData);
        initNet();
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        rvContent.setLayoutManager(manager);
        rvContent.setAdapter(adapter);
        rvContent.addItemDecoration(new ItemDecoration(15));
    }

    @Override
    protected void setData() {
        super.setData();
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initNet() {
        net.post(NetConfig.LY_GOODS, map, this);
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
            Toast.makeText(getContext(), "请重试", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRefresh() {
        initNet();
    }
}
