package com.example.huichuanyi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.MyClothess;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Bob on 2016/8/10.
 */
public class MC_MyClothesAdapter extends RecyclerView.Adapter<MC_MyClothesAdapter.MyViewHolder> {
    private List<MyClothess.BodyBean.ClothesInfoBean> mData;
    private Context mContext;
    private View.OnClickListener mListener;

    public void setOnItemClickListener(View.OnClickListener listener){
        mListener = listener;
    }
    public MC_MyClothesAdapter(Context context, List<MyClothess.BodyBean.ClothesInfoBean> data){
        mContext = context;
        mData = data;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mc_myclothes_item,null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        String getphoto = mData.get(position).getClothes_pic();
        if(!TextUtils.isEmpty(getphoto)) {
            Picasso.with(mContext).load(getphoto).into(holder.mImageView);
        }
       if (mListener!=null){
            holder.mImageView.setTag(position);
            holder.mImageView.setOnClickListener(mListener);
        }
        /* holder.mImageViewMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] cities = {"出行衣橱", "其他衣橱","家庭衣橱"};
                new AlertDialog.Builder(mContext).setTitle("移动").setItems(cities,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int wardrobe_id = -1;
                                if(which==0) {
                                     wardrobe_id = 2;
                                }
                                if(which==1) {
                                    wardrobe_id = 4;
                                }
                                if(which==2) {
                                    wardrobe_id = 1;
                                }
                                RequestParams params = new RequestParams(NetConfig.MOVE_PIC);
                                params.addBodyParameter("cloid",mData.get(position).getId());
                                params.addBodyParameter("wardrobe_id",wardrobe_id+"");
                                x.http().post(params, new Callback.CommonCallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        if(TextUtils.equals(result,"1")) {
                                            Toast.makeText(mContext, "移动成功", Toast.LENGTH_SHORT).show();
                                            mData.remove(position);
                                            notifyDataSetChanged();
                                        }else{
                                            Toast.makeText(mContext, "移动失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable ex, boolean isOnCallback) {

                                    }

                                    @Override
                                    public void onCancelled(CancelledException cex) {

                                    }

                                    @Override
                                    public void onFinished() {

                                    }
                                });
                            }
                        }).create().show();
            }
        });*/
       /* holder.mImageViewDetele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext).setMessage("确认要删除吗？").
                        setTitle("提示").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestParams params = new RequestParams(NetConfig.DELETE_PIC);
                        params.addBodyParameter("cloid",mData.get(position).getId());
                        params.addBodyParameter("move","0");
                        x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                if(!TextUtils.equals("result","1")) {
                                    Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                                    mData.remove(position);
                                    notifyDataSetChanged();
                                }else{
                                    Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {

                            }

                            @Override
                            public void onCancelled(CancelledException cex) {

                            }

                            @Override
                            public void onFinished() {

                            }
                        });

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();*/
           /* }
        });*/
    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    public static  class  MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView/*,mImageViewMove,mImageViewDetele*/;
        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_item_recycler_3);
            /*mImageViewMove = (ImageView) itemView.findViewById(R.id.iv_item_move);
            mImageViewDetele = (ImageView) itemView.findViewById(R.id.iv_item_detele);*/
        }
    }


}
