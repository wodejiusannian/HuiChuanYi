package com.example.huichuanyi.ui.activity.home;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.share.Share;
import com.example.huichuanyi.ui.activity.adapter.HomeCollocationRvAdapter;
import com.example.huichuanyi.ui.activity.adapter.HomeCollocationVpAdapter;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.ImageUtils;
import com.example.huichuanyi.utils.ItemDecoration;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import static com.example.huichuanyi.R.id.iv_collocation_save;

public class CollocationDiaryActivity extends BaseActivity {

    private static final String TAG = "CollocationDiaryActivity";

    @ViewInject(R.id.rv_collocation_jeans)
    private RecyclerView jeans;

    @ViewInject(R.id.ll_girl_boy)
    private LinearLayout girlOrBoy;

    @ViewInject(R.id.rv_collocation_jacket)
    private RecyclerView jacket;

    @ViewInject(R.id.vp_collocation_diary)
    private ViewPager vpMoban;


    @ViewInject(R.id.iv_collocation_jacket)
    private ImageView ivJacket;

    @ViewInject(R.id.iv_collocation_jeans)
    private ImageView ivJeans;


    @ViewInject(R.id.iv_collocation_save)
    private ImageView ivSave;

    private boolean leftHide = false;

    private boolean rightHide = false;

    private boolean canSave = false;

    private String sdPathShare;

    private HomeCollocationVpAdapter vpAdapter;
    private int[] jacketData = {R.mipmap.hm_diary_color1, R.mipmap.hm_diary_color2, R.mipmap.hm_diary_color3, R.mipmap.hm_diary_color4, R.mipmap.hm_diary_color5, R.mipmap.hm_diary_color6,
            R.mipmap.hm_diary_color7, R.mipmap.hm_diary_color8, R.mipmap.hm_diary_color9, R.mipmap.hm_diary_color10, R.mipmap.hm_diary_color11, R.mipmap.hm_diary_color12, R.mipmap.hm_diary_color13, R.mipmap.hm_diary_color14, R.mipmap.hm_diary_color15, R.mipmap.hm_diary_color16,
            R.mipmap.hm_diary_color17, R.mipmap.hm_diary_color18, R.mipmap.hm_diary_color19, R.mipmap.hm_diary_color20, R.mipmap.hm_diary_color21, R.mipmap.hm_diary_color22, R.mipmap.hm_diary_color23, R.mipmap.hm_diary_color24, R.mipmap.hm_diary_color25, R.mipmap.hm_diary_color26,
            R.mipmap.hm_diary_color27, R.mipmap.hm_diary_color28, R.mipmap.hm_diary_color29, R.mipmap.hm_diary_color30, R.mipmap.hm_diary_color31, R.mipmap.hm_diary_color32, R.mipmap.hm_diary_color33, R.mipmap.hm_diary_color34, R.mipmap.hm_diary_color35, R.mipmap.hm_diary_color36,
            R.mipmap.hm_diary_color37, R.mipmap.hm_diary_color38, R.mipmap.hm_diary_color39, R.mipmap.hm_diary_color40};
    private int[] jeansData;
    private int[] girls = {R.mipmap.girl_one, R.mipmap.girl_two, R.mipmap.girl_three, R.mipmap.girl_four};
    private int[] boys = {R.mipmap.man_one, R.mipmap.man_two};

