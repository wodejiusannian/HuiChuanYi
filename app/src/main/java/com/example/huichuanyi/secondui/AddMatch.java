package com.example.huichuanyi.secondui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.MatchRVAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.RoundImageView;
import com.example.huichuanyi.utils.ItemDecoration;
import com.example.huichuanyi.bean.MyClothess;
import com.example.huichuanyi.utils.ImageUtils;
import com.example.huichuanyi.utils.MyJson;
import com.example.huichuanyi.utils.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class AddMatch extends BaseActivity implements View.OnClickListener {
    private ImageView mImageViewBack, mImageViewTest, mImageViewCamera;
    private RoundImageView mImageViewFirst,mImageViewSecond,mImageViewThird;
    private LinearLayout mLinearLayout;
    private PopupWindow pop;
    private RecyclerView mRecyclerView;
    private MatchRVAdapter mAdapter;
    private List<MyClothess.BodyBean.ClothesInfoBean> mData;
    private int i = -1;
    private Button mButtonSure;
    private List<String> mList;
    private boolean flag = true;
    private String type,user_id;
    private EditText mEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);

    }

    @Override
    public void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.iv_addmatch_back);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_addmatch_pictures);
        mImageViewFirst = (RoundImageView) findViewById(R.id.iv_add_match_first);
        mImageViewSecond = (RoundImageView) findViewById(R.id.iv_add_match_second);
        mImageViewThird = (RoundImageView) findViewById(R.id.iv_add_match_third);
        mImageViewTest = (ImageView) findViewById(R.id.iv_test);
        mImageViewCamera = (ImageView) findViewById(R.id.iv_add_match_camera);
        mButtonSure = (Button) findViewById(R.id.btn_add_match_sure);
        mEditText = (EditText) findViewById(R.id.et_et_daipeixinde);
    }

    @Override
    public void initData() {
        user_id = new User(this).getUseId()+"";
        mList= new ArrayList<>();
        type = getIntent().getStringExtra("type");
    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        mImageViewBack.setOnClickListener(this);
        mImageViewFirst.setOnClickListener(this);
        mImageViewSecond.setOnClickListener(this);
        mImageViewThird.setOnClickListener(this);
        mImageViewCamera.setOnClickListener(this);
        mButtonSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_addmatch_back:
                finish();
                break;
            case R.id.iv_add_match_first:
                initPopoupWindow();
                i = 1;
                break;
            case R.id.iv_add_match_second:
                initPopoupWindow();
                i = 2;
                break;
            case R.id.iv_add_match_third:
                initPopoupWindow();
                i = 3;
                break;
            case R.id.iv_add_match_item:
                int p = (int) v.getTag();
                if (i == 1) {
                    Picasso.with(this).load(mData.get(p).getClothes_pic()).into(mImageViewFirst);
                    mImageViewFirst.setPivotX(mImageViewFirst.getWidth()/2);
                    mImageViewFirst.setPivotY(mImageViewFirst.getHeight()/2);
                    mImageViewFirst.setRotation(-40);
                } else if (i == 2) {
                    Picasso.with(this).load(mData.get(p).getClothes_pic()).into(mImageViewSecond);
                } else if (i == 3) {
                    Picasso.with(this).load(mData.get(p).getClothes_pic()).into(mImageViewThird);
                    mImageViewThird.setPivotX(mImageViewThird.getWidth()/2);
                    mImageViewThird.setPivotY(mImageViewThird.getHeight()/2);
                    mImageViewThird.setRotation(20);
                }
                pop.dismiss();
                break;
            case R.id.iv_add_match_camera:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_add_match_sure:
                if(flag) {
                    Bitmap bitMap = getBitMap(mLinearLayout);
                    String bitMap1 = ImageUtils.saveBitMapToFile(this, "bitMap", bitMap, true);
                    String trim = mEditText.getText().toString().trim();
                    mList.add(0,bitMap1);
                    ImageUtils.upPicture(this,new User(this).getUseId()+"",type,"0","0","家庭衣橱","办公",mList,"","","",trim+"");
                    flag = false;
                }else{
                    Toast.makeText(AddMatch.this, "亲，已经上传过了", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public Bitmap getBitMap(LinearLayout mLinear) {
        mLinear.setDrawingCacheEnabled(true);
        mLinear.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mLinear.layout(0, 0, mLinear.getMeasuredWidth(), mLinear.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(mLinear.getDrawingCache());
        mLinear.setDrawingCacheEnabled(false);
        return bitmap;
    }

    private void initPopoupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_match, null);
        initPopData(view);
        pop = new PopupWindow(view, GridLayoutManager.LayoutParams.MATCH_PARENT, GridLayoutManager.LayoutParams.MATCH_PARENT, false);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        if (pop.isShowing()) {
            pop.dismiss();
        } else {
            pop.showAsDropDown(mImageViewTest);
        }

    }

    private void initPopData(View view) {
        view.findViewById(R.id.iv_add_match_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rc_add_match_popup);
        mData = new ArrayList<>();
        RequestParams params = new RequestParams(NetConfig.CHA_KAN_YI_FU);
        params.addBodyParameter("user_id",user_id);
        params.addBodyParameter("wardrobe_id","");
        params.addBodyParameter("clothes_typeId","");
        params.addBodyParameter("clothes_situation","");
        params.addBodyParameter("clothes_styleId","");
        params.addBodyParameter("clothes_season","");
        params.addBodyParameter("clothes_caizhi","");
        params.addBodyParameter("clothes_move","1");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                String ret = MyJson.getRet(result);
                mData.clear();
                if (TextUtils.equals("0",ret)){
                    Gson gson = new Gson();
                    MyClothess myClothess = gson.fromJson(result, MyClothess.class);
                    mData.addAll(myClothess.getBody().getClothesInfo());
                }
                mAdapter.notifyDataSetChanged();
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
        });
        mAdapter = new MatchRVAdapter(this, mData);
        mAdapter.setOnItemClickListener(this);
        GridLayoutManager mManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new ItemDecoration(15));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bitmap myPhoto = (Bitmap) data.getExtras().get("data");
            String myP = ImageUtils.saveBitMapToFile(this, "myP", myPhoto, true);
            Bitmap bitmap = BitmapFactory.decodeFile(myP);
            mImageViewCamera.setImageBitmap(bitmap);
            mList.add(myP);
        }
    }
}