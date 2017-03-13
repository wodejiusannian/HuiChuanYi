package com.example.huichuanyi.ui.activity;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.LoadingAdapter;
import com.example.huichuanyi.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

public class LoadingActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    private LoadingAdapter mAdapter;
    private List<ImageView> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_loding);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        initView();
        initData();
        setData();
        setListener();
    }

    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_loading_isFirst);
    }

    public void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ImageView mImageView = new ImageView(this);
            if (i == 0) {
                mImageView.setImageResource(R.drawable.yindao1);
            }
            if (i == 1) {
                mImageView.setImageResource(R.drawable.yindao2);
            }
            if (i == 2) {
                mImageView.setImageResource(R.drawable.yindao3);
            }
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mData.add(mImageView);
        }
        mAdapter = new LoadingAdapter(mData);
        mViewPager.setAdapter(mAdapter);
    }

    public void setData() {
    }

    public void setListener() {
        mData.get(2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ActivityUtils.switchTo(this, MainActivity.class);
        finish();
    }
}
