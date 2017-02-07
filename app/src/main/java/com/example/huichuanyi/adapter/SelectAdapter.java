package com.example.huichuanyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.huichuanyi.R;

/**
 * Created by 小五 on 2017/1/9.
 */
public class SelectAdapter extends BaseAdapter{
    private String[] mData;
    private Context mContext;

    public SelectAdapter(String[] mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.length==0?0:mData.length;
    }

    @Override
    public Object getItem(int position) {
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_btn_change,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mSelect.setText(mData[position]);
        return convertView;
    }

    public static class ViewHolder{
        private TextView mSelect;
        public ViewHolder(View view) {
            mSelect = (TextView) view.findViewById(R.id.item_tv_btn_change);
        }
    }
}
