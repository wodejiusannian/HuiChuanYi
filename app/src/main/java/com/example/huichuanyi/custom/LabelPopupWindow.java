package com.example.huichuanyi.custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.BtnChangeAdapter;
import com.example.huichuanyi.adapter.BtnChangeAdapter7;
import com.example.huichuanyi.adapter.BtnChangeAdapter_2;
import com.example.huichuanyi.adapter.BtnChangeAdapter_4;
import com.example.huichuanyi.adapter.BtnChangeAdapter_5;
import com.example.huichuanyi.utils.ItemDecoration;
import com.example.huichuanyi.bean.Label;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小五 on 2017/1/13.
 */
public class LabelPopupWindow extends PopupWindow implements View.OnClickListener, BtnChangeAdapter.GetOcc, BtnChangeAdapter_2.GetSeason, BtnChangeAdapter7.GetSort, BtnChangeAdapter_5.GetMaterial, BtnChangeAdapter_4.GetStylesssss {

    private Context mContext;
    private RecyclerView mOcc,mSeason,mSort,mMaterial,mStyle;
    private String[] seasons = {"休闲","商务","社交"};
    private String[] arr = {"春秋","夏","冬","四季"};
    private String[] sort = {"上衣","裤子","裙子","鞋子","包","配饰","家居服"};
    private EditText mTime,mPrice,mSize,mBrand,mLocation;
    private BtnChangeAdapter adapter;
    private ImageView mSure,mDismiss;
    private GetData mGetData;
    private String jsons,mStringOcc,mStringSort,mStringStyle,mStringSeason,mStringMaterial,clothes_styleId;
    private int tagOcc,tagStyle,tagSort;
    private BtnChangeAdapter_4 mAdapter4;
    private List<Label.CloStyleBean> mCloStyle;
    public void setLabel(GetData getData){
        mGetData = getData;
    }
    public LabelPopupWindow(Context context) {
        mContext = context;
        View contentView = LayoutInflater.from(context).inflate(R.layout.activity_lable, null);
        initPop(contentView);
        initView(contentView);
        initData();
        setData();
        setListener();
    }

    private void setData() {
        getStyle("1");
    }

