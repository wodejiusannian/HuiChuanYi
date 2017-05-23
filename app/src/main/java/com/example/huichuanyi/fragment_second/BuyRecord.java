package com.example.huichuanyi.fragment_second;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.BuyRecordAdapter;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.bean.RecordBuy;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.secondui.PayOrderActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.Utils;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 小五 on 2017/2/8.
 */

public class BuyRecord extends BaseFragment implements UtilsInternet.XCallBack, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, MySelfDialog.OnYesClickListener {
    private UtilsInternet internet = UtilsInternet.getInstance();
    private List<RecordBuy> mData = new ArrayList<>();
    private Map<String, String> map = new HashMap<>();
    private Map<String, Object> jumpMap = new HashMap<>();
    private BuyRecordAdapter mAdapter;

    private ListView mShow;
    private SwipeRefreshLayout mReFresh;

    private String user_id;
    private int internetTag = 0;
    private int pos = -1;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_list, null);
        getChildView(view);
        return view;
    }

    private void getChildView(View view) {
        mShow = (ListView) view.findViewById(R.id.fragment_list_view);
        mReFresh = (SwipeRefreshLayout) view.findViewById(R.id.sf_buy_record_refresh);
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter = new BuyRecordAdapter(getContext(), mData, R.layout.item_fragment_buy_record);
        mShow.setAdapter(mAdapter);
        user_id = SharedPreferenceUtils.getUserData(getContext(),1);
        map.put("user_id", user_id);
        loadData();
    }

    @Override
    protected void setData() {
        super.setData();
    }


    @Override
    protected void initEvent() {
        super.initEvent();
        mReFresh.setOnRefreshListener(this);
        mAdapter.setOnItemClick(this);
    }


    private void loadData() {
        internet.post(NetConfig.GET_BUY_RECORD, map, this);
    }

    @Override
    public void onRefresh() {
        loadData();
        mReFresh.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        pos = (int) v.getTag();
        RecordBuy recordBuy = mData.get(pos);
        switch (v.getId()) {
            case R.id.btn_connection_our:
                connectionOur();
                break;
            case R.id.btn_see_car:
                Map<String, Object> map = new HashMap<>();
                String way_no = recordBuy.getWay_no();
                String way_name = recordBuy.getWay_name();
                String way_code = recordBuy.getWay_code();
                String way_phone = recordBuy.getWay_phone();
                String clothes_get = recordBuy.getClothes_get();
                map.put("way_no", way_no);
                map.put("way_name", way_name);
                map.put("way_code", way_code);
                map.put("way_phone", way_phone);
                map.put("clothes_get", clothes_get);
                ActivityUtils.switchTo(getActivity(), SeeCarActivity.class, map);
                break;
            case R.id.btn_go_pay:
                goPay(recordBuy);
                break;
            case R.id.btn_confirm_receipt:
                String state = recordBuy.getState();
                confirmReceipt(state, recordBuy);
                break;
            default:
                break;
        }
    }

    /*
    * 确认收货
    * */
    private void confirmReceipt(String state, RecordBuy recordBuy) {
        if (!TextUtils.equals("10", state)) {
            receipt(recordBuy);
        } else {
            MySelfDialog mySelfDialog = new MySelfDialog(getContext());
            mySelfDialog.setTitle("温馨提示");
            mySelfDialog.setMessage("卖家还没有发货，请耐心等待");
            mySelfDialog.setOnNoListener("取消", null);
            mySelfDialog.setOnYesListener("催单", this);
            mySelfDialog.show();
        }
    }

    /*
    * 联系我们
    * */
    private void connectionOur() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "18601026066"));
        startActivity(intent);
    }

    /*
    * 去支付
    * */
    private void goPay(RecordBuy recordBuy) {
        String order_id = recordBuy.getOrder_id();
        String total_price = recordBuy.getTotal_price();
        String clothes_get = recordBuy.getClothes_get();
        String clothes_name = recordBuy.getClothes_name();
        jumpMap.put("orderid", order_id);
        jumpMap.put("nowMoney", total_price);
        jumpMap.put("managerPhoto", clothes_get);
        jumpMap.put("managerName", clothes_name);
        jumpMap.put("type", "3");
        ActivityUtils.switchTo(getActivity(), PayOrderActivity.class, jumpMap);
    }

    /*
    * 确认收货
    * */
    private void receipt(RecordBuy recordBuy) {
        internetTag = 3;
        map.clear();
        map.put("user_id", user_id);
        map.put("id", recordBuy.getOrder_id());
        map.put("state", "11");
        internet.post(NetConfig.CONFIRM_RECEIPT_365, map, this);
    }

    @Override
    public void onResponse(String result) {
        Utils.Log(result);
        switch (internetTag) {
            case 0:
                mData.clear();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray body = object.getJSONArray("body");
                    for (int i = 0; i < body.length(); i++) {
                        JSONObject obj = body.getJSONObject(i);
                        RecordBuy record = new RecordBuy();
                        record.setClothes_get(obj.getString("clothes_get"));
                        record.setNum(obj.getString("num"));
                        record.setClothes_name(obj.getString("clothes_name"));
                        record.setSize_name(obj.getString("size_name"));
                        record.setWay_no(obj.getString("way_no"));
                        record.setWay_code(obj.getString("way_code"));
                        record.setWay_name(obj.getString("way_name"));
                        record.setWay_phone(obj.getString("way_phone"));
                        record.setTotal_price(obj.getString("total_price"));
                        record.setState(obj.getString("state"));
                        record.setColor_name(obj.getString("color_name"));
                        record.setOrder_id(obj.getString("order_id"));
                        record.setRemarks(obj.getString("remarks"));
                        mData.add(record);
                    }
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                internetTag = 0;
                String ret = JsonUtils.getRet(result);
                if (TextUtils.equals("0", ret)) {
                    mData.remove(pos);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick() {
        Toast.makeText(getContext(), "催单成功", Toast.LENGTH_SHORT).show();
    }
}