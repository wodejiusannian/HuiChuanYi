package com.example.huichuanyi.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.InfoATClothesAdapter;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.modle.Statistics;
import com.example.huichuanyi.utils.User;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class Statistics_fragment_clothes extends BaseFragment {
    private ListView mListView;
    private InfoATClothesAdapter mAdapter;
    private List<Statistics.ListBean> mClothesName;
    private TextView mTextView;
    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.info_fragment_clothes, null);
        getChildView(view);
        return view;
    }

    private void getChildView(View view) {
        mListView = (ListView) view.findViewById(R.id.lv_info_clothes);
        mTextView = (TextView) view.findViewById(R.id.tv_info_clothes);
    }

    @Override
    protected void initData() {
        super.initData();
        mClothesName = new ArrayList<>();
        mAdapter = new InfoATClothesAdapter(mClothesName,getActivity());
        RequestParams params = new RequestParams(NetConfig.STATISTIC_CLOTHES);
        params.addBodyParameter("userid",new User(getActivity()).getUseId()+"");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(TextUtils.equals(result,"0")) {
                    Toast.makeText(getActivity(), "亲，您衣橱还没有衣服哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                Gson gson = new Gson();
                Statistics statistics = gson.fromJson(result, Statistics.class);
                mClothesName.addAll(statistics.getList());
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

    @Override
    protected void setData() {
        super.setData();
        mListView.setAdapter(mAdapter);
    }
}
