package com.example.huichuanyi.ui_third;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.utils.ActivityUtils;

public class Item_DetailsActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout mJump;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

    }

    @Override
    public void initView() {
        mJump = (RelativeLayout) findViewById(R.id.rl_item_details_select);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        mJump.setOnClickListener(this);
    }

    public void back(View view){
        finish();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rl_item_details_select:
                ActivityUtils.switchTo(this,Write_OrderActivity.class);
                break;
           /* case R.id.pop_btn_buy_365_sure:
                ActivityUtils.switchTo(this,My_AddressActivity.class);
                break;*/
            default:
                break;
        }
    }



    /*rivate void showPopWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_buy_365, null);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setAnimationStyle(R.style.AnimationPreview);
        // 在底部显示
        window.showAtLocation(mJump,Gravity.BOTTOM, 0, 0);
        view.findViewById(R.id.pop_btn_buy_365_sure).setOnClickListener(this);
    }*/


}
