package com.example.huichuanyi.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.MC_MyClothesAdapter2;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.ItemDecoration;
import com.example.huichuanyi.bean.MyClothess;
import com.example.huichuanyi.ui.activity.PicActivity;
import com.example.huichuanyi.utils.MyJson;
import com.example.huichuanyi.utils.User;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MC_TripAndElseActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ImageView mImageViewBack;
    private RecyclerView mRecycleView;
    private List<MyClothess.BodyBean.ClothesInfoBean> mData;
    private MC_MyClothesAdapter2 mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private String word_id;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sorts);

    }

    @Override
    public void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.iv_my_sorts_back);
        mRecycleView = (RecyclerView) findViewById(R.id.rv_my_sorts_pic);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sl_my_sorts_refresh);
        mTextView = (TextView) findViewById(R.id.tv_feilei_zhonglei);
    }

    @Override
    public void initData() {
        mData = new ArrayList<>();
        mAdapter = new MC_MyClothesAdapter2(this, mData);
        String yichu = getIntent().getStringExtra("yichu");
        word_id = getIntent().getStringExtra("word_id");
        mTextView.setText(yichu);
        getData();
    }

    public void getData() {
        RequestParams params = new RequestParams(NetConfig.CHA_KAN_YI_FU);
        params.addBodyParameter("user_id", new User(this).getUseId() + "");
        params.addBodyParameter("clothes_wardrobeId", word_id);
        params.addBodyParameter("clothes_typeId", "%");
        params.addBodyParameter("clothes_situation", "%");
        params.addBodyParameter("clothes_styleId", "%");
        params.addBodyParameter("clothes_season", "%");
        params.addBodyParameter("clothes_caizhi", "%");
        params.addBodyParameter("clothes_move", "1");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                String ret = MyJson.getRet(result);
                mData.clear();
                if (TextUtils.equals("0", ret)) {
                    Gson gson = new Gson();
                    MyClothess myClothess = gson.fromJson(result, MyClothess.class);
                    mData.addAll(myClothess.getBody().getClothesInfo());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //Toast.makeText(MC_TripAndElseActivity.this, R.string.err_internet, Toast.LENGTH_SHORT).show();
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
    public void setData() {
        GridLayoutManager mLayout = new GridLayoutManager(this, 3);
        mRecycleView.setLayoutManager(mLayout);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addItemDecoration(new ItemDecoration(15));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshFriend");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
    }

    @Override
    public void setListener() {
        mImageViewBack.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_my_sorts_back:
                finish();
                break;
            case R.id.iv_item_recycler_3:
                int position = (int) v.getTag();
                Intent intent = new Intent(this, PicActivity.class);
                intent.putExtra("mList", (Serializable) mData);
                intent.putExtra("position", position);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRefresh() {
        mData.clear();
        getData();
        mRefreshLayout.setRefreshing(false);
    }

    // broadcast receiver
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshFriend")) {
                getData();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mRefreshBroadcastReceiver);
    }
}
