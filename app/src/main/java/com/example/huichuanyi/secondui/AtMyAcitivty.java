package com.example.huichuanyi.secondui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;

public class AtMyAcitivty extends BaseActivity implements View.OnClickListener {
    private ImageView mImageViewBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_my_acitivty);

    }


    @Override
    public void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.iv_atmy_back);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        mImageViewBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.iv_atmy_back:
                finish();
                break;
        }
    }
}
