package com.example.huichuanyi.fragment_first;

import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.MyPartnerAdapter;
import com.example.huichuanyi.baidumap.Fresh_365;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.bean.CardItem;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui_first.MainActivity;
import com.example.huichuanyi.ui_second.LiJiYuYueActivity;
import com.example.huichuanyi.ui_third.Item_DetailsActivity;
import com.example.huichuanyi.ui_third.RecordActivity;
import com.example.huichuanyi.ui_third.Write_OrderActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.MySharedPreferences;
import com.example.huichuanyi.utils.User;
import com.example.huichuanyi.utils.Utils;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Fragment_365 extends BaseFragment implements View.OnClickListener, UtilsInternet.XCallBack, Fresh_365 {

    private ViewPager mPics;
    private MyPartnerAdapter mAdapter;
    private TextView mRecord;
    private LinearLayout mPay, mNoPay;
    private Button mAdd, mWill;
    private UtilsInternet instance = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();
    private Map<String, Object> jumpMap = new HashMap<>();
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
        activity.setFresh365(this);
    }

    @Override
    protected void setData() {
        super.setData();
        userID = new User(getContext()).getUseId();
        if (userID > 0) {
            map.put("user_id", userID + "");
            instance.post(NetConfig.GET_RECOMMEND_NEW, map, this);
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
                if (mData.size() == 0) return;
                int currentItem = mPics.getCurrentItem();
                addJumpData(currentItem);
                ActivityUtils.switchTo(getActivity(), Write_OrderActivity.class, jumpMap);
                break;
            case R.id.btn_will_pay:
                goDredge();
                break;
            default:
                int tag = (int) v.getTag();
                addJumpData(tag);
                ActivityUtils.switchTo(getActivity(), Item_DetailsActivity.class, jumpMap);
                break;
        }
    }

    /*
    * 添加调到下个页面的数据的
    * */
    private void addJumpData(int currentItem) {
        CardItem item = mData.get(currentItem);
        String clothes_get = item.getClothes_get();
        String color = item.getColor();
        String color_name = item.getColor_name();
        String id = item.getId();
        String introduction = item.getIntroduction();
        String price_dj = item.getPrice_dj();
        String reason = item.getReason();
        String size_name = item.getSize_name();
        String clothes_name = item.getClothes_name();
        jumpMap.put("clothes_get", clothes_get);
        jumpMap.put("color", color);
        jumpMap.put("color_name", color_name);
        jumpMap.put("id", id);
        jumpMap.put("type", "3");
        jumpMap.put("introduction", introduction);
        jumpMap.put("price_dj", price_dj);
        jumpMap.put("name", clothes_name);
        jumpMap.put("reason", reason);
        jumpMap.put("size_name", size_name);
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
    }

    @Override
    public void onResponse(String result) {
        Utils.Log(result);
        try {
            JSONObject object = new JSONObject(result);
            JSONArray body = object.getJSONArray("body");
            for (int i = 0; i < body.length(); i++) {
                JSONObject obj = body.getJSONObject(i);
                CardItem item = new CardItem();
                item.setSize_name(obj.getString("size_name"));
                item.setReason(obj.getString("reason"));
                item.setRecommend_time(obj.getString("recommend_time"));
                item.setPrice_dj(obj.getString("price_dj"));
                item.setSize_get(obj.getString("size_get"));
                item.setClothes_get(obj.getString("clothes_get"));
                item.setColor_name(obj.getString("color_name"));
                item.setIntroduction(obj.getString("introduction"));
                item.setId(obj.getString("id"));
                item.setId(obj.getString("clothes_name"));
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
    public void reFresh365() {
        Toast.makeText(getContext(), "登陆成功后，刷新下是否购买365", Toast.LENGTH_SHORT).show();
        MySharedPreferences.save365(getContext(), null);
        isYetPay();
    }
}
