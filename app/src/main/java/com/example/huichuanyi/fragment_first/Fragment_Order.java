package com.example.huichuanyi.fragment_first;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.HomeAdapter;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.bean.Banner;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.DateTimePickDialogUtil;
import com.example.huichuanyi.custom.EditDialog;
import com.example.huichuanyi.secondui.AtMyAcitivty;
import com.example.huichuanyi.ui.activity.DaPeiRiJiActivity;
import com.example.huichuanyi.ui.activity.LiJiYuYueActivity;
import com.example.huichuanyi.ui.activity.MyOrderActivity;
import com.example.huichuanyi.ui.activity.RegisterActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.MySharedPreferences;
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

import butterknife.BindViews;
import butterknife.OnClick;

public class Fragment_Order extends BaseFragment implements View.OnClickListener, OnItemClickListener, EditDialog.EditYes {
    private RollPagerView mRollPagerView;
    private List<Banner.ListBean> mImages;
    private HomeAdapter mPageAdapter;
    private static final int JUMP_TIME = 4000;
    @BindViews({R.id.btn_fragment_first_location, R.id.btn_fragment_first_time})
    public Button[] buttons;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_first_order, null);
        getChildView(view);
        return view;
    }

    private void getChildView(View view) {
        mRollPagerView = (RollPagerView) view.findViewById(R.id.rv_fragment_first_order);
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
        if (CommonUtils.isEmpty(city)) {
            buttons[0].setText("亲，请添加定位权限");
        } else {
            buttons[0].setText(city);
        }
        buttons[1].setText(getNowTime());
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mRollPagerView.setOnItemClickListener(this);
    }

    private void initViewPager() {
        mPageAdapter = new HomeAdapter(mRollPagerView, mImages, getActivity());
        RequestParams params = new RequestParams(NetConfig.BANNER_URL);
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

    @OnClick({R.id.btn_fragment_first_location, R.id.btn_fragment_first_time, R.id.btn_fragment_first_order})
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
                String mLocation = buttons[0].getText().toString().trim();
                String mTime = buttons[1].getText().toString().trim();
                if (!TextUtils.isEmpty(mLocation) && !TextUtils.isEmpty(mTime) && !TextUtils.equals("亲，请添加定位权限", mLocation)) {
                    map.put("location", mLocation);
                    map.put("time", mTime);
                    map.put("order_365", "select_order");
                    ActivityUtils.switchTo(getActivity(), LiJiYuYueActivity.class, map);
                } else {
                    CommonUtils.Toast(getContext(), "请输入所在城市");
                }
                break;
        }
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String str = sdf.format(new Date());
        DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                getActivity(), str);
        dateTimePicKDialog.dateTimePicKDialog(buttons[1]);
    }

    @Override
    public void onItemClick(int position) {
        if (!getUser()) {
            ActivityUtils.switchTo(getActivity(), RegisterActivity.class);
            return;
        }
        switch (position) {
            case 0:
                ActivityUtils.switchTo(getActivity(), AtMyAcitivty.class);
                break;
            case 1:
                ActivityUtils.switchTo(getActivity(), MyOrderActivity.class);
                break;
            case 2:
                ActivityUtils.switchTo(getActivity(), DaPeiRiJiActivity.class);
                break;
            default:
                break;
        }

    }

    private void initEditText() {
        EditDialog editDialog = new EditDialog(getContext());
        editDialog.setOnClickNo("取消");
        editDialog.setOnClickYes("确定", this);
        editDialog.show();
    }

    @Override
    public void getEdit(String edit) {
        buttons[0].setText(edit);
    }
}
