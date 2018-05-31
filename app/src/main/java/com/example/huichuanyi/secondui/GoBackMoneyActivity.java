package com.example.huichuanyi.secondui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.model.OrderFormOrder;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class GoBackMoneyActivity extends BaseActivity {

    @BindViews({R.id.tv_shenqing_name, R.id.tv_shenqing_moneyall,
            R.id.tv_shenqing_allmoney, R.id.textView6})
    TextView[] tvs;

    @BindView(R.id.et_shenqing_write)
    EditText mEditText;

    private String retMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shen_qing_tui_kuan);

    }

    public void back(View view) {
        finish();
    }

    private OrderFormOrder.BodyBean bean;

    @Override
    public void initData() {
        bean = getIntent().getParcelableExtra("bean");
    }

    OrderFormOrder.BodyBean.OrderInfoBean infoBean;

    @Override
    public void setData() {
        infoBean = bean.getOrderInfo().get(0);
        tvs[0].setText(infoBean.getSellerUserName());
        tvs[1].setText(infoBean.getMoneyTotal());
        tvs[2].setText(infoBean.getMoneyTotal());
        tvs[3].setText(infoBean.getMoneyTotal());
    }

    @Override
    public void setListener() {
    }


    @OnClick(R.id.btn_shenqingtuikuan_sure)
    public void onEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_shenqingtuikuan_sure:
                String refundReason = mEditText.getText().toString();
                if (refundReason.length() < 10) {
                    Toast.makeText(this, "退款理由不能小于10个字哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("1".equals(infoBean.getOrderType())) {
                    RequestParams pa = new RequestParams(NetConfig.TUI_KUAN_ACARUS_KILLING);
                    pa.addBodyParameter("token", "82D5FBD40259C743ADDEF14D0E22F347");
                    pa.addBodyParameter("orderId", bean.getOrderId());
                    pa.addBodyParameter("orderType", "1");
                    pa.addBodyParameter("deleteStatus", infoBean.getDeleteStatus());
                    pa.addBodyParameter("refundReason", refundReason);
                    x.http().post(pa, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject object = new JSONObject(result);
                                String ret = object.getString("ret");
                                if (TextUtils.equals(ret, "0")) {
                                    Toast.makeText(GoBackMoneyActivity.this, "正在申请中，亲，请耐心等待哦", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else if (TextUtils.equals(result, "2")) {
                                    Toast.makeText(GoBackMoneyActivity.this, "退款订单已经申请，请勿重复提交", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(GoBackMoneyActivity.this, "申请退款失败", Toast.LENGTH_SHORT).show();
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
            default:
                break;
        }
    }

    private void isTime() {
        RequestParams pa = new RequestParams(NetConfig.TUI_KUAN_TIME_BOOLEAN);
        pa.addBodyParameter("orderTime", infoBean.getPayTime());
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
                        Toast.makeText(GoBackMoneyActivity.this, "申请退款失败", Toast.LENGTH_SHORT).show();
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
        RequestParams params = new RequestParams(NetConfig.GOBACKMONEY_GODOOR);
        String userid = SharedPreferenceUtils.getUserData(this, 1);
        params.addBodyParameter("buyUserId", userid);
        params.addBodyParameter("orderType", infoBean.getOrderType());
        params.addBodyParameter("deleteStatus", infoBean.getDeleteStatus());
        params.addBodyParameter("orderId", bean.getOrderId());
        params.addBodyParameter("refundReason", reason);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
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
        String ret = JsonUtils.getRet(result);
        if (TextUtils.equals(ret, "0")) {
            Toast.makeText(GoBackMoneyActivity.this, "正在申请中，亲，请耐心等待哦", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(GoBackMoneyActivity.this, "退款订单已经申请，请勿重复提交", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + infoBean.getSellerPhone()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        dialog.show();
    }
}
