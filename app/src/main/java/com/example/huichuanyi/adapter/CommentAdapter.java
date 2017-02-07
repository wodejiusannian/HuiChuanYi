package com.example.huichuanyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.modle.Comment;

import java.util.List;

public class CommentAdapter extends BaseAdapter {
    private List<Comment> mData;
    private Context mContext;

    public CommentAdapter(List<Comment> data, Context context) {
        mData = data;
        mContext = context;
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
        ViewHolder mHolder = null;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.commenet_item,null);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else{
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.mTextViewComment.setText(mData.get(position).getContent());
        mHolder.mTextViewUserId.setText(mData.get(position).getUser_name());
        return convertView;
    }
    public static class ViewHolder{

            private TextView mTextViewUserId,mTextViewComment;
            public ViewHolder(View view){
                mTextViewUserId = (TextView) view.findViewById(R.id.tv_command_item_username);
                mTextViewComment = (TextView) view.findViewById(R.id.tv_command_item_command);
        }
    }
}
