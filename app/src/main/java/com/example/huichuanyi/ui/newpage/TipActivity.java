package com.example.huichuanyi.ui.newpage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class TipActivity extends BaseActivity {

    @BindView(R.id.iv)
    ImageView iv;

    private int[] home = {R.mipmap.hm_main_home1, R.mipmap.hm_main_home2, R.mipmap.hm_main_home3, R.mipmap.hm_main_home4};

    private int[] service = {R.mipmap.hm_main_service1, R.mipmap.hm_main_service2, R.mipmap.hm_main_service3, R.mipmap.hm_main_service4};

    private int[] shopcar = {R.mipmap.hm_main_shopcar1, R.mipmap.hm_main_shopcar2};

    private int[] mine = {R.mipmap.hm_main_mine1, R.mipmap.hm_main_mine2, R.mipmap.hm_main_mine3, R.mipmap.hm_main_mine4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void setData() {
        first = getIntent().getStringExtra("first");
        if ("1".equals(first)) {
            iv.setImageResource(home[0]);
        } else if ("2".equals(first)) {
            iv.setImageResource(service[0]);
        } else if ("3".equals(first)) {
            iv.setImageResource(shopcar[0]);
        } else if ("4".equals(first)) {
            iv.setImageResource(mine[0]);
        }
    }

    private String first;

    int i = 1;

    @OnClick(R.id.iv)
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.iv:
                if ("1".equals(first)) {
                    if (i == home.length) {
                        finish();
                    } else {
                        iv.setImageResource(home[i]);
                    }
                } else if ("2".equals(first)) {
                    if (i == service.length) {
                        finish();
                    } else {
                        iv.setImageResource(service[i]);
                    }
                } else if ("3".equals(first)) {
                    if (i == shopcar.length) {
                        finish();
                    } else {
                        iv.setImageResource(shopcar[i]);
                    }
                } else if ("4".equals(first)) {
                    if (i == mine.length) {
                        finish();
                    } else {
                        iv.setImageResource(mine[i]);
                    }
                }
                i++;
                break;
            default:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        String str = SharedPreferenceUtils.getIsFirstOpen(this);
        SharedPreferenceUtils.writeIsFirstOpen(this, str + first);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
