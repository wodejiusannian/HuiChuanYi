package com.example.huichuanyi.ui.activity.lanyang;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.ui.base.BaseActivity;

import butterknife.BindView;

public class LyOpenRTCActivity extends BaseActivity {
    @BindView(R.id.wb_activity_rtc_content)
    WebView webContent;

    @BindView(R.id.tv_activity_rtc_title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ly_open_rtc);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        String url = getIntent().getStringExtra("url");
        loadingUrl(url);
    }

    @Override
    protected void setData() {

    }

    private void loadingUrl(String url) {
        webContent.loadUrl(url);
    }

    public void back(View view) {
        finish();
    }
}
