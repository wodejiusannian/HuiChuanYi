package com.example.huichuanyi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huichuanyi.R;

import java.util.ArrayList;
import java.util.List;


public class BtnChangeAdapter_2 extends RecyclerView.Adapter<BtnChangeAdapter_2.MyViewHolder> {
    private String[] mData;
    private Context mContext;
    private GetSeason mGetSeason;
    private View.OnClickListener mClick;
    public void setGetSeason(GetSeason getSeason){
        mGetSeason = getSeason;
    }
    private List<Boolean> isClicks;
    public BtnChangeAdapter_2(Context context, String[] data){
        mContext = context;
        mData = data;
        isClicks = new ArrayList<>();
        for (int i = 0; i <mData.length; i++ ){
            isClicks.add(false);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_btn_change,null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String season = mData[position];
        holder.mTextView.setText(season);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetSeason.getSeason(season);
                for(int i = 0; i <mData.length;i++){
                    isClicks.set(i,false);
                }
                isClicks.set(position,true);
                notifyDataSetChanged();
            }
        });
        if(isClicks.get(position)){
            holder.mTextView.setBackgroundResource(R.drawable.back_red_write);
            holder.mTextView.setTextColor(Color.parseColor("#ffffff"));
        }else{
            holder.mTextView.setBackgroundResource(R.drawable.back_black_writes);
            holder.mTextView.setTextColor(Color.parseColor("#ffffff"));
        }
    }
    public interface GetSeason{
        void getSeason(String season);
    }
    @Override
    public int getItemCount() {
        return mData==null?0:mData.length;
    }
    public static  class  MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_tv_btn_change);
        }
    }


}
