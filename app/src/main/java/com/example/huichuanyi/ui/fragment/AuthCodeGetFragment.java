package com.example.huichuanyi.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.example.huichuanyi.ui.activity.login.LoginHMService;
import com.example.huichuanyi.ui.base.BaseFragment;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonStatic;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.LoginControl;
import com.example.huichuanyi.utils.SMSUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

@ContentView(R.layout.fragment_get_auth_code)
public class AuthCodeGetFragment extends BaseFragment implements SMSUtils.SMSOnResponse, UtilsInternet.XCallBack {
    private static final String TAG = "AuthCodeGetFragment";
    private LoginControl control;

    private boolean isAgree = true;

    @ViewInject(R.id.et_login_phone)
    private EditText mPhone;

    @ViewInject(R.id.cb_login_service)
    private CheckBox mService;

    @ViewInject(R.id.iv_login_next)
    private ImageView mNext;

    @ViewInject(R.id.btn_send_sms)
    private TextView tvTime;

    @ViewInject(R.id.et_no_register_pwd)
    private EditText noRegisterPwd;

    @ViewInject(R.id.et_login_pwd)
    private EditText etAuthCode;

    @ViewInject(R.id.tv_get_auth_code_forget_pwd)
    private TextView tvForgetPWD;

    @ViewInject(R.id.et_get_auth_code_pwd)
    private EditText writePWD;


    private SMSUtils sms;

    private String mType;

    private Map<String, String> map = new HashMap<>();

    private int netType = 1;

    private boolean flag = true;

    private UtilsInternet net = UtilsInternet.getInstance();

    @Event({R.id.btn_send_sms, R.id.iv_login_next, R.id.iv_login_qq, R.id.iv_login_wechat, R.id.go_service, R.id.tv_get_auth_code_forget_pwd})
    private void onEvent(View v) {
        switch (v.getId()) {
            case R.id.btn_send_sms:
                final String number = mPhone.getText().toString().trim().replace(" ", "");
                VerificationCodeDialog codeDialog = new VerificationCodeDialog(getContext());
                codeDialog.setPhone(number, new VerificationCodeDialog.EditYes() {
                    @Override
                    public void getEdit() {
                        SMSTimeUtils smsTimeUtils = new SMSTimeUtils(tvTime, 60000, 1000);
                        smsTimeUtils.start();
                    }
                });
                codeDialog.show();
                break;
            case R.id.iv_login_next:
                //这个地方是点击确定的地方
                sure();
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
            case R.id.tv_get_auth_code_forget_pwd:
                next();
                break;
            default:
                break;
        }
    }

    private void sure() {
        if (flag)
            phoneAndPwd();
        else
            phoneAndCode();
    }


    private void phoneAndCode() {
        String number = mPhone.getText().toString().trim().replace(" ", "");
        String pwd = noRegisterPwd.getText().toString().trim().replace(" ", "");
        String code = etAuthCode.getText().toString().trim().replace(" ", "");
        if (!CommonUtils.isEmpty(number) && number.length() == 11) {
            if (isAgree) {
                if (!CommonUtils.isEmpty(pwd) && !CommonUtils.isEmpty(code)) {
                    map.put("phone", number);
                    map.put("pwd", pwd);
                    map.put("send_type", mType);
                    map.put("code", code);
                    net.post(NetConfig.LOGIN_THROUGH_PHONE_AUTH_CODE, map, this);
                } else {
                    Toast.makeText(getContext(), "请将信息填写完整", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "登录需要同意服务条款", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "请正确输入手机号", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    * 忘记密码
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


    @Override
    protected void initData() {
        super.initData();
        sms = new SMSUtils();
    }

    TextWatcher mTextWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 11) {
                isHavePWD(s.toString());
                //mNext.setVisibility(View.GONE);
            } else {
                handler.sendEmptyMessage(1001);
                //mNext.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /*
    * params phone 手机号
    *
    * 判断手机是否有密码
    * */
    private void isHavePWD(String phone) {
        RequestParams params = new RequestParams(NetConfig.LOGIN_IS_HAVE_PWD);
        params.addBodyParameter("phone", phone);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject o = new JSONObject(result);
                    String ret = o.getString("ret");
                    if ("0".equals(ret)) {
                        handler.sendEmptyMessage(0);
                    } else if ("1020".equals(ret)) {
                        handler.sendEmptyMessage(1020);
                    } else {
                        handler.sendEmptyMessage(1045);
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

    /*
    *  0 代表有密码
    *  1024 代表没有密码
    *  1048 服务器内部错误了
    *
    * */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case 0:
                    flag = true;
                    writePWD.setVisibility(View.VISIBLE);
                    tvForgetPWD.setVisibility(View.VISIBLE);
                    tvTime.setVisibility(View.GONE);
                    etAuthCode.setVisibility(View.GONE);
                    mNext.setVisibility(View.VISIBLE);
                    noRegisterPwd.setVisibility(View.GONE);
                    break;
                case 1020:
                    flag = false;
                    writePWD.setVisibility(View.GONE);
                    tvForgetPWD.setVisibility(View.GONE);
                    tvTime.setVisibility(View.VISIBLE);
                    etAuthCode.setVisibility(View.VISIBLE);
                    mNext.setVisibility(View.VISIBLE);
                    noRegisterPwd.setVisibility(View.VISIBLE);
                    break;
                case 1045:
                    Toast.makeText(getContext(), "请重试", Toast.LENGTH_SHORT).show();
                    break;
                case 1001:
                    writePWD.setVisibility(View.GONE);
                    tvForgetPWD.setVisibility(View.GONE);
                    tvTime.setVisibility(View.GONE);
                    etAuthCode.setVisibility(View.GONE);
                    mNext.setVisibility(View.GONE);
                    noRegisterPwd.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onSuccess(String send_type) {
        Toast.makeText(getContext(), "成功获取验证码", Toast.LENGTH_SHORT).show();
        mType = send_type;
    }

    public void phoneAndPwd() {
        String number = mPhone.getText().toString().trim().replace(" ", "");
        String pwd = writePWD.getText().toString().trim().replace(" ", "");
        if (!CommonUtils.isEmpty(number) && number.length() == 11) {
            if (isAgree) {
                if (!CommonUtils.isEmpty(pwd)) {
                    map.put("phone", number);
                    map.put("pwd", pwd);
                    net.post(NetConfig.LOGIN_THROUGH_PHONE, map, this);
                } else {
                    Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "登录需要同意服务条款", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "请正确输入手机号", Toast.LENGTH_SHORT).show();
        }
    }


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

                    if (!"0".equals(ret)) {
                        Toast.makeText(getContext(), "密码错误", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissLoading();
    }
}
