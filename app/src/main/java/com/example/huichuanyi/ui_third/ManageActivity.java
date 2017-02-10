package com.example.huichuanyi.ui_third;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.CommentAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MyListView;
import com.example.huichuanyi.custom.RoundImageView;
import com.example.huichuanyi.modle.Comment;
import com.example.huichuanyi.utils.ActivityUtils;
import com.squareup.picasso.Picasso;

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

public class ManageActivity extends BaseActivity implements View.OnClickListener {
    private Button mButtonOrder;
    private TextView mTextViewManageName, mTextViewIntroduction;
    private ImageView mImageViewBack;
    private RoundImageView mImageViewPhoto;
    private List<Comment> mData;
    private MyListView mListView;
    private CommentAdapter mAdapter;
    private String price1, price2, price_baseNum1, price_baseNum2, price_raiseNum, price_raisePrice;
    private String managerid, managerName, managerPhone, managerPhoto, city;
    private RatingBar mRatingBar;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            managerName = bundle.getString("name");
            managerPhone = bundle.getString("phone");
            managerPhoto = bundle.getString("photo");
            String star = bundle.getString("star");
            if (!TextUtils.isEmpty(star)) {
                Float aFloat = Float.valueOf(star);
                mRatingBar.setRating(aFloat);
            }
            String introduction = bundle.getString("introduction");
            if (!TextUtils.isEmpty(managerName)) {
                mTextViewManageName.setText(managerName);
            }
            if (!TextUtils.isEmpty(managerPhoto) && !TextUtils.equals("null", managerPhoto)) {
                Picasso.with(ManageActivity.this).load(managerPhoto).into(mImageViewPhoto);
            }
            if (!TextUtils.isEmpty(introduction) && !TextUtils.equals("null", introduction)) {
                mTextViewIntroduction.setText(introduction);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

    }

    @Override
    public void initView() {
        mButtonOrder = (Button) findViewById(R.id.bt_manage_order);
        mImageViewBack = (ImageView) findViewById(R.id.iv_manage_back);
        mListView = (MyListView) findViewById(R.id.lv_manage_show);
        mTextViewManageName = (TextView) findViewById(R.id.tv_manage_name);
        mImageViewPhoto = (RoundImageView) findViewById(R.id.iv_manage_photo);
        mTextViewIntroduction = (TextView) findViewById(R.id.tv_datata_one);
        mRatingBar = (RatingBar) findViewById(R.id.rb_manage_star);
    }

    @Override
    public void initData() {
        mData = new ArrayList<>();
        mAdapter = new CommentAdapter(mData, this);
        Intent intent = getIntent();
        managerid = intent.getStringExtra("managerid");
        city = intent.getStringExtra("city");
        price1 = intent.getStringExtra("price1");
        price2 = intent.getStringExtra("price2");
        price_baseNum1 = intent.getStringExtra("price_baseNum1");
        price_baseNum2 = intent.getStringExtra("price_baseNum2");
        price_raiseNum = intent.getStringExtra("price_raiseNum");
        price_raisePrice = intent.getStringExtra("price_raisePrice");
        RequestParams params = new RequestParams(NetConfig.MANAGER_URL);
        params.addBodyParameter("manager_id", managerid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    if ("0".equals(result) || "-1".equals(result)) {
                        Toast.makeText(ManageActivity.this, "网络错误，请重试", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JSONObject json = new JSONObject(result);
                    Message mMessage = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", json.getString("name"));
                    bundle.putString("phone", json.getString("phone"));
                    bundle.putString("photo", json.getString("photo"));
                    bundle.putString("introduction", json.getString("introduction"));
                    bundle.putString("star", json.getString("alleval"));
                    mMessage.setData(bundle);
                    mHandler.sendMessage(mMessage);
                    JSONArray evaluates = json.getJSONArray("evaluates");
                    for (int i = 0; i < evaluates.length(); i++) {
                        JSONObject object = evaluates.getJSONObject(i);
                        Comment comment = new Comment();
                        comment.setContent(object.getString("content"));
                        comment.setUser_name(object.getString("user_name"));
                        mData.add(comment);
                    }
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //Toast.makeText(ManageActivity.this, R.string.err_internet, Toast.LENGTH_SHORT).show();
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
    public void setData() {
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void setListener() {
        mButtonOrder.setOnClickListener(this);
        mImageViewBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_manage_order:
                Map<String, Object> map = new HashMap<>();
                map.put("managerid", managerid);
                map.put("managename", managerName + "");
                map.put("managerPhone", managerPhone);
                map.put("managerPhoto", managerPhoto);
                map.put("city", city);
                map.put("price1", price1);
                map.put("price2", price2);
                map.put("price_baseNum1", price_baseNum1);
                map.put("price_baseNum2", price_baseNum2);
                map.put("price_raiseNum", price_raiseNum);
                map.put("price_raisePrice", price_raisePrice);
                ActivityUtils.switchTo(this, OrderDetailsActivity.class, map);
                finish();
                break;
            case R.id.iv_manage_back:
                finish();
                break;
        }
    }
}
