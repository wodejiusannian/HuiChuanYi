package com.example.huichuanyi.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.FreshPhoto;
import com.example.huichuanyi.baidumap.Fresh_365;
import com.example.huichuanyi.baidumap.Location;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.config.SystemParams;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.download.DownLoadUtils;
import com.example.huichuanyi.download.DownloadApk;
import com.example.huichuanyi.fragment_first.Fragment_365;
import com.example.huichuanyi.fragment_first.Fragment_Home;
import com.example.huichuanyi.fragment_first.Fragment_Mine;
import com.example.huichuanyi.ui.fragment.OrderFragment;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

public class
MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, UtilsInternet.XCallBack {

    @ViewInject(R.id.rg_main_navigation)
    private RadioGroup mRadioGroup;

    @ViewInject(R.id.rl_masking)
    private LinearLayout mask;

    @ViewInject(R.id.sv_main_show_active)
    private SimpleDraweeView maskActive;

    private static boolean isExit = false;
    private FreshPhoto mFreshPhoto;
    private Fresh_365 mFresh_365;
    private Fragment[] mFragments;
    private int index;
    private int currentTabIndex;
    private UtilsInternet net = UtilsInternet.getInstance();
    private String hm_adpage_share_url, hm_adpage_webview_url, hm_activity_name;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    Handler mHand = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String hm_adpage_pic_url = data.getString("hm_adpage_pic_url");
            maskActive.setImageURI(hm_adpage_pic_url);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void initView() {
        net.get(NetConfig.IS_HAVE_ACTIVITY, null, this);
    }

    public void initData() {
        Fragment_Home mHome = new Fragment_Home();
        OrderFragment mOrder = new OrderFragment();
        Fragment_Mine mMine = new Fragment_Mine();
        Fragment_365 m365 = new Fragment_365();
        mFragments = new Fragment[]{mHome, mOrder, m365, mMine};
        getSupportFragmentManager().beginTransaction().
                add(R.id.rl_main_show, mHome).hide(mOrder).
                show(mHome).commit();
        isFresh();
    }

    public void setData() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshFriend");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
    }

    public void setListener() {
        mRadioGroup.setOnCheckedChangeListener(this);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            CommonUtils.Toast(getApplicationContext(), "再按一次退出程序");
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            System.exit(0);
        }
    }


    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshFriend")) {
                if (mFreshPhoto != null) {
                    mFreshPhoto.getPhoto();
                }
                if (mFresh_365 != null) {
                    mFresh_365.reFresh365();
                }
            }
        }
    };

    public void setCallBack(FreshPhoto callBack) {
        this.mFreshPhoto = callBack;
    }

    public void setFresh365(Fresh_365 fresh365) {
        mFresh_365 = fresh365;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DownloadApk.unregisterBroadcast(this);
        unregisterReceiver(mRefreshBroadcastReceiver);
    }

    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
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
        final boolean isFresh = instance.getBoolean("isFresh");
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
                            if (!isFresh) {
                                MySelfDialog mDialog = new MySelfDialog(MainActivity.this);
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
                        }
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

    @Event({R.id.iv_main_mask_delete, R.id.sv_main_show_active})
    private void onEvent(View v) {
        switch (v.getId()) {
            case R.id.iv_main_mask_delete:
                mask.setVisibility(View.GONE);
                break;
            case R.id.sv_main_show_active:
                if (CommonUtils.isEmpty(hm_adpage_share_url) || CommonUtils.isEmpty(hm_adpage_webview_url))
                    return;

                Map<String, Object> map = new HashMap<>();
                map.put("hm_adpage_share_url", hm_adpage_share_url);
                map.put("hm_adpage_webview_url", hm_adpage_webview_url);
                map.put("hm_activity_name", hm_activity_name);
                ActivityUtils.switchTo(this, HMWebActivity.class, map);
                mask.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    //切换替换Fragment
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_main_home:
                index = 0;
                break;
            case R.id.rb_main_order:
                index = 1;
                break;
            case R.id.rb_main_365:
                index = 2;
                break;
            case R.id.rb_main_me:
                index = 3;
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(mFragments[currentTabIndex]);
            if (!mFragments[index].isAdded()) {
                trx.add(R.id.rl_main_show, mFragments[index]);
            }
            trx.show(mFragments[index]).commit();
            currentTabIndex = index;
        }
    }


    @Override
    public void onResponse(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString("hm_adpage_pic_url", object.getString("hm_adpage_pic_url"));
            hm_adpage_share_url = object.getString("hm_adpage_share_url");
            hm_adpage_webview_url = object.getString("hm_adpage_webview_url");
            hm_activity_name = object.getString("hm_activity_name");
            String hm_activity_exists = object.getString("hm_activity_exists");
            if (TextUtils.equals("Y", hm_activity_exists)) {
                Location.isHaveActive = true;
            }
            msg.setData(bundle);
            mHand.sendMessage(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
