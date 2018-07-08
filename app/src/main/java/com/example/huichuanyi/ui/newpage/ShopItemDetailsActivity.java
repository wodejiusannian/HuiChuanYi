package com.example.huichuanyi.ui.newpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.ShopList;
import com.example.huichuanyi.common_view.model.ShopCarType4Model;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.dialog.RecommendDialog;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.ActivityCacheUtils;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.MUtilsInternet;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static android.webkit.WebSettings.LOAD_NO_CACHE;

public class ShopItemDetailsActivity extends BaseActivity {

    @BindView(R.id.wb_item_show)
    WebView mShow;

    private RecommendDialog dialog;


    private String clothes_id, clothes_cz;

    private ShopList.BodyBean bean;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detailss);
        ActivityCacheUtils.addActivity(this);
    }


    @Override
    public void initData() {
        Intent intent = getIntent();
        clothes_id = intent.getStringExtra("id");
        clothes_cz = intent.getStringExtra("clothes_cz");
        bean = (ShopList.BodyBean) intent.getSerializableExtra("body");
        dialog = new RecommendDialog(this, bean.getClothes_id(), bean.getMianliao_id());
        String url = String.format("http://hmyc365.net/hmyc/file/app/app-clothes-xq/index.html?token=%s&clothesId=%s", NetConfig.TOKEN, clothes_id);
        loadindUrl(url);
    }


    @Override
    public void setData() {
        dialog.setInfomation(new RecommendDialog.InformationData() {
            @Override
            public void getInformation(int tag, String color_name, String stock_id) {
                switch (tag) {
                    case 1:
                        isBuy365(tag, color_name, stock_id);
                        break;
                    case 2:
                        isBuy365(tag, color_name, stock_id);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void isBuy365(final int tag, final String color_name, final String stock_id) {
        map.put("buyUserId", SharedPreferenceUtils.getUserData(this, 1));
        map.put("user_id", SharedPreferenceUtils.getUserData(this, 1));
        net.post2(NetConfig.IS_BUY_365, map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    String ret = JsonUtils.getRet(result);
                    if ("0".equals(ret)) {
                        JSONObject object = new JSONObject(result);
                        JSONObject body = object.getJSONObject("body");
                        String studio_id = body.getString("studio_id");
                        addCar(tag, color_name, stock_id, studio_id);
                    } else {
                        Toast.makeText(ShopItemDetailsActivity.this, "亲，请先购买365会员哦", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private MUtilsInternet net = MUtilsInternet.getInstance();

    private Map<String, String> map = new HashMap<>();

    /* /admiral/app/hmyc/order/mall/addGwc.htm?token=
     // 必传字段
     private String buyUserId;// 用户
     private String goodsId;// 商品
     private String recommendUserId;// 工作室ID
     private String goodsSocketId;// 库存
     // 非必传字段
     private String goodsCz;
     private String buyUserCity;// 用户城市
     private String buyUserName;
     private String buyUserPic;
     private String orderRemarkBuyer;// 用户备注
     说明：返回code：3013，请先购买VIP*/
    private void addCar(final int tag, final String color_name, String stock_id, String recommendUserId) {
        map.put("goodsId", bean.getClothes_id());
        map.put("recommendUserId", recommendUserId);
        map.put("goodsSocketId", stock_id);
        net.post(NetConfig.HMSHOPCAR_ADD, this, map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    String ret = obj.getString("ret");
                    if ("0".equals(ret)) {
                        if (tag == 1) {
                            Toast.makeText(ShopItemDetailsActivity.this, "加入购物车成功", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject body = obj.getJSONObject("body");
                            goBuy(color_name, body.getString("id"));
                        }
                        dialog.dismiss();
                    } else {
                        Toast.makeText(ShopItemDetailsActivity.this, "加入购物车失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void goBuy(String color_name, String id) {
        ArrayList<ShopCarType4Model> array = new ArrayList<>();
        array.add(new ShopCarType4Model(color_name, bean.getClothes_id(), bean.getClothes_name(), bean.getClothes_pic(),
                bean.getClothes_price_yh() + "", bean.getMianliao_id(), id, 1, ""));
        Intent intent = new Intent(this, ShopCarOrderDetailsActivity.class);
        intent.putParcelableArrayListExtra("shoplist", array);
        startActivity(intent);
    }


    @Override
    public void setListener() {

    }


    public void back(View view) {
        finish();
    }


    private void loadindUrl(String url) {
        WebSettings webSettings = mShow.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(LOAD_NO_CACHE);
        webSettings.setJavaScriptEnabled(true);
        mShow.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        WebChromeClient wvcc = new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {


            }

        };
        mShow.setWebChromeClient(wvcc);
        if (url != null) {
            mShow.loadUrl(url);
        }
    }

    @OnClick({R.id.shop_item_btn_recommend})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.shop_item_btn_recommend:
                dialog.show();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
