package com.example.huichuanyi.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.huichuanyi.R;

public class DialogUtils {
    private View.OnClickListener mListener;

    public void setOnClickListener(View.OnClickListener listener){
        mListener = listener;
    }
    public  void showDialog( Context context,String tiShi,String show) {
         AlertDialog.Builder MyBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        View layout = LayoutInflater.from(context).inflate(R.layout.exit_mine, null);
        TextView mTextViewTiShi = (TextView) layout.findViewById(R.id.tv_exit_tishi);
        TextView mTextViewShow = (TextView) layout.findViewById(R.id.tv_exit_show);
        Button mButtonSure = (Button) layout.findViewById(R.id.btn_layout_sure);
        Button mButtonCancle = (Button) layout.findViewById(R.id.btn_layout_cancle);
        mButtonSure.setOnClickListener(mListener);
        mButtonCancle.setOnClickListener(mListener);
        mTextViewTiShi.setText(tiShi);
        mTextViewShow.setText(show);
        MyBuilder.setView(layout);
         MyBuilder.create().show();
        MyBuilder.create().getWindow().setLayout(700,500);
    }
}
