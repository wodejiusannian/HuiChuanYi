package com.example.huichuanyi.ui.activity.lanyang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.HomeLyDetailsAdapter;
import com.example.huichuanyi.adapter.LyDetailsAdapter;
import com.example.huichuanyi.common_view.model.LyBanner;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.LineTextView;
import com.example.huichuanyi.custom.MyListView;
import com.example.huichuanyi.ui.activity.MainActivity;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

import static com.example.huichuanyi.utils.SharedPreferenceUtils.getUserData;

public class LyShopDetailsActivity extends BaseActivity implements UtilsInternet.XCallBack {
    private static final String TAG = "LyShopDetailsActivity";

    @BindViews({R.id.tv_ly_shopdetails_count})
    TextView[] tvs;

    @BindView(R.id.roll_shopdetails_banner)
    RollPagerView rollPagerView;

    @BindView(R.id.mv_shopdetails_changgui)
    MyListView myListView;

    @BindViews({R.id.tv_shopdetails_name, R.id.tv_shopdetails_price,
            R.id.tv_shopdetails_changgui, R.id.tv_shopdetails_shopcar_count, R.id.tv_ly_shopdetails_count})
    TextView[] textViews;

    @BindView(R.id.lv_ly_shopdetails_price)
    LineTextView lineTextView;

    private HomeLyDetailsAdapter adapter;

    private int count = 1;

    private int shopchat = 0;
    private UtilsInternet internet = UtilsInternet.getInstance();

    private Map<String, String> map = new HashMap<>();

    private List<LyBanner.item_1> mData = new ArrayList<>();

    private List<String> changgui = new ArrayList<>();

    private LyDetailsAdapter rvAdapter;

    private int stock_id, goods_id;

    private double price_one;

