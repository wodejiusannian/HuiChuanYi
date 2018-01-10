package com.example.huichuanyi.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// ┏┓　　　┏┓
// ┏┛┻━━━┛┻┓
// ┃　　　　　　　┃ 　
// ┃　　　━　　　┃
// ┃　┳┛　┗┳　┃
// ┃　　　　　　　┃
// ┃　　　┻　　　┃
// ┃　　　　　　　┃
// ┗━┓　　　┏━┛
// ┃　　　┃ 神兽保佑　　　　　　　　
// ┃　　　┃ 代码无BUG！
// ┃　　　┗━━━┓
// ┃　　　　　　　┣┓
// ┃　　　　　　　┏┛
// ┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
// ┗┻┛　┗┻┛
public class CouponDialog extends Dialog {

    @BindView(R.id.et_coupon_dialog_content)
    EditText content;

    private Context context;
    private EventResult eventResult;

    public CouponDialog(@NonNull Context context) {
        super(context, R.style.MySelfDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_coupon);
        ButterKnife.bind(this);
    }

    public void onResult(EventResult v) {
        if (v != null)
            eventResult = v;
    }

    @OnClick(R.id.btn_coupon_dialog_sure)
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.btn_coupon_dialog_sure:
                if (eventResult != null) {
                    String c = content.getText().toString();
                    if (!CommonUtils.isEmpty(c)) {
                        eventResult.onResult(c);
                    } else {
                        Toast.makeText(context, "请输入优惠码", Toast.LENGTH_SHORT).show();
                    }
                }
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface EventResult {
        void onResult(String result);
    }
}
