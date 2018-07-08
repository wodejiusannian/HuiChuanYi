package com.example.huichuanyi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.MyClothess;
import com.example.huichuanyi.custom.GlideRoundTransform;

import java.util.List;

/**
 * Created by Bob on 2016/8/10.
 */
public class MC_MyClothesAdapter2 extends RecyclerView.Adapter<MC_MyClothesAdapter2.MyViewHolder> {
    private List<MyClothess.BodyBean.ClothesInfoBean> mData;
    private Context mContext;
    private View.OnClickListener mListener;
    private View.OnLongClickListener mLongClickListener;

    public void setOnItemClickListener(View.OnClickListener listener) {
        mListener = listener;
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        mLongClickListener = listener;
    }

    public MC_MyClothesAdapter2(Context context, List<MyClothess.BodyBean.ClothesInfoBean> data) {
        mContext = context;
        mData = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mc_myclothes_item, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        String getphoto = mData.get(position).getClothes_pic();
        Glide.with(mContext).load(getphoto).bitmapTransform(new GlideRoundTransform(mContext)).into(holder.mImageView);
        if (mListener != null) {
            holder.test.setTag(position);
            holder.test.setOnClickListener(mListener);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private RelativeLayout test;
        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_item_recycler_3);
            test = (RelativeLayout) itemView.findViewById(R.id.rl_test);
        }
    }


}
