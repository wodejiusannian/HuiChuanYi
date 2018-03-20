package com.example.huichuanyi.secondui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class PingJiaActivity extends BaseActivity implements View.OnClickListener {
    private RatingBar mBarZhiLiang, mBarTaiDu, mBarTime;
    private EditText mEditTextPingJia;
    private Button mButtonTiJiao;
    private String orderid, manager_id, user_name, manager_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_jia);
    }

    @Override
    public void initView() {
        mBarZhiLiang = (RatingBar) findViewById(R.id.rb_pingjia_zhiliang);
        mBarTaiDu = (RatingBar) findViewById(R.id.rb_pingjia_taidu);
        mBarTime = (RatingBar) findViewById(R.id.rb_pingjia_time);
        mEditTextPingJia = (EditText) findViewById(R.id.et_pingjia_pingjia);
        mButtonTiJiao = (Button) findViewById(R.id.btn_pingjia_tijiao);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        orderid = intent.getStringExtra("orderid");
        manager_id = intent.getStringExtra("manager_id");
        user_name = intent.getStringExtra("user_name");
        manager_name = intent.getStringExtra("manager_name");
        type = intent.getStringExtra("type");
    }

    String type;

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        mButtonTiJiao.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pingjia_tijiao:
                String pingjia = mEditTextPingJia.getText().toString();
                if (pingjia.length() < 10) {
                    Toast.makeText(this, "评价最少10个字哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("1".equals(type)) {
                    float zhilaing = mBarZhiLiang.getRating();
                    float taidu = mBarTaiDu.getRating();
                    float shijian = mBarTime.getRating();
                    float evaluateAverage = (float) ((zhilaing + taidu + shijian) / 3.00);
                    RequestParams pa = new RequestParams(NetConfig.PINGJIA_ACARUS_KILLING);
                    pa.addBodyParameter("token", "82D5FBD40259C743ADDEF14D0E22F347");
                    pa.addBodyParameter("orderId", orderid);
                    pa.addBodyParameter("evaluateContent", pingjia);
                    pa.addBodyParameter("orderType", "1");
                    pa.addBodyParameter("evaluateAverage", evaluateAverage + "");
                    x.http().post(pa, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            String ret = JsonUtils.getRet(result);
                            if ("0".equals(ret)) {
                                Toast.makeText(PingJiaActivity.this, "评价成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(PingJiaActivity.this, "请重新提交", Toast.LENGTH_SHORT).show();
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
                    upPingJiaTo();
                }
                break;
        }
    }

    private void upPingJiaTo() {
        float zhilaing = mBarZhiLiang.getRating();
        float taidu = mBarTaiDu.getRating();
        float shijian = mBarTime.getRating();
        String pingjia = mEditTextPingJia.getText().toString().trim();
        RequestParams params = new RequestParams(NetConfig.EVALUATE_ORDER);
        params.addBodyParameter("orderid", orderid);
        params.addBodyParameter("manager_id", manager_id);
        params.addBodyParameter("user_name", user_name);
        params.addBodyParameter("manager_name", manager_name);
        params.addBodyParameter("user_code", SharedPreferenceUtils.getUserData(this, 1));
        params.addBodyParameter("stars1", zhilaing + "");
        params.addBodyParameter("stars2", taidu + "");
        params.addBodyParameter("stars3", shijian + "");
        params.addBodyParameter("content", pingjia);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.equals(result, "1")) {
                    Toast.makeText(PingJiaActivity.this, "评价成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(PingJiaActivity.this, "评价失败", Toast.LENGTH_SHORT).show();
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

    public void back(View view) {
        finish();
    }
}
