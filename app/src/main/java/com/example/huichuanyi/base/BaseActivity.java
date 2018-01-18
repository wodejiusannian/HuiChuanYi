package com.example.huichuanyi.base;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.huichuanyi.custom.MyProgressDialog;
import com.gyf.barlibrary.ImmersionBar;
import com.umeng.analytics.MobclickAgent;

import org.xutils.x;

public abstract class BaseActivity extends AppCompatActivity {
    private MyProgressDialog progressDialog;
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(layoutResID);
        x.view().inject(this);
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .init();
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

}
