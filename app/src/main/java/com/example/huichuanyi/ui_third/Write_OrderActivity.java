package com.example.huichuanyi.ui_third;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.secondui.PayOrderActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.User;
import com.example.huichuanyi.utils.Utils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Write_OrderActivity extends BaseActivity implements View.OnClickListener, UtilsInternet.XCallBack {
    private LinearLayout mAddressInfo;
    private TextView mName, mPhone, mAddress;
    private EditText mRemarks;
    private UtilsInternet instance = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();
    private Button mSubmit;
    private String user_id, clothes_id, address_id, color_name, size_name, clothes_get, name, type;
    private int flag = 0;
    private Map<String, Object> jumpMap = new HashMap<>();
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
        mSubmit = (Button) this.findViewById(R.id.btn_write_order_submit);
        mRemarks = (EditText) this.findViewById(R.id.et_write_order_remark);
    }

    @Override
    public void initData() {
        getUpActivityData();
        map.put("user_id", new User(this).getUseId() + "");
        instance.post(NetConfig.GET_PERSON_ADDRESS, map, this);
    }

    private void getUpActivityData() {
        Intent intent = getIntent();
        clothes_get = intent.getStringExtra("clothes_get");
        String color = intent.getStringExtra("color");
        color_name = intent.getStringExtra("color_name");
        clothes_id = intent.getStringExtra("id");
        type = intent.getStringExtra("type");
        String introduction = intent.getStringExtra("introduction");
        name = intent.getStringExtra("name");
        String price_dj = intent.getStringExtra("price_dj");
        String reason = intent.getStringExtra("reason");
        size_name = intent.getStringExtra("size_name");
        SimpleDraweeView studioLogo = (SimpleDraweeView) this.findViewById(R.id.sv_clothe_item_info_record);
        studioLogo.setImageURI(clothes_get);
        TextView mName = (TextView) this.findViewById(R.id.tv_clothe_item_info_record_style);
        mName.setText(name);
        TextView mSize = (TextView) this.findViewById(R.id.tv_clothe_item_info_record_size);
        mSize.setText(size_name);
        TextView mColor = (TextView) this.findViewById(R.id.tv_clothe_item_info_record_color);
        mColor.setText(color_name);
        TextView mPrice = (TextView) this.findViewById(R.id.tv_clothe_item_info_record_price);
        mPrice.setText("￥" + price_dj);
    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        mAddressInfo.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
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
            address_id = data.getStringExtra("address_id");
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
            case R.id.btn_write_order_submit:
                String remarks = mRemarks.getText().toString().trim();
                flag = 1;
                map.clear();
                map.put("user_id", new User(this).getUseId() + "");
                map.put("clothes_id", clothes_id);
                map.put("address_id", address_id);
                map.put("color_name", color_name);
                map.put("size_name", size_name);
                map.put("num", "1");
                map.put("remarks", remarks);
                instance.post(NetConfig.PAY_365_CLO_ORDER, map, this);
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
        switch (flag) {
            case 0:
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray body = object.getJSONArray("body");
                    JSONObject jsonObject = body.getJSONObject(0);
                    String receive_city = jsonObject.getString("receive_city");
                    String receive_name = jsonObject.getString("receive_name");
                    String receive_phone = jsonObject.getString("receive_phone");
                    String receive_address = jsonObject.getString("receive_address");
                    address_id = jsonObject.getString("id");
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
                break;
            case 1:
                Utils.Log(result);
                try {
                    JSONObject obj = new JSONObject(result);
                    String ret = obj.getString("ret");
                    if (TextUtils.equals("0", ret)) {
                        JSONObject body = obj.getJSONObject("body");
                        String one_price = body.getString("one_price");
                        String orderid = body.getString("id");
                        jumpMap.put("managerPhoto", clothes_get);
                        jumpMap.put("managerName", name);
                        jumpMap.put("nowMoney", one_price);
                        jumpMap.put("orderid", orderid);
                        jumpMap.put("type", type);
                        ActivityUtils.switchTo(this, PayOrderActivity.class, jumpMap);
                    } else {
                        Toast.makeText(this, "网络错误，请重试", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }
}
