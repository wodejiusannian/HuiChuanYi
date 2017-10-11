package com.example.huichuanyi.ui.activity.lanyang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.LyBuyAdapter;
import com.example.huichuanyi.common_view.model.LyCommendPeople;
import com.example.huichuanyi.common_view.model.LyShopCar;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MyListView;
import com.example.huichuanyi.custom.MySelfPayDialog;
import com.example.huichuanyi.ui.activity.AddressListActivity;
import com.example.huichuanyi.ui.activity.pay.CMBPayActivity;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.IsSuccess;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsPay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class LyBuyActivity extends BaseActivity implements MySelfPayDialog.OnYesClickListener, IsSuccess {

    @BindView(R.id.mlw_ly_buy_content)
    MyListView lvContent;

    @BindView(R.id.tg_ly_buy_open)
    ToggleButton tb;

    private List<LyShopCar.BodyBean> mData = new ArrayList<>();

    private List<LyShopCar.BodyBean> data;
    @BindViews({R.id.tv_ly_buy_commendpeople, R.id.tv_lybuy_name, R.id.tv_lybuy_introduce, R.id.tv_write_order_phone,
            R.id.tv_write_order_address, R.id.tv_write_order_name, R.id.tv_lybuy_money, R.id.tv_lybuy_count, R.id.rv_lyshopdetails_zhanwei})
    TextView[] tvs;

    @BindViews({R.id.rl_lybuy_commendpeople, R.id.rl_lybuy_address})
    RelativeLayout[] rls;

    @BindViews({R.id.iv_lybuy_photo, R.id.iv_lybuy_shop_logo})
    ImageView[] ivs;


    private LyBuyAdapter adapter;

    private String tag, studio_id, order_id, ry_studio_name, aliOrWeChat;

    private int stock_id;
    private UtilsPay pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ly_buy);
    }

    @Override
    protected void setListener() {
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mData.clear();
                if (isChecked) {
                    mData.addAll(data);
                } else {
                    if (data.size() > 2)
                        mData.addAll(data.subList(0, 2));
                    else
                        mData.addAll(data);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");
        data = intent.getParcelableArrayListExtra("buying");
        stock_id = intent.getIntExtra("stock_id", 0);
        adapter = new LyBuyAdapter(mData, this);
        lvContent.setAdapter(adapter);
        pay = new UtilsPay(this);
        pay.isSuccess(this);
    }

    @Override
    protected void setData() {
        if (data.size() > 2) {
            mData.addAll(data.subList(0, 2));
        } else {
            mData.addAll(data);
        }
        adapter.notifyDataSetChanged();
        String shop_logo = mData.get(0).getShop_logo();
        Glide.with(this).load(shop_logo).into(ivs[1]);
        double money = 0;
        int count = 0;
        for (LyShopCar.BodyBean bean : data) {
            money += bean.getPrice_one() * bean.getNum();
            count += bean.getNum();
        }
        tvs[6].setText("共计：" + money);
        tvs[7].setText("共" + count + "件");
    }


    @OnClick({R.id.rl_ly_buy_commendpeople, R.id.rl_ly_buy_address, R.id.tv_lybuy_go_pay})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.rl_ly_buy_commendpeople:
                startActivityForResult(new Intent(this, LyCommendPeopleActivity.class), 1);
                break;
            case R.id.rl_ly_buy_address:
                startActivityForResult(new Intent(this, AddressListActivity.class), 2);
                break;
            case R.id.tv_lybuy_go_pay:
                addOrder();
                break;
            default:
                break;
        }
    }

    private void addOrder() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("user_id", SharedPreferenceUtils.getUserData(this, 1));
            obj.put("studio_id", studio_id);
            String s1 = tvs[5].getText().toString();
            String s2 = tvs[3].getText().toString();
            String s3 = tvs[4].getText().toString();
            if (CommonUtils.isEmpty(s1) || CommonUtils.isEmpty(s2) || CommonUtils.isEmpty(s3)) {
                Toast.makeText(this, "请完善地址信息", Toast.LENGTH_SHORT).show();
                return;
            }
            obj.put("shr_name", s1);
            obj.put("shr_phone", s2);
            obj.put("shr_address", s3);
            StringBuffer buff = new StringBuffer();
            for (LyShopCar.BodyBean bean : data) {
                buff.append(bean.getPic_url());
                buff.append(",");
            }
            String s = buff.toString();
            obj.put("ry_pic_pj", s.substring(0, s.length() - 1));
            obj.put("ry_studio_name", ry_studio_name);
            JSONArray array = new JSONArray();
            for (LyShopCar.BodyBean bean : data) {
                JSONObject o = new JSONObject();
                o.put("id", bean.getId());
                o.put("goods_id", bean.getGoods_id());
                o.put("num", bean.getNum());
                o.put("stock_id", stock_id);
                array.put(o);
            }
            obj.put("goods_info", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestParams pa = new RequestParams(NetConfig.LY_SHOP_ADD_ORDER);
        pa.addBodyParameter("mydata", obj.toString());
        x.http().post(pa, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    String ret = obj.getString("ret");
                    if ("0".equals(ret)) {
                        showDialog();
                        order_id = obj.getJSONObject("body").getString("order_id");
                    } else {
                        Toast.makeText(LyBuyActivity.this, "请重试", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(LyBuyActivity.this, "请重试", Toast.LENGTH_SHORT).show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                try {
                    String ids = data.getStringExtra("ids");
                    if (CommonUtils.isEmpty(ids)) {
                        LyCommendPeople.BodyBean model = (LyCommendPeople.BodyBean) data.getSerializableExtra("id");
                        studio_id = model.getStudio_id() + "";
                        ry_studio_name = model.getName_fzr();
                        rls[0].setVisibility(View.VISIBLE);
                        tvs[0].setVisibility(View.GONE);
                        Glide.with(this).load(model.getPic_url()).error(R.mipmap.stand).into(ivs[0]);
                        tvs[1].setText(ry_studio_name);
                        tvs[2].setText(model.getName_gzs());
                    } else {
                        studio_id = "0";
                        rls[0].setVisibility(View.GONE);
                        tvs[0].setVisibility(View.VISIBLE);
                        tvs[0].setText("不选择服务顾问");
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "亲，您还没有选择推荐人", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                try {
                    tvs[8].setVisibility(View.GONE);
                    rls[1].setVisibility(View.VISIBLE);
                    String name = data.getStringExtra("name");
                    String phone = data.getStringExtra("phone");
                    String city = data.getStringExtra("city");
                    String address = data.getStringExtra("selector_address");
                    tvs[5].setText(name);
                    tvs[3].setText(phone);
                    tvs[4].setText(city + address);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "亲，您还没有选择地址", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public void back(View view) {
        finish();
    }


    private void showDialog() {
        MySelfPayDialog mySelfPayDialog = new MySelfPayDialog(this);
        mySelfPayDialog.setOnNoListener("取消", null);
        mySelfPayDialog.setOnYesListener("确定", this);
        mySelfPayDialog.setTitle("选择支付");
        mySelfPayDialog.show();
    }

    @Override
    public void onClick(String tag) {
        aliOrWeChat = tag;
        switch (tag) {
            case "1":
                RequestParams params = new RequestParams(NetConfig.LY_SHOP_GET_SIGN);
                params.addBodyParameter("order_id", order_id);
                params.addBodyParameter("pay_type", "1");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject object = new JSONObject(result);
                            JSONObject body = object.getJSONObject("body");
                            String sign = body.getString("sign");
                            pay.aliPay(sign);
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
                break;
            case "2":
                RequestParams pa = new RequestParams(NetConfig.LY_SHOP_GET_SIGN);
                pa.addBodyParameter("order_id", order_id);
                pa.addBodyParameter("pay_type", "2");
                x.http().post(pa, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        pay.weChatPay(result);
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
            case "3":
                RequestParams pap = new RequestParams(NetConfig.LY_SHOP_GET_SIGN);
                pap.addBodyParameter("order_id", order_id);
                pap.addBodyParameter("pay_type", "3");
                x.http().post(pap, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Intent intent = new Intent(LyBuyActivity.this, CMBPayActivity.class);
                        intent.putExtra("order_id", result);
                        startActivity(intent);
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
            default:
                break;
        }
    }

    @Override
    public void isSuccess(int success) {
        if ("1".equals(aliOrWeChat)) {
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
