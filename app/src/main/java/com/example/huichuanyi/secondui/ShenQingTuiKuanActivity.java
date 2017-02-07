package com.example.huichuanyi.secondui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.RoundImageView;
import com.example.huichuanyi.utils.User;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class ShenQingTuiKuanActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImageViewBack;
    private RoundImageView mImagePhoto;
    private TextView mTextViewName,mTextMoneyAll;
    private String managePhoto,manageName,allMoney,orderid,state,reason;
    private Button mButtonSure;
    private EditText mEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shen_qing_tui_kuan);
    }

    @Override
    public void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.iv_shenqing_back);
        mImagePhoto = (RoundImageView) findViewById(R.id.iv_shenqing_photo);
        mTextViewName = (TextView) findViewById(R.id.tv_shenqing_name);
        mTextMoneyAll = (TextView) findViewById(R.id.tv_shenqing_moneyall);
        mButtonSure = (Button) findViewById(R.id.btn_shenqingtuikuan_sure);
        mEditText = (EditText) findViewById(R.id.et_shenqing_write);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        managePhoto = intent.getStringExtra("managePhoto");
        manageName = intent.getStringExtra("manageName");
        allMoney = intent.getStringExtra("allMoney");
        orderid = intent.getStringExtra("orderid");
        state = intent.getStringExtra("state");
    }

    @Override
    public void setData() {
        if (managePhoto.length()>7) {
            Picasso.with(this).load(managePhoto).into(mImagePhoto);
        }
        mTextViewName.setText(manageName);
        mTextMoneyAll.setText(allMoney);
    }

    @Override
    public void setListener() {
        mImageViewBack.setOnClickListener(this);
        mButtonSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.iv_shenqing_back:
                finish();
                break;
            case R.id.btn_shenqingtuikuan_sure:
                upTuiKuanData();
                break;
        }
    }

    private void upTuiKuanData() {
        reason = mEditText.getText().toString().trim();
        RequestParams params = new RequestParams(NetConfig.TUI_KUAN);
        String userid = new User(this).getUseId() + "";
        params.addBodyParameter("orderid",orderid);
        params.addBodyParameter("state",state);
        params.addBodyParameter("userid",userid);
        params.addBodyParameter("reason",reason);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(TextUtils.equals(result,"1")) {
                    Toast.makeText(ShenQingTuiKuanActivity.this, "正在申请中，亲，请耐心等待哦", Toast.LENGTH_SHORT).show();
                    finish();
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
}
