package com.example.huichuanyi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.bean.MyClothess;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by Bob on 2016/8/10.
 */
public class MC_OldAdapter extends RecyclerView.Adapter<MC_OldAdapter.MyViewHolder> {
    private List<MyClothess.BodyBean.ClothesInfoBean> mData;
    private Context mContext;
    private View.OnClickListener mListener;

    public void setOnItemClickListener(View.OnClickListener listener){
        mListener = listener;
    }

    public MC_OldAdapter(Context context, List<MyClothess.BodyBean.ClothesInfoBean> data){
        mContext = context;
        mData = data;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mc_oldclothes_item,null);
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
        holder.mImageViewRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySelfDialog mySelfDialog = new MySelfDialog(mContext);
                mySelfDialog.setMessage("确定要还原到原来的衣橱吗");
                mySelfDialog.setOnYesListener("确定", new MySelfDialog.OnYesClickListener() {
                    @Override
                    public void onClick() {
                        RequestParams params = new RequestParams(NetConfig.DELETE_PIC);
                        params.addBodyParameter("cloid",mData.get(position).getClothes_id());
                        params.addBodyParameter("move","1");
                        x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                if(TextUtils.equals(result,"1")) {
                                    Toast.makeText(mContext, "还原成功", Toast.LENGTH_SHORT).show();
                                    mData.remove(position);
                                    notifyDataSetChanged();
                                }else{
                                    Toast.makeText(mContext, "还原失败", Toast.LENGTH_SHORT).show();
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
                });
                mySelfDialog.setOnNoListener("取消",null);
                mySelfDialog.show();
            }
        });
        holder.mImageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySelfDialog mySelfDialog = new MySelfDialog(mContext);
                mySelfDialog.setMessage("确认要永久删除吗？");
                mySelfDialog.setOnYesListener("确定", new MySelfDialog.OnYesClickListener() {
                    @Override
                    public void onClick() {
                        RequestParams params = new RequestParams(NetConfig.THOROUGH_DELETE_CLOTHES);
                        params.addBodyParameter("cloid",mData.get(position).getClothes_id());
                        params.addBodyParameter("userid", SharedPreferenceUtils.getUserData(mContext,1));
                        x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                if(!TextUtils.equals("result","2")) {
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
                });
                mySelfDialog.setOnNoListener("取消",null);
                mySelfDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    public static  class  MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView,mImageViewRestore,mImageViewDelete;
        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_item_recycler_3);
            mImageViewRestore = (ImageView) itemView.findViewById(R.id.iv_item_restore);
            mImageViewDelete = (ImageView) itemView.findViewById(R.id.iv_item_thorough_delete);
        }
    }

}
