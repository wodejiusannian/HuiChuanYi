package com.example.huichuanyi.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.PersonAdapter;
import com.example.huichuanyi.baidumap.Location;
import com.example.huichuanyi.bean.City;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.ui.activity.ManageActivity;
import com.example.huichuanyi.ui.base.BaseFragment;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.google.gson.Gson;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 小五 on 2017/4/7.
 */

@ContentView(R.layout.fragment_lijiyuyue_default)
public class LiJiYuYueDefaultFragment extends BaseFragment implements UtilsInternet.XCallBack, SwipeRefreshLayout.OnRefreshListener {

    @ViewInject(R.id.sf_default_refresh)
    private SwipeRefreshLayout refreshLayout;
    @ViewInject(R.id.lv_default_studios)
    private ListView studios;


    private List<City.BodyBean> mData = new ArrayList<>();
    private Map<String, String> value = new HashMap<>();

    private UtilsInternet net = UtilsInternet.getInstance();
    private PersonAdapter adapter;

    @ViewInject(R.id.iv_nobody)
    private ImageView noBody;
    @ViewInject(R.id.ll_no_body)
    private LinearLayout mllnoBody;

    @Override
    protected void initView() {
        super.initView();
        if (mData == null)
            mData = new ArrayList<>();
        if (value == null)
            value = new HashMap<>();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("refreshstudio");
        getActivity().registerReceiver(mRefreshBroadcastReceiver, intentFilter);
    }

    @Override
    protected void initData() {
        super.initData();
        adapter = new PersonAdapter(getContext(), mData, R.layout.order_person);
        studios.setAdapter(adapter);
        loadData();
    }

    private void loadData() {
        value.put("city", Location.mAddress);
        value.put("type", "15");
        value.put("lng", Location.lng);
        value.put("lat", Location.lat);
        value.put("user_id", SharedPreferenceUtils.getUserData(getContext(), 1));
        net.post(NetConfig.GET_STUDIO_LIST, value, this);
    }

    ;

    @Override
    protected void initEvent() {
        super.initEvent();
        refreshLayout.setOnRefreshListener(this);
        studios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City.BodyBean bodyBean = mData.get(position);
                String service = bodyBean.getService();
                if (TextUtils.equals("已开通", service)) {
                    jump(bodyBean);
                } else {
                    showBusyDialog();
                }
            }
        });
    }

    /*
    * 已开通，我们跳转下
    * */
    private void jump(City.BodyBean bodyBean) {
        if (bodyBean != null) {
            Intent intent = new Intent(getContext(), ManageActivity.class);
            intent.putExtra("bodyBean", bodyBean);
            startActivity(intent);
        }
    }

    @Override
    public void onResponse(String result) {
        String s = JsonUtils.getRet(result);
        mData.clear();
        if (TextUtils.equals("0", s)) {
            Message message = Message.obtain();
            Gson gson = new Gson();
            City city = gson.fromJson(result, City.class);
            if (city.getBody().size() == 0) {
                message.what = 1;
            } else {
                message.what = 2;
            }
            mHandler.sendMessage(message);
            mData.addAll(city.getBody());
            adapter.notifyDataSetChanged();
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mllnoBody.setVisibility(View.VISIBLE);
                    noBody.setImageResource(R.mipmap.include_busy);
                    break;
                case 2:
                    mllnoBody.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onRefresh() {
        loadData();
        refreshLayout.setRefreshing(false);
    }


    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("refreshstudio")) {
                loadData();
            }
        }
    };

    /*
   * 没有开通的工作室提示
   * */
    private void showBusyDialog() {
        MySelfDialog mySelfDialog = new MySelfDialog(getContext());
        mySelfDialog.setTitle("温馨提示");
        mySelfDialog.setMessage("该工作室忙,请选择其他工作室");
        mySelfDialog.setOnYesListener("确定", null);
        mySelfDialog.setOnNoListener("取消", null);
        mySelfDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mRefreshBroadcastReceiver);
    }
}