    private String goods_name, shop_logo, pic_url, shop_name, details_page, sellerPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ly_shop_details);
    }

    @Override
    protected void setListener() {
        rvAdapter = new LyDetailsAdapter(changgui, this);
        myListView.setAdapter(rvAdapter);
        rollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(LyShopDetailsActivity.this, LyDetailsWebActivity.class);
                intent.putExtra("details_page", details_page);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    protected void initData() {
        goods_id = getIntent().getIntExtra("goods_id", 0);
        sellerPicture = getIntent().getStringExtra("sellerPicture");
        map.put("goods_id", goods_id + "");
        map.put("user_id", getUserData(this, 1));
    }

    private void initNet() {
        internet.post(NetConfig.LY_GOOD_DETAILS, map, this);
    }

    @Override
    protected void setData() {
        adapter = new HomeLyDetailsAdapter(rollPagerView, mData, this);
        rollPagerView.setAdapter(adapter);
        initNet();
    }

    @OnClick({/*R.id.tv_ly_shopdetails_buy,*/ R.id.rl_ly_shopdetails_gobuycar, R.id.iv_ly_shopdetails_delete, R.id.tv_ly_shopdetails_addcar, R.id.iv_ly_shopdetails_add})
    public void onEvent(View v) {
        switch (v.getId()) {
           /* case R.id.tv_ly_shopdetails_buy:
                ArrayList<LyShopCar.BodyBean> list = new ArrayList<>();
                list.add(new LyShopCar.BodyBean(false, false, 0, price_one, Integer.parseInt(tvs[0].getText().toString()), goods_id, goods_name, shop_logo, pic_url, shop_name));
                Intent intent = new Intent(this, LyBuyActivity.class);
                intent.putParcelableArrayListExtra("buying", list);
                intent.putExtra("tag", "0");
                intent.putExtra("stock_id", stock_id);
                startActivity(intent);
                break;*/
            case R.id.rl_ly_shopdetails_gobuycar:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("page", 2);
                startActivity(intent);
                break;
            case R.id.iv_ly_shopdetails_delete:
                if (count > 1)
                    count--;
                else
                    Toast.makeText(this, "数量不能为0", Toast.LENGTH_SHORT).show();
                tvs[0].setText(count + "");
                break;
            case R.id.iv_ly_shopdetails_add:
                count++;
                tvs[0].setText(count + "");
                break;
            case R.id.tv_ly_shopdetails_addcar:
                addCar();
                break;
            default:
                break;
        }
    }

    /*
    * 加入购物车
    * */
    private void addCar() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.show();
        RequestParams pa = new RequestParams(NetConfig.SHOPCAR_ADD);
        String buyUserId = SharedPreferenceUtils.getUserData(this, 1);
        String orderNumber = tvs[0].getText().toString();
        String buyUserCity = SharedPreferenceUtils.getCity(this);
        String buyUserName = SharedPreferenceUtils.getUserData(this, 2);
        pa.addBodyParameter("buyUserId", buyUserId);
        pa.addBodyParameter("goodsId", goods_id + "");
        pa.addBodyParameter("orderNumber", orderNumber);
        pa.addBodyParameter("buyUserCity", buyUserCity);
        pa.addBodyParameter("buyUserCity", buyUserCity);
        pa.addBodyParameter("sellerPicture", sellerPicture);
        pa.addBodyParameter("buyUserName", buyUserName);
        pa.addBodyParameter("orderRemarkBuyer", "");
        for (int i = 0; i < changgui.size(); i++) {
            if (i == 0) {
                pa.addBodyParameter("goodsColor", changgui.get(i));
            } else if (i == 1) {
                pa.addBodyParameter("goodsSize", changgui.get(i));
            }
        }
        x.http().post(pa, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String ret = object.getString("ret");
                    if ("0".equals(ret)) {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                shopchat += count;
                                textViews[3].setText(shopchat + "");
                            }
                        });
                        Toast.makeText(LyShopDetailsActivity.this, "加入购物车成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LyShopDetailsActivity.this, "加入购物车失败", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(LyShopDetailsActivity.this, "加入购物车失败", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
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

    public void back(View view) {
        finish();
    }

    @Override
    public void onResponse(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject body = object.getJSONObject("body");
            JSONArray main = body.getJSONArray("main");
            JSONArray pic = body.getJSONArray("pic");
            JSONArray attribute = body.getJSONArray("attribute");
            JSONArray stock = body.getJSONArray("stock");
            for (int i = 0; i < attribute.length(); i++) {
                JSONObject a = attribute.getJSONObject(i);
                String tag = a.getString("tag");
                String explain = a.getString("explain");
                changgui.add(tag + ":" + explain);
            }
            for (int i = 0; i < main.length(); i++) {
                JSONObject p = main.getJSONObject(i);
                LyBanner.item_1 banner = new LyBanner.item_1();
                banner.setPic_url(p.getString("pic_url"));
                mData.add(banner);
            }
            for (int i = 0; i < pic.length(); i++) {
                JSONObject p = pic.getJSONObject(i);
                LyBanner.item_1 banner = new LyBanner.item_1();
                banner.setPic_url(p.getString("pic_url"));
                mData.add(banner);
            }
            // shopchat = body.getInt("shopcart");
            final String color = stock.getJSONObject(0).getString("color");
            final JSONObject obj = main.getJSONObject(0);
            stock_id = stock.getJSONObject(0).getInt("stock_id");
            price_one = obj.getInt("price");
            goods_name = obj.getString("name");
            shop_logo = obj.getString("shop_logo");
            details_page = obj.getString("details_page");
            final String price_init = obj.getString("price_init");
            pic_url = obj.getString("pic_url");
            shop_name = obj.getString("shop_name");
            final String name = obj.getString("name");
            final double price = obj.getDouble("price");
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    if (textViews[0] != null)
                        textViews[0].setText(name);
                    if (textViews[1] != null)
                        textViews[1].setText(price + "");
                    if (lineTextView != null)
                        lineTextView.setText(price_init);
                    if (textViews[2] != null)
                        textViews[2].setText(color);
                    /*if (textViews[3] != null)
                        textViews[3].setText(shopchat + "");*/
                }
            });
            rvAdapter.notifyDataSetChanged();
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "请重试", Toast.LENGTH_SHORT).show();
        }
    }

}
