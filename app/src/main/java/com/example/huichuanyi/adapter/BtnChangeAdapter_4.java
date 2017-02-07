package com.example.huichuanyi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.modle.Label;

import java.util.ArrayList;
import java.util.List;


public class BtnChangeAdapter_4 extends RecyclerView.Adapter<BtnChangeAdapter_4.MyViewHolder> {
    private List<Label.CloStyleBean> mData;
    private Context mContext;
    private GetStylesssss mGetStyle;
    public void setGetStyle(GetStylesssss style){
        mGetStyle = style;
    }
    private List<Boolean> isClicks;
    public BtnChangeAdapter_4(Context context, List<Label.CloStyleBean> data){
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
        Label.CloStyleBean bean = mData.get(position);
        final String cloStyle_name =bean.getCloStyle_name();
        final String cloStyle_id =bean.getCloStyle_id();
        holder.mTextView.setText(cloStyle_name);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetStyle.getStylesssss(cloStyle_id,cloStyle_name,position);
                for(int i = 0; i <mData.size();i++){
                    isClicks.set(i,false);
                }
                isClicks.set(position,true);
                notifyDataSetChanged();
            }
        });
        if (mData!=null){
            if(isClicks.get(position)){
                holder.mTextView.setBackgroundResource(R.drawable.back_red_write);
                holder.mTextView.setTextColor(Color.parseColor("#ffffff"));
            }else{
                holder.mTextView.setBackgroundResource(R.drawable.back_black_writes);
                holder.mTextView.setTextColor(Color.parseColor("#ffffff"));
            }
        }
    }
    public interface GetStylesssss{
        void getStylesssss(String id,String name,int pos);
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


}
