package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;

public class ComplainActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImageViewPhone;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
    }

    @Override
    public void initView() {
        mImageViewPhone = (ImageView) findViewById(R.id.iv_complain_phone);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        mImageViewPhone.setOnClickListener(this);
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_complain_phone:

                break;
            default:

                break;
        }
    }
}
