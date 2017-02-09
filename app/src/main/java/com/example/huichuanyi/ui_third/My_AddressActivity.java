package com.example.huichuanyi.ui_third;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;

import widget.OnWheelChangedListener;
import widget.WheelView;

public class My_AddressActivity extends BaseActivity implements View.OnClickListener , OnWheelChangedListener {
    private Button mTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
    }

    @Override
    public void initView() {
        mTest = (Button) findViewById(R.id.btn_test);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        mTest.setOnClickListener(this);

    }

   public void back(View view){
       finish();
   }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {

    }




}
