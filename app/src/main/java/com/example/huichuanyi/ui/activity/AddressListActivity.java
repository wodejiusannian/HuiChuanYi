package com.example.huichuanyi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.ListAddressAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.bean.MyAddress;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.Utils;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressListActivity extends BaseActivity implements ListAddressAdapter.Info, View.OnClickListener, UtilsInternet.XCallBack, SwipeRefreshLayout.OnRefreshListener, MySelfDialog.OnYesClickListener, AdapterView.OnItemLongClickListener {

    private ListView mShow;
    private ListAddressAdapter adapter;
    private List<MyAddress> mData = new ArrayList<>();
    private Intent intent;
    private TextView mSure;
    private String mName, mPhone, mAddress, mCity;
    private Button mAdd;
    private UtilsInternet instance;
    private Map<String, String> map = new HashMap<>();
    private SwipeRefreshLayout mRefresh;
    private String user_Id, address_id;
    private int pos, flag;

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
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.sf_list_address);
    }

    @Override
    public void initData() {
        intent = getIntent();
        user_Id = SharedPreferenceUtils.getUserData(this,1);
        map.put("user_id", user_Id);
        instance = UtilsInternet.getInstance();
        adapter = new ListAddressAdapter(this, mData);
    }

    @Override
    public void setData() {
        mShow.setAdapter(adapter);
        loadMore();
    }

    @Override
    public void setListener() {
        adapter.getInfo(this);
        mSure.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        mRefresh.setOnRefreshListener(this);
        adapter.setOnItemUpDateListener(this);
        mShow.setOnItemLongClickListener(this);

    }

    public void back(View view) {
        finish();
    }


    @Override
    public void getInfo(String addressId, String receive_city, String name, String phone, String add) {
        mCity = receive_city;
        mName = name;
        mPhone = phone;
        mAddress = add;
        address_id = addressId;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_list_address_sure:
                transmissionInfo();
                break;
            case R.id.btn_add_address:
                addPersonAddress();
                break;
            case R.id.tv_item_update_address:
                int tag = (int) v.getTag();
                upDateAddress(tag);
                break;
            default:
                break;
        }
    }

    /*
    * 修改选择的地址
    * */
    private void upDateAddress(int tag) {
        Intent intent = new Intent(this, AddressWriteActivity.class);
        intent.putExtra("tag", tag);
        intent.putExtra("type", "9001");
        intent.putExtra("addressId", mData.get(tag).getId());
        intent.putExtra("name", mData.get(tag).getReceive_name());
        intent.putExtra("phone", mData.get(tag).getReceive_phone());
        intent.putExtra("city", mData.get(tag).getReceive_city());
        intent.putExtra("selector_address", mData.get(tag).getReceive_address());
        startActivityForResult(intent, 1000);
    }

    /*
    * 进行地址添加的页面
    * */
    private void addPersonAddress() {
        Intent intent = new Intent(this, AddressWriteActivity.class);
        startActivityForResult(intent, 1000);
    }

    /*
    * 选择地址后，将数据返回到上个页面
    * */
    private void transmissionInfo() {
        intent.putExtra("name", mName);
        intent.putExtra("phone", mPhone);
        intent.putExtra("selector_address", mAddress);
        intent.putExtra("city", mCity);
        intent.putExtra("address_id", address_id);
        setResult(1001, intent);
        finish();
    }

    /*
    * 关闭掉添加或者修改地址的操作
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 1001) {
            flag = 0;
            map.clear();
            map.put("user_id", user_Id);
            instance.post(NetConfig.GET_PERSON_ADDRESS, map, this);
        }
    }

    /*
     * 在数据中进行添加并且添加到第一个
     * */
    private void addDate(String city, String street, String name, String phone) {
        MyAddress bb = new MyAddress();
        bb.setReceive_city(city);
        bb.setReceive_address(street);
        bb.setReceive_name(name);
        bb.setReceive_phone(phone);
        mData.add(0, bb);
        adapter.notifyDataSetChanged();
    }

    /*
    * 修改成功在刚才选择修改的地方进行修改
    * */
    private void update(int tag, String city, String street, String name, String phone) {
        MyAddress bb = new MyAddress();
        bb.setReceive_city(city);
        bb.setReceive_address(street);
        bb.setReceive_name(name);
        bb.setReceive_phone(phone);
        mData.set(tag, bb);
    }

    /*
    * 页面刷新操作
    * */
    @Override
    public void onRefresh() {
        loadMore();
    }

    /*
    * 获取数据的操作
    * */
    private void loadMore() {
        instance.post(NetConfig.GET_PERSON_ADDRESS, map, this);
        mRefresh.setRefreshing(false);
    }

    /*
    * 如果执行的删除操作，那么flag = 1，如果执行的获取地址列表的操作flag = 0；
    * */
    @Override
    public void onResponse(String result) {
        Utils.Log(result);
        switch (flag) {
            case 0:
                mData.clear();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray body = jsonObject.getJSONArray("body");
                    JSONObject jsonObject1 = body.getJSONObject(0);
                    mName = jsonObject1.getString("receive_name");
                    mPhone = jsonObject1.getString("receive_phone");
                    mCity = jsonObject1.getString("receive_city");
                    mAddress = jsonObject1.getString("receive_address");
                    address_id = jsonObject1.getString("id");
                    for (int i = 0; i < body.length(); i++) {
                        JSONObject object = body.getJSONObject(i);
                        MyAddress address = new MyAddress();
                        address.setReceive_address(object.getString("receive_address"));
                        address.setReceive_name(object.getString("receive_name"));
                        address.setReceive_phone(object.getString("receive_phone"));
                        address.setReceive_city(object.getString("receive_city"));
                        address.setId(object.getString("id"));
                        mData.add(address);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                break;
            case 1:
                flag = 0;
                String ret = JsonUtils.getRet(result);
                if (TextUtils.equals("0", ret)) {
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }

    /*
    *点击确认删除要做到事件
    * */
    @Override
    public void onClick() {
        map.clear();
        String id = mData.get(pos).getId();
        mData.remove(pos);
        adapter.notifyDataSetChanged();
        flag = 1;
        map.put("id", id);
        map.put("user_id", user_Id);
        instance.post(NetConfig.DELETE_PERSON_ADDRESS, map, this);
    }

    /*
    * ListView的长按进行删除事件
    * */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        pos = position;
        MySelfDialog mySelfDialog = new MySelfDialog(this);
        mySelfDialog.setMessage("确认要删除当前地址吗？");
        mySelfDialog.setTitle("温馨提示");
        mySelfDialog.setOnNoListener("取消", null);
        mySelfDialog.setOnYesListener("确定", this);
        mySelfDialog.show();
        return false;
    }
}
