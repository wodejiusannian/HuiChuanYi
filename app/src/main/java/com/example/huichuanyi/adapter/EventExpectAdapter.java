package com.example.huichuanyi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huichuanyi.R;

/**
 * Created by Bob on 2016/8/10.
 */
public class EventExpectAdapter extends RecyclerView.Adapter<EventExpectAdapter.MyViewHolder> {
    private String[]  mData;
    private Context mContext;
    private View.OnClickListener mListener;
    public void setOnItemClickListener(View.OnClickListener listener){
        mListener = listener;
    }

    public EventExpectAdapter(Context context, String[] data){
        mContext = context;
        mData = data;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_vertical_expect,null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText(mData[position]);
        if (mListener!=null){
            holder.mTextView.setTag(position);
            holder.mTextView.setOnClickListener(mListener);
        }
    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.length;
    }

    public static  class  MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_expect_recycler);
        }
    }


}
