package com.example.huichuanyi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.MatchAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.bean.Match;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.secondui.ShowPhotoActivity;
import com.example.huichuanyi.ui.activity.home.CollocationDiaryActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeDaPeiRiJiActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private Button mButtonAdd;
    private ListView mListView;
    private List<Match.EvaluatesBean> mData;
    private MatchAdapter mAdapter;
    private String type = "0";
    private SwipeRefreshLayout mRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
    }

    @Override
    public void initView() {
        mButtonAdd = (Button) findViewById(R.id.btn_match_add);
        mListView = (ListView) findViewById(R.id.lv_match_show);
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.sf_match_refresh);
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void initData() {
        getData("0");
        mData = new ArrayList<>();
        mAdapter = new MatchAdapter(mData, this);
    }

    @Override
    public void setData() {
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void setListener() {
        mButtonAdd.setOnClickListener(this);
        mListView.setOnItemClickListener(this);
        mRefresh.setOnRefreshListener(this);
        registerForContextMenu(mListView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_match_add:
                startActivity(new Intent(this, CollocationDiaryActivity.class));
                break;
        }
    }

    public void getData(String wardrobe_id) {

        RequestParams params = new RequestParams(NetConfig.MATCH_DIARY);
        params.addBodyParameter("userid", SharedPreferenceUtils.getUserData(this, 1));
        params.addBodyParameter("wardrobe_id", wardrobe_id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if ("0".equals(result)) {
                    Toast.makeText(HomeDaPeiRiJiActivity.this, "亲，您这个衣橱还没有衣服哦，赶快去添加衣服吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                mData.clear();
                Gson gson = new Gson();
                Match match = gson.fromJson(result, Match.class);
                mData.addAll(match.getEvaluates());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(HomeDaPeiRiJiActivity.this, "获取数据失败，请刷新重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object> map = new HashMap<>();
        String getshowpic = mData.get(position).getGetlogopic();
        String getshowpic1 = mData.get(position).getGetshowpic();
        String fourpic = mData.get(position).getFourpic();
        String time = mData.get(position).getTime();
        map.put("image1", getshowpic);
        map.put("getshowpic1", getshowpic1);
        map.put("daipei", fourpic);
        map.put("time", time);
        ActivityUtils.switchTo(this, ShowPhotoActivity.class, map);
    }


    @Override
    public void onRefresh() {
        getData(type);
        mRefresh.setRefreshing(false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("是否删除");
        //添加菜单项
        menu.add(0, Menu.FIRST, 0, "删除");
        menu.add(0, Menu.FIRST + 1, 0, "取消");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int i = info.position;
        int itemId = item.getItemId();
        if (itemId == 1) {
            RequestParams params = new RequestParams(NetConfig.DELETE_MATCH);
            params.addBodyParameter("userid", SharedPreferenceUtils.getUserData(this, 1));
            params.addBodyParameter("id", mData.get(i).getId());
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if (TextUtils.equals("1", result)) {
                        Toast.makeText(HomeDaPeiRiJiActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        mData.remove(i);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(HomeDaPeiRiJiActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    //Toast.makeText(HomeDaPeiRiJiActivity.this, R.string.err_internet, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }
        return super.onContextItemSelected(item);
    }
}
