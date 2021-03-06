package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.utils.ActivityUtils;

import java.util.HashMap;
import java.util.Map;

public class HomeWoDeYiChuActivity extends BaseActivity implements View.OnClickListener {
    private ImageView  JiaTingYiChu, ChuXingYiChu, JiuYiHuiShou, QiTaYiChu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_clothess);
    }

    @Override
    public void initView() {
        JiaTingYiChu = (ImageView) findViewById(R.id.iv_my_clothes_jiatingyichu);
        ChuXingYiChu = (ImageView) findViewById(R.id.iv_my_clothes_chuxingyichu);
        JiuYiHuiShou = (ImageView) findViewById(R.id.iv_my_clothes_jiuyihuishou);
        QiTaYiChu = (ImageView) findViewById(R.id.iv_my_clothes_qitayichu);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        JiaTingYiChu.setOnClickListener(this);
        ChuXingYiChu.setOnClickListener(this);
        JiuYiHuiShou.setOnClickListener(this);
        QiTaYiChu.setOnClickListener(this);
    }

    public void back(View view){
        finish();
    }
    @Override
    public void onClick(View v) {
        Map<String, Object> map = new HashMap<>();
        switch (v.getId()) {
            case R.id.iv_my_clothes_jiatingyichu:
                map.put("yichu", "家庭衣橱");
                ActivityUtils.switchTo(this, MC_HomeActivity.class, map);
                break;
            case R.id.iv_my_clothes_chuxingyichu:
                map.put("word_id", "2");
                map.put("yichu", "出行衣橱");
                ActivityUtils.switchTo(this, MC_TripAndElseActivity.class, map);
                break;
            case R.id.iv_my_clothes_jiuyihuishou:
                map.put("yichu", "旧衣回收");
                ActivityUtils.switchTo(this, MC_OldClothesActivity.class);
                break;
            case R.id.iv_my_clothes_qitayichu:
                map.put("word_id", "4");
                map.put("yichu", "其他衣橱");
                ActivityUtils.switchTo(this, MC_TripAndElseActivity.class, map);
                break;
        }
    }
}
