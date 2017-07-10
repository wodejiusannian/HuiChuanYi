package com.example.huichuanyi.ui.activity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.PayVideoAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.bean.Video;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.custom.MySelfPayDialog;
import com.example.huichuanyi.ui.activity.pay.YWTPayActivity;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.IsSuccess;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.example.huichuanyi.utils.UtilsPay;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoPayActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, MySelfDialog.OnYesClickListener, MySelfPayDialog.OnYesClickListener, UtilsInternet.XCallBack, MySelfPayDialog.OnNoClickListener, IsSuccess {

    @ViewInject(R.id.lv_video_pay)
    private ListView mListView;

    @ViewInject(R.id.rv_pay_video_photo)
    private SimpleDraweeView videoPhoto;

    @ViewInject(R.id.tv_video_pay)
    private TextView videoPay;

    @ViewInject(R.id.tv_item_select_speech)
    private TextView videoName;

    private int singlePrice;

    private List<Video.BodyBean> mList;

    private PayVideoAdapter mAdapter;
    private String user_id;
    private UtilsInternet net = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();
    private StringBuffer buffer = new StringBuffer();

    private int isFlag = 1;

    private String city, order_id;

    private String aliOrWeChat;

    private UtilsPay pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_video);
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        mList = (List<Video.BodyBean>) intent.getSerializableExtra("list");
        int pos = intent.getIntExtra("pos", 0);
        Video.BodyBean bodyBean = mList.get(pos);
        String video_price = bodyBean.getVideo_price();
        buffer.append(bodyBean.getVideo_id());
        singlePrice = Integer.parseInt(video_price);
        videoName.setText(bodyBean.getVideo_name());
        videoPhoto.setImageURI(bodyBean.getVideo_pic());
        videoPay.setText(video_price);
        mList.remove(pos);
        for (int i = 0; i < mList.size(); i++) {
            String user_id = mList.get(i).getUser_id();
            if (!CommonUtils.isEmpty(user_id)) {
                mList.remove(i);
            }
        }
        mAdapter = new PayVideoAdapter(mList, this, this);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        user_id = SharedPreferenceUtils.getUserData(this, 1);
        pay = new UtilsPay(this);
        pay.isSuccess(this);
        String citys = SharedPreferenceUtils.getCity(this);
        if (CommonUtils.isEmpty(citys)) {
            city = "北京市";
        } else {
            city = citys;
        }
    }


    @Override
    public void setData() {
    }

    @Override
    public void setListener() {
        mAdapter.registerDataSetObserver(new DataSetObserver() {
            //在你调用Adapter的notifiedDataChanged的时候
            @Override
            public void onChanged() {
                super.onChanged();
                int money = mAdapter.getMoney() + singlePrice;
                if (money > 100) {
                    money -= 20;
                }
                videoPay.setText(money + "");
            }
        });
    }

    @Event(R.id.btn_pay)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay:
                showLoading();
                getOrderId();
                //showDialog();
                break;
            default:
                break;
        }
    }

    private void getOrderId() {
        buffer.delete(1, buffer.length());
        for (int i = 0; i < mList.size(); i++) {
            Video.BodyBean bodyBean = mList.get(i);
            if (bodyBean.isChecked) {
                buffer.append(",");
                buffer.append(bodyBean.getVideo_id());
            }
        }
        map.put("user_id", user_id);
        map.put("promo_code", "");
        map.put("user_city", city);
        map.put("video_id_pj", buffer.toString());
        net.post(NetConfig.GET_SHIPIN_ORDER_ID, map, this);
    }

    private void showDialog() {
        MySelfPayDialog mySelfPayDialog = new MySelfPayDialog(this);
        mySelfPayDialog.setOnNoListener("取消", this);
        mySelfPayDialog.setOnYesListener("确定", this);
        mySelfPayDialog.setTitle("选择支付");
        mySelfPayDialog.show();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_item_select:
                Integer position = (Integer) buttonView.getTag();
                mList.get(position).isChecked = isChecked;
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onClick() {
        isFlag = 1;
    }

    @Override
    public void onClick(String tag) {
        if (TextUtils.equals("3", tag)) {
            Intent intent = new Intent(this, YWTPayActivity.class);
            intent.putExtra("type", "4");
            intent.putExtra("order_id", order_id);
            startActivity(intent);
            return;
        }
        aliOrWeChat = tag;
        map.put("pay_type", tag + "");
        map.put("video_order_id", order_id);
        net.post(NetConfig.PAY_SHIPIN, map, this);
    }

    @Override
    public void onResponse(String result) {
        switch (isFlag) {
            case 1:
                try {
                    String ret = JsonUtils.getRet(result);
                    if (TextUtils.equals("0", ret)) {
                        isFlag = 2;
                        JSONObject object = new JSONObject(result);
                        JSONObject body = object.getJSONObject("body");
                        order_id = body.getString("order_id");
                        dismissLoading();
                        showDialog();
                    } else {
                        Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                isFlag = 1;
                switch (aliOrWeChat) {
                    case "1":
                        try {
                            JSONObject object = new JSONObject(result);
                            JSONObject body = object.getJSONObject("body");
                            String sign = body.getString("sign");
                            pay.aliPay(sign);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "2":
                        pay.weChatPay(result);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void isSuccess(int success) {
        if (TextUtils.equals("1", aliOrWeChat)) {
            switch (success) {
                case 9000:
                    CommonUtils.Toast(this, "支付成功");
                    finish();
                    break;
                case 9001:
                    CommonUtils.Toast(this, "支付失败");
                    break;
                default:
                    break;
            }
        }
    }
}
