package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.MySharedPreferences;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ToaActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toa);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {

    }

    @OnClick(R.id.btn_buy)
    public void onclick(View view) {
        String city = MySharedPreferences.getBuyCity(this);
        Map<String, Object> map = new HashMap<>();
        map.put("location", city);
        map.put("order_365", "365");
        ActivityUtils.switchTo(this, LiJiYuYueActivity.class, map);
    }

    public void back(View view) {
        finish();
    }
}
