package com.example.huichuanyi.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;

/**
 * Created by 小五 on 2016/12/26.
 */
public class MySureDialog extends Dialog {
    private String mMessage;
    private OnYesClickListener mYesListener;
    private TextView mTextViewMessage;

    private TextView mmButtonYes;

    public void setOnYesListener(OnYesClickListener yesListener) {
        mYesListener = yesListener;
    }


    public void setMessage(String message) {
        if (message != null) {
            mMessage = message;
        }
    }


    public MySureDialog(Context context) {
        super(context, R.style.MySelfDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sure);
        setCanceledOnTouchOutside(false);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        mmButtonYes = (TextView) findViewById(R.id.my_self_yes);
        mTextViewMessage = (TextView) findViewById(R.id.my_self_message);
    }

    private void initData() {
        if (mMessage != null) {
            mTextViewMessage.setText(mMessage);
        }
    }

    private void setListener() {


        mmButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mYesListener != null) {
                    mYesListener.onClick();
                }
                dismiss();
            }
        });


    }


    public interface OnYesClickListener {
        void onClick();
    }


}
