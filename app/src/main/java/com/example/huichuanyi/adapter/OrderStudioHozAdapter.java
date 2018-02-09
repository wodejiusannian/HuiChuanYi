package com.example.huichuanyi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.model.OrderStudioHoz;
import com.example.huichuanyi.custom.RoundImageView;

import java.util.List;

/**
 * Created by Bob on 2016/8/10.
 */
public class OrderStudioHozAdapter extends RecyclerView.Adapter<OrderStudioHozAdapter.MyViewHolder> {
    private List<OrderStudioHoz> mData;
    private Context mContext;
    private View.OnClickListener mListener;

    public void setOnItemClickListener(View.OnClickListener listener) {
        mListener = listener;
    }

    public OrderStudioHozAdapter(Context context, List<OrderStudioHoz> data) {
        mContext = context;
        mData = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_sutdio_hoz, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OrderStudioHoz orderStudioHoz = mData.get(position);
        Glide.with(mContext).load(orderStudioHoz.piv).into(holder.mIv);
        if (mListener != null) {
            holder.hahahah.setTag(position);
            holder.hahahah.setOnClickListener(mListener);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private RoundImageView mIv;

        private LinearLayout hahahah;

        public MyViewHolder(View itemView) {
            super(itemView);
            mIv = (RoundImageView) itemView.findViewById(R.id.item_iv_order_studio_hoz);
            hahahah = (LinearLayout) itemView.findViewById(R.id.ll_item_hahhaha);
        }
    }


}
