package com.example.huichuanyi.bean;

import android.annotation.SuppressLint;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.PayVideoAdapter;
import com.example.huichuanyi.alipay.PayResult;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.custom.RoundImageView;
import com.example.huichuanyi.utils.MySharedPreferences;
import com.example.huichuanyi.utils.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PayVideoActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, MySelfDialog.OnYesClickListener {
    private ListView mListView;
    private String videoid;
    private List<Video.ListBean> mData;
    private PayVideoAdapter mAdapter;
    private Button mButton;
    private TextView mTextView, mTextViewName;
    private String price;
    private int intPrice, isAliWeChat = 1;
    private Set<String> mId;
    private StringBuffer buffer = new StringBuffer();
    private RoundImageView mRoundImageView;
    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PayVideoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(PayVideoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_video);
    }

    @Override
    public void initView() {
        mListView = (ListView) findViewById(R.id.ll_payvideo_shop);
        mTextView = (TextView) findViewById(R.id.tv_payvideo_count);
        mButton = (Button) findViewById(R.id.bt_payvideo_pay);
        mRoundImageView = (RoundImageView) findViewById(R.id.rv_pay_video_photo);
        mTextViewName = (TextView) findViewById(R.id.tv_item_select_speech);
    }

    @Override
    public void initData() {
        videoid = getIntent().getStringExtra("videoid");
        price = getIntent().getStringExtra("price");
        String geticon = getIntent().getStringExtra("geticon");
        String name = getIntent().getStringExtra("name");
        mTextViewName.setText(name);
        if (!TextUtils.isEmpty(geticon)) {
            Picasso.with(this).load(geticon).into(mRoundImageView);
        }
        intPrice = Integer.parseInt(price);
        mData = new ArrayList<>();
        mId = new HashSet<>();
        mId.add(videoid);
        mAdapter = new PayVideoAdapter(mData, this, this);
        getData();
    }

    private void getData() {
        RequestParams params = new RequestParams(NetConfig.PAY_VIDEO);
        params.addBodyParameter("userid", new User(this).getUseId() + "");
        params.addBodyParameter("videoid", videoid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Video video = gson.fromJson(result, Video.class);
                mData.addAll(video.getList());
                mAdapter.notifyDataSetChanged();
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

    @Override
    public void setData() {
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void setListener() {
        mButton.setOnClickListener(this);
        mAdapter.registerDataSetObserver(new DataSetObserver() {
            //在你调用Adapter的notifiedDataChanged的时候
            @Override
            public void onChanged() {
                super.onChanged();
                int money = mAdapter.getMoney() + intPrice;
                if (mId.size() == 4) {
                    money -= 20;
                }
                mTextView.setText(money + "");
                mButton.setText("确认付款");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bt_payvideo_pay:
                MySelfDialog mySelfDialog = new MySelfDialog(this);
                mySelfDialog.setTitle("选择支付");
                mySelfDialog.setPay(true);
                mySelfDialog.setOnAliPayListener(new MySelfDialog.OnAliPayClickListener() {
                    @Override
                    public void onClick() {
                        isAliWeChat = 1;
                    }
                });
                mySelfDialog.setOnYesListener("确认", this);
                mySelfDialog.setOnNoListener("取消", null);
                mySelfDialog.show();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_item_select:
                Integer position = (Integer) buttonView.getTag();
                String id = mData.get(position).getId();
                if (position != null) {
                    if (isChecked) {
                        mId.add(id);
                    } else {
                        mId.remove(id);
                    }
                    mData.get(position).isChecked = isChecked;
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void onClick() {
        switch (isAliWeChat) {
            case 1:
                buffer.delete(0, buffer.length());
                for (String str : mId) {
                    buffer.append(str);
                    buffer.append(",");
                }
                buffer.delete(buffer.length() - 1, buffer.length());
                String s = mTextView.getText().toString();
                int anInt = Integer.parseInt(s);
                RequestParams params = new RequestParams(NetConfig.PAY_BUG);
                params.addBodyParameter("userid", new User(PayVideoActivity.this).getUseId() + "");
                params.addBodyParameter("money", anInt + "");
                params.addBodyParameter("city", MySharedPreferences.getCity(this));
                params.addBodyParameter("type", "1");
                params.addBodyParameter("videosid", buffer.toString());
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(final String get) {
                        Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(PayVideoActivity.this);
                                Map<String, String> result = alipay.payV2(get, true);
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
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
                break;
            case 2:

                break;
            default:

                break;
        }
    }

    public void back(View view) {
        finish();
    }
}
