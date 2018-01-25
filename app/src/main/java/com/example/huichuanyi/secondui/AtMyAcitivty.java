package com.example.huichuanyi.secondui;

import android.os.Bundle;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;

public class AtMyAcitivty extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_my_acitivty);

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

    public void back(View view) {
        finish();
    }
}
