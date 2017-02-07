package com.example.huichuanyi.ui_second;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.MyPartnerAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.custom.BounceBackViewPager;
import com.example.huichuanyi.ui_third.Item_DetailsActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.DepthPageTransformer;

public class Partner_Clothes extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ImageView mBack;
    private BounceBackViewPager mBBP;
    private MyPartnerAdapter adapter;
    private TextView mPager;
    private Button mJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner__clothes);
    }

    @Override
    public void initView() {
        mBack = (ImageView) findViewById(R.id.iv_partner_clothes_back);
        mBBP = (BounceBackViewPager) findViewById(R.id.bb_partner_clothes_show);
        mPager = (TextView) findViewById(R.id.tv_partner_clothes_pager);
        mJump = (Button) findViewById(R.id.btn_partner_clothes_jump_add);
    }

    @Override
    public void initData() {
        adapter = new MyPartnerAdapter(this);
        mBBP.setOffscreenPageLimit(3);
        mBBP.setPageTransformer(false, new DepthPageTransformer());
        mBBP.setPageMargin(30);
        mBBP.setAdapter(adapter);
        mBBP.setOnPageChangeListener(this);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        mBack.setOnClickListener(this);
        mJump.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_partner_clothes_back:
                finish();
                break;
            case R.id.btn_partner_clothes_jump_add:
                ActivityUtils.switchTo(this,Item_DetailsActivity.class);
                break;
            default:
                int tag = (int) v.getTag();
                Toast.makeText(this, "tag = "+tag, Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mPager.setText((mBBP.getCurrentItem()+1)+"/10");
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
