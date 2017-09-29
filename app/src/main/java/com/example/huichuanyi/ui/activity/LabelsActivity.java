package com.example.huichuanyi.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.MyPicAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.bean.Pic;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.LabelPopupWindow;
import com.example.huichuanyi.utils.ImageUtils;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LabelsActivity extends BaseActivity implements View.OnClickListener, LabelPopupWindow.GetData {
    private ImageView mBack;
    private ListView mPhoto;
    private List<String> mPhotos;
    private static final int REQUEST_CAMERA_CODE = 11;
    private List<Pic> mData;
    private MyPicAdapter mAdapter;
    private LabelPopupWindow mPop;
    private String jsons, user_id;
    private int morePosition = -1;
    private TextView mShowPop, mUp;
    private String photoTime;
    private Callback.Cancelable post;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labels);
    }

    @Override
    public void initView() {
        SharedPreferences hqSysCloTag = getSharedPreferences("hqSysCloTag", 0);
        jsons = hqSysCloTag.getString("hqSysCloTag", "");
        getStyle("1");
        mPhoto = (ListView) findViewById(R.id.lv_labels_photos);
        mBack = (ImageView) findViewById(R.id.iv_labels_back);
        mPop = new LabelPopupWindow(this);
        mShowPop = (TextView) findViewById(R.id.tv_labels_show_pop);
        mUp = (TextView) findViewById(R.id.tv_labels_up_sure);
        mProgress = new ProgressDialog(this);
        invokeCamera();
    }

    @Override
    public void initData() {
        user_id = SharedPreferenceUtils.getUserData(this,1);
        photoTime = new Date().getTime() + "";
        mData = new ArrayList<>();
        mAdapter = new MyPicAdapter(this, mData, jsons);
    }

    @Override
    public void setData() {
        mPhoto.setAdapter(mAdapter);
    }

    @Override
    public void setListener() {
        mBack.setOnClickListener(this);
        mAdapter.setOnItemClick(this);
        mPop.setLabel(this);
        mUp.setOnClickListener(this);
    }


    public void invokeCamera() {
        PhotoPickerIntent intent = new PhotoPickerIntent(this);
        intent.setSelectModel(SelectModel.MULTI);
        intent.setShowCarema(true);
        intent.setMaxTotal(9);
        startActivityForResult(intent, REQUEST_CAMERA_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA_CODE:
                    mPhotos = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    for (int i = 0; i < mPhotos.size(); i++) {
                        Pic pic = new Pic();
                        pic.setPicPath(mPhotos.get(i));
                        List<String> style = getStyle("1");
                        List<String> styleId = getStyleId("1");
                        pic.setmListStyle(style);
                        pic.setmListStyleId(styleId);
                        mData.add(pic);
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_labels_back:
                finish();
                break;
            case R.id.tv_item_label_more:
                morePosition = (int) v.getTag();
                mPop.showPopupWindow(mShowPop);
                break;
            case R.id.iv_item_label_delete:
                int pos = (int) v.getTag();
                mData.remove(pos);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_labels_up_sure:
                mProgress.setMessage("正在上传中");
                mProgress.setCanceledOnTouchOutside(false);
                //转Json
                toJson();
                break;
            default:

                break;
        }
    }

    //更新下数据
    private List<String> getStyle(String str) {
        List<String> mStyles = new ArrayList<>();
        List<String> mStylesId = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(jsons);
            JSONObject body = object.getJSONObject("body");
            JSONArray cloStyle = body.getJSONArray("cloStyle");
            for (int i = 0; i < cloStyle.length(); i++) {
                JSONObject arr = cloStyle.getJSONObject(i);
                if (TextUtils.equals(str, arr.getString("cloType_id"))) {
                    mStyles.add(arr.getString("cloStyle_name"));
                    mStylesId.add(arr.getString("cloStyle_id"));
                }
            }
            return mStyles;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //更新下数据
    private List<String> getStyleId(String str) {
        List<String> mStylesId = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(jsons);
            JSONObject body = object.getJSONObject("body");
            JSONArray cloStyle = body.getJSONArray("cloStyle");
            for (int i = 0; i < cloStyle.length(); i++) {
                JSONObject arr = cloStyle.getJSONObject(i);
                if (TextUtils.equals(str, arr.getString("cloType_id"))) {
                    mStylesId.add(arr.getString("cloStyle_id"));
                }
            }
            return mStylesId;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void toJson() {
        JSONArray jsonArray = new JSONArray();
        JSONObject objects = new JSONObject();
        JSONObject object = null;
        try {
            objects.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int x = 0; x < mData.size(); x++) {
            Pic pic = mData.get(x);
            try {
                object = new JSONObject();
                String clothes_season = pic.getSeason();
                String clothes_situation = pic.getOcc();
                String clothes_styleId = pic.getClothes_styleId();
                String sort = pic.getSort();
                String time = pic.getTime();
                String price = pic.getPrice();
                String clothes_caizhi = pic.getMaterial();
                String size = pic.getSize();
                String brand = pic.getBrand();
                String location = pic.getLocation();
                if (!TextUtils.isEmpty(clothes_situation) && !TextUtils.isEmpty(sort) &&
                        !TextUtils.isEmpty(clothes_styleId)) {
                    object.put("clothes_styleId", clothes_styleId);
                    object.put("clothes_typeId", sort);
                    object.put("clothes_season", clothes_season);
                    object.put("clothes_situation", clothes_situation);
                    object.put("clothes_caizhi", clothes_caizhi);
                    object.put("clothes_buyTime", time);
                    object.put("clothes_price", price);
                    object.put("clothes_size", size);
                    object.put("clothes_brand", brand);
                    object.put("clothes_location", location);
                    object.put("clothes_imgName", photoTime + "_" + x + ".png");
                    jsonArray.put(object);
                    object = null;
                } else {
                    Toast.makeText(LabelsActivity.this, "请为每一件衣服选定标签", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            String s = jsonArray.toString();
            objects.put("body", jsonArray);
            mProgress.show();
            upPhoto(objects.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getData(String occ, String sort, String style, String season,
                        String material, int tagOcc, int tagSort, int tagStyle,
                        String time, String price, String size, String brand,
                        String location, String clothes_styleId) {
        if (TextUtils.isEmpty(occ)) {
            occ = "休闲";
        }
        if (TextUtils.isEmpty(sort)) {
            sort = "上衣";
        }
        if (TextUtils.isEmpty(style)) {
            season = "西服套装";
        }
        Pic mPic = new Pic();
        mPic.setOcc(occ);
        mPic.setSort(sort);
        mPic.setClothes_styleId(clothes_styleId);
        mPic.setTime(time);
        mPic.setPrice(price);
        mPic.setSize(size);
        mPic.setBrand(brand);
        mPic.setLocation(location);
        mPic.setCheckOcc(true);
        mPic.setTagOcc(tagOcc);
        mPic.setCheckSort(true);
        mPic.setTagSort(tagSort);
        mPic.setCheckStyle(true);
        mPic.setTagStyle(tagStyle);
        mPic.setStyle(style);
        mPic.setSeason(season);
        mPic.setMaterial(material);
        mPic.setmListStyle(getStyle((tagSort + 1) + ""));
        mData.remove(morePosition);
        mData.add(morePosition, mPic);
        mAdapter.notifyDataSetChanged();
    }

    //上传衣服
    public void upPhoto(String jsons) {
        RequestParams params = new RequestParams(NetConfig.LABEL_PATH_UP);
        params.addBodyParameter("data", jsons);
        if (mPhotos.size() == 0) {
            return;
        }
        for (int i = 0; i < mPhotos.size(); i++) {
            Bitmap ratio = ImageUtils.getSmallBitmap(mPhotos.get(i));
            String s = ImageUtils.saveBitMapToFile(this, photoTime + "_" + i + ".png", ratio, true);
            params.addBodyParameter("clothes_imgs", new File(s));
        }

        post = x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                String condition = JsonUtils.parseJson(result);

                Toast.makeText(LabelsActivity.this, condition, Toast.LENGTH_SHORT).show();

                if (TextUtils.equals("上传成功", condition)) {
                    finish();
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
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (post != null && post.isCancelled()) {
            post.cancel();
        }
    }
}
