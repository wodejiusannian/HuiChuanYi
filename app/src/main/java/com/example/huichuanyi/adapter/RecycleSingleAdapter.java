package com.example.huichuanyi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.ui.modle.OrderGoDoorPrice;

import java.util.List;

/**
 * Created by Bob on 2016/8/10.
 */
public class RecycleSingleAdapter extends RecyclerView.Adapter<RecycleSingleAdapter.MyViewHolder> {
    private List<OrderGoDoorPrice> mData;

    private Context mContext;

    private boolean[] booleens;

    private ItemData mListener;

    public void setOnItemGetData(ItemData itemData) {
        mListener = itemData;
    }

    public RecycleSingleAdapter(Context context, List<OrderGoDoorPrice> data) {
        mContext = context;
        mData = data;
        booleens = new boolean[mData.size()];
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_vertical_expect, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        OrderGoDoorPrice orderGoDoorPrice = mData.get(position);
        holder.mTextView.setText(orderGoDoorPrice.priceRange);
        if (booleens.length > 0 && booleens[position]) {
            holder.mTextView.setTextColor(Color.parseColor("#ffffff"));
            holder.mTextView.setBackgroundResource(R.drawable.shape_red_4dp);
        } else {
            holder.mTextView.setTextColor(Color.parseColor("#000000"));
            holder.mTextView.setBackgroundResource(R.drawable.shape_black_4dp);
        }
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.getItemData(mData.get(position));
                for (int i = 0; i < mData.size(); i++) {
                    booleens[i] = false;
                }
                booleens[position] = true;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_expect_recycler);
        }
    }

    public interface ItemData {
        void getItemData(OrderGoDoorPrice str);
    }
}
