package com.example.huichuanyi.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.custom.RoundImageView;
import com.example.huichuanyi.modle.Indent;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 小五 on 2016/12/27.
 */
public class IndentAdapter extends BaseAdapter{
    private List<Indent> mData;
    private Context mContext;
    private OnMyClickInterFace myClickInterFace;
    public void setCallBack(OnMyClickInterFace onMyClickInterFace){
        myClickInterFace = onMyClickInterFace;
    }
    public IndentAdapter(List<Indent> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size()==0?0:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_indent_item_progress,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mData!=null&&mData.size()>0){
            Indent listBean = mData.get(position);
            String state = listBean.getState();
            //当state为0的时候是未完成状态，要去支付
            //当state为1的时候是支付成功状态，要去观看
            if (TextUtils.equals("0",state)){
                holder.mGoPay.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals("1",state)) {
                holder.mGoLook.setVisibility(View.VISIBLE);
            }
            String photoUrl = listBean.getPhotoUrl();
            if (photoUrl!=null){
                Picasso.with(mContext).load(photoUrl).into(holder.mMovie);
            }
            String money = listBean.getMoney();
            holder.mPrice.setText("¥"+money);
            holder.mTotal.setText("总计¥"+money);
            String vidsSize = listBean.getVidsSize();
            if (vidsSize!=null){
                String[] split = vidsSize.split(",");
                holder.mCount.setText(split.length+"");
            }
            holder.mTitle.setText(listBean.getVideosid());
            holder.mGoPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setTag(position);
                    myClickInterFace.setClickListener(v);
                }
            });
            holder.mGoLook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setTag(position);
                    myClickInterFace.setClickListener(v);
                }
            });
        }
        return convertView;
    }

    public static class ViewHolder{
        private TextView mTitle,mCount,mPrice,mTotal;
        private ImageView mGoPay,mGoLook;
        private RoundImageView mMovie;
        public ViewHolder(View view) {
            mTitle = (TextView) view.findViewById(R.id.tv_item_fragment_indent_title);
            mCount = (TextView) view.findViewById(R.id.tv_item_fragment_indent_count);
            mPrice = (TextView) view.findViewById(R.id.tv_item_fragment_indent_price);
            mTotal = (TextView) view.findViewById(R.id.tv_item_fragment_indent_total);
            mGoPay = (ImageView) view.findViewById(R.id.iv_item_fragment_indent_go_pay);
            mGoLook = (ImageView) view.findViewById(R.id.iv_item_fragment_indent_go_look);
            mMovie = (RoundImageView) view.findViewById(R.id.rv_item_fragment_indent_movie);
        }
    }

    public interface OnMyClickInterFace{
        void setClickListener(View view);
    }
}
