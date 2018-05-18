package com.example.huichuanyi.ui.newpage;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.huichuanyi.R;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.WebViewUtils;

import butterknife.BindView;

public class HomeMeasureActivity extends BaseActivity {

    @BindView(R.id.pb_homemeauser_loading)
    ProgressBar loading;

    @BindView(R.id.wb_homemeasure_show)
    WebView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_measure);
    }

    public void back(View view) {
        finish();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        String url = "http://hmyc365.net/hmyc/file/app/app-liangti-code/index.html?token=%s&userPic=%s&userId=%s";
        url = String.format(url, NetConfig.TOKEN, SharedPreferenceUtils.getUserData(this, 3), SharedPreferenceUtils.getUserData(this, 1));
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
            public void onResultUrl(String url,String u) {

            }
        });
        utils.LoadingUrl(show, url);
    }

    @Override
    protected void setData() {

    }
}
