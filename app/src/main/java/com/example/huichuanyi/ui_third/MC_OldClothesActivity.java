package com.example.huichuanyi.ui_third;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.MC_OldAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.utils.ItemDecoration;
import com.example.huichuanyi.modle.MyClothess;
import com.example.huichuanyi.utils.MyJson;
import com.example.huichuanyi.utils.User;
import com.example.huichuanyi.utils.UtilsInternet;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MC_OldClothesActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, UtilsInternet.XCallBack {
    private ImageView mImageViewBack;
    private RecyclerView mRecycleView;
    private List<MyClothess.BodyBean.ClothesInfoBean> mData;
    private MC_OldAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private TextView mTextView,mTextViewALL;
    private String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_old_clothes);

    }

    @Override
    public void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.iv_my_sort_back);
        mRecycleView = (RecyclerView) findViewById(R.id.rv_my_sort_pic);
        mRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.sl_my_sort_refresh);
        mTextView = (TextView) findViewById(R.id.tv_feilei_zhonglei);
        mTextViewALL = (TextView) findViewById(R.id.tv_old_clothes_all);
    }

    @Override
    public void initData() {
        user_id = new User(this).getUseId()+"";
        mData = new ArrayList<>();
        mAdapter = new MC_OldAdapter(this,mData);
        mTextView.setText("旧衣回收");
        getData();
    }
    public  void  getData(){
        Map<String,String> map = new HashMap<>();
        map.put("user_id",user_id);
        map.put("clothes_wardrobeId","");
        map.put("clothes_situation","");
        map.put("clothes_styleId","");
        map.put("clothes_season","");
        map.put("clothes_caizhi","");
        map.put("clothes_move","0");
        UtilsInternet.getInstance().post(NetConfig.CHA_KAN_YI_FU,map,this);
    }
    @Override
    public void setData() {
        GridLayoutManager mLayout = new GridLayoutManager(this,3);
        mRecycleView.setLayoutManager(mLayout);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addItemDecoration(new ItemDecoration(15));
    }

    @Override
    public void setListener() {
        mImageViewBack.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        mTextViewALL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.iv_my_sort_back:
                finish();
                break;
            case R.id.tv_old_clothes_all:
                MySelfDialog mySelfDialog = new MySelfDialog(this);
                mySelfDialog.setOnNoListener("取消",null);
                mySelfDialog.setOnYesListener("确定", new MySelfDialog.OnYesClickListener() {
                    @Override
                    public void onClick() {
                        ReStoreAll();
                    }
                });
                mySelfDialog.setMessage("确认全部还原吗？");
                mySelfDialog.show();
                break;

        }
    }

    private void ReStoreAll() {
        RequestParams params = new RequestParams(NetConfig.RESTORE_ALL);
        params.addBodyParameter("userid",user_id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(TextUtils.equals("1",result)) {
                    Toast.makeText(MC_OldClothesActivity.this, "还原成功", Toast.LENGTH_SHORT).show();
                    mData.clear();
                    mAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(MC_OldClothesActivity.this, "还原失败", Toast.LENGTH_SHORT).show();
                }
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

    @Override
    public void onRefresh() {
        getData();
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResponse(String result) {
        String ret = MyJson.getRet(result);
        if (TextUtils.equals("0",ret)){
            mData.clear();
            Gson gson = new Gson();
            MyClothess myClothess = gson.fromJson(result, MyClothess.class);
            mData.addAll(myClothess.getBody().getClothesInfo());
            mAdapter.notifyDataSetChanged();
        }
    }
}
