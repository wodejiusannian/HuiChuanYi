package com.example.huichuanyi.fragment_second;

import android.view.LayoutInflater;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseFragment;

/**
 * Created by 小五 on 2017/2/8.
 */

public class RefreshRecord extends BaseFragment {

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_list, null);
        return view;
    }

}
