package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.RoundImageView;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.User;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class MyOrderDetailsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mMyOrderBack, mComplain;
    private TextView mName, mPhone, mAddress, mTime, mCount,
            mAllMoney1, mAllMoney2, mManageName, mRemarks;
    private RoundImageView mPhoto;
    private Button mButtonAgain;
    private String city;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String order_person = data.getString("order_person");
            String phone = data.getString("phone");
            String address = data.getString("address");
            String order_time = data.getString("order_time");
            String money = data.getString("money");
            String num = data.getString("num");
            String namager_photo = data.getString("namager_photo");
            String namager_name = data.getString("namager_name");
            String remarks = data.getString("remarks");
            mName.setText(order_person);
            mPhone.setText(phone);
            mAddress.setText(address);
            mTime.setText(order_time);
            mCount.setText(num);
            mAllMoney1.setText(money);
            mAllMoney2.setText(money);
            mManageName.setText(namager_name);
            mRemarks.setText(remarks);
            if (namager_photo.length() > 5) {
                Picasso.with(MyOrderDetailsActivity.this).load(namager_photo).into(mPhoto);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detailsss);
    }

    @Override
    public void initView() {
        mMyOrderBack = (ImageView) findViewById(R.id.iv_my_details_back);
        mName = (TextView) findViewById(R.id.tv_my_order_name);
        mPhone = (TextView) findViewById(R.id.tv_my_order_phone);
        mAddress = (TextView) findViewById(R.id.tv_my_order_address);
        mTime = (TextView) findViewById(R.id.tv_my_order_time);
        mCount = (TextView) findViewById(R.id.tv_my_order_count);
        mAllMoney1 = (TextView) findViewById(R.id.tv_my_order_allMoney1);
        mAllMoney2 = (TextView) findViewById(R.id.tv_my_order_allMoney2);
        mManageName = (TextView) findViewById(R.id.tv_my_order_manage_name);
        mRemarks = (TextView) findViewById(R.id.tv_my_order_remarks);
        mPhoto = (RoundImageView) findViewById(R.id.iv_my_order_my_photo);
        mComplain = (ImageView) findViewById(R.id.iv_my_order_complain);
        mButtonAgain = (Button) findViewById(R.id.bt_my_order_again);
    }

    @Override
    public void initData() {
        String orderid = getIntent().getStringExtra("orderid");
        String userid = new User(this).getUseId() + "";
        RequestParams params = new RequestParams(NetConfig.GET_DETAILS_SHOPPING);
        params.addBodyParameter("userid", userid);
        params.addBodyParameter("orderid", orderid);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.equals("0", result)) {
                    return;
                } else {
                    try {
                        JSONObject object = new JSONObject(result);
                        String order_person = object.getString("order_person");
                        String phone = object.getString("phone");
                        String address = object.getString("address");
                        String order_time = object.getString("order_time");
                        String money = object.getString("money");
                        String num = object.getString("num");
                        String namager_photo = object.getString("namager_photo");
                        String namager_name = object.getString("namager_name");
                        String remarks = object.getString("remarks");
                        city = object.getString("city");
                        Bundle bundle = new Bundle();
                        bundle.putString("order_person", order_person);
                        bundle.putString("phone", phone);
                        bundle.putString("address", address);
                        bundle.putString("order_time", order_time);
                        bundle.putString("money", money);
                        bundle.putString("num", num);
                        bundle.putString("namager_photo", namager_photo);
                        bundle.putString("namager_name", namager_name);
                        bundle.putString("remarks", remarks);
                        Message msg = Message.obtain();
                        msg.setData(bundle);
                        mHandler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
        });
    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        mMyOrderBack.setOnClickListener(this);
        mButtonAgain.setOnClickListener(this);
        mComplain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_my_details_back:
                finish();
                break;
            case R.id.bt_my_order_again:
                Map<String, Object> map = new HashMap<>();
                map.put("location", city);
                map.put("time", getNowTime());
                ActivityUtils.switchTo(this, LiJiYuYueActivity.class, map);
                finish();
                break;
            /*case R.id.iv_my_order_complain:
                ActivityUtils.switchTo(this,ComplainActivity.class);
                break;*/
            default:

                break;
        }
    }

    public String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        GregorianCalendar ca = new GregorianCalendar();
        int i = ca.get(GregorianCalendar.AM_PM);
        String nowTime = sdf.format(new Date());
        if (i == 0) {
            return nowTime + " " + "上午";
        } else if (i == 1) {
            return nowTime + " " + "下午";
        }
        return null;
    }
}
