package com.example.huichuanyi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.CommentAdapter;
import com.example.huichuanyi.baidumap.Location;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.bean.City;
import com.example.huichuanyi.bean.Comment;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MyListView;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ManageActivity extends BaseActivity implements UtilsInternet.XCallBack {
    @ViewInject(R.id.bt_manage_order)
    private Button mButtonOrder;

    @ViewInject(R.id.lv_manage_show)
    private MyListView mListView;

    @ViewInject(R.id.tv_manage_name)
    private TextView mTextViewManageName;

    @ViewInject(R.id.tv_datata_one)
    private TextView mTextViewIntroduction;

    @ViewInject(R.id.rb_manage_star)
    private RatingBar mRatingBar;

    @ViewInject(R.id.iv_manage_photo)
    private SimpleDraweeView mImageViewPhoto;

    private UtilsInternet net = UtilsInternet.getInstance();

    private List<Comment> mData = new ArrayList<>();
    private Map<String, String> map = new HashMap<>();

    private City.BodyBean bodyBean;

    private CommentAdapter mAdapter;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String managerName = bundle.getString("name");
            String managerPhoto = bundle.getString("photo");
            String star = bundle.getString("star");
            String introduction = bundle.getString("introduction");
            if (!CommonUtils.isEmpty(star)) {
                Float aFloat = Float.valueOf(star);
                mRatingBar.setRating(aFloat);
            }
            if (!CommonUtils.isEmpty(managerName)) {
                mTextViewManageName.setText(managerName);
            }
            if (!CommonUtils.isEmpty(managerPhoto)) {
                mImageViewPhoto.setImageURI(managerPhoto);
            }
            if (!CommonUtils.isEmpty(introduction)) {
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
        if (mData == null)
            mData = new ArrayList<>();
        if (map == null)
            map = new HashMap<>();

        bodyBean = (City.BodyBean) getIntent().getSerializableExtra("bodyBean");
        String studioId = bodyBean.getId();
        map.put("manager_id", studioId);
        net.post(NetConfig.MANAGER_URL, map, this);
        if (TextUtils.equals("365", Location.mOrder_365)) {
            mButtonOrder.setText("立即购买365VIP");
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {
        mAdapter = new CommentAdapter(mData, this);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void setListener() {

    }


    @Override
    public void onResponse(String result) {
        try {
            if ("0".equals(result) || "-1".equals(result)) {
                Toast.makeText(ManageActivity.this, "网络错误，请重试", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject json = new JSONObject(result);
            Message mMessage = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString("name", json.getString("name"));
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

    @Event(R.id.bt_manage_order)
    private void onClick(View v) {
        if (v != null) {
            Intent intent = null;
            if (TextUtils.equals("365", Location.mOrder_365)) {
                intent = new Intent(this, Buy_365Activity.class);
            } else {
                intent = new Intent(this, GoDoorInfoActivity.class);
            }
            intent.putExtra("bodyBean", bodyBean);
            startActivity(intent);
        }
    }

    public void back(View view) {
        finish();
    }

}
