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


public class BtnChangeAdapter7 extends RecyclerView.Adapter<BtnChangeAdapter7.MyViewHolder> {
    private String[] mData;
    private Context mContext;
    private GetSort mGetSort;
    public void setGetSort(GetSort getSort){
        mGetSort = getSort;
    }
    private List<Boolean> isClicks;
    public BtnChangeAdapter7(Context context, String[] data){
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
        final String sort= mData[position];
        holder.mTextView.setText(sort);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetSort.getSort((position+1)+"",position);
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

    @Override
    public int getItemCount() {
        return mData==null?0:mData.length;
    }

    public interface GetSort{
        void getSort(String occ,int pos);
    }

    public static  class  MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_tv_btn_change);
        }
    }

}
