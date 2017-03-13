package com.example.huichuanyi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.Location;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.DateTimePickDialogUtil;
import com.example.huichuanyi.custom.RoundImageView;
import com.example.huichuanyi.secondui.PayOrderActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.MySharedPreferences;
import com.example.huichuanyi.utils.User;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private ImageView popupDialog, mImageViewBack;
    private RoundImageView mImageViewPhoto;
    private EditText mETWriteCount, mEditTextUserName, mEditTextUserPhone,
            mEditTextUserAddress, mEditTextRemarks;
    private TextView moneyAll, allMoney, mTextViewManageName, mTime, mPrice;
    private String nowMoney, city;
    private Button mButtonSubmit;
    private String managerid, managerName, managerPhone, managerPhoto, price1, price2, price_baseNum1, price_baseNum2, price_raiseNum, price_raisePrice;
    private int intPrice1, intPrice2, intPrice_baseNum1, intPrice_baseNum2, intPrice_raiseNum, intPrice_raisePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_order_details);

    }

    @Override
    public void initView() {
        popupDialog = (ImageView) findViewById(R.id.iv_details_popup);
        mETWriteCount = (EditText) findViewById(R.id.et_order_writeCount);
        allMoney = (TextView) findViewById(R.id.tv_details_allmoney);
        mButtonSubmit = (Button) findViewById(R.id.bt_details_submit);
        mImageViewBack = (ImageView) findViewById(R.id.iv_details_back);
        mEditTextUserName = (EditText) findViewById(R.id.et_order_write);
        mEditTextUserPhone = (EditText) findViewById(R.id.et_order_phone);
        mEditTextUserAddress = (EditText) findViewById(R.id.et_order_address);
        mEditTextRemarks = (EditText) findViewById(R.id.et_order_markets);
        moneyAll = (TextView) findViewById(R.id.tv_order_moneyall);
        mTime = (TextView) findViewById(R.id.tv_order_details_mTime);
        mImageViewPhoto = (RoundImageView) findViewById(R.id.iv_order_photo);
        mTextViewManageName = (TextView) findViewById(R.id.tv_order_name);
        mPrice = (TextView) findViewById(R.id.tv_price_details);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        managerid = intent.getStringExtra("managerid");
        managerName = intent.getStringExtra("managename");
        managerPhone = intent.getStringExtra("managerPhone");
        managerPhoto = intent.getStringExtra("managerPhoto");
        city = intent.getStringExtra("city");
        price1 = intent.getStringExtra("price1");
        price2 = intent.getStringExtra("price2");
        price_baseNum1 = intent.getStringExtra("price_baseNum1");
        price_baseNum2 = intent.getStringExtra("price_baseNum2");
        price_raiseNum = intent.getStringExtra("price_raiseNum");
        price_raisePrice = intent.getStringExtra("price_raisePrice");

        if (!TextUtils.isEmpty(price1) && !TextUtils.equals("null", price1)) {
            intPrice1 = Integer.parseInt(price1);
        }
        if (!TextUtils.isEmpty(price2) && !TextUtils.equals("null", price2)) {
            intPrice2 = Integer.parseInt(price2);
        }
        if (!TextUtils.isEmpty(price_baseNum1) && !TextUtils.equals("null", price_baseNum1)) {
            intPrice_baseNum1 = Integer.parseInt(price_baseNum1);
        }
        if (!TextUtils.isEmpty(price_baseNum2) && !TextUtils.equals("null", price_baseNum2)) {
            intPrice_baseNum2 = Integer.parseInt(price_baseNum2);
        }
        if (!TextUtils.isEmpty(price_raiseNum) && !TextUtils.equals("null", price_raiseNum)) {
            intPrice_raiseNum = Integer.parseInt(price_raiseNum);
        }
        if (!TextUtils.isEmpty(price_raisePrice) && !TextUtils.equals("null", price_raisePrice)) {
            intPrice_raisePrice = Integer.parseInt(price_raisePrice);
        }
        mPrice.setText("200件以内价格为" + price1 + "\n详情请点击按钮查看");
    }

    @Override
    public void setData() {
        if (!TextUtils.isEmpty(Location.mTime)) {
            mTime.setText(Location.mTime);
        }
        if (!TextUtils.isEmpty(managerPhoto)) {
            Picasso.with(this).load(managerPhoto).into(mImageViewPhoto);
        }
        if (!TextUtils.isEmpty(managerName)) {
            mTextViewManageName.setText(managerName);
        }
    }

    @Override
    public void setListener() {
        popupDialog.setOnClickListener(this);
        mButtonSubmit.setOnClickListener(this);
        mImageViewBack.setOnClickListener(this);
        mETWriteCount.addTextChangedListener(this);
        mTime.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_details_popup:
                showDialog();
                break;
            case R.id.bt_details_submit:
                uploadingData();
                break;
            case R.id.iv_details_back:
                finish();
                break;
            case R.id.tv_order_details_mTime:
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                String str = sdf.format(new Date());
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        this, str);
                dateTimePicKDialog.dateTimePicKDialog2(mTime);
                break;
        }
    }

    protected void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.view_show_dialog);
        builder.create().show();
    }

    public String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String format = formatter.format(new Date());
        return format;
    }

    public void uploadingData() {
        String userPhone = mEditTextUserPhone.getText().toString().trim();
        String userAddress = mEditTextUserAddress.getText().toString().trim();
        String userName = mEditTextUserName.getText().toString().trim();
        String clothesCount = mETWriteCount.getText().toString().toString().trim();
        String userRemarks = mEditTextRemarks.getText().toString().trim();
        String ordertime = mTime.getText().toString().trim();
        if (!TextUtils.isEmpty(clothesCount) && !TextUtils.isEmpty(userPhone) && !TextUtils.isEmpty(userAddress) && !TextUtils.isEmpty(userName)) {
            final RequestParams params = new RequestParams(NetConfig.UPLOADING_COM_DETAILS);
            params.addBodyParameter("manager_id", managerid);
            params.addBodyParameter("user_code", new User(this).getUseId() + "");
            params.addBodyParameter("user_name", userName);
            params.addBodyParameter("manager_name", managerName);
            params.addBodyParameter("manager_number", managerPhone);
            params.addBodyParameter("city", MySharedPreferences.getCity(this));
            params.addBodyParameter("address", userAddress);
            params.addBodyParameter("contact_number", userPhone);
            params.addBodyParameter("service_mode", "上门服务");
            params.addBodyParameter("number", clothesCount);
            params.addBodyParameter("money", nowMoney);
            params.addBodyParameter("ordertime", ordertime);
            params.addBodyParameter("ordername", userName);
            params.addBodyParameter("remarks", userRemarks);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if (result.length() > 5) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("managerPhoto", managerPhoto);
                        map.put("managerName", managerName);
                        map.put("nowMoney", nowMoney);
                        map.put("orderid", result);
                        map.put("type", "1");
                        ActivityUtils.switchTo(OrderDetailsActivity.this, PayOrderActivity.class, map);
                    } else if ("0".equals(result)) {
                        Toast.makeText(OrderDetailsActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    // Toast.makeText(OrderDetailsActivity.this, R.string.err_internet, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        } else {
            Toast.makeText(OrderDetailsActivity.this, "亲，姓名，手机号，地址，和衣服数量不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s)) {
            Integer nowCount = Integer.valueOf(s.toString());
            if (0 == nowCount) {
                nowMoney = "0";
                allMoney.setText(nowMoney);
                moneyAll.setText(nowMoney);
                return;
            }
            if (0 < nowCount && nowCount <= intPrice_baseNum1) {
                nowMoney = price1;
                allMoney.setText(nowMoney);
                moneyAll.setText(nowMoney);
                return;
            } else if (nowCount <= intPrice_baseNum2) {
                nowMoney = price2;
                allMoney.setText(nowMoney);
                moneyAll.setText(nowMoney);
                return;
            } else {
                Log.i("TAG", "------------"+intPrice_raiseNum);
                int a = (nowCount - intPrice_baseNum2) / intPrice_raiseNum;
                a = a + 1;
                nowMoney = (a * intPrice_raisePrice + intPrice2) + "";
                allMoney.setText(nowMoney);
                moneyAll.setText(nowMoney);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


}
