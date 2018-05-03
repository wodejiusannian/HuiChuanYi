package com.example.huichuanyi.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.secondui.AtMyAcitivty;
import com.example.huichuanyi.secondui.FanKuiActivity;
import com.example.huichuanyi.utils.ActivityUtils;

public class MineSettingActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private RelativeLayout mRelativeLayoutFankui,
            mRelativeLayoutLianxi, mRelativeLayoutmy, mRelativeLayoutUpDate;
    private ToggleButton mToggleButton;
    private SharedPreferences mShare;
    private int check;
    private Dialog mLiXiDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

    }

    @Override
    public void initView() {
        mToggleButton = (ToggleButton) findViewById(R.id.tb_setting_jpush);
        mRelativeLayoutFankui = (RelativeLayout) findViewById(R.id.rl_setting_fankui);
        mRelativeLayoutLianxi = (RelativeLayout) findViewById(R.id.rl_setting_lianxi);
        mRelativeLayoutmy = (RelativeLayout) findViewById(R.id.rl_setting_my);
        mRelativeLayoutUpDate = (RelativeLayout) findViewById(R.id.rl_setting_update);
    }

    @Override
    public void initData() {
        mShare = getSharedPreferences("isCheck", 0);
        check = mShare.getInt("check", 0);

    }

    @Override
    public void setData() {
        if (check == 0) {
            mToggleButton.setChecked(true);
        } else {
            mToggleButton.setChecked(false);
        }
    }

    @Override
    public void setListener() {
        mRelativeLayoutFankui.setOnClickListener(this);
        mRelativeLayoutLianxi.setOnClickListener(this);
        mRelativeLayoutmy.setOnClickListener(this);
        mToggleButton.setOnCheckedChangeListener(this);
        mRelativeLayoutUpDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_setting_fankui:
                ActivityUtils.switchTo(this, FanKuiActivity.class);
                break;
            case R.id.rl_setting_lianxi:
                showDialog();
                break;
            case R.id.rl_setting_my:
                ActivityUtils.switchTo(this, AtMyAcitivty.class);
                break;
            case R.id.bt_lianxi_cancle:
                mLiXiDialog.dismiss();
                break;
            case R.id.tv_lianxi_phone:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:18210293536"));
                startActivity(intent);
                break;
        }
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences.Editor edit = mShare.edit();
        if (isChecked) {
            edit.putInt("check", 0);
        } else {
            edit.putInt("check", 1);
        }
        edit.commit();
    }

    public void showDialog() {
        mLiXiDialog = new Dialog(this, R.style.my_dialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.layout_lianxi, null);
        root.findViewById(R.id.tv_lianxi_phone).setOnClickListener(this);
        root.findViewById(R.id.bt_lianxi_cancle).setOnClickListener(this);
        mLiXiDialog.setContentView(root);
        Window dialogWindow = mLiXiDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0;
        lp.y = -20;

        lp.width = (int) (getResources().getDisplayMetrics().widthPixels) - 50; // 宽度*/
        root.measure(10, 10);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mLiXiDialog.show();
    }

}
