package com.example.huichuanyi.base;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.huichuanyi.R;
import com.example.huichuanyi.custom.MyProgressDialog;
import com.example.huichuanyi.utils.User;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import org.xutils.x;

public abstract class BaseActivity extends AppCompatActivity {
    private MyProgressDialog progressDialog;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(layoutResID);
        x.view().inject(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
        systemBarTintManager.setStatusBarTintEnabled(true);
        systemBarTintManager.setNavigationBarTintEnabled(true);
        systemBarTintManager.setTintResource(R.color.text_color);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
        initData();
        setData();
        setListener();
    }
    public abstract void setListener();

    public abstract void initView();

    public abstract void initData();

    public abstract void setData();


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    protected void showLoading() {
        progressDialog = new MyProgressDialog(this);
        progressDialog.show();
    }

    protected void dismissLoading() {
        progressDialog.dismiss();
    }

    protected boolean getUser() {
        if (new User(this).getUseId() > 0) {
            return true;
        }
        return false;
    }
}
