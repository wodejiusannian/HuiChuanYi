package com.example.huichuanyi.ui.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.example.huichuanyi.R;

import org.xutils.view.annotation.Event;
import org.xutils.x;

public class RegisterOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_over_register);
        x.view().inject(this);
    }

    @Event(R.id.tv_over_info)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_over_info:
                startActivity(new Intent(this, RegisterOverCompleteInfoActivity.class));
                break;
            default:
                break;
        }
    }

    public void back(View view) {
        finish();
    }
}
