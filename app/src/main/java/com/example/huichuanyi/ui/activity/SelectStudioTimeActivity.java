package com.example.huichuanyi.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.huichuanyi.R;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.adapter.SelectTimeAdapter;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.ui.modle.Week;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.ItemDecoration;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectStudioTimeActivity extends BaseActivity implements UtilsInternet.XCallBack, SelectTimeAdapter.getPos {

    @BindView(R.id.btn_time_sure)
    Button go;

    @BindView(R.id.rv_time_week)
    RecyclerView rvWeek;

    private SelectTimeAdapter adapter;

    private UtilsInternet net = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();
    List<Week> mData = new ArrayList<>();
    @BindView(R.id.rb_am)
    Button mAm;
    @BindView(R.id.rb_pm)
    Button mPm;

    private String day, am_pm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_studio_time_acivity);
    }

    @Override
    public void setListener() {
    }


    @Override
    public void initData() {
        adapter = new SelectTimeAdapter(this, mData);
        GridLayoutManager layout = new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false);
        rvWeek.setLayoutManager(layout);
        rvWeek.setAdapter(adapter);
        rvWeek.addItemDecoration(new ItemDecoration(13));
        adapter.notifyDataSetChanged();
        String studio_id = getIntent().getStringExtra("studio_id");
        map.put("studio_id", studio_id);
        net.post(NetConfig.GET_STUDIO_TIME, map, this);
    }

    @Override
    public void setData() {
        adapter.setOnItemOnClickListener(this);
    }

    @OnClick({R.id.btn_time_sure, R.id.rb_am, R.id.rb_pm})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.btn_time_sure:
                tranData();
                break;
            case R.id.rb_am:
                am_pm = "上午";
                if (mPm.isEnabled()) {
                    mPm.setTextColor(Color.parseColor("#666666"));
                    mPm.setBackgroundColor(Color.WHITE);
                }
                go.setEnabled(true);
                mAm.setTextColor(Color.WHITE);
                mAm.setBackgroundColor(Color.parseColor("#ac0000"));
                break;
            case R.id.rb_pm:
                am_pm = "下午";
                if (mAm.isEnabled()) {
                    mAm.setTextColor(Color.parseColor("#666666"));
                    mAm.setBackgroundColor(Color.WHITE);
                }
                mPm.setTextColor(Color.WHITE);
                mPm.setBackgroundColor(Color.parseColor("#ac0000"));
                go.setEnabled(true);
                break;
            default:
                break;
        }
    }


    /*
    * 向上个Activity传输数据
    * */
    private void tranData() {
        if (!CommonUtils.isEmpty(day) && !CommonUtils.isEmpty(am_pm)) {
            Intent in = new Intent();
            in.putExtra("time", day + am_pm);
            this.setResult(2000, in);
            finish();
        }
    }

    public void back(View view) {
        if (view != null) {
            finish();
        }
    }


    @Override
    public void onResponse(String result) {
        try {
            JSONObject ob = new JSONObject(result);
            JSONObject body = ob.getJSONObject("body");
            JSONArray busy_time = body.getJSONArray("busy_time");
            String days = body.getString("feture_days");
            String[] split = days.split(",");
            for (int i = 0; i < split.length; i++) {
                Week w = new Week();
                String s = split[i];
                w.setBusy_date(s);
                for (int j = 0; j < busy_time.length(); j++) {
                    JSONObject js = busy_time.getJSONObject(j);
                    String busy_date = js.getString("busy_date");
                    if (TextUtils.equals(s, busy_date)) {
                        String busy_time_slot_tag = js.getString("busy_time_slot_tag");
                        w.setBusy_time_slot_tag(busy_time_slot_tag);
                    } else {
                    }
                }
                mData.add(w);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getPos(int pos) {
        mAm.setEnabled(true);
        mPm.setEnabled(true);
        go.setEnabled(false);
        Week week = mData.get(pos);
        day = week.getBusy_date();
        String busy_time_slot_tag = week.getBusy_time_slot_tag();
        if (TextUtils.equals("PM_BUSY", busy_time_slot_tag)) {
            mPm.setText("下午\n\n已约满");
            mPm.setEnabled(false);
            mPm.setBackgroundColor(Color.parseColor("#eeeeee"));
            mAm.setText("上午\n\n9:00-12:00");
            mAm.setTextColor(Color.parseColor("#666666"));
            mAm.setEnabled(true);
            mAm.setBackgroundColor(Color.WHITE);
        } else if (TextUtils.equals("AM_BUSY", busy_time_slot_tag)) {
            mAm.setText("上午\n\n已约满");
            mAm.setEnabled(false);
            mAm.setBackgroundColor(Color.parseColor("#eeeeee"));
            mPm.setText("下午\n\n12:00-18:00");
            mPm.setTextColor(Color.parseColor("#666666"));
            mPm.setEnabled(true);
            mPm.setBackgroundColor(Color.WHITE);
        } else {
            mAm.setBackgroundColor(Color.WHITE);
            mPm.setBackgroundColor(Color.WHITE);
            mAm.setTextColor(Color.parseColor("#666666"));
            mPm.setTextColor(Color.parseColor("#666666"));
            mAm.setText("上午\n\n9:00-12:00");
            mPm.setText("下午\n\n12:00-18:00");
        }
    }
}
