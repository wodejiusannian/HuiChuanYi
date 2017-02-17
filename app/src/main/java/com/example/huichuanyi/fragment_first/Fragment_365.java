package com.example.huichuanyi.fragment_first;

import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.MyPartnerAdapter;
import com.example.huichuanyi.baidumap.FreshPhoto;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.bean.CardItem;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui_first.MainActivity;
import com.example.huichuanyi.ui_second.LiJiYuYueActivity;
import com.example.huichuanyi.ui_third.Item_DetailsActivity;
import com.example.huichuanyi.ui_third.RecordActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.MySharedPreferences;
import com.example.huichuanyi.utils.User;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;


public class Fragment_365 extends BaseFragment implements View.OnClickListener, UtilsInternet.XCallBack, FreshPhoto {

    private ViewPager mPics;
    private MyPartnerAdapter mAdapter;
    private TextView mRecord;
    private LinearLayout mPay, mNoPay;
    private Button mAdd, mWill;
    private UtilsInternet instance = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();
    private List<CardItem> mData = new ArrayList<>();
    private int userID;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_365, null);
        getChildView(view);
        return view;
    }

    private void getChildView(View view) {
        mPics = (ViewPager) view.findViewById(R.id.vp_365_pics);
        mRecord = (TextView) view.findViewById(R.id.tv_365_record);
        mNoPay = (LinearLayout) view.findViewById(R.id.ll_no_pay_money);
        mPay = (LinearLayout) view.findViewById(R.id.ll_yet_pay_money);
        mAdd = (Button) view.findViewById(R.id.btn_365_add);
        mWill = (Button) view.findViewById(R.id.btn_will_pay);
        MySharedPreferences.save365(getContext(), "365");
        isYetPay();
    }


    @Override
    protected void initData() {
        super.initData();
        isYetPay();
        mAdapter = new MyPartnerAdapter(mData);
        mPics.setPageMargin(50);
        mPics.setOffscreenPageLimit(3);
        mPics.setAdapter(mAdapter);
        MainActivity activity = (MainActivity) getActivity();
        activity.setCallBack(this);
    }

    @Override
    protected void setData() {
        super.setData();
        userID = new User(getContext()).getUseId();
        if (userID > 0) {
            map.put("user_id", userID + "");
            instance.post(NetConfig.GET_RECOMMEND_ALL, map, this);
        }

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdapter.setOnItemClickListener(this);
        mRecord.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        mWill.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_365_record:
                ActivityUtils.switchTo(getActivity(), RecordActivity.class);
                break;
            case R.id.btn_365_add:
                //ActivityUtils.switchTo(getActivity(), BuChaJiaActivity.class);
                isYetPay();
                break;
            case R.id.btn_will_pay:
                goDredge();
                break;
            default:
                int tag = (int) v.getTag();
                ActivityUtils.switchTo(getActivity(), Item_DetailsActivity.class);
                break;
        }
    }


    /*
    * 点击去开通
    * */
    private void goDredge() {
        String city = MySharedPreferences.getCity(getContext());
        Map<String, Object> map = new HashMap<>();
        map.put("location", city);
        map.put("order_365", "365");
        ActivityUtils.switchTo(getActivity(), LiJiYuYueActivity.class, map);
        //ActivityUtils.switchTo(getActivity(), ListAddress.class);
    }

    @Override
    public void onResponse(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONArray body = object.getJSONArray("body");
            for (int i = 0; i < body.length(); i++) {
                JSONObject obj = body.getJSONObject(i);
                CardItem item = new CardItem();
                item.setClothes_get(obj.getString("clothes_get"));
                item.setColor(obj.getString("color"));
                item.setIntroduction(obj.getString("introduction"));
                item.setName(obj.getString("name"));
                item.setPrice_dj(obj.getString("price_dj"));
                item.setId(obj.getString("id"));
                mData.add(item);
            }
            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(getActivity());
    }

    /*
    * 是否已经购买365服务,购买成功or购买失败
    * */
    private void isYetPay() {
        String m365 = MySharedPreferences.get365(getContext());
        if (TextUtils.equals("365", m365)) {
            mPay.setVisibility(View.VISIBLE);
            mRecord.setVisibility(View.VISIBLE);
            mNoPay.setVisibility(View.GONE);
        } else {
            mRecord.setVisibility(View.GONE);
            mNoPay.setVisibility(View.VISIBLE);
            mPay.setVisibility(View.GONE);
        }
    }

    @Override
    public void getPhoto(String str) {
        MySharedPreferences.save365(getContext(), null);
        isYetPay();
    }
}
