package com.example.huichuanyi.ui_second;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.config.SystemParams;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.download.DownLoadUtils;
import com.example.huichuanyi.download.DownloadApk;
import com.example.huichuanyi.secondui.AtMyAcitivty;
import com.example.huichuanyi.secondui.FanKuiActivity;
import com.example.huichuanyi.ui_four.ReviseActivity;
import com.example.huichuanyi.utils.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class SettingActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private RelativeLayout mRelativeLayoutFankui,
            mRelativeLayoutLianxi, mRelativeLayoutmy, mRelativeLayoutUpDate, mRelativeRevise;
    private ToggleButton mToggleButton;
    private SharedPreferences mShare;
    private int check;
    private Dialog mLiXiDialog;
    private ImageView mImageViewBack;

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
        mImageViewBack = (ImageView) findViewById(R.id.iv_setting_back);
        mRelativeLayoutUpDate = (RelativeLayout) findViewById(R.id.rl_setting_update);
        mRelativeRevise = (RelativeLayout) findViewById(R.id.rl_setting_revise);
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
        mImageViewBack.setOnClickListener(this);
        mRelativeLayoutUpDate.setOnClickListener(this);
        mRelativeRevise.setOnClickListener(this);
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
            case R.id.iv_setting_back:
                finish();
                break;
            case R.id.rl_setting_update:
                isFresh();
                break;
            case R.id.rl_setting_revise:
                ActivityUtils.switchTo(this, ReviseActivity.class);
                break;
        }
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

    private String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void isFresh() {
        //1.注册下载广播接收器.
        DownloadApk.registerBroadcast(this);
        //2.删除已存在的Apk
        DownloadApk.removeFile(this);
        final SystemParams instance = SystemParams.getInstance();
        RequestParams params = new RequestParams(NetConfig.IS_FRESH_PATH);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    String serverVersion = obj.getString("serverVersion");
                    final String url = obj.getString("url");
                    String msg = obj.getString("msg");
                    String lastForce = obj.getString("lastForce");
                    if (!TextUtils.equals(serverVersion, getVersionName())) {
                        if (TextUtils.equals("Y", lastForce) || TextUtils.equals("y", lastForce)) {
                            if (DownLoadUtils.getInstance(getApplicationContext()).canDownload()) {
                                DownloadApk.downloadApk(getApplicationContext(), url, "慧美衣橱正在更新...", "hobbees");
                            } else {
                                DownLoadUtils.getInstance(getApplicationContext()).skipToDownloadManager();
                            }
                        } else {
                            MySelfDialog mDialog = new MySelfDialog(SettingActivity.this);
                            mDialog.setTitle("是否更新");
                            mDialog.setMessage(msg);
                            mDialog.setOnYesListener("确定", new MySelfDialog.OnYesClickListener() {
                                @Override
                                public void onClick() {
                                    if (DownLoadUtils.getInstance(getApplicationContext()).canDownload()) {
                                        DownloadApk.downloadApk(getApplicationContext(), url, "慧美衣橱正在更新...", "huimeiApk");
                                    } else {
                                        DownLoadUtils.getInstance(getApplicationContext()).skipToDownloadManager();
                                    }
                                }
                            });
                            mDialog.setOnNoListener("取消", new MySelfDialog.OnNoClickListener() {
                                @Override
                                public void onClick() {
                                    instance.setBoolean("isFresh", true);
                                }
                            });
                            mDialog.show();
                        }
                    } else {
                        Toast.makeText(SettingActivity.this, "您的版本已经是最新的了，继续保持哦", Toast.LENGTH_SHORT).show();
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
}
