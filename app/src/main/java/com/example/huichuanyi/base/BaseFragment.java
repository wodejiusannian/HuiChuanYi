package com.example.huichuanyi.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huichuanyi.utils.User;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment {
    private Unbinder unbind;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = initView();
        unbind = ButterKnife.bind(this, root);
        return root;
    }


    protected abstract View initView();

    protected void initData() {

    }

    ;

    protected void initEvent() {

    }

    ;

    protected void setData() {

    }

    ;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();

        setData();

        initEvent();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbind.unbind();
    }

    protected boolean getUser() {
        if (new User(getContext()).getUseId() > 0) {
            return true;
        }
        return false;
    }
}
