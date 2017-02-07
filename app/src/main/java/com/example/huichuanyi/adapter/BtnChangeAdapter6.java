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


public class BtnChangeAdapter6 extends RecyclerView.Adapter<BtnChangeAdapter6.MyViewHolder> {
    private List<String> mData;
    private Context mContext;
    private GetOccc mGetOcc;
    public void setGetOcc(GetOccc getOcc){
        mGetOcc = getOcc;
    }
    private List<Boolean> isClicks;
    public BtnChangeAdapter6(Context context,  List<String> data){
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

        final String occ= mData.get(position);
        holder.mTextView.setText(occ);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetOcc.getOccc(occ);
                for(int i = 0; i <mData.size();i++){
                    isClicks.set(i,false);
                }
                isClicks.set(position,true);
                notifyDataSetChanged();
            }
        });
        if(isClicks.get(position)){
            holder.mTextView.setBackgroundResource(R.drawable.button_red);
            holder.mTextView.setTextColor(Color.parseColor("#ffffff"));
        }else{
            holder.mTextView.setBackgroundResource(R.drawable.background);
            holder.mTextView.setTextColor(Color.parseColor("#666666"));
        }
    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    public interface GetOccc{
        void getOccc(String occc);
    }

    public static  class  MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_tv_btn_change);
        }
    }


}
