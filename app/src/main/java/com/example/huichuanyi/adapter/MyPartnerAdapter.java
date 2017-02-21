package com.example.huichuanyi.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.CardItem;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by 小五 on 2017/1/18.
 */
public class MyPartnerAdapter extends PagerAdapter {

    private List<CardItem> mData;
    private View.OnClickListener mOnclick;

    public void setOnItemClickListener(View.OnClickListener onClick) {
        mOnclick = onClick;
    }

    public MyPartnerAdapter(List<CardItem> data) {
        mData = data;
    }


    @Override
    public int getCount() {
        return mData.size() == 0 ? 0 : mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup Container, int position, Object object) {
        ((ViewPager) Container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_clothes_info, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        view.setTag(position);
        view.setOnClickListener(mOnclick);
        return view;
    }

    private void bind(CardItem item, View view) {
        SimpleDraweeView sv = (SimpleDraweeView) view.findViewById(R.id.sv_item_clothes);
        sv.setImageURI(item.getClothes_get());
        TextView style = (TextView) view.findViewById(R.id.item_clothes_style);
        TextView name = (TextView) view.findViewById(R.id.item_clothes_name);
        TextView content = (TextView) view.findViewById(R.id.item_clothes_content);
        style.setText(item.getColor_name());
        content.setText(item.getIntroduction());
        name.setText(item.getColor_name());
    }

}
