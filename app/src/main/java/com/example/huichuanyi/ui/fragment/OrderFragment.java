package com.example.huichuanyi.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.ui.activity.LiJiYuYueActivity;
import com.example.huichuanyi.ui.base.BaseFragment;
import com.example.huichuanyi.utils.ActivityUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by 小五 on 2017/3/23.
 */
@ContentView(R.layout.fragment_order_copy)
public class OrderFragment extends BaseFragment {
    @ViewInject(R.id.tv_order_sure)
    private TextView orderSure;
    @ViewInject(R.id.iv)
    private ImageView iv;

    @Override
    protected void initEvent() {
        super.initEvent();
        orderSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.switchTo(getActivity(), LiJiYuYueActivity.class);
            }
        });
    }

    @Override
    protected void setData() {
        super.setData();
    }


}