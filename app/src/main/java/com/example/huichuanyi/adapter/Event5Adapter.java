package com.example.huichuanyi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.huichuanyi.R;

/**
 * Created by Bob on 2016/8/10.
 */
public class Event5Adapter extends RecyclerView.Adapter<Event5Adapter.MyViewHolder> {
    private int[] mData;
    private Context mContext;
    private View.OnClickListener mListener;
    private View.OnLongClickListener mLongClickListener;

    public void setOnItemClickListener(View.OnClickListener listener){
        mListener = listener;
    }
    public void setOnLongClickListener(View.OnLongClickListener listener){
        mLongClickListener = listener;
    }
    public Event5Adapter(Context context, int[] data){
        mContext = context;
        mData = data;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_vertical_2,null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mImageView.setBackgroundResource(mData[position]);
        if (mListener!=null){
            holder.mImageView.setTag(position);
            holder.mImageView.setOnClickListener(mListener);
        }

        if (mLongClickListener!=null){
            holder.mImageView.setTag(position);
            holder.mImageView.setOnLongClickListener(mLongClickListener);
        }

        //holder.mTextView.setOnLongClickListener();
    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.length;
    }

    public static  class  MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_item_recycler);
        }
    }


}
