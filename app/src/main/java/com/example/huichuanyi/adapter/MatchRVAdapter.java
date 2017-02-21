package com.example.huichuanyi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.MyClothess;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MatchRVAdapter extends RecyclerView.Adapter<MatchRVAdapter.MyViewHolder> {
    private List<MyClothess.BodyBean.ClothesInfoBean> mData;
    private Context mContext;
    private View.OnClickListener mListener;

    public void setOnItemClickListener(View.OnClickListener listener){
        mListener = listener;
    }

    public MatchRVAdapter(Context context, List<MyClothess.BodyBean.ClothesInfoBean> data){
        mContext = context;
        mData = data;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.match_rv_item,null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String getphoto = mData.get(position).getClothes_pic();
        Picasso.with(mContext).load(getphoto).into(holder.mImageView);
        if (mListener!=null){
            holder.mImageView.setTag(position);
            holder.mImageView.setOnClickListener(mListener);
        }



    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    public static  class  MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_add_match_item);
        }
    }


}
