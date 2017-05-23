package com.example.huichuanyi.ui.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.ui.activity.login.LoginByAuthCodeActivity;
import com.example.huichuanyi.ui.activity.login.LoginHMService;
import com.example.huichuanyi.ui.base.BaseFragment;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonStatic;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.LoginControl;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

@ContentView(R.layout.fragment_get_auth_code)
public class AuthCodeGetFragment extends BaseFragment {
    private LoginControl control;

    private boolean isAgree = true;

    @ViewInject(R.id.et_login_phone)
    private EditText mPhone;

    @ViewInject(R.id.cb_login_service)
    private CheckBox mService;

    @ViewInject(R.id.iv_login_next)
    private ImageView mNext;

    @Event({R.id.iv_login_next, R.id.iv_login_qq, R.id.iv_login_wechat, R.id.go_service})
    private void onEvent(View v) {
        switch (v.getId()) {
            case R.id.iv_login_next:
                next();
                break;
            case R.id.iv_login_qq:
                if (isAgree) {
                    showLoading();
                    control.thirdLogin(QQ.NAME);
                } else {
                    Toast.makeText(getContext(), "登录需要同意服务条款", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_login_wechat:
                if (isAgree) {
                    showLoading();
                    control.thirdLogin(Wechat.NAME);
                } else {
                    Toast.makeText(getContext(), "登录需要同意服务条款", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.go_service:
                ActivityUtils.switchTo(getActivity(), LoginHMService.class);
                break;
            default:
                break;
        }
    }

    /*
    * 点击下一步
    * */
    private void next() {
        String phone = mPhone.getText().toString().trim();
        if (!CommonUtils.isEmpty(phone) && phone.length() == 11) {
            if (isAgree) {
                CommonStatic.LOGIN_PHONE = phone;
                LoginByAuthCodeActivity activity = (LoginByAuthCodeActivity) getActivity();
                activity.currentItem(1);
            } else {
                Toast.makeText(getContext(), "登录需要同意服务条款", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "请正确输入手机号", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initView() {
        super.initView();
        control = LoginControl.getInstance(getActivity());
        mService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAgree = isChecked;
            }
        });
        mPhone.addTextChangedListener(mTextWatch);
    }

    TextWatcher mTextWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                mNext.setVisibility(View.GONE);
            } else {
                mNext.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
