package com.example.huichuanyi.secondui;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.custom.RoundImageView;
import com.squareup.picasso.Picasso;

public class ShowPhotoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImageViewBack;
    private RoundImageView mImageViewFirst,mImageViewSecond;
    private String image1,image2;
    private TextView mTextView,mTextViewTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);

    }

    @Override
    public void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.iv_show_photo_back);
        mImageViewFirst = (RoundImageView) findViewById(R.id.rv_show_photo_first);
        mImageViewSecond = (RoundImageView) findViewById(R.id.rv_show_photo_second);
        mTextView = (TextView) findViewById(R.id.tv_main_xinde);
        mTextViewTime = (TextView) findViewById(R.id.tv_time);
    }

    @Override
    public void initData() {
        image1 = getIntent().getStringExtra("image1");
        image2 = getIntent().getStringExtra("getshowpic1");
        String daipei = getIntent().getStringExtra("daipei");
        String time = getIntent().getStringExtra("time");
        if(!TextUtils.isEmpty(daipei)) {
            mTextView.setText(daipei);
        }
        if(!TextUtils.isEmpty(time)) {
            mTextViewTime.setText(time);
        }
    }

    @Override
    public void setData() {
        if(!TextUtils.isEmpty(image1)) {
            Picasso.with(this).load(image1).into(mImageViewFirst);
        }
        if(!TextUtils.isEmpty(image2)) {
            Picasso.with(this).load(image2).into(mImageViewSecond);
        }
    }

    @Override
    public void setListener() {
        mImageViewBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.iv_show_photo_back:
                finish();
                break;
        }
    }
}
