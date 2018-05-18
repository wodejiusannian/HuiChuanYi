package com.example.huichuanyi.ui.activity.loading;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.LoadingAdapter;
import com.example.huichuanyi.ui.activity.login.LoginByAuthCodeActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

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
                mImageView.setImageResource(R.drawable.welcomehmo);
            }
            if (i == 1) {
                mImageView.setImageResource(R.drawable.welcomehmt);
            }
            if (i == 2) {
                mImageView.setImageResource(R.drawable.welcometh);
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
        SharedPreferenceUtils.saveIsFirst(this);
        ActivityUtils.switchTo(this, LoginByAuthCodeActivity.class);
        finish();
    }
}
