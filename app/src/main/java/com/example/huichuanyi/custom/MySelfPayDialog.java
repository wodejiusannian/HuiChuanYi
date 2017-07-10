package com.example.huichuanyi.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.PayState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小五 on 2016/12/26.
 */
public class MySelfPayDialog extends Dialog implements PayListView.PayState {
    private Button mButtonNo, mButtonYes;

    private String mNo, mYes, mTitle;
    private OnNoClickListener mNoListener;
    private OnYesClickListener mYesListener;
    private TextView mTextViewTitle;
    private String isALiOrWeChat ;
    /*private RadioGroup mRg;*/
    private PayListView payListView;

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
        payListView = (PayListView) findViewById(R.id.include_pay_list);
        /*mRg = (RadioGroup) findViewById(R.id.rg_pay);*/
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
        initPayListView();
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

      /*  mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_ali_pay:
                        isALiOrWeChat = 1;
                        break;
                    case R.id.rb_wechat_pay:
                        isALiOrWeChat = 2;
                        break;
                    case R.id.rb_cmb_pay:
                        isALiOrWeChat = 3;
                        break;
                    default:
                        break;
                }
            }
        });*/
    }
    private void initPayListView() {
        RequestParams params = new RequestParams("http://hmyc365.net:8081/HM/app/system/pay/getPaySort.do");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                List<PayState> data = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray arr = object.getJSONArray("body");
                    for (int i = 0; i < arr.length(); i++) {

                        PayState pa = new PayState();
                        JSONObject o1 = arr.getJSONObject(i);
                        if (i == 0) {
                            isALiOrWeChat = o1.getString("pay_type");
                        }
                        pa.type = o1.getString("pay_type");
                        pa.pic = o1.getString("pic_url");
                        data.add(pa);
                    }
                    payListView.setData(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        payListView.getPos(this);
    }

    @Override
    public void state(String p) {
        isALiOrWeChat = p;
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }

    public interface OnNoClickListener {
        void onClick();
    }

    public interface OnYesClickListener {
        void onClick(String tag);
    }

}
