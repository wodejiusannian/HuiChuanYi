package com.example.huichuanyi.fragment_second;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.IndentAdapter;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.LyListIndent;
import com.example.huichuanyi.common_view.model.LyListIndentScroll;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MySelfPayDialog;
import com.example.huichuanyi.ui.activity.HomeVideoCoverActivity;
import com.example.huichuanyi.ui.activity.pay.CMBPayActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.IsSuccess;
import com.example.huichuanyi.utils.ItemDecoration;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsPay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Over_Indent extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, IndentAdapter.OnMyClickInterFace, IsSuccess, MySelfPayDialog.OnYesClickListener {

    @BindView(R.id.rv_fragment_indent_content)
    RecyclerView content;

    @BindView(R.id.sf_fragment_progress_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private List<Visitable> mData;

    private MultiTypeAdapter mAdapter;

    private String aliOrWeChat, order_id;
    private String[] states = {"10", "11", "12", "20", "30,", "40"};
    private UtilsPay pay;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_progress_over, null);
        return view;
    }


    @Override
    protected void initData() {
        super.initData();
        mData = new ArrayList<>();
        mAdapter = new MultiTypeAdapter(mData);
        content.setLayoutManager(new LinearLayoutManager(getContext()));
        content.setAdapter(mAdapter);
        content.addItemDecoration(new ItemDecoration(10));
        getData();
    }

    private void getData() {
        JSONObject mydata = new JSONObject();
        try {
            mydata.put("user_id", SharedPreferenceUtils.getUserData(getContext(), 1));
            JSONArray array = new JSONArray();
            for (int i = 0; i < states.length; i++) {
                JSONObject o = new JSONObject();
                o.put("state", states[i]);
                array.put(o);
            }
            mydata.put("states", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestParams params = new RequestParams(NetConfig.LY_SHOP_LIST);
        params.addBodyParameter("mydata", mydata.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    mData.clear();
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray body = jsonObject.getJSONArray("body");
                    for (int i = 0; i < body.length(); i++) {
                        JSONObject object = body.getJSONObject(i);
                        String pic_url = object.getString("pic_url");
                        if (pic_url.contains(",")) {
                            LyListIndentScroll scroll = new LyListIndentScroll();
                            scroll.setNum(object.getInt("num"));
                            scroll.setName(object.getString("name"));
                            scroll.setMoney_one(object.getDouble("money_now"));
                            scroll.setMoney_pay(object.getDouble("money_pay"));
                            scroll.setOrder_id(object.getString("order_id"));
                            scroll.setPic_url(object.getString("pic_url"));
                            scroll.setState(object.getString("state"));
                            mData.add(scroll);
                        } else {
                            LyListIndent indent = new LyListIndent();
                            indent.setNum(object.getInt("num"));
                            indent.setName(object.getString("name"));
                            indent.setMoney_one(object.getDouble("money_now"));
                            indent.setMoney_pay(object.getDouble("money_pay"));
                            indent.setOrder_id(object.getString("order_id"));
                            indent.setPic_url(object.getString("pic_url"));
                            indent.setState(object.getString("state"));
                            mData.add(indent);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "请刷新重试", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getContext(), "请刷新重试", Toast.LENGTH_SHORT).show();
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
        mAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
                Visitable visitable = mData.get(tag);
                if (visitable instanceof LyListIndent) {
                    LyListIndent in = (LyListIndent) visitable;
                    order_id = in.getOrder_id();
                } else if (visitable instanceof LyListIndentScroll) {
                    LyListIndentScroll in = (LyListIndentScroll) visitable;
                    order_id = in.getOrder_id();
                }
                showDialog();
            }
        });
    }

    @Override
    protected void setData() {
        super.setData();
        pay = new UtilsPay(getContext());
        pay.isSuccess(this);
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

    @Override
    public void onClick(String tag) {
        aliOrWeChat = tag;
        switch (tag) {
            case "1":
                RequestParams params = new RequestParams(NetConfig.LY_SHOP_GET_SIGN);
                params.addBodyParameter("order_id", order_id);
                params.addBodyParameter("pay_type", "1");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject object = new JSONObject(result);
                            JSONObject body = object.getJSONObject("body");
                            String sign = body.getString("sign");
                            pay.aliPay(sign);
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                break;
            case "2":
                RequestParams pa = new RequestParams(NetConfig.LY_SHOP_GET_SIGN);
                pa.addBodyParameter("order_id", order_id);
                pa.addBodyParameter("pay_type", "2");
                x.http().post(pa, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        pay.weChatPay(result);
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
                break;
            case "3":
                RequestParams pap = new RequestParams(NetConfig.LY_SHOP_GET_SIGN);
                pap.addBodyParameter("order_id", order_id);
                pap.addBodyParameter("pay_type", "3");
                x.http().post(pap, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Intent intent = new Intent(getContext(), CMBPayActivity.class);
                        intent.putExtra("order_id", result);
                        startActivity(intent);
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
                break;
            default:
                break;
        }
    }

    @Override
    public void isSuccess(int success) {
        if ("1".equals(aliOrWeChat)) {
            switch (success) {
                case 9000:
                    CommonUtils.Toast(getContext(), "支付成功");
                    getActivity().finish();
                    break;
                case 9001:
                    CommonUtils.Toast(getContext(), "支付失败");
                    break;
                default:
                    break;
            }
        }
    }

    private void showDialog() {
        MySelfPayDialog mySelfPayDialog = new MySelfPayDialog(getContext());
        mySelfPayDialog.setOnNoListener("取消", null);
        mySelfPayDialog.setOnYesListener("确定", this);
        mySelfPayDialog.setTitle("选择支付");
        mySelfPayDialog.show();
    }
}
