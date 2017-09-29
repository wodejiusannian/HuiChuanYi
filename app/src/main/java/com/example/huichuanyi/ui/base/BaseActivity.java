package com.example.huichuanyi.ui.base;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.huichuanyi.R;
import com.example.huichuanyi.custom.MyProgressDialog;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    Unbinder unbinder;

    private static MyProgressDialog selfDialog;


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);
        initActionBar();
        initData();
        setData();
        setListener();

    }

    private void initActionBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
        systemBarTintManager.setStatusBarTintEnabled(true);
        systemBarTintManager.setNavigationBarTintEnabled(true);
        systemBarTintManager.setTintResource(R.color.text_color);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    protected abstract void setListener();

    protected abstract void initData();

    protected abstract void setData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        dismissDialog();
    }


    protected void loadingDialog() {
        if (selfDialog == null) {
            selfDialog = new MyProgressDialog(this);
        }
        selfDialog.show();
    }

    protected void dismissDialog() {
        if (selfDialog != null) {
            selfDialog.dismiss();
            selfDialog = null;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

}
