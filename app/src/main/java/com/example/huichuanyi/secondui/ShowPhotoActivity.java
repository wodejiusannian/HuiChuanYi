package com.example.huichuanyi.secondui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

public class ShowPhotoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImageViewBack;
    private SimpleDraweeView mImageViewFirst;
    private String image1;
    private TextView mTextView, mTextViewTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);

    }

    @Override
    public void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.iv_show_photo_back);
        mImageViewFirst = (SimpleDraweeView) findViewById(R.id.rv_show_photo_first);
        mTextView = (TextView) findViewById(R.id.tv_main_xinde);
        mTextViewTime = (TextView) findViewById(R.id.tv_time);
    }

    @Override
    public void initData() {
        image1 = getIntent().getStringExtra("image1");
        String daipei = getIntent().getStringExtra("daipei");
        String time = getIntent().getStringExtra("time");
        if (!TextUtils.isEmpty(daipei)) {
            mTextView.setText(daipei);
        }
        if (!TextUtils.isEmpty(time)) {
            mTextViewTime.setText(time);
        }
    }

    @Override
    public void setData() {
        mImageViewFirst.setImageURI(image1);
    }

    @Override
    public void setListener() {
        mImageViewBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_show_photo_back:
                finish();
                break;
        }
    }
}
