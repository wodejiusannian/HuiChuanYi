package com.example.huichuanyi.ui_third;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;

public class Write_OrderActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mAddressInfo;
    private TextView mName, mPhone, mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_order);
    }

    @Override
    public void initView() {
        mAddressInfo = (LinearLayout) findViewById(R.id.ll_write_order_address_info);
        mName = (TextView) findViewById(R.id.tv_write_order_name);
        mPhone = (TextView) findViewById(R.id.tv_write_order_phone);
        mAddress = (TextView) findViewById(R.id.tv_write_order_address);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        mAddressInfo.setOnClickListener(this);
    }


    public void back(View view) {
        finish();
    }


    private void jumpWriteOrder() {
        Intent intent = new Intent(this, Write_AddressActivity.class);
        startActivityForResult(intent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 1001) {
            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");
            String street = data.getStringExtra("street");
            mName.setText(name);
            mAddress.setText(street);
            mPhone.setText(phone);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_write_order_address_info:
                jumpWriteOrder();
                break;
            default:
                break;
        }
    }
}
