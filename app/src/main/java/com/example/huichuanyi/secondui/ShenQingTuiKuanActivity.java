package com.example.huichuanyi.secondui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.bean.ServiceBean;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class ShenQingTuiKuanActivity extends BaseActivity implements View.OnClickListener {

    //private RoundImageView mImagePhoto;
    private TextView mTextViewName, mTextMoneyAll, allMoeny;
    private Button mButtonSure;
    private EditText mEditText;

    private TextView textView6;
    private String ordertime;

    private String managernumber;

    private String mResult;

    private String retMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shen_qing_tui_kuan);
    }

    @Override
    public void initView() {
        mTextViewName = (TextView) findViewById(R.id.tv_shenqing_name);
        mTextMoneyAll = (TextView) findViewById(R.id.tv_shenqing_moneyall);
        mButtonSure = (Button) findViewById(R.id.btn_shenqingtuikuan_sure);
        mEditText = (EditText) findViewById(R.id.et_shenqing_write);
        textView6 = (TextView) this.findViewById(R.id.textView6);
        allMoeny = (TextView) this.findViewById(R.id.tv_shenqing_allmoney);

    }

    public void back(View view) {
        finish();
    }

    private ServiceBean.BodyBean bean;

    @Override
    public void initData() {
        bean = (ServiceBean.BodyBean) getIntent().getSerializableExtra("bean");
    }

    @Override
    public void setData() {
        mTextViewName.setText(bean.getSellerUserName());
        mTextMoneyAll.setText(bean.getMoneyTotal());
        textView6.setText(bean.getMoneyTotal());
        allMoeny.setText(bean.getMoneyTotal());
    }

    @Override
    public void setListener() {
        mButtonSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_shenqingtuikuan_sure:
                String refundReason = mEditText.getText().toString();
                if (refundReason.length() < 10) {
                    Toast.makeText(this, "退款理由不能小于10个字哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("1".equals(bean.getOrderType())) {
                    RequestParams pa = new RequestParams(NetConfig.TUI_KUAN_ACARUS_KILLING);
                    pa.addBodyParameter("token", "82D5FBD40259C743ADDEF14D0E22F347");
                    pa.addBodyParameter("orderId", bean.getOrderId());
                    pa.addBodyParameter("orderType", "1");
                    pa.addBodyParameter("deleteStatus", bean.getDeleteStatus());
                    pa.addBodyParameter("refundReason", refundReason);
                    x.http().post(pa, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject object = new JSONObject(result);
                                String ret = object.getString("ret");
                                if (TextUtils.equals(ret, "0")) {
                                    Toast.makeText(ShenQingTuiKuanActivity.this, "正在申请中，亲，请耐心等待哦", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else if (TextUtils.equals(result, "2")) {
                                    Toast.makeText(ShenQingTuiKuanActivity.this, "退款订单已经申请，请勿重复提交", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ShenQingTuiKuanActivity.this, "申请退款失败", Toast.LENGTH_SHORT).show();
                                }
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
                    });
                } else {
                    isTime();
                }
                break;
        }
    }

    private void isTime() {
        RequestParams pa = new RequestParams(NetConfig.TUI_KUAN_TIME_BOOLEAN);
        pa.addBodyParameter("orderTime", bean.getConsigneeTime());
        x.http().post(pa, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsono = new JSONObject(result);
                    String retCode = jsono.getString("retCode");
                    retMsg = jsono.getString("retMsg");
                    if ("1".equals(retCode)) {
                        showDialogs();
                    } else if ("2".equals(retCode)) {
                        upTuiKuanData();
                    } else {
                        Toast.makeText(ShenQingTuiKuanActivity.this, "申请退款失败", Toast.LENGTH_SHORT).show();
                    }
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
        });
    }

    private void upTuiKuanData() {
        String reason = mEditText.getText().toString().trim();
        RequestParams params = new RequestParams(NetConfig.TUI_KUAN);
        String userid = SharedPreferenceUtils.getUserData(this, 1);
        params.addBodyParameter("orderid", bean.getOrderId());
        params.addBodyParameter("state", bean.getDeleteStatus());
        params.addBodyParameter("userid", userid);
        params.addBodyParameter("reason", reason);
        params.addBodyParameter("orderTime", ordertime);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                mResult = result;
                go(result);
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

    private void go(String result) {
        if (TextUtils.equals(result, "1")) {
            Toast.makeText(ShenQingTuiKuanActivity.this, "正在申请中，亲，请耐心等待哦", Toast.LENGTH_SHORT).show();
            finish();
        } else if (TextUtils.equals(result, "2")) {
            Toast.makeText(ShenQingTuiKuanActivity.this, "退款订单已经申请，请勿重复提交", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ShenQingTuiKuanActivity.this, "申请退款失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialogs() {
        MySelfDialog dialog = new MySelfDialog(this);
        dialog.setMessage(retMsg);
        dialog.setOnNoListener("取消", null);
        dialog.setOnYesListener("联系工作室", new MySelfDialog.OnYesClickListener() {
            @Override
            public void onClick() {
                upTuiKuanData();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + managernumber));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        dialog.show();
    }
}
