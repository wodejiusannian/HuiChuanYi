package com.example.huichuanyi.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

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
public class VerificationCodeDialog extends Dialog {

    @BindView(R.id.iv_verification_code)
    ImageView viriCode;

    @BindView(R.id.et_verification_code)
    EditText etCode;

    private String phone;

    private EditYes mEditYes;

    private Context context;

    public void setPhone(String phone, EditYes mEditYes) {
        if (!CommonUtils.isEmpty(phone)) {
            this.phone = phone;
        }
        this.mEditYes = mEditYes;
    }

    public VerificationCodeDialog(@NonNull Context context) {
        super(context, R.style.MySelfDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_verification_code);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        onResultInt();
    }

    @OnClick({R.id.iv_verification_code, R.id.btn_verification_code, R.id.tv_verification_code_cancel})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.iv_verification_code:
                onResultInt();
                break;
            case R.id.btn_verification_code:
                goVerificationCode();
                break;
            case R.id.tv_verification_code_cancel:
                this.dismiss();
                break;
            default:
                break;
        }
    }

    private void onResultInt() {
        RequestParams params = new RequestParams("http://hmyc365.net:8081/HM/app/login/verificationCode/getPicVerificationCode.do");
        params.addBodyParameter("phone", phone);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject ob = new JSONObject(result);
                    JSONObject body = ob.getJSONObject("body");
                    String url = body.getString("url");
                    Glide.with(context).load(url).into(viriCode);
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

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }

    private void goVerificationCode() {
        RequestParams pa = new RequestParams("http://hmyc365.net:8081/HM/app/login/verificationCode/provingPicVerificationCode.do");
        pa.addBodyParameter("phone", phone);
        pa.addBodyParameter("code", etCode.getText().toString().trim().replace(" ", ""));
        x.http().post(pa, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject ob = new JSONObject(result);
                    String ret = ob.getString("ret");
                    if (TextUtils.equals("0", ret)) {
                        //成功
                        mEditYes.getEdit();
                        Toast.makeText(context, "验证码获取成功", Toast.LENGTH_SHORT).show();
                        dismiss();
                    } else {
                        //失败
                        onResultInt();
                        Toast.makeText(context, "图片验证码错误，请重新输入", Toast.LENGTH_SHORT).show();
                    }
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

    }


    public interface EditYes {
        void getEdit();
    }
}
