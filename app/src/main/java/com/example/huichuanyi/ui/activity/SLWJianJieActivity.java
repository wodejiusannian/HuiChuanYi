package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SLWJianJieActivity extends BaseActivity {


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

    }

    public void back(View view) {
        finish();
    }
}
