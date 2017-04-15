package com.example.huichuanyi.fragment_second;

import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.IndentAdapter;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.bean.Indent;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.activity.HomeVideoCoverActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class Over_Indent extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, IndentAdapter.OnMyClickInterFace {
    private ListView mListView;
    private IndentAdapter mAdapter;
    private List<Indent> mData;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_progress, null);
        getChildView(view);
        return view;
    }

    private void getChildView(View view) {
        mListView = (ListView) view.findViewById(R.id.lv_fragment_progress_list);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sf_fragment_progress_refresh);
    }

    @Override
    protected void initData() {
        super.initData();
        mData = new ArrayList<>();
        mAdapter = new IndentAdapter(mData, getActivity());
        getData();
    }

    private void getData() {
        RequestParams params = new RequestParams(NetConfig.INDENT_URL);
        String userid = new User(getActivity()).getUseId() + "";
        params.addBodyParameter("userid", userid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.equals("0", result)) {
                    return;
                }
                mData.clear();
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray list = obj.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject object = list.getJSONObject(i);
                        String state = object.getString("state");
                        if (TextUtils.equals("1", state)) {
                            Indent indent = new Indent();
                            indent.setMoney(object.getString("money"));
                            indent.setState(object.getString("state"));
                            indent.setPhotoUrl(object.getString("photoUrl"));
                            indent.setVideosid(object.getString("videosid"));
                            mData.add(indent);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
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

    @Override
    protected void initEvent() {
        super.initEvent();
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter.setCallBack(this);
    }

    @Override
    protected void setData() {
        super.setData();
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        getData();
        mRefreshLayout.setRefreshing(false);
    }


    @Override
    public void setClickListener(View view) {
        ActivityUtils.switchTo(getActivity(), HomeVideoCoverActivity.class);
    }
}
