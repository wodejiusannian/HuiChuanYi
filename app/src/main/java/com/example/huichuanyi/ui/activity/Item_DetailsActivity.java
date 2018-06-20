package com.example.huichuanyi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.model.PrivateRecommendModel;
import com.example.huichuanyi.common_view.model.ShopCarType4Model;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.share.Share;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.ui.newpage.ShopCarOrderDetailsActivity;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.WebViewUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

public class Item_DetailsActivity extends BaseActivity {

    @BindView(R.id.rl_item_details_select)
    RelativeLayout mJump;

    @BindView(R.id.wb_item_show)
    WebView mShow;

    @BindView(R.id.pb_buy_loading)
    ProgressBar mLoading;

    public static Item_DetailsActivity item_detailsActivity;

    private WebViewUtils webViewUtils;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        item_detailsActivity = this;
    }


    private String url;

    @Override
    public void initData() {
        bean = (PrivateRecommendModel) getIntent().getSerializableExtra("bean");
        url = String.format("http://hmyc365.net/hmyc/file/app/app-clothes-info/index.html?token=%s&id=%s&userPicture=%s&userName=%s", NetConfig.TOKEN, bean.getId(), SharedPreferenceUtils.getUserData(this, 3), SharedPreferenceUtils.getUserData(this, 2));
        Log.e("TAG", "initData: ----" + url);
        webViewUtils = new WebViewUtils(new WebViewUtils.WebOnResult() {
            @Override
            public void onResultProgress(int progress) {
                if (progress == 100) {
                    mLoading.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    mLoading.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    mLoading.setProgress(progress);//设置进度值
                }
            }

            @Override
            public void onResultTitle(String title) {
            }

            @Override
            public void onResultUrl(String url, String u) {
                if (u.contains("a=chatWithManager")) {
                    RequestParams params = new RequestParams(NetConfig.IS_BUY_365);
                    params.addBodyParameter("user_id", SharedPreferenceUtils.getUserData(Item_DetailsActivity.this, 1));
                    x.http().post(params, new Callback.CacheCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject object = new JSONObject(result);
                                JSONObject body = object.getJSONObject("body");
                                String studio_name = body.getString("studio_name");
                                String studio_id = body.getString("studio_id");
                                RongIM im = RongIM.getInstance();
                                if (im != null && studio_id != null) {
                                    im.startPrivateChat(Item_DetailsActivity.this, "hmgls_" + studio_id, studio_name);
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

                        @Override
                        public boolean onCache(String result) {
                            return false;
                        }
                    });
                }
            }
        });
        webViewUtils.LoadingUrl(mShow, url);
    }

    PrivateRecommendModel bean;
    /*
    * 初始化webview
    * */


    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
    }

    @OnClick({R.id.rl_item_details_select, R.id.iv_web_share})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.rl_item_details_select:
                ArrayList<ShopCarType4Model> array = new ArrayList<>();
                array.add(new ShopCarType4Model(bean.getColor_name(), bean.getId(), bean.getClothes_name(), bean.getClothes_get(),
                        bean.getPrice_dj(), bean.getSize_name(), bean.getId(), 1, bean.getId()));
                Intent intent = new Intent(this, ShopCarOrderDetailsActivity.class);
                intent.putParcelableArrayListExtra("shoplist", array);
                startActivity(intent);
                break;
            case R.id.iv_web_share:
                String str = String.format("http://hmyc365.net/hmyc/file/app/app-clothes-info/index.html?token=82D5FBD40259C743ADDEF14D0E22F347&id=%s", bean.getId());
                Share.showShare1(this, str, bean.getClothes_name(), bean.getClothes_get());
                break;
            default:
                break;
        }
    }


    public void back(View view) {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        item_detailsActivity = null;
    }

}
