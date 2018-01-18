package com.example.huichuanyi.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.Fresh_365;
import com.example.huichuanyi.baidumap.Location;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.fragment_first.Fragment_Home;
import com.example.huichuanyi.fragment_first.Fragment_Mine;
import com.example.huichuanyi.fragment_first.MainFragment365;
import com.example.huichuanyi.ui.activity.login.LoginByAuthCodeActivity;
import com.example.huichuanyi.ui.fragment.Order2Fragment;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UpdateUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, UtilsInternet.XCallBack {
    private static final String TAG = "MainActivity";

    @ViewInject(R.id.tv_messager_is_have)
    private TextView tDian;

    @ViewInject(R.id.rg_main_navigation)
    private RadioGroup mRadioGroup;

    @ViewInject(R.id.rl_masking)
    private LinearLayout mask;

    @ViewInject(R.id.rl_main_show)
    private RelativeLayout rl;
    @ViewInject(R.id.sv_main_show_active)
    private SimpleDraweeView maskActive;

    private static boolean isExit = false;
    private boolean have = false;
    //private FreshPhoto mFreshPhoto;
    private Fresh_365 mFresh_365;
    private Fragment[] mFragments;
    private int index;
    private int currentTabIndex;
    private UtilsInternet net = UtilsInternet.getInstance();
    private String hm_adpage_share_url, hm_adpage_webview_url, hm_activity_name;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    Handler mHand = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String hm_adpage_pic_url = data.getString("hm_adpage_pic_url");
            if (!CommonUtils.isEmpty(hm_adpage_pic_url)) {
                mask.setVisibility(View.VISIBLE);
                maskActive.setImageURI(hm_adpage_pic_url);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void initView() {
        net.get(NetConfig.IS_HAVE_ACTIVITY, null, this);
    }

    public void initData() {
        Fragment_Home mHome = new Fragment_Home();
        Order2Fragment mOrder = new Order2Fragment();
        Fragment_Mine mMine = new Fragment_Mine();
        MainFragment365 m365 = new MainFragment365();
        mFragments = new Fragment[]{mHome, mOrder, m365, mMine};
        int page = getIntent().getIntExtra("page", 0);

        if (page == 0) {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.rl_main_show, mHome).hide(mOrder).
                    show(mHome).commit();
        } else if (page == 1) {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.rl_main_show, mHome).hide(mMine).
                    show(mOrder).commit();
        } else if (page == 2) {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.rl_main_show, mHome).hide(mMine).
                    show(m365).commit();
        }
    }

    public void setData() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshFriend");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
        UpdateUtils.getInstance(getApplicationContext()).update(true);
    }

    public void setListener() {
        mRadioGroup.setOnCheckedChangeListener(this);
        connect(SharedPreferenceUtils.getToken(MainActivity.this));
        //rl.setFitsSystemWindows(true);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            CommonUtils.Toast(getApplicationContext(), "再按一次退出程序");
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            System.exit(0);
        }
    }


    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshFriend")) {
              /*  if (mFreshPhoto != null) {
                    mFreshPhoto.getPhoto();
                }*/
                if (mFresh_365 != null) {
                    mFresh_365.reFresh365();
                }
            }
        }
    };


    public void setFresh365(Fresh_365 fresh365) {
        mFresh_365 = fresh365;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mRefreshBroadcastReceiver);
    }


    @Event({R.id.iv_main_mask_delete, R.id.sv_main_show_active})
    private void onEvent(View v) {
        switch (v.getId()) {
            case R.id.iv_main_mask_delete:
                mask.setVisibility(View.GONE);
                break;
            case R.id.sv_main_show_active:
                if (CommonUtils.isEmpty(hm_adpage_share_url) || CommonUtils.isEmpty(hm_adpage_webview_url))
                    return;

                Map<String, Object> map = new HashMap<>();
                map.put("hm_adpage_share_url", hm_adpage_share_url);
                map.put("hm_adpage_webview_url", hm_adpage_webview_url);
                map.put("hm_activity_name", hm_activity_name);
                ActivityUtils.switchTo(this, HMWebActivity.class, map);
                mask.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    //切换替换Fragment
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_main_home:
                index = 0;
                rl.setFitsSystemWindows(true);
                break;
            case R.id.rb_main_order:
                index = 1;
                rl.setFitsSystemWindows(false);
                break;
            case R.id.rb_main_365:
                index = 2;
                rl.setFitsSystemWindows(false);
                break;
            case R.id.rb_main_me:
                rl.setFitsSystemWindows(false);
                index = 3;
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(mFragments[currentTabIndex]);
            if (!mFragments[index].isAdded()) {
                trx.add(R.id.rl_main_show, mFragments[index]);
            }
            trx.show(mFragments[index]).commit();
            currentTabIndex = index;
        }
    }


    @Override
    public void onResponse(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString("hm_adpage_pic_url", object.getString("hm_adpage_pic_url"));
            hm_adpage_share_url = object.getString("hm_adpage_share_url");
            hm_adpage_webview_url = object.getString("hm_adpage_webview_url");
            hm_activity_name = object.getString("hm_activity_name");
            String hm_activity_exists = object.getString("hm_activity_exists");
            if (TextUtils.equals("Y", hm_activity_exists)) {
                Location.isHaveActive = true;
            }
            msg.setData(bundle);
            mHand.sendMessage(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token 从服务端获取的用户身份令牌（Token）。
     * @return RongIM  客户端核心类的实例。
     */
    private void connect(String token) {
        RongIM.setOnReceiveMessageListener(new MyOnReceiveMessage());

        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查
             * 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {

            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {

                RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
                    @Override
                    public void onChanged(ConnectionStatus connectionStatus) {
                        switch (connectionStatus) {
                            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "您的账号在其他手机登录，请确认后重新登录", Toast.LENGTH_SHORT).show();
                                        SharedPreferenceUtils.writeUserId(MainActivity.this, null);
                                        SharedPreferenceUtils.save365(MainActivity.this, null);
                                        startActivity(new Intent(MainActivity.this, LoginByAuthCodeActivity.class));
                                        finish();
                                    }
                                });
                                break;
                            default:
                                break;
                        }

                    }
                });
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    public class MyOnReceiveMessage implements RongIMClient.OnReceiveMessageListener {


        @Override
        public boolean onReceived(final io.rong.imlib.model.Message message, int i) {
            handler.sendEmptyMessage(0);
            Intent broadcast = new Intent("action.have.msg");
            broadcast.putExtra("isRead", i + 1);
            sendOrderedBroadcast(broadcast, null);
            return false;
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tDian.setVisibility(View.VISIBLE);
            have = true;
        }
    };

    public void hideDian() {
        tDian.setVisibility(View.GONE);
    }

    public boolean isHave() {
        return have;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int page = intent.getIntExtra("page", 0);
        RadioButton but = (RadioButton) mRadioGroup.getChildAt(page);
        but.setChecked(true);
    }
}
