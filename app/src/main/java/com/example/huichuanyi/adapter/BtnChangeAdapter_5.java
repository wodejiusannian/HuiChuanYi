package com.example.huichuanyi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.Label;

import java.util.ArrayList;
import java.util.List;


public class BtnChangeAdapter_5 extends RecyclerView.Adapter<BtnChangeAdapter_5.MyViewHolder> {
    private List<Label.CloQualityBean> mData;
    private Context mContext;
    private GetMaterial mGetMaterial;
    public void setGetMaterial(GetMaterial getMaterial){
        mGetMaterial = getMaterial;
    }
    private List<Boolean> isClicks;
    public BtnChangeAdapter_5(Context context, List<Label.CloQualityBean> data){
        mContext = context;
        mData = data;
        isClicks = new ArrayList<>();

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        for (int i = 0; i <mData.size(); i++ ){
            isClicks.add(false);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_btn_change,null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String material = mData.get(position).getCloQuality_name();
        holder.mTextView.setText(material);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetMaterial.getMaterial(material);
                for(int i = 0; i <mData.size();i++){
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

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }
    public interface GetMaterial{
        void getMaterial(String material);
    }
    public static  class  MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_tv_btn_change);
        }
    }


}
