package com.example.huichuanyi.ui.activity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.RTCReport;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.ItemDecoration;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class RTCReportActivity extends BaseActivity {

    @BindView(R.id.sf_activity_rtc_fresh)
    SwipeRefreshLayout freshView;

    @BindView(R.id.rv_activity_rtc_content)
    RecyclerView contentView;

    private MultiTypeAdapter adapter;

    private List<Visitable> mData = new ArrayList<>();

    private UtilsInternet net = UtilsInternet.getInstance();

    private Map<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtcreport);
    }

    @Override
    protected void setListener() {
        freshView.setRefreshing(true);
        loadingData();
        freshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadingData();
            }
        });
    }

    @Override
    protected void initData() {
        adapter = new MultiTypeAdapter(mData);
        map.put("user_id", SharedPreferenceUtils.getUserData(this, 1));
    }

    @Override
    protected void setData() {
        contentView.setLayoutManager(new LinearLayoutManager(this));
        contentView.setAdapter(adapter);
        contentView.addItemDecoration(new ItemDecoration(20));
        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String position = (String) v.getTag();
                String[] split = position.split("=");
                int anInt = Integer.parseInt(split[0]);
                onSuccessResult(anInt, split[1]);
            }
        });
    }

    /*
    * 获取rct报告
    * */
    private void onSuccessResult(int anInt, final String tag) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.show();
        RTCReport rtcReport = (RTCReport) mData.get(anInt);
        RequestParams pa = new RequestParams("http://hmyc365.net:8081/HM/app/rtc/report/info/getRtcResultUrl.do");
        pa.addBodyParameter("user_id", SharedPreferenceUtils.getUserData(this, 1));
        pa.addBodyParameter("order_id", rtcReport.getOrder_id());
        x.http().post(pa, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONObject body = object.getJSONObject("body");
                    JSONObject url = body.getJSONObject("url");
                    String pdfTableAllUrl = url.getString("pdfTableAllUrl");
                    String pdfUrl = url.getString("pdfUrl");
                    switch (tag) {
                        case "0":
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse(pdfTableAllUrl);
                            intent.setData(content_url);
                            startActivity(intent);
                            break;
                        case "1":
                            //获取剪贴板管理器：
                            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            // 创建普通字符型ClipData
                            ClipData mClipData = ClipData.newPlainText("Label", pdfUrl);
                            // 将ClipData内容放到系统剪贴板里。
                            cm.setPrimaryClip(mClipData);
                            Toast.makeText(RTCReportActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                    dialog.dismiss();
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
    }

    public void back(View view) {
        finish();
    }

    /*
    * 加载数据
    * */
    private void loadingData() {
        net.post("http://hmyc365.net:8081/HM/app/rtc/order/info/getOrderRtcList.do", map, new UtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                mData.clear();
                try {
                    JSONObject ob = new JSONObject(result);
                    JSONObject body = ob.getJSONObject("body");
                    String picUrl = body.getString("picUrl");
                    JSONArray dataList = body.getJSONArray("dataList");
                    for (int i = 0; i < dataList.length(); i++) {
                        JSONObject object = dataList.getJSONObject(i);
                        String name = object.getString("name");
                        String position = object.getString("position");
                        String order_id = object.getString("order_id");
                        String phone = object.getString("phone");
                        RTCReport report = new RTCReport(position, phone, name, order_id, picUrl);
                        mData.add(report);
                    }
                    adapter.notifyDataSetChanged();
                    if (freshView.isRefreshing())
                        freshView.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
