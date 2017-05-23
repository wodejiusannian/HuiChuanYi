package com.example.huichuanyi.ui.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.huichuanyi.R;

/**
 * Created by 小五 on 2017/4/24.
 */

public class HomeCollocationRvAdapter extends RecyclerView.Adapter<HomeCollocationRvAdapter.MyViewHolder> {

    private int[] mData;
    private Context mContext;
    private View.OnClickListener mListener;

    public void setOnItemClickListener(View.OnClickListener listener) {
        mListener = listener;
    }


    public HomeCollocationRvAdapter(Context context, int[] data) {
        mContext = context;
        mData = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_collocation, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mImageView.setBackgroundResource(mData[position]);
        if (mListener != null) {
            holder.mImageView.setTag(position);
            holder.mImageView.setOnClickListener(mListener);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_collocation);
        }
    }
}
