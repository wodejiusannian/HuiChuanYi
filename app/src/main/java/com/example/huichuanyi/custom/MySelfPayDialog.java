package com.example.huichuanyi.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;

/**
 * Created by 小五 on 2016/12/26.
 */
public class MySelfPayDialog extends Dialog {
    private Button mButtonNo, mButtonYes;

    private String mNo, mYes, mTitle;
    private OnNoClickListener mNoListener;
    private OnYesClickListener mYesListener;
    private TextView mTextViewTitle;
    private RelativeLayout mAliPay, mWeChat;
    private boolean mFlag = false;
    private int isALiOrWeChat = 1;

    private ImageView aliNormal, aLiSelect, weChatNormal, weChatSelect;

    public void setOnNoListener(String no, OnNoClickListener noListener) {
        if (no != null) {
            mNo = no;
        }
        mNoListener = noListener;
    }

    public void setOnYesListener(String yes, OnYesClickListener yesListener) {
        if (yes != null) {
            mYes = yes;
        }
        mYesListener = yesListener;
    }


    public void setTitle(String title) {
        if (title != null) {
            mTitle = title;
        }
    }


    public MySelfPayDialog(Context context) {
        super(context, R.style.MySelfDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_self_dialog);
        setCanceledOnTouchOutside(false);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        mButtonNo = (Button) findViewById(R.id.my_self_no);
        mButtonYes = (Button) findViewById(R.id.my_self_yes);
        mTextViewTitle = (TextView) findViewById(R.id.my_self_title);
        aliNormal = (ImageView) findViewById(R.id.iv_alipay_normal);
        mWeChat = (RelativeLayout) findViewById(R.id.rl_we_chat);
        aLiSelect = (ImageView) findViewById(R.id.iv_alipay_select);
        weChatNormal = (ImageView) findViewById(R.id.iv_wechat_normal);
        weChatSelect = (ImageView) findViewById(R.id.iv_wechat_select);
        mAliPay = (RelativeLayout) findViewById(R.id.rl_ali_pay);
    }

    private void initData() {

        if (mTitle != null) {
            mTextViewTitle.setText(mTitle);
        }
        if (mNo != null) {
            mButtonNo.setText(mNo);
        }
        if (mYes != null) {
            mButtonYes.setText(mYes);
        }
    }

    private void setListener() {

        mButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNoListener != null) {
                    mNoListener.onClick();
                }
                dismiss();
            }
        });
        mButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mYesListener != null) {
                    mYesListener.onClick(isALiOrWeChat);
                }
                dismiss();
            }
        });

        mAliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aLiSelect.setVisibility(View.VISIBLE);
                aliNormal.setVisibility(View.GONE);
                weChatNormal.setVisibility(View.VISIBLE);
                weChatSelect.setVisibility(View.GONE);
                isALiOrWeChat = 1;
            }
        });
        mWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aLiSelect.setVisibility(View.GONE);
                aliNormal.setVisibility(View.VISIBLE);
                weChatNormal.setVisibility(View.GONE);
                weChatSelect.setVisibility(View.VISIBLE);
                isALiOrWeChat = 2;
            }
        });
    }


    public interface OnNoClickListener {
        void onClick();
    }

    public interface OnYesClickListener {
        void onClick(int tag);
    }

}
