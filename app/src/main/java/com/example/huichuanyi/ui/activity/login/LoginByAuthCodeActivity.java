package com.example.huichuanyi.ui.activity.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.VpFragmentAdapter;
import com.example.huichuanyi.ui.fragment.AuthCodeGetFragment;
import com.example.huichuanyi.ui.fragment.AuthCodeWriteFragment;
import com.example.huichuanyi.utils.CommonStatic;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class LoginByAuthCodeActivity extends AppCompatActivity {

    @ViewInject(R.id.vp_login_loading)
    private ViewPager mLoading;

    private VpFragmentAdapter adapter;


    private List<Fragment> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_auth_code_login);
        x.view().inject(this);
        initView();
        initData();
        setData();
        setListener();
    }


    public void setListener() {

    }

    public void initView() {
        adapter = new VpFragmentAdapter(getSupportFragmentManager(), mData);
        mLoading.setAdapter(adapter);
        mData.add(new AuthCodeGetFragment());
        mData.add(new AuthCodeWriteFragment());
        adapter.notifyDataSetChanged();
    }

    public void initData() {

    }

    public void setData() {

    }

    /*
    * 监听返回键
    * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            int currentItem = mLoading.getCurrentItem();
            switch (currentItem) {
                case 0:
                    finish();
                    break;
                case 1:
                    mLoading.setCurrentItem(0);
                    break;
                default:
                    break;
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void currentItem(int pos) {
        mLoading.setCurrentItem(pos);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonStatic.LOGIN_PHONE = null;
    }
}
