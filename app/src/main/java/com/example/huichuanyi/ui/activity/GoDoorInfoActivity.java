package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;

import org.xutils.view.annotation.ViewInject;

public class GoDoorInfoActivity extends BaseActivity {
    @ViewInject(R.id.btn_include)
    private Button mBtnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_door_info);
    }

    @Override
    public void initView() {
        mBtnOrder.setText("立即预约");
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
