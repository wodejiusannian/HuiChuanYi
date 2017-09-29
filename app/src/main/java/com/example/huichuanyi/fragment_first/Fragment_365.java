package com.example.huichuanyi.fragment_first;

import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.MyPartnerAdapter;
import com.example.huichuanyi.baidumap.Fresh_365;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.bean.CardItem;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.ui.activity.DatumActivity;
import com.example.huichuanyi.ui.activity.Item_DetailsActivity;
import com.example.huichuanyi.ui.activity.MainActivity;
import com.example.huichuanyi.ui.activity.SLWActiveActivity;
import com.example.huichuanyi.ui.activity.SLWJianJieActivity;
import com.example.huichuanyi.ui.activity.SLWRecordActivity;
import com.example.huichuanyi.ui.activity.SLWWriteInfoActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonUtils;
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

import io.rong.imkit.RongIM;


public class Fragment_365 extends BaseFragment implements View.OnClickListener, UtilsInternet.XCallBack, Fresh_365, MySelfDialog.OnYesClickListener {
    private UtilsInternet instance = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();
    private Map<String, Object> jumpMap = new HashMap<>();
    private List<CardItem> mData = new ArrayList<>();

    private LinearLayout mPay, mNoPay, mActivity;
    private ViewPager mPics;
    private TextView mRecord;
    private Button mAdd, mWill, mInvite, mCopy;

    private MyPartnerAdapter mAdapter;
    private String buyCity;

