package com.example.huichuanyi.ui.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.MyViewPagerPicsAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.LabelPopupWindow;
import com.example.huichuanyi.custom.MyRelativeLayout;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.bean.MyClothess;
import com.example.huichuanyi.utils.MyJson;
import com.example.huichuanyi.utils.User;
import com.example.huichuanyi.utils.Utils_Data;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WDYCPicActivity extends BaseActivity implements View.OnClickListener, LabelPopupWindow.GetData, MySelfDialog.OnYesClickListener, AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    private ViewPager mPics;
    private List<ImageView> mImages;
    private MyViewPagerPicsAdapter mAdapter;
    private ImageView mBack,mDelete;
    private TextView mMove,mEdit;
    private LabelPopupWindow mPop;
    private AlertDialog dialog;
    private ToggleButton mPicInfo;
    private RelativeLayout mShow;
    private List<MyClothess.BodyBean.ClothesInfoBean> mList;
    private MyRelativeLayout mContent;
    private String yichuzhonglei;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
        systemBarTintManager.setStatusBarTintEnabled(true);
        systemBarTintManager.setNavigationBarTintEnabled(true);
        systemBarTintManager.setTintResource(R.color.black);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    @Override
    public void initView() {
        mBack = (ImageView) findViewById(R.id.iv_pic_back);
        mDelete = (ImageView) findViewById(R.id.iv_pic_picdelete);
        mMove = (TextView) findViewById(R.id.tv_pic_move);
        mEdit = (TextView) findViewById(R.id.tv_pic_edit);
        mPics = (ViewPager) findViewById(R.id.vp_pic_pics);
        mPop = new LabelPopupWindow(this);
        mPicInfo = (ToggleButton) findViewById(R.id.tg_pic_info);
        mShow = (RelativeLayout) findViewById(R.id.rl_pic_show);
        mContent = (MyRelativeLayout) findViewById(R.id.mr_pic_show_content);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mList = (List<MyClothess.BodyBean.ClothesInfoBean>)intent.getSerializableExtra("mList");
        int position = intent.getIntExtra("position", 0);
        yichuzhonglei = intent.getStringExtra("yichuzhonglei");
        mImages = new ArrayList<>();
        mAdapter = new MyViewPagerPicsAdapter(mImages);
        mPics.setAdapter(mAdapter);
        for (int i = 0;i < mList.size();i++){
            ImageView view = new ImageView(this);
            Picasso.with(this).load(mList.get(i).getClothes_pic()).into(view);
            mImages.add(view);
        }
        mAdapter.notifyDataSetChanged();
        mPics.setCurrentItem(position);
        contentAddView(position);
    }

    @Override
    public void setData() {

    }

    private void contentAddView(int pos) {
        mContent.removeAllViews();
        String kuanshi = mList.get(pos).getClothes_situation();
        String season = mList.get(pos).getClothes_season();
        String caizhi = mList.get(pos).getClothes_caizhi();
        String typeId = mList.get(pos).getClothes_typeId();
        String buyTime = mList.get(pos).getClothes_buyTime();
        String brand = mList.get(pos).getClothes_brand();
        String location = mList.get(pos).getClothes_location();
        String size = mList.get(pos).getClothes_size();
        String price = mList.get(pos).getClothes_price();
        if (!TextUtils.isEmpty(typeId)){
            TextView textViewType= new TextView(this);
            switch(typeId){
                case "1":
                    textViewType.setText("上衣");
                    break;
                case "2":
                    textViewType.setText("裤子");
                    break;
                case "3":
                    textViewType.setText("裙子");
                    break;
                case "4":
                    textViewType.setText("鞋");
                    break;
                case "5":
                    textViewType.setText("包");
                    break;
                case "6":
                    textViewType.setText("配饰");
                    break;
                case "7":
                    textViewType.setText("其它");
                    break;
                default:

                break;
            }
            textViewType.setTextColor(Color.WHITE);
            textViewType.setGravity(Gravity.CENTER);
            textViewType.setBackgroundResource(R.drawable.backgroundsss);
            mContent.addView(textViewType);
        }
        if (!TextUtils.isEmpty(caizhi)){
            TextView textViewCaiZhi= new TextView(this);
            textViewCaiZhi.setText(caizhi);
            textViewCaiZhi.setGravity(Gravity.CENTER);
            textViewCaiZhi.setBackgroundResource(R.drawable.backgroundsss);
            textViewCaiZhi.setTextColor(Color.WHITE);
            mContent.addView(textViewCaiZhi);
        }
        if (!TextUtils.isEmpty(season)){
            TextView textViewSeason = new TextView(this);
            textViewSeason.setText(season);
            textViewSeason.setGravity(Gravity.CENTER);
            textViewSeason.setBackgroundResource(R.drawable.backgroundsss);
            textViewSeason.setTextColor(Color.WHITE);
            mContent.addView(textViewSeason);
        }
        if (!TextUtils.isEmpty(buyTime)){
            TextView textViewBuyTime = new TextView(this);
            textViewBuyTime.setTextColor(Color.WHITE);
            textViewBuyTime.setGravity(Gravity.CENTER);
            textViewBuyTime.setText(buyTime);
            textViewBuyTime.setBackgroundResource(R.drawable.backgroundsss);
            mContent.addView(textViewBuyTime);

        }
        if (!TextUtils.isEmpty(brand)){
            TextView textViewBrand = new TextView(this);
            textViewBrand.setTextColor(Color.WHITE);
            textViewBrand.setGravity(Gravity.CENTER);
            textViewBrand.setText(brand);
            textViewBrand.setBackgroundResource(R.drawable.backgroundsss);
            mContent.addView(textViewBrand);

        }
        if (!TextUtils.isEmpty(location)){
            TextView textViewLocation = new TextView(this);
            textViewLocation.setTextColor(Color.WHITE);
            textViewLocation.setGravity(Gravity.CENTER);
            textViewLocation.setText(location);
            textViewLocation.setBackgroundResource(R.drawable.backgroundsss);
            mContent.addView(textViewLocation);

        }
        if (!TextUtils.isEmpty(size)){
            TextView textViewSize = new TextView(this);
            textViewSize.setTextColor(Color.WHITE);
            textViewSize.setGravity(Gravity.CENTER);
            textViewSize.setText(size);
            textViewSize.setBackgroundResource(R.drawable.backgroundsss);
            mContent.addView(textViewSize);

        }
        if (!TextUtils.isEmpty(price)){
            TextView textViewPrice = new TextView(this);
            textViewPrice.setTextColor(Color.WHITE);
            textViewPrice.setGravity(Gravity.CENTER);
            textViewPrice.setText(price);
            textViewPrice.setBackgroundResource(R.drawable.backgroundsss);
            mContent.addView(textViewPrice);

        }
    }

    @Override
    public void setListener() {
        mBack.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mEdit.setOnClickListener(this);
        mMove.setOnClickListener(this);
        mPop.setLabel(this);
        mPicInfo.setOnCheckedChangeListener(this);
        mPics.setOnPageChangeListener(this);
    }

    //显示移动的dialog
    private void showMoveDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_self_move, null);
        //移动dialog数据的显示
        ListView mMove = (ListView) view.findViewById(R.id.lv_dialog_self_move);
        initMoveData(mMove);
        dialog = new AlertDialog.Builder(this).setView(view).create();
        dialog.show();
    }

    //初始化移动的dialog
    private void initMoveData(ListView mMove) {
        Log.i("TAG", "----------------"+yichuzhonglei);
        if (TextUtils.equals("11", yichuzhonglei)) {
            String[] move = {"出行衣橱","其他衣橱"};
            ArrayAdapter<String> moveAdapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,move);
            mMove.setAdapter(moveAdapter);
        }else{
            String[] move = {"出行衣橱","其他衣橱","家庭衣橱"};
            ArrayAdapter<String> moveAdapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,move);
            mMove.setAdapter(moveAdapter);
        }
        mMove.setOnItemClickListener(this);
    }

    //显示删除的dialog
    private void showDeleteDialog() {
        MySelfDialog mySelfDialog = new MySelfDialog(this);
        mySelfDialog.setOnYesListener("确定",this);
        mySelfDialog.setOnNoListener("取消",null);
        mySelfDialog.setMessage("亲，确认删除吗？");
        mySelfDialog.show();
    }

    //编辑后确定所做的事情
    @Override
    public void getData(String occ, String sort, String style, String season, String material, int tagSeason, int tagSort, int tagStyle, String time, String price, String size, String brand, String location, String styleId) {
        int currentItem = mPics.getCurrentItem();
        Map<String,String> map = new HashMap<>();
        map.put("user_id",new User(this).getUseId()+"");
        map.put("clothes_id",mList.get(currentItem).getClothes_id());
        map.put("clothes_situation",occ);
        map.put("clothes_typeId",tagSort+"");
        map.put("clothes_styleId",tagStyle+"");
        map.put("clothes_season",season);
        map.put("clothes_caizhi",material);
        map.put("clothes_buyTime",time);
        map.put("clothes_price",price);
        map.put("clothes_size",size);
        map.put("clothes_brand",brand);
        map.put("clothes_location",location);
        String dataObject = Utils_Data.getDataObject(map);
        RequestParams params = new RequestParams(NetConfig.UPDATE_CLO);
        params.addBodyParameter("data",dataObject);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                String ret = MyJson.getRet(result);
                if (TextUtils.equals("0",ret)){
                    Toast.makeText(WDYCPicActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(WDYCPicActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
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

    //确认删除所做事件
    @Override
    public void onClick() {
        final int currentItem = mPics.getCurrentItem();
        RequestParams params = new RequestParams(NetConfig.DELETE_PIC);
        params.addBodyParameter("cloid",mList.get(currentItem).getClothes_id());
        params.addBodyParameter("move","0");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(!TextUtils.equals("result","1")) {
                    Toast.makeText(WDYCPicActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    mImages.remove(currentItem);
                    mList.remove(currentItem);
                    mAdapter.notifyDataSetChanged();
                    sendBroad();
                    if (mImages.size()==0){
                        finish();
                    }
                    int currentItem1 = mPics.getCurrentItem();
                    contentAddView(currentItem1);
                }else{
                    Toast.makeText(WDYCPicActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
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

    //这个是移动到那个衣橱需要做的事情
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int pos = -1;
        if (position==0){
            pos = 2;
        }
        if (position == 1){
            pos = 4;
        }
        if (position == 2){
            pos = 1;
        }
        final int currentItem = mPics.getCurrentItem();
        RequestParams params = new RequestParams(NetConfig.MOVE_PIC);
        params.addBodyParameter("cloid",mList.get(currentItem).getClothes_id());
        params.addBodyParameter("wardrobe_id",pos+"");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(TextUtils.equals(result,"1")) {
                    Toast.makeText(WDYCPicActivity.this, "移动成功", Toast.LENGTH_SHORT).show();
                    mImages.remove(currentItem);
                    mList.remove(currentItem);
                    mAdapter.notifyDataSetChanged();
                    sendBroad();
                    if (mImages.size()==0){
                        finish();
                    }
                    int currentItem1 = mPics.getCurrentItem();
                    contentAddView(currentItem1);

                }else{
                    Toast.makeText(WDYCPicActivity.this, "移动失败", Toast.LENGTH_SHORT).show();
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
        dialog.dismiss();
    }

    //其它按钮做所得事情
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_pic_back:
                finish();
                break;
            case R.id.iv_pic_picdelete:
                //弹出删除的dialog
                showDeleteDialog();
                break;
            case R.id.tv_pic_move:
                //弹出移动的dialog
                showMoveDialog();
                break;
            case R.id.tv_pic_edit:
                mPop.showPopupWindow(mDelete);
                break;
            default:

                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            ObjectAnimator animator = ObjectAnimator.ofFloat(mShow, "translationY", 0, -400);
            animator.setDuration(200);
            animator.start();
        }else{
            ObjectAnimator animator = ObjectAnimator.ofFloat(mShow, "translationY",0,0);
            animator.setDuration(200);
            animator.start();
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mContent.removeAllViews();
        contentAddView(position);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public void sendBroad(){
        Intent intent = new Intent();
        intent.setAction("action.refreshFriend");
        sendBroadcast(intent);
    }

}
