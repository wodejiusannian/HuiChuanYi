package com.example.huichuanyi.ui.activity.register;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.GetCity;
import com.example.huichuanyi.utils.CommonUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class RegisterOverCompleteInfoActivity extends AppCompatActivity implements GetCity.WillGetCity {
    @ViewInject(R.id.rg_boy_girl)
    private RadioGroup girlOrBoy;
    @ViewInject(R.id.click_get_location)
    private TextView getLocation;

    private GetCity mGetCity;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String location = data.getString("location");
            getLocation.setText(location);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_complete_info);
        x.view().inject(this);
        initData();
        setListener();
    }

    private void initData() {
        mGetCity = new GetCity(getApplicationContext());
        mGetCity.startLocation();
        mGetCity.setGetCity(this);
    }

    private void setListener() {
        girlOrBoy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_girl:
                        Toast.makeText(RegisterOverCompleteInfoActivity.this, "女的", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rb_boy:
                        Toast.makeText(RegisterOverCompleteInfoActivity.this, "男的", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void getWillGetCity(String city, String lat, String lng) {
        if (!CommonUtils.isEmpty(city)) {
            Bundle bundle = new Bundle();
            bundle.putString("location", city);
            Message message = Message.obtain();
            message.setData(bundle);
            mHandler.sendMessage(message);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGetCity.stopLocation();
    }
}