    private TextView chat;

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
        mInvite = (Button) view.findViewById(R.id.btn_will_invite);
        mCopy = (Button) view.findViewById(R.id.btn_will_pay_copy);
        mActivity = (LinearLayout) view.findViewById(R.id.ll_is_activity);
        chat = (TextView) view.findViewById(R.id.tv_365_chat);
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
        mWill.setGravity(Gravity.CENTER);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdapter.setOnItemClickListener(this);
        mRecord.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        mWill.setOnClickListener(this);
        mInvite.setOnClickListener(this);
        mCopy.setOnClickListener(this);
        chat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_365_record:
                ActivityUtils.switchTo(getActivity(), SLWRecordActivity.class);
                break;
            case R.id.btn_will_pay_copy:
                isHaveBuyCity();
                break;
            case R.id.btn_365_add:
                if (mData.size() == 0) return;
                int currentItem = mPics.getCurrentItem();
                addJumpData(currentItem);
                ActivityUtils.switchTo(getActivity(), SLWWriteInfoActivity.class, jumpMap);
                break;
            case R.id.btn_will_pay:
                isHaveBuyCity();
                break;
            case R.id.btn_will_invite:
                buyCity = SharedPreferenceUtils.getBuyCity(getActivity());
                if (TextUtils.isEmpty(buyCity)) {
                    showDialog();
                } else {
                    ActivityUtils.switchTo(getActivity(), SLWActiveActivity.class);
                }
                break;
            case R.id.tv_365_chat:
                RequestParams params = new RequestParams(NetConfig.IS_BUY_365);
                params.addBodyParameter("user_id", SharedPreferenceUtils.getUserData(getContext(), 1));
                x.http().post(params, new Callback.CacheCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject object = new JSONObject(result);
                            JSONObject body = object.getJSONObject("body");
                            String studio_name = body.getString("studio_name");
                            String studio_id = body.getString("studio_id");
                            RongIM im = RongIM.getInstance();
                            if (im != null && studio_id != null) {
                                im.startPrivateChat(getContext(), "hmgls_" + studio_id, studio_name);
                            }
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

                    @Override
                    public boolean onCache(String result) {
                        return false;
                    }
                });
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
        String recommend_id = item.getRecommend_id();
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
        jumpMap.put("recommend_id", recommend_id);
    }


    /*
    * 点击去开通
    * */
    private void goDredge() {
        /*String city = SharedPreferenceUtils.getBuyCity(getContext());
        Map<String, Object> map = new HashMap<>();
        map.put("location", city);
        map.put("order_365", "365");
        ActivityUtils.switchTo(getActivity(), LiJiYuYueActivity.class, map);*/
        ActivityUtils.switchTo(getActivity(), SLWJianJieActivity.class);
    }

    @Override
    public void onResponse(String result) {
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
                item.setClothes_name(obj.getString("clothes_name"));
                item.setRecommend_id(obj.getString("recommend_id"));
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
        String activity = SharedPreferenceUtils.getActivity(getActivity());
        if (TextUtils.equals("Y", activity)) {
            mCopy.setVisibility(View.GONE);
            mActivity.setVisibility(View.VISIBLE);
        }
        map.put("user_id", SharedPreferenceUtils.getUserData(getContext(), 1));
        instance.post(NetConfig.GET_RECOMMEND_NEW, map, this);
        String m365 = SharedPreferenceUtils.get365(getContext());
        if (TextUtils.equals("365", m365)) {
            mPay.setVisibility(View.VISIBLE);
            mRecord.setVisibility(View.VISIBLE);
            chat.setVisibility(View.VISIBLE);
            mNoPay.setVisibility(View.GONE);
        } else {
            mRecord.setVisibility(View.GONE);
            mNoPay.setVisibility(View.VISIBLE);
            mPay.setVisibility(View.GONE);
            chat.setVisibility(View.GONE);
        }
    }


    @Override
    public void reFresh365() {
        isYetPay();
    }

    private void isHaveBuyCity() {
        buyCity = SharedPreferenceUtils.getBuyCity(getActivity());
        RequestParams params = new RequestParams(NetConfig.GET_INFORMATION);
        params.addBodyParameter("userid", SharedPreferenceUtils.getUserData(getContext(), 1));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.equals("0", result)) {
                    return;
                }
                try {
                    JSONObject object = new JSONObject(result);
                    String name = object.getString("name");
                    String sex = object.getString("sex");
                    String age = object.getString("age");
                    String city = object.getString("city");
                    String height = object.getString("height");
                    String weight = object.getString("weight");
                    String occupation = object.getString("occupation");
                    String orientate = object.getString("orientate");
                    String birthday = object.getString("birthday");
                    String pos1 = object.getString("pos1");
                    String phone_data = object.getString("phone_data");
                    String pos2 = object.getString("pos2");
                    String pos3 = object.getString("pos3");
                    String character = object.getString("character");
                    String colour_tag = object.getString("colour_tag");
                    String neck_circumference = object.getString("neck_circumference");
                    String shoulder_circumference = object.getString("shoulder_circumference");
                    String bust = object.getString("bust");
                    String waistline = object.getString("waistline");
                    String hipline = object.getString("hipline");
                    String desired_image = object.getString("desired_image");
                    if (CommonUtils.isEmpty(name) || CommonUtils.isEmpty(age) || CommonUtils.isEmpty(city) || CommonUtils.isEmpty(height)
                            || CommonUtils.isEmpty(weight) || CommonUtils.isEmpty(occupation) || CommonUtils.isEmpty(orientate) || CommonUtils.isEmpty(birthday)
                            || CommonUtils.isEmpty(pos1) || CommonUtils.isEmpty(phone_data) || CommonUtils.isEmpty(pos2) || CommonUtils.isEmpty(pos3) || CommonUtils.isEmpty(character)
                            || CommonUtils.isEmpty(colour_tag) || CommonUtils.isEmpty(neck_circumference) || CommonUtils.isEmpty(shoulder_circumference) || CommonUtils.isEmpty(bust) || CommonUtils.isEmpty(waistline)
                            || CommonUtils.isEmpty(hipline) || CommonUtils.isEmpty(desired_image)) {
                        showDialog();
                    } else {
                        goDredge();
                    }
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

    private void showDialog() {
        MySelfDialog mySelfDialog = new MySelfDialog(getContext());
        mySelfDialog.setTitle("温馨提示");
        mySelfDialog.setMessage("请完善您资料中全部信息！");
        mySelfDialog.setOnNoListener("取消", null);
        mySelfDialog.setOnYesListener("去完善资料", this);
        mySelfDialog.show();
    }

    @Override
    public void onClick() {
        ActivityUtils.switchTo(getActivity(), DatumActivity.class);
    }
}
