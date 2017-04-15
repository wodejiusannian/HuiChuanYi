package com.example.huichuanyi.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.huichuanyi.R;
import com.example.huichuanyi.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 小五 on 2017/3/9.
 */

public class EditDialog extends Dialog {

    @BindView(R.id.et_location_edit)
    EditText etLocationEdit;
    @BindView(R.id.btn_location_cancle)
    Button btnLocationCancle;
    @BindView(R.id.btn_location_sure)
    Button btnLocationSure;
    private EditYes editYes;
    private String mYes, mNo;

    public EditDialog(@NonNull Context context) {
        super(context, R.style.MySelfDialog);
    }

    public void setOnClickYes(String yes, EditYes mEditYes) {
        if (!CommonUtils.isEmpty(yes)) {
            mYes = yes;
        }
        editYes = mEditYes;
    }

    public void setOnClickNo(String no) {
        if (!CommonUtils.isEmpty(no)) {
            mNo = no;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_order_location);
        ButterKnife.bind(this);
        initView();
        setListener();
    }

    private void setListener() {
        btnLocationSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editYes.getEdit(etLocationEdit.getText().toString().trim());
                dismiss();
            }
        });
        btnLocationCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initView() {
        btnLocationCancle.setText(mNo);
        btnLocationSure.setText(mYes);
    }

    public interface EditYes {
        void getEdit(String edit);
    }

}
