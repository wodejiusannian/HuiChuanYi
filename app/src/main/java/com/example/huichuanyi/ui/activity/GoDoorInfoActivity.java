package com.example.huichuanyi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.bean.City;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.DateTimePickDialogUtil;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.ReminderUtils;
import com.example.huichuanyi.utils.User;
import com.example.huichuanyi.utils.UtilsInternet;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GoDoorInfoActivity extends BaseActivity implements UtilsInternet.XCallBack {
    private static final String TAG = GoDoorInfoActivity.class.getSimpleName();

    @ViewInject(R.id.tv_godoor_name)
    private TextView tvName;

    @ViewInject(R.id.tv_godoor_phone)
    private TextView tvPhone;

    @ViewInject(R.id.tv_godoor_add)
    private TextView tvAdd;

    @ViewInject(R.id.et_godoor_count)
    private TextView etCount;

    @ViewInject(R.id.tv_godoor_money)
    private TextView tvMoney;

    @ViewInject(R.id.tv_studio_info_name)
    private TextView studioName;

    @ViewInject(R.id.sv_studio_info_logo)
    private SimpleDraweeView studioLogo;

    @ViewInject(R.id.tv_studio_into_jianjie)
    private TextView studioJianjie;

    @ViewInject(R.id.tv_studio_into_city)
    private TextView studioCity;

    @ViewInject(R.id.tv_godoor_time)
    private TextView tvTime;

    private String userID, address_id, managerid, manager_name, manager_number, city;
    private UtilsInternet net = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();

    private int intPrice1, intPrice2, intPrice_baseNum1, intPrice_baseNum2, intPrice_raiseNum, intPrice_raisePrice;

    private int flag = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            tvName.setText(data.getString("receive_name"));
            tvPhone.setText(data.getString("receive_phone"));
            tvAdd.setText(data.getString("receive_city") + data.getString("receive_address"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_door_info);
    }

    @Override
    public void initView() {
        if (map == null)
            map = new HashMap<>();
        userID = new User(this).getUseId() + "";
        City.BodyBean bodyBean = (City.BodyBean) getIntent().getSerializableExtra("bodyBean");
        managerid = bodyBean.getId();
        manager_name = bodyBean.getName();
        manager_number = bodyBean.getPhone();
        city = bodyBean.getCity();
        studioLogo.setImageURI(bodyBean.getPhoto_get());
        studioName.setText(bodyBean.getName());
        studioCity.setText(city);
        studioJianjie.setText(bodyBean.getIntroduction());
        intPrice_baseNum1 = stringToInt(bodyBean.getBase_num1());
        intPrice_baseNum2 = stringToInt(bodyBean.getBase_num2());
        intPrice_raiseNum = stringToInt(bodyBean.getRaise_num());
        intPrice1 = stringToInt(bodyBean.getBase_price1());
        intPrice2 = stringToInt(bodyBean.getBase_price2());
        intPrice_raisePrice = stringToInt(bodyBean.getRaise_price());
        map.put("user_id", userID);
        net.post(NetConfig.GET_PERSON_ADDRESS, map, this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        etCount.addTextChangedListener(textWatcher);
    }

    @Event({R.id.tv_pay_what, R.id.rl_time, R.id.rl_address, R.id.btn_pay})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pay_what:
                ActivityUtils.switchTo(this, LiJiYuYueWhatActivity.class);
                break;
            case R.id.rl_time:
                selectTime();
                break;
            case R.id.rl_address:
                jumpListAddress();
                break;
            case R.id.btn_pay:
                goPay();
                break;
            default:
                break;
        }
    }

    /*
    * 提交支付
    * */
    private void goPay() {
        String time = tvTime.getText().toString().trim();
        String count = etCount.getText().toString().trim();
        String user_name = tvName.getText().toString().trim();
        String userCity = tvAdd.getText().toString().trim();
        String userPhone = tvPhone.getText().toString().trim();
        String money = tvMoney.getText().toString().trim();
        if (!CommonUtils.isEmpty(address_id) && !CommonUtils.isEmpty(time) && !CommonUtils.isEmpty(count)) {
            flag = 1;
            map.put("manager_id", managerid);
            map.put("user_code", userID);
            map.put("user_name", user_name);
            map.put("service_mode", "上门服务");
            map.put("manager_name", manager_name);
            map.put("manager_number", manager_number);
            map.put("city", city);
            map.put("selector_address", userCity);
            map.put("contact_number", userPhone);
            map.put("number", count);
            map.put("money", money);
            map.put("ordername", user_name);
            map.put("ordertime", time);
            map.put("remarks", "");
            net.post(NetConfig.UPLOADING_COM_DETAILS, map, this);
        } else {
            ReminderUtils.Toast(this, "请完整填写信息");
        }
    }

    /*
    * 选择时间
    * */
    private void selectTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String str = sdf.format(new Date());
        DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                this, str);
        dateTimePicKDialog.dateTimePicKDialog2(tvTime);
    }

    /*
    * 输入衣服数量，计算价格
    * */
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!TextUtils.isEmpty(s)) {
                Integer nowCount = Integer.valueOf(s.toString());
                if (0 == nowCount) {
                    tvMoney.setText("0");
                    return;
                }
                if (0 < nowCount && nowCount <= intPrice_baseNum1) {
                    tvMoney.setText(intPrice1 + "");
                    return;
                } else if (intPrice_baseNum1 < nowCount && nowCount <= intPrice_baseNum2) {
                    tvMoney.setText(intPrice2 + "");
                    return;
                } else {
                    int a = (nowCount - intPrice_baseNum2) / intPrice_raiseNum;
                    a = a + 1;
                    tvMoney.setText((a * intPrice_raisePrice + intPrice2) + "");
                    return;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /*
    * String 转 int
    * */
    private int stringToInt(String str) {
        return Integer.parseInt(str);
    }

    private void jumpListAddress() {
        Intent intent = new Intent(this, AddressListActivity.class);
        startActivityForResult(intent, 1000);
    }

    /*
    * 处理选择地址后的数据
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 1001) {
            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");
            String city = data.getStringExtra("city");
            String address = data.getStringExtra("selector_address");
            address_id = data.getStringExtra("address_id");
            tvName.setText(name);
            tvPhone.setText(phone);
            tvAdd.setText(city + address);
        }
    }

    /*
    * 处理网络请求的结果
    * */
    @Override
    public void onResponse(String result) {
        switch (flag) {
            /*
            * case == 0 时，处理的是选择地址
            * */
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
            /*
            * case == 1时处理提交支付的数据
            * */
            case 1:
                Log.e(TAG, "onResponse: " + result);
            default:
                break;
        }

    }

    public void back(View view) {
        finish();
    }
}
