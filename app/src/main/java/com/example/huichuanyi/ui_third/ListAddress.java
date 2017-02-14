package com.example.huichuanyi.ui_third;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.ListAddressAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.bean.MyAddress;

import java.util.ArrayList;
import java.util.List;

public class ListAddress extends BaseActivity implements ListAddressAdapter.Info, View.OnClickListener {

    private ListView mShow;
    private ListAddressAdapter adapter;
    private List<MyAddress> mData;
    private Intent intent;
    private TextView mSure;
    private String mName, mPhone, mAddress;
    private Button mAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_address);
    }

    @Override
    public void initView() {
        mShow = (ListView) findViewById(R.id.lv_list_address_show);
        mSure = (TextView) findViewById(R.id.tv_list_address_sure);
        mAdd = (Button) findViewById(R.id.btn_add_address);
    }

    @Override
    public void initData() {
        intent = getIntent();
        mData = new ArrayList<>();
        adapter = new ListAddressAdapter(this, mData);
    }

    @Override
    public void setData() {
        mShow.setAdapter(adapter);
        for (int i = 0; i < 5; i++) {
            MyAddress address = new MyAddress();
            address.setAddress("山东");
            address.setName("吴波");
            address.setPhone("18363833181");
            mData.add(address);
        }
        if (mData.size() != 0) {
            MyAddress address = mData.get(0);
            mName = address.name;
            mPhone = address.phone;
            mAddress = address.getAddress();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setListener() {
        adapter.getInfo(this);
        mSure.setOnClickListener(this);
        mAdd.setOnClickListener(this);
    }

    public void back(View view) {
        finish();
    }


    @Override
    public void getInfo(String name, String phone, String add) {
        mName = name;
        mPhone = phone;
        mAddress = add;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_list_address_sure:
                transmissionInfo();
                break;
            case R.id.btn_add_address:
                Intent intent = new Intent(this, Write_AddressActivity.class);
                startActivityForResult(intent, 1000);
                break;
            default:
                break;
        }
    }

    private void transmissionInfo() {
        intent.putExtra("name", mName);
        intent.putExtra("phone", mPhone);
        intent.putExtra("street", mAddress);
        setResult(1001, intent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 1001) {
            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");
            String street = data.getStringExtra("street");
            MyAddress myAddress = new MyAddress();
            myAddress.setPhone(phone);
            myAddress.setName(name);
            myAddress.setAddress(street);
            mData.add(0, myAddress);
            adapter.notifyDataSetChanged();
        }
    }
}
