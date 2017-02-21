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


public class BtnChangeAdapter_3 extends RecyclerView.Adapter<BtnChangeAdapter_3.MyViewHolder> {
    private List<Label.CloTypeBean> mData;
    private Context mContext;
    private GetStyles mListener;
    public void setOnGet(GetStyles listener){
        mListener = listener;
    }
    private List<Boolean> isClicks;
    public BtnChangeAdapter_3(Context context, List<Label.CloTypeBean> data){
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
        final String cloType_name = mData.get(position).getCloType_name();
        holder.mTextView.setText(cloType_name);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.getSort(mData.get(position).getCloType_id());
                for(int i = 0; i <mData.size();i++){
                    isClicks.set(i,false);
                }
                isClicks.set(position,true);
                notifyDataSetChanged();
            }
        });
        if (mData!=null){
            if(isClicks.get(position)){
                holder.mTextView.setBackgroundResource(R.drawable.button_red);
                holder.mTextView.setTextColor(Color.parseColor("#ffffff"));
            }else{
                holder.mTextView.setBackgroundResource(R.drawable.background);
                holder.mTextView.setTextColor(Color.parseColor("#666666"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    public static  class  MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_tv_btn_change);
        }
    }

    public interface  GetStyles{
        void getSort(String string);
    }

}
