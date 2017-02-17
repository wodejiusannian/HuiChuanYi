package com.example.huichuanyi.ui_third;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.User;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Write_OrderActivity extends BaseActivity implements View.OnClickListener, UtilsInternet.XCallBack {
    private LinearLayout mAddressInfo;
    private TextView mName, mPhone, mAddress;
    private UtilsInternet instance = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();
    private android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            mName.setText(data.getString("receive_name"));
            mPhone.setText(data.getString("receive_phone"));
            mAddress.setText(data.getString("receive_city") + data.getString("receive_address"));
        }
    };

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
        map.put("user_id", new User(this).getUseId() + "");
        instance.post(NetConfig.GET_PERSON_ADDRESS, map, this);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 1001) {
            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");
            String city = data.getStringExtra("city");
            String address = data.getStringExtra("address");
            mName.setText(name);
            mPhone.setText(phone);
            mAddress.setText(city + address);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_write_order_address_info:
                jumpListAddress();
                break;
            default:
                break;
        }
    }

    private void jumpListAddress() {
        Intent intent = new Intent(this, ListAddress.class);
        startActivityForResult(intent, 1000);
    }

    @Override
    public void onResponse(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONArray body = object.getJSONArray("body");
            JSONObject jsonObject = body.getJSONObject(0);
            String receive_city = jsonObject.getString("receive_city");
            String receive_name = jsonObject.getString("receive_name");
            String receive_phone = jsonObject.getString("receive_phone");
            String receive_address = jsonObject.getString("receive_address");
            Bundle bundle = new Bundle();
            Message message = Message.obtain();
            bundle.putString("receive_city", receive_city);
            bundle.putString("receive_name", receive_name);
            bundle.putString("receive_phone", receive_phone);
            bundle.putString("receive_address", receive_address);
            message.setData(bundle);
            mHandler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
