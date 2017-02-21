package com.example.huichuanyi.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.custom.RoundImageView;
import com.example.huichuanyi.bean.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PayVideoAdapter extends BaseAdapter{
    private List<Video.ListBean> mData;
    private Context mContext;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;
    public PayVideoAdapter(List<Video.ListBean> data, Context context,CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        mData = data;
        mContext = context;
        mOnCheckedChangeListener =onCheckedChangeListener;
    }

    @Override
    public int getCount() {
        return mData.size()==0?0:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_pay_video,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mCheckBox.setOnCheckedChangeListener(mOnCheckedChangeListener);
        String geticon = mData.get(position).getGeticon();
        if(!TextUtils.isEmpty(geticon)) {
            Picasso.with(mContext).load(geticon).into(holder.mRoundImageView);
        }
        Video.ListBean listBean = mData.get(position);
        holder.mCheckBox.setTag(position);
        //设置checkbox的选中状态
        holder.mCheckBox.setChecked(listBean.isChecked);
        holder.mTextView.setText(mData.get(position).getName());
        return convertView;
    }
    public static class ViewHolder{
        private CheckBox mCheckBox;
        private TextView mTextView;
        private RoundImageView mRoundImageView;
        public ViewHolder(View view){
            mCheckBox = (CheckBox) view.findViewById(R.id.cb_item_select);
            mTextView = (TextView) view.findViewById(R.id.tv_item_select_speech);
            mRoundImageView = (RoundImageView) view.findViewById(R.id.item_rv_photo_pay_video);
        }
    }
    public int getMoney(){
        int money=0;
        if(mData==null||mData.size()==0) {
            return 0;
        }else{
            for(int i = 0; i < mData.size(); i++) {
                Video.ListBean listBean = mData.get(i);
                if(listBean.isChecked) {
                    String money1 = listBean.getMoney();
                    int anInt = Integer.parseInt(money1);
                    money+=anInt;
                }
            }
        }
        return money;
    }
}
