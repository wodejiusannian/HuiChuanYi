package com.example.huichuanyi.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.bean.City;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.GlideRoundTransform;
import com.example.huichuanyi.custom.dialog.CouponDialog;
import com.example.huichuanyi.secondui.PayOrderActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.ReminderUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;


public class GoDoorInfoActivity extends BaseActivity implements UtilsInternet.XCallBack {

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
    private ImageView studioLogo;

    @ViewInject(R.id.tv_studio_into_jianjie)
    private TextView studioJianjie;

    @ViewInject(R.id.tv_ly_buy_commendpeople)
    private TextView coupon;

    @ViewInject(R.id.tv_studio_into_city)
    private TextView studioCity;

    @ViewInject(R.id.tv_godoor_time)
    private TextView tvTime;

    @ViewInject(R.id.rl_ly_youhuiquan)
    private RelativeLayout youhuiquan;

    private String userID, address_id, managerid, manager_name, city, manager_photo, mmCount, invitation_code;
    private UtilsInternet net = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();


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
        userID = SharedPreferenceUtils.getUserData(this, 1);
        City.BodyBean bodyBean = (City.BodyBean) getIntent().getSerializableExtra("bodyBean");
        managerid = bodyBean.getId();
        manager_name = bodyBean.getName();
        manager_photo = bodyBean.getPhoto_get();
        city = bodyBean.getCity();
        Glide.with(this).load(bodyBean.getPhoto_get()).transform(new GlideRoundTransform(this)).into(studioLogo);
        studioName.setText(bodyBean.getName());
        studioCity.setText(city);
        studioJianjie.setText(bodyBean.getIntroduction());
        map.put("user_id", userID);
        net.post(NetConfig.GET_PERSON_ADDRESS, map, this);
    }

    @Override
    public void initData() {
        final ImageView iv = (ImageView) findViewById(R.id.iv);
        RequestParams pa = new RequestParams("http://hmyc365.net:8080/HuiMei/app/appOrder!xiadanTishi.action");
        x.http().post(pa, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(GoDoorInfoActivity.this).load(result).error(R.mipmap.hm_24_hour_no_cancel).into(iv);
                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void setData() {
    }



    @Override
    public void setListener() {
        etCount.addTextChangedListener(watcher);
    }

    @Event({R.id.tv_pay_what, R.id.rl_time, R.id.rl_address, R.id.btn_pay, R.id.rl_ly_youhuiquan})
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
            case R.id.rl_ly_youhuiquan:
                CouponDialog dialog = new CouponDialog(this);
                dialog.onResult(new CouponDialog.EventResult() {
                    @Override
                    public void onResult(String result) {
                        invitation_code = result;
                        coupon.setText(result);
                        handler.postDelayed(delayRun, 800);
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                });
                dialog.show();
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
        if (!CommonUtils.isEmpty(address_id) && !TextUtils.equals("选择时间", time) && !CommonUtils.isEmpty(count)) {
            showLoading();
            flag = 1;
            map.put("user_id", userID);
            map.put("studio_id", managerid);
            map.put("clothes_num", count);
            map.put("address_id", address_id);
            if (CommonUtils.isEmpty(invitation_code)) {
                map.put("type", "0");
            } else {
                map.put("type", "1");
            }
            map.put("shr_address", userCity);
            map.put("shr_phone", userPhone);
            map.put("shr_name", user_name);
            map.put("invitation_code", invitation_code);
            map.put("order_date", time.substring(0, 10));
            String substring = time.substring(10, time.length());
            if (TextUtils.equals("上午", substring)) {
                map.put("order_date_tag", "AM_BUSY");
            } else {
                map.put("order_date_tag", "PM_BUSY");
            }
            map.put("order_time", time);
            map.put("remarks", city);
            net.post(NetConfig.UPLOADING_COM_DETAILS, map, this);
        } else {
            ReminderUtils.Toast(this, "请完整填写信息");
        }
    }

    /*
    * 选择时间
    * */
    private void selectTime() {
        Intent in = new Intent(this, SelectStudioTimeActivity.class);
        in.putExtra("studio_id", managerid);
        startActivityForResult(in, 2000);
    }


    private Handler handler = new Handler();

    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            RequestParams pa = new RequestParams(NetConfig.GO_DOOR_MONEY);
            pa.addBodyParameter("studio_id", managerid);
            pa.addBodyParameter("studio_city", city);
            pa.addBodyParameter("clothes_num", mmCount);
            pa.addBodyParameter("invitation_code", invitation_code);
            x.http().post(pa, new Callback.CacheCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject object = new JSONObject(result);
                        JSONObject body = object.getJSONObject("body");
                        final String price = body.getString("price");
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                tvMoney.setText(price);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }

                @Override
                public boolean onCache(String result) {
                    return false;
                }
            });
        }
    };

    TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (delayRun != null) {
                //每次editText有变化的时候，则移除上次发出的延迟线程
                handler.removeCallbacks(delayRun);
            }
            mmCount = s.toString();
            //延迟800ms，如果不再输入字符，则执行该线程的run方法
            handler.postDelayed(delayRun, 800);
        }
    };

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
        } else if (requestCode == 2000) {
            try {
                String time = data.getStringExtra("time");
                tvTime.setText(time);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
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
                try {
                    dismissLoading();
                    JSONObject object = new JSONObject(result);

                    String ret = object.getString("ret");
                    if (TextUtils.equals("1090", ret)) {
                        Toast.makeText(this, "优惠码错误", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.equals("5000", ret)) {
                        Toast.makeText(this, "购买成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MyOrderActivity.class));
                        finish();
                    } else {
                        JSONObject body = object.getJSONObject("body");
                        String order_id = body.getString("order_id");
                        if (LiJiYuYueActivity.instanceLiji != null) {
                            LiJiYuYueActivity.instanceLiji.finish();
                            LiJiYuYueActivity.instanceLiji = null;
                        }
                        if (ManageActivity.instanceManage != null) {
                            ManageActivity.instanceManage.finish();
                            ManageActivity.instanceManage = null;
                        }
                        Map<String, Object> jumpMap = new HashMap<>();
                        jumpMap.put("managerPhoto", manager_photo);
                        jumpMap.put("managerName", manager_name);
                        jumpMap.put("nowMoney", tvMoney.getText().toString().trim());
                        jumpMap.put("orderid", order_id);
                        jumpMap.put("type", "1");
                        jumpMap.put("num", "1");
                        jumpMap.put("studio_id", managerid);
                        jumpMap.put("city", city);
                        jumpMap.put("count", etCount.getText().toString());
                        jumpMap.put("invitation_code", invitation_code);
                        ActivityUtils.switchTo(this, PayOrderActivity.class, jumpMap);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            default:
                break;
        }

    }

    public void back(View view) {
        finish();
    }
}
