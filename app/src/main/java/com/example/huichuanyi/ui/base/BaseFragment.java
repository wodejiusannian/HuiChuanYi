package com.example.huichuanyi.ui.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.xutils.x;


public abstract class BaseFragment extends Fragment {

    public BaseFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return x.view().inject(this, inflater, container);
    }

    protected void initView() {

    }

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
        initView();
        initData();
        setData();
        initEvent();
    }


}
