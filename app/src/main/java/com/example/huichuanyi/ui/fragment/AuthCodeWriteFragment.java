package com.example.huichuanyi.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.SMSTimeUtils;
import com.example.huichuanyi.custom.dialog.VerificationCodeDialog;
import com.example.huichuanyi.ui.activity.MainActivity;
import com.example.huichuanyi.ui.activity.login.LoginByAuthCodeActivity;
import com.example.huichuanyi.ui.base.BaseFragment;
import com.example.huichuanyi.utils.CommonStatic;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

@ContentView(R.layout.fragment_write_auth_code)
public class AuthCodeWriteFragment extends BaseFragment implements UtilsInternet.XCallBack {
    private static final String TAG = "AuthCodeWriteFragment";

    @ViewInject(R.id.et_write_auth_code_phone)
    private EditText phone;

    @ViewInject(R.id.et_write_auth_code_auth_code)
    private EditText authCode;

    @ViewInject(R.id.et_write_auth_code_pwd)
    private EditText pwd;

    @ViewInject(R.id.tv_write_auth_code_time)
    private TextView send;

    @ViewInject(R.id.iv_login_next)
    private ImageView next;


    private String mType;

    @ViewInject(R.id.iv_login_back)
    private ImageView back;

    @ViewInject(R.id.iv_login_back_copy)
    private ImageView backCopy;

    private int netType = 1;

    private Map<String, String> map = new HashMap<>();
    private UtilsInternet net = UtilsInternet.getInstance();

    @Override
    protected void initEvent() {
        super.initEvent();
        phone.addTextChangedListener(watcher);
        pwd.addTextChangedListener(watcher);
        authCode.addTextChangedListener(watcher);
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String number = phone.getText().toString().trim().replace(" ", "");
            String ps = pwd.getText().toString().trim().replace(" ", "");
            String code = authCode.getText().toString().trim().replace(" ", "");
            if (!CommonUtils.isEmpty(number) && !CommonUtils.isEmpty(ps) && !CommonUtils.isEmpty(code)) {
                back.setVisibility(View.VISIBLE);
                backCopy.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
            } else {
                back.setVisibility(View.GONE);
                backCopy.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected void initView() {
        super.initView();


    }


    @Event({R.id.iv_login_back, R.id.tv_write_auth_code_time, R.id.iv_login_next, R.id.iv_login_back_copy})
    private void onEvent(View v) {
        switch (v.getId()) {
            case R.id.iv_login_back:
                LoginByAuthCodeActivity activity = (LoginByAuthCodeActivity) getActivity();
                activity.currentItem(0);
                break;
            case R.id.tv_write_auth_code_time:
                final String number = phone.getText().toString().trim().replace(" ", "");
                VerificationCodeDialog codeDialog = new VerificationCodeDialog(getContext());
                codeDialog.setPhone(number, new VerificationCodeDialog.EditYes() {
                    @Override
                    public void getEdit() {
                        SMSTimeUtils smsTimeUtils = new SMSTimeUtils(send, 60000, 1000);
                        smsTimeUtils.start();
                    }
                });
                codeDialog.show();
                break;
            case R.id.iv_login_next:
                netLogin();
                break;
            case R.id.iv_login_back_copy:
                LoginByAuthCodeActivity activity2 = (LoginByAuthCodeActivity) getActivity();
                activity2.currentItem(0);
                break;
            default:
                break;
        }
    }


    private void netLogin() {
        String number = phone.getText().toString().trim().replace(" ", "");
        String pw = pwd.getText().toString().trim().replace(" ", "");
        String code = authCode.getText().toString().trim().replace(" ", "");
        if (!CommonUtils.isEmpty(number) && !CommonUtils.isEmpty(pw) && !CommonUtils.isEmpty(code)) {
            map.put("phone", number);
            map.put("pwd", pw);
            map.put("send_type", mType);
            map.put("code", code);
            net.post(NetConfig.LOGIN_THROUGH_PHONE_AUTH_CODE, map, this);
        } else {
            Toast.makeText(getContext(), "请将信息填写完整", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            phone.setText(CommonStatic.LOGIN_PHONE);
            forceOpenSoftKeyboard(getActivity());
        }
    }


    /*
    * 强制显示软键盘
    * */
    public void forceOpenSoftKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /*
    * 获取到用户的数据
    * */
    @Override
    public void onResponse(String result) {
        switch (netType) {
            case 1:
                try {
                    JSONObject json = new JSONObject(result);
                    String ret = json.getString("ret");
                    if ("1030".equals(ret)) {
                        Toast.makeText(getContext(), "验证码错误", Toast.LENGTH_SHORT).show();
                        dismissLoading();
                        return;
                    }
                    JSONObject body = json.getJSONObject("body");
                    String user_name = body.getString("user_name");
                    String user_headpic_url = body.getString("user_headpic_url");
                    String user_id = body.getString("user_id");
                    String user_vip_end_date = body.getString("user_vip_end_date");
                    map.put("type", "1");
                    map.put("user_id", user_id);
                    map.put("user_name", user_name);
                    map.put("user_pic", user_headpic_url);
                    if (!CommonUtils.isEmpty(user_vip_end_date)) {
                        SharedPreferenceUtils.save365(getContext(), "365");
                    } else {
                        SharedPreferenceUtils.save365(getContext(), null);
                    }
                    String user_city = body.getString("user_city");
                    SharedPreferenceUtils.saveBuyCity(getContext(), user_city);
                    SharedPreferenceUtils.writeUserData(getContext(), user_name, user_id, user_headpic_url);
                    netType = 2;
                    String rid = JPushInterface.getRegistrationID(getContext().getApplicationContext());
                    map.put("registration_id", rid);
                    net.post(NetConfig.UP_J_PUSH_REGISTRATION_ID, map, this);
                } catch (JSONException e) {
                    dismissLoading();
                    Toast.makeText(getContext(), "验证码错误", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case 2:
                dismissLoading();
                getIMToken();
                break;
            case 5:
                try {
                    JSONObject j = new JSONObject(result);
                    JSONObject b = j.getJSONObject("body");
                    SharedPreferenceUtils.saveToken(getContext(), b.getString("token"));
                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }

    private void getIMToken() {
        netType = 5;
        map.put("type", "1");
        net.post("http://hmyc365.net:8084/HM/stu/im/rong/getTokenIM.do", map, this);
    }
}
