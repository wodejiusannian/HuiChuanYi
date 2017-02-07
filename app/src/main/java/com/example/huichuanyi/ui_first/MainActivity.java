package com.example.huichuanyi.ui_first;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.FreshPhoto;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.config.SystemParams;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.download.DownLoadUtils;
import com.example.huichuanyi.download.DownloadApk;
import com.example.huichuanyi.fragment_first.Fragment_Home;
import com.example.huichuanyi.fragment_first.Fragment_Mine;
import com.example.huichuanyi.fragment_first.Fragment_Order;
import com.example.huichuanyi.modle.MessageEvent;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup mRadioGroup;
    private Fragment mCurrentFragment;
    private FragmentManager mManger;
    private static boolean isExit = false;
    private FreshPhoto mFreshPhoto;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
        systemBarTintManager.setStatusBarTintEnabled(true);
        systemBarTintManager.setNavigationBarTintEnabled(true);
        systemBarTintManager.setTintResource(R.color.text_color);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initView();
        initData();
        setData();
        setListener();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
    public void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_main_navigation);
        mManger = getSupportFragmentManager();
    }

    public void initData() {
        isFresh();
    }

    public void setData() {
        showAndHide(R.id.rl_main_show,Fragment_Home.class);
    }

    public void setListener() {
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Class<? extends Fragment> clazz = null;
        switch (checkedId) {
            case R.id.rb_main_home:
                clazz = Fragment_Home.class;
                break;
            case R.id.rb_main_order:
                clazz = Fragment_Order.class;
                break;
            case R.id.rb_main_me:
                clazz = Fragment_Mine.class;
                break;
            default:
                break;
        }
        showAndHide(R.id.rl_main_show, clazz);
        /*switch (checkedId) {
            case  R.id.rb_main_home:
                showAndHide(R.id.rl_main_show, Fragment_Home.class);
                break;
            case  R.id.rb_main_order:
                showAndHide(R.id.rl_main_show, Fragment_Order.class);
                break;
            case  R.id.rb_main_me:
                showAndHide(R.id.rl_main_show, Fragment_Mine.class);
                break;
        }*/
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
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            System.exit(0);
        }
    }

   public void onEventMainThread(MessageEvent event) {
        if (TextUtils.equals("11",event.getMessage())){
            mFreshPhoto.getPhoto("1");
        }
    }
    public void setCallBack(FreshPhoto callBack) {
        this.mFreshPhoto = callBack;
    }

    private void showAndHide(int contentId, Class<? extends Fragment> clazz) {
        //判断当前的fragment和需要替换的fragment是否是统一一个
        if (mCurrentFragment != null && mCurrentFragment.getClass().getSimpleName().equals(clazz.getSimpleName())) {
            return;
        }

        //判断fragment有没有添加过
        FragmentTransaction transaction = mManger.beginTransaction();
        //
        Fragment fragment = null;
        try {

            Fragment fragmentByTag = mManger.findFragmentByTag(clazz.getSimpleName());

            if (fragmentByTag != null) {
                //显示需要的fragment
                transaction.show(fragmentByTag);
                //隐藏当前的fragment
                transaction.hide(mCurrentFragment);
                //让记录当前的fragment赋值为显示的fragment
                mCurrentFragment = fragmentByTag;
            } else {
                //通过无参的 公开的构造函数来创建Fragment实例
                fragment = clazz.newInstance();
                //当前的Fragment没有添加过 把Fragment添加到manager里面
                transaction.add(contentId, fragment, clazz.getSimpleName());
                if (mCurrentFragment != null) {
                    //隐藏当前的Fragment
                    transaction.hide(mCurrentFragment);
                }
                //记录当前的Fragment是那个
                mCurrentFragment = fragment;
            }
            transaction.commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /*private void showAndHide(int contentId, Class<? extends Fragment> clazz){
        //判断当前的fragment是否和需要替换掉的fragment是否一致
        if (mCurrentFragment != null && mCurrentFragment.getClass().getSimpleName().equals(clazz.getCanonicalName())) {
            return;
        }

        FragmentTransaction transaction = mManager.beginTransaction();
        Fragment fragment = null;
        try {
            Fragment fragmentByTag = mManager.findFragmentByTag(clazz.getSimpleName());
            if(fragmentByTag!=null){
                transaction.show(fragmentByTag);
                transaction.hide(mCurrentFragment);
                mCurrentFragment = fragmentByTag;
            }else{
                fragment = clazz.newInstance();
                transaction.add(contentId,fragment,clazz.getSimpleName());
                if(mCurrentFragment!=null){
                    transaction.hide(mCurrentFragment);
                }
                mCurrentFragment = fragment;
            }
            transaction.commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DownloadApk.unregisterBroadcast(this);
    }
    private String getVersionName(){
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(),0);
            String version = packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return  null;
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
                    if (!TextUtils.equals(serverVersion,getVersionName())){
                        if (TextUtils.equals("Y",lastForce)||TextUtils.equals("y",lastForce)){
                            if (DownLoadUtils.getInstance(getApplicationContext()).canDownload()) {
                                DownloadApk.downloadApk(getApplicationContext(), url, "慧美衣橱正在更新...", "hobbees");
                            } else {
                                DownLoadUtils.getInstance(getApplicationContext()).skipToDownloadManager();
                            }
                        }else {
                            if (!isFresh){
                                MySelfDialog mDialog = new MySelfDialog(MainActivity.this);
                                mDialog.setTitle("是否更新");
                                mDialog.setMessage(msg);
                                mDialog.setOnYesListener("确定",new MySelfDialog.OnYesClickListener() {
                                    @Override
                                    public void onClick() {
                                        if (DownLoadUtils.getInstance(getApplicationContext()).canDownload()) {
                                            DownloadApk.downloadApk(getApplicationContext(), url, "慧美衣橱正在更新...", "huimeiApk");
                                        } else {
                                            DownLoadUtils.getInstance(getApplicationContext()).skipToDownloadManager();
                                        }
                                    }
                                });
                                mDialog.setOnNoListener("取消",new MySelfDialog.OnNoClickListener() {
                                    @Override
                                    public void onClick() {
                                        instance.setBoolean("isFresh",true);
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
}
