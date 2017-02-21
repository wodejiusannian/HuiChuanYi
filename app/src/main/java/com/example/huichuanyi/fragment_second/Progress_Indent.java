package com.example.huichuanyi.fragment_second;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.IndentAdapter;
import com.example.huichuanyi.alipay.AuthResult;
import com.example.huichuanyi.alipay.PayResult;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.bean.Indent;
import com.example.huichuanyi.utils.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Progress_Indent extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, IndentAdapter.OnMyClickInterFace, MySelfDialog.OnYesClickListener {
    private ListView mListView;
    private IndentAdapter mAdapter;
    private List<Indent> mData;
    private String id;
    private SwipeRefreshLayout mRefreshLayout;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(getContext(), "支付成功", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        Toast.makeText(getContext(),
                                "授权成功\n" + String.format("authCode:%s",
                                        authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        Toast.makeText(getContext(),
                                "授权失败" + String.format("authCode:%s",
                                        authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

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
                        if (TextUtils.equals("0", state)) {
                            Indent indent = new Indent();
                            indent.setMoney(object.getString("money"));
                            indent.setState(object.getString("state"));
                            indent.setPhotoUrl(object.getString("photoUrl"));
                            indent.setVideosid(object.getString("videosid"));
                            indent.setVidsSize(object.getString("vidsSize"));
                            indent.setId(object.getString("id"));
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
        int position = (int) view.getTag();
        id = mData.get(position).getId();
        MySelfDialog mDialog = new MySelfDialog(getContext());
        mDialog.setPay(true);
        mDialog.setTitle("选择支付");
        mDialog.setOnNoListener("取消", null);
        mDialog.setOnYesListener("确定", this);
        mDialog.show();
    }

    @Override
    public void onClick() {
        RequestParams params = new RequestParams(NetConfig.INDENT_PAY_VIDEO);
        params.addBodyParameter("userid", new User(getContext()).getUseId() + "");
        params.addBodyParameter("id", id);
        params.addBodyParameter("type", "1");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String get) {
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(getActivity());
                        Map<String, String> result = alipay.payV2(get, true);
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                Thread payThread = new Thread(payRunnable);
                payThread.start();
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
}
