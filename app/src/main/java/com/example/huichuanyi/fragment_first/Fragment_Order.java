package com.example.huichuanyi.fragment_first;

import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.HomeAdapter;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.DateTimePickDialogUtil;
import com.example.huichuanyi.bean.Banner;
import com.example.huichuanyi.secondui.AtMyAcitivty;
import com.example.huichuanyi.ui_second.DaPeiRiJiActivity;
import com.example.huichuanyi.ui_second.LiJiYuYueActivity;
import com.example.huichuanyi.ui_second.MyOrderActivity;
import com.example.huichuanyi.ui_second.RegisterActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.MySharedPreferences;
import com.example.huichuanyi.utils.User;
import com.google.gson.Gson;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_Order extends BaseFragment implements View.OnClickListener, OnItemClickListener {
    private RollPagerView mRollPagerView;
    private List<Banner.ListBean> mImages;
    private HomeAdapter mPageAdapter;
    private static final int JUMP_TIME = 4000;
    private Button mButtonLocation, mButtonTime, mButtonOrder;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_first_order, null);
        getChildView(view);
        return view;
    }

    private void getChildView(View view) {
        mRollPagerView = (RollPagerView) view.findViewById(R.id.rv_fragment_first_order);
        mButtonLocation = (Button) view.findViewById(R.id.btn_fragment_first_location);
        mButtonTime = (Button) view.findViewById(R.id.btn_fragment_first_time);
        mButtonOrder = (Button) view.findViewById(R.id.btn_fragment_first_order);
    }

    @Override
    protected void initData() {
        super.initData();
        mImages = new ArrayList<>();
        initViewPager();
    }

    @Override
    protected void setData() {
        super.setData();
        mRollPagerView.setAdapter(mPageAdapter);
        String city = MySharedPreferences.getCity(getContext());
        if (city == null || TextUtils.equals("null", city) || TextUtils.isEmpty(city)) {
            mButtonLocation.setText("亲，请添加定位权限");
        } else {
            mButtonLocation.setText(city);
        }
        mButtonTime.setText(getNowTime());
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mButtonLocation.setOnClickListener(this);
        mButtonTime.setOnClickListener(this);
        mButtonOrder.setOnClickListener(this);
        mRollPagerView.setOnItemClickListener(this);
    }

    private void initViewPager() {
        mPageAdapter = new HomeAdapter(mRollPagerView, mImages, getActivity());
        RequestParams params = new RequestParams(NetConfig.BANNER_ONE);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Banner banner = gson.fromJson(result, Banner.class);
                mImages.addAll(banner.getList());
                mPageAdapter.notifyDataSetChanged();
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
        mRollPagerView.setHintView(new ColorPointHintView(getActivity(), Color.parseColor("#ac0000"), Color.WHITE));
        mRollPagerView.setPlayDelay(JUMP_TIME);
    }

    public String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        GregorianCalendar ca = new GregorianCalendar();
        int i = ca.get(GregorianCalendar.AM_PM);
        String nowTime = sdf.format(new Date());
        if (i == 0) {
            return nowTime + " " + "上午";
        } else if (i == 1) {
            return nowTime + " " + "下午";
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fragment_first_location:
                initEditText();
                break;
            case R.id.btn_fragment_first_time:
                initDatePicker();
                break;
            case R.id.btn_fragment_first_order:
                Map<String, Object> map = new HashMap<>();
                String mLocation = mButtonLocation.getText().toString().trim();
                String mTime = mButtonTime.getText().toString().trim();
                if (!TextUtils.isEmpty(mLocation) && !TextUtils.isEmpty(mTime) && !TextUtils.equals("亲，请添加定位权限", mLocation)) {
                    map.put("location", mLocation);
                    map.put("time", mTime);
                    map.put("order_365", "order");
                    ActivityUtils.switchTo(getActivity(), LiJiYuYueActivity.class, map);
                } else {
                    Toast.makeText(getActivity(), "请输入所在城市哦", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String str = sdf.format(new Date());
        DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                getActivity(), str);
        dateTimePicKDialog.dateTimePicKDialog(mButtonTime);
    }

    @Override
    public void onItemClick(int position) {
        int useId = new User(getActivity()).getUseId();
        if (useId == 0) {
            ActivityUtils.switchTo(getActivity(), RegisterActivity.class);
            return;
        }
        if (position == 0) {
            ActivityUtils.switchTo(getActivity(), AtMyAcitivty.class);
        }
        if (position == 1) {
            ActivityUtils.switchTo(getActivity(), MyOrderActivity.class);
        }
        if (position == 2) {
            ActivityUtils.switchTo(getActivity(), DaPeiRiJiActivity.class);
        }

    }

    private void initEditText() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_order_location, null);
        final AlertDialog dialog = new AlertDialog.Builder(getContext()).setView(view).create();
        final EditText edit = (EditText) view.findViewById(R.id.et_location_edit);
        view.findViewById(R.id.btn_location_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mLocation = edit.getText().toString();
                if (!TextUtils.isEmpty(mLocation)) {
                    mButtonLocation.setText(mLocation);
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "亲，请输入地址哦", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.btn_location_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
