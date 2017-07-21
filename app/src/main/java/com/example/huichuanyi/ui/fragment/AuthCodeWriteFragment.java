package com.example.huichuanyi.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.activity.MainActivity;
import com.example.huichuanyi.ui.activity.custom.SecurityCodeView;
import com.example.huichuanyi.ui.activity.login.LoginByAuthCodeActivity;
import com.example.huichuanyi.ui.base.BaseFragment;
import com.example.huichuanyi.utils.CommonStatic;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.SMSUtils;
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
public class AuthCodeWriteFragment extends BaseFragment implements SecurityCodeView.InputCompleteListener, SMSUtils.SMSOnResponse, UtilsInternet.XCallBack {
    private static final String TAG = "AuthCodeWriteFragment";

    @ViewInject(R.id.scv_login_auth_code)
    private SecurityCodeView codeView;

    @ViewInject(R.id.tv_login_phone)
    private TextView mPhone;

    @ViewInject(R.id.tv_login_auth_second)
    private TextView second;

    private int time = 90;

    private boolean isCanSend = false;

    private SMSUtils sms;

    private String mType;

    private int netType = 1;

    private Map<String, String> map = new HashMap<>();
    private UtilsInternet net = UtilsInternet.getInstance();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;
            second.setText(time + "s");
            if (time == 0) {
                time = 90;
                isCanSend = false;
                second.setText("重新发送");
                return;
            }
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }
    };

    @Override
    protected void initEvent() {
        super.initEvent();
        codeView.setInputCompleteListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        sms = new SMSUtils();
    }

    @Override
    protected void initView() {
        super.initView();

    }


    @Event({R.id.iv_login_back, R.id.tv_login_auth_second})
    private void onEvent(View v) {
        switch (v.getId()) {
            case R.id.iv_login_back:
                LoginByAuthCodeActivity activity = (LoginByAuthCodeActivity) getActivity();
                activity.currentItem(0);
                break;
            case R.id.tv_login_auth_second:
                if (!isCanSend) {
                    smsSend();
                    isCanSend = true;
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mPhone.setText(CommonStatic.LOGIN_PHONE);
            if (!isCanSend) {
                mHandler.sendEmptyMessageDelayed(1, 1000);
                smsSend();
            }
            isCanSend = true;
            forceOpenSoftKeyboard(getActivity());
        }
    }


    private void smsSend() {
        sms.smsSend(CommonStatic.LOGIN_PHONE, this);
    }

    @Override
    public void inputComplete() {
        showLoading();
        map.put("user_phone", CommonStatic.LOGIN_PHONE);
        map.put("phone_code", codeView.getEditContent());
        map.put("send_type", mType);
        net.post(NetConfig.LOGIN_PATH, map, this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSuccess(String send_type) {
        Toast.makeText(getContext(), "成功获取验证码", Toast.LENGTH_SHORT).show();
        mType = send_type;
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
