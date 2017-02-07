package com.example.huichuanyi.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.custom.RoundImageView;
import com.example.huichuanyi.modle.City;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PersonAdapter extends BaseAdapter{
    private List<City.BodyBean> mData;
    private Context mContext;
    public PersonAdapter(List<City.BodyBean> data, Context context){
        mData = data;
        mContext = context;
    }
    @Override
    public int getCount() {
        return mData==null?0:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mData.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mHolder;
        if(convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.order_person,null);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else{
            mHolder = (ViewHolder) convertView.getTag();
        }

        String name = mData.get(position).getGzs_name();
        if(!TextUtils.isEmpty(name)) {
            mHolder.mTextViewName.setText(name);
        }

        String introduction = mData.get(position).getGzs_jianjie();
        if(!TextUtils.isEmpty(introduction)) {
            mHolder.mTextViewIntroduction.setText(introduction);
        }
        String city = mData.get(position).getGzs_city();

        if(!TextUtils.isEmpty(city)) {
            mHolder.mTextViewCity.setText(city);
        }
       /* String state = mData.get(position).getState();
        if (TextUtils.equals("0",state)){
            mHolder.mTextViewOrder.setVisibility(View.VISIBLE);
        }*/
        String photo = mData.get(position).getGzs_photo();
        String service = mData.get(position).getGzs_fuwu();
        if(!TextUtils.equals("已开通",service)) {
            mHolder.mImageViewNews.setVisibility(View.GONE);
        }
        Log.i("TAG", "---------"+photo);
        if(!TextUtils.isEmpty(photo)&&photo.length()>5) {
            Picasso.with(mContext).load(photo).into(mHolder.mImageViewPhoto);
        }else{
            mHolder.mImageViewPhoto.setImageResource(R.mipmap.stand);
        }

        return convertView;
    }
    public static class ViewHolder{
        private RoundImageView mImageViewPhoto;
        private TextView mTextViewName,mTextViewIntroduction,mTextViewCity,mTextViewOrder;
        private ImageView mImageViewNews;

        public ViewHolder(View view){
            mImageViewPhoto = (RoundImageView) view.findViewById(R.id.iv_person_photo);
            mTextViewName = (TextView) view.findViewById(R.id.tv_person_name);
            mTextViewIntroduction = (TextView) view.findViewById(R.id.tv_person_introduction);
            mTextViewCity = (TextView) view.findViewById(R.id.tv_person_city);
            mImageViewNews = (ImageView) view.findViewById(R.id.iv_person_news);
            mTextViewOrder = (TextView) view.findViewById(R.id.tv_person_order);
        }
    }
}
