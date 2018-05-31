package com.example.huichuanyi.ui.newpage;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.ActivityCacheUtils;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.WebViewUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class HMURL2Activity extends BaseActivity {

    @BindView(R.id.pb_homemeauser_loading)
    ProgressBar loading;

    @BindView(R.id.wb_homemeasure_show)
    WebView show;

    @BindView(R.id.title)
    TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_measure2);
        ActivityCacheUtils.addActivity(this);
    }

    public void back(View view) {
        finish();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        mTitle.setText(title);
        WebViewUtils utils = new WebViewUtils(new WebViewUtils.WebOnResult() {
            @Override
            public void onResultProgress(int progress) {
                if (progress == 100) {
                    loading.setVisibility(View.GONE);
                } else {
                    loading.setVisibility(View.VISIBLE);
                    loading.setProgress(progress);
                }
            }

            @Override
            public void onResultTitle(String title) {
            }

            @Override
            public void onResultUrl(String url, String u) {

            }
        });
        utils.LoadingUrl(show, url);
    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.tv_order_sure})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_order_sure:
                ActivityUtils.switchTo(this, OrderStudioListActivity.class);
                break;
            default:
                break;
        }
    }
}