    private String user_id;
    private List<Integer> mobans = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dismissLoading();
            sdPathShare = msg.getData().getString("sdPath");
            ivSave.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collocation_diary);
    }


    @Override
    public void setListener() {

    }

    /*
    * 初始化view
    * */
    @Override
    public void initView() {
        HomeCollocationRvAdapter jacketAdapter = new HomeCollocationRvAdapter(this, jacketData);
        GridLayoutManager jacketManager = new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false);
        jacket.setLayoutManager(jacketManager);
        jacket.setAdapter(jacketAdapter);
        jacket.addItemDecoration(new ItemDecoration(10));
        jeansData = reverseArray(jacketData);
        HomeCollocationRvAdapter jeansAdapter = new HomeCollocationRvAdapter(this, jeansData);
        GridLayoutManager jeansManager = new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false);
        jeansManager.setReverseLayout(true);
        jeans.setLayoutManager(jeansManager);
        jeans.setAdapter(jeansAdapter);
        jeans.addItemDecoration(new ItemDecoration(10));
        jacketAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSave.setVisibility(View.VISIBLE);
                int item = vpMoban.getCurrentItem();
                int color = (int) v.getTag();
                vpMoban.getChildAt(item).findViewById(R.id.mv_one).setBackgroundResource(jacketData[color]);
            }
        });
        jeansAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSave.setVisibility(View.VISIBLE);
                int item = vpMoban.getCurrentItem();
                int color = (int) v.getTag();
                vpMoban.getChildAt(item).findViewById(R.id.mv_two).setBackgroundResource(jeansData[color]);
            }
        });

        vpAdapter = new HomeCollocationVpAdapter(mobans);
        vpMoban.setAdapter(vpAdapter);
        vpMoban.setOffscreenPageLimit(4);
    }

    @Override
    public void initData() {
        user_id = SharedPreferenceUtils.getUserData(this,1);
    }

    @Override
    public void setData() {

    }

    @Event({R.id.iv_collocation_save, R.id.iv_collocation_jacket, R.id.iv_collocation_jeans, R.id.btn_boy, R.id.btn_girl, R.id.iv_collocation_share})
    private void onEvent(View v) {
        switch (v.getId()) {
            case iv_collocation_save:
                if (canSave) {
                    //保存图片并上传
                    save();
                } else {
                    Toast.makeText(this, "请搭配服饰", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_collocation_jacket:
                ivSave.setVisibility(View.VISIBLE);
                topTranSport(jacket);
                leftHide = !leftHide;
                break;
            case R.id.iv_collocation_jeans:
                ivSave.setVisibility(View.VISIBLE);
                bottomTranSport(jeans);
                rightHide = !rightHide;
                break;
            case R.id.btn_boy:
                canSave = true;
                girlOrBoy.setVisibility(View.GONE);
                for (int i = 0; i < boys.length; i++) {
                    mobans.add(boys[i]);
                }
                vpAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_girl:
                canSave = true;
                girlOrBoy.setVisibility(View.GONE);
                for (int i = 0; i < girls.length; i++) {
                    mobans.add(girls[i]);
                }
                vpAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_collocation_share:
                Log.e(TAG, "onEvent: " + sdPathShare);
                Share.sdCardShare(this, sdPathShare, "快乐生活，来自慧美，让衣橱管理走进千万家");
                break;
            default:
                break;
        }
    }

    /*
    * 反转数组
    * */
    private static int[] reverseArray(int[] Array) {
        int[] new_array = new int[Array.length];
        for (int i = 0; i < Array.length; i++) {
            // 反转后数组的第一个元素等于源数组的最后一个元素：
            new_array[i] = Array[Array.length - i - 1];
        }
        return new_array;
    }

    /*
    * 获取图片，保存上传
    * */
    private void save() {
        showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int item = vpMoban.getCurrentItem();
                LinearLayout mDiary = (LinearLayout) vpMoban.getChildAt(item).findViewById(R.id.ll_collocation_diary);
                Bitmap bit = getBitMap(mDiary);
                String sdPath = ImageUtils.saveBitMapToFileJPG(CollocationDiaryActivity.this, "name", bit, true);
                ImageUtils.upPicture(CollocationDiaryActivity.this, user_id, "0", "0", "0", "家庭衣橱", "办公", sdPath);
                if (!CommonUtils.isEmpty(sdPath)) {
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString("sdPath", sdPath);
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                }
            }
        }).start();
    }

    public Bitmap getBitMap(LinearLayout mLinear) {
        mLinear.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(mLinear.getDrawingCache());
        mLinear.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public void back(View view) {
        finish();
    }

    public void topTranSport(View view) {
        int measuredWidth = 0;
        if (view != null && ivJacket != null)
            measuredWidth = view.getWidth() + ivJacket.getWidth();

        if (!leftHide) {
            ObjectAnimator.ofFloat(view, "translationX", 0f, -measuredWidth).setDuration(500).start();
        } else {
            ObjectAnimator.ofFloat(view, "translationX", -measuredWidth, 0f).setDuration(500).start();
        }
    }

    public void bottomTranSport(View view) {
        int measuredWidth = 0;
        if (view != null && ivJeans != null)
            measuredWidth = view.getWidth() + ivJeans.getWidth();

        if (!rightHide) {
            ObjectAnimator.ofFloat(view, "translationX", 0, measuredWidth).setDuration(500).start();
        } else {
            ObjectAnimator.ofFloat(view, "translationX", measuredWidth, 0).setDuration(500).start();
        }
    }
}