    private void initData() {
        SharedPreferences hqSysCloTag = mContext.getSharedPreferences("hqSysCloTag", 0);
        jsons = hqSysCloTag.getString("hqSysCloTag", "");
        adapter = new BtnChangeAdapter(mContext,seasons);
        mCloStyle = new ArrayList<>();
        mAdapter4 = new BtnChangeAdapter_4(mContext,mCloStyle);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext,1, GridLayoutManager.HORIZONTAL,false);
        mOcc.setLayoutManager(mGridLayoutManager);
        mOcc.setAdapter(adapter);
        mOcc.addItemDecoration(new ItemDecoration(15));
        setSeason();
        setSort();
        setStyle();
        setMaterial();
    }

    private void setSort() {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext,1, GridLayoutManager.HORIZONTAL,false);
        BtnChangeAdapter7 mAdapter = new BtnChangeAdapter7(mContext,sort);
        mSort.setLayoutManager(mGridLayoutManager);
        mSort.setAdapter(mAdapter);
        mSort.addItemDecoration(new ItemDecoration(15));
        mAdapter.setGetSort(this);
    }


    private void setListener() {
        adapter.setGetOcc(this);
        mSure.setOnClickListener(this);
        mDismiss.setOnClickListener(this);
    }

    private void initView(View contentView) {
        mOcc = (RecyclerView) contentView.findViewById(R.id.rv_label_occ);
        mSeason = (RecyclerView) contentView.findViewById(R.id.rv_label_season);
        mSort = (RecyclerView) contentView.findViewById(R.id.rv_label_sort);
        mMaterial = (RecyclerView) contentView.findViewById(R.id.rv_label_material);
        mStyle = (RecyclerView) contentView.findViewById(R.id.rv_label_style);
        mSure= (ImageView) contentView.findViewById(R.id.iv_pop_up_sure);
        mDismiss = (ImageView) contentView.findViewById(R.id.iv_pop_dismiss);
        mTime = (EditText) contentView.findViewById(R.id.et_label_time);
        mPrice = (EditText) contentView.findViewById(R.id.et_label_price);
        mSize = (EditText) contentView.findViewById(R.id.et_label_size);
        mBrand = (EditText) contentView.findViewById(R.id.et_label_brand);
        mLocation = (EditText) contentView.findViewById(R.id.et_label_location);
    }

    private void setStyle() {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext,1, GridLayoutManager.HORIZONTAL,false);
        mStyle.setLayoutManager(mGridLayoutManager);
        mStyle.setAdapter(mAdapter4);
        mStyle.addItemDecoration(new ItemDecoration(15));
        mAdapter4.setGetStyle(this);
    }
    private void setSeason() {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext,1, GridLayoutManager.HORIZONTAL,false);
        BtnChangeAdapter_2 mAdapter = new BtnChangeAdapter_2(mContext,arr);
        mSeason.setLayoutManager(mGridLayoutManager);
        mSeason.setAdapter(mAdapter);
        mSeason.addItemDecoration(new ItemDecoration(15));
        mAdapter.setGetSeason(this);
    }
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAtLocation(parent, Gravity.CENTER, 0, 0);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_pop_season:
                break;
            case R.id.iv_pop_up_sure:
                String time = mTime.getText().toString().trim();
                String price = mPrice.getText().toString().trim();
                String size = mSize.getText().toString().trim();
                String brand = mBrand.getText().toString().trim();
                String location = mLocation.getText().toString().trim();
                mGetData.getData(mStringOcc,mStringSort,mStringStyle,
                        mStringSeason,mStringMaterial,tagOcc,tagSort,
                        tagStyle,time,price,size,brand,location,clothes_styleId);
                this.dismiss();
                break;
            case R.id.iv_pop_dismiss:
                dismiss();
                break;
            default:

                break;
        }
    }

    private void initPop(View contentView) {
        this.setContentView(contentView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);
    }

    @Override
    public void getOcc(String occ,int pos) {
        tagOcc = pos;
        mStringOcc = occ;
    }

    @Override
    public void getSeason(String season) {
        mStringSeason = season;
    }
    private void setMaterial() {
        List<Label.CloQualityBean> mM = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(jsons);
            JSONObject body = object.getJSONObject("body");
            JSONArray cloQuality = body.getJSONArray("cloQuality");
            for (int i = 0;i<cloQuality.length();i++){
                JSONObject object1 = cloQuality.getJSONObject(i);
                Label.CloQualityBean bean = new Label.CloQualityBean();
                bean.setCloQuality_name(object1.getString("cloQuality_name"));
                mM.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext,1, GridLayoutManager.HORIZONTAL,false);
        BtnChangeAdapter_5 mAdapter = new BtnChangeAdapter_5(mContext,mM);
        mMaterial.setLayoutManager(mGridLayoutManager);
        mMaterial.setAdapter(mAdapter);
        mMaterial.addItemDecoration(new ItemDecoration(15));
        mAdapter.setGetMaterial(this);
    }
    @Override
    public void getSort(String sort,int pos) {
        mStringSort = sort;
        tagSort = pos;
        getStyle(sort);
    }

    @Override
    public void getMaterial(String material) {
        mStringMaterial = material;
    }

    @Override
    public void getStylesssss(String id, String name,int pos) {
        clothes_styleId = id;
        tagStyle = pos;
        mStringStyle = name;
    }

    public interface GetData{
        void getData(String occ, String sort,String
                style,String season,String material,int tagSeason,
                int tagSort,int tagStyle,String time,String price,
                String size,String brand,String location,String styleId);
    }

    //更新下数据
    private void getStyle(String str){
        mCloStyle.clear();
        try {
            JSONObject object = new JSONObject(jsons);
            JSONObject body = object.getJSONObject("body");
            JSONArray cloStyle = body.getJSONArray("cloStyle");
            for (int i = 0;i<cloStyle.length();i++){
                JSONObject arr = cloStyle.getJSONObject(i);
                if (TextUtils.equals(str,arr.getString("cloType_id"))){
                    Label.CloStyleBean style = new Label.CloStyleBean();
                    style.setCloStyle_name(arr.getString("cloStyle_name"));
                    style.setCloStyle_id(arr.getString("cloStyle_id"));
                    mCloStyle.add(style);
                }
            }
            mAdapter4.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
