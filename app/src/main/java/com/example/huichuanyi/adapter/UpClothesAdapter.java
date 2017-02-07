package com.example.huichuanyi.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huichuanyi.R;
import com.example.huichuanyi.custom.RoundImageView;
import com.example.huichuanyi.utils.ImageUtils;

import java.util.List;

/**
 * Created by Bob on 2016/8/10.
 */
public class UpClothesAdapter extends RecyclerView.Adapter<UpClothesAdapter.MyViewHolder> {
    private List<String> mData;
    private Context mContext;
    private View.OnClickListener mListener;
    private View.OnLongClickListener mLongClickListener;

    public void setOnItemClickListener(View.OnClickListener listener){
        mListener = listener;
    }

    public void setOnLongClickListener(View.OnLongClickListener listener){
        mLongClickListener = listener;
    }

    public UpClothesAdapter(Context context, List<String> data){
        mContext = context;
        mData = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_up_clothes,null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position<mData.size()){
            String s = mData.get(position);
            if (!TextUtils.isEmpty(s)){
                Bitmap smallBitmap = ImageUtils.getSmallBitmap(s);
                holder.mPic.setImageBitmap(smallBitmap);
            }
        }
        if (mListener!=null){
            holder.mPic.setTag(position);
            holder.mPic.setOnClickListener(mListener);
        }

        if (mLongClickListener!=null){
            holder.mPic.setTag(position);
            holder.mPic.setOnLongClickListener(mLongClickListener);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static  class  MyViewHolder extends RecyclerView.ViewHolder{

        private RoundImageView mPic;
        public MyViewHolder(View itemView) {
            super(itemView);
            mPic = (RoundImageView) itemView.findViewById(R.id.rv_item_up_clothes);
        }

    }


}
