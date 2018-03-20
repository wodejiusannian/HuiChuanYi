package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.emum.ServiceType;
import com.example.huichuanyi.utils.ServiceSingleUtils;

public class LiJiYuYueWhatActivity extends BaseActivity {
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_li_ji_yu_yue_what);

    }

    @Override
    public void initView() {
        iv = (ImageView) this.findViewById(R.id.iv);
        if (ServiceSingleUtils.getInstance().getServiceType() == ServiceType.SERVICE_ACARUS_KILLING) {
            Glide.with(this).load("http://hmyc365.net/hmyc/file/app-studio-picture/price-info-0.jpg").into(iv);
        } else {
            String gra = getIntent().getStringExtra("gra");
            switch (gra) {
                case "0":
                    Glide.with(this).load("http://hmyc365.net/hmyc/file/app-studio-picture/price-info-0.png").into(iv);
                    break;
                case "1":
                    Glide.with(this).load("http://hmyc365.net/hmyc/file/app-studio-picture/price-info-1.png").into(iv);
                    break;
                case "2":
                    Glide.with(this).load("http://hmyc365.net/hmyc/file/app-studio-picture/price-info-2.png").into(iv);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {

    }

    public void back(View view) {
        finish();
    }
}
