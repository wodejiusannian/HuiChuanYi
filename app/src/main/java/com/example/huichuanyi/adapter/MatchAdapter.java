package com.example.huichuanyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.custom.RoundImageView;
import com.example.huichuanyi.bean.Match;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MatchAdapter extends BaseAdapter{
    private List<Match.EvaluatesBean> mData;
    private Context mContext;
    public MatchAdapter(List<Match.EvaluatesBean> data, Context context){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.match_item,null);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else{
            mHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(mContext).load(mData.get(position).getGetlogopic()).into(mHolder.mImageViewPhoto);
        mHolder.mTextViewDay.setText("18");
        mHolder.mTextViewTime.setText(mData.get(position).getTime());
        return convertView;
    }
    public static class ViewHolder{
        private RoundImageView mImageViewPhoto;
        private TextView mTextViewDay,mTextViewTime;
        public ViewHolder(View view){
            mImageViewPhoto = (RoundImageView) view.findViewById(R.id.rv_match_item_photo);
            mTextViewDay = (TextView) view.findViewById(R.id.tv_match_item_day);
            mTextViewTime = (TextView) view.findViewById(R.id.tv_match_item_time);
        }
    }
}
