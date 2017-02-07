package com.example.huichuanyi.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.custom.RoundImageView;
import com.example.huichuanyi.modle.Progress;
import com.example.huichuanyi.secondui.PingJiaActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OverAdapter extends BaseAdapter implements View.OnClickListener {
    private List<Progress.ListBean> mData;
    private Context mContext;

    public OverAdapter(List<Progress.ListBean> data, Context context) {
        mData = data;
        mContext = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder = null;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_over_item,null);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else{
            mHolder = (ViewHolder) convertView.getTag();
        }
        if (mData!=null&&mData.size()>0){
            Progress.ListBean  mPosition=   mData.get(position);
            String manager_photo = mPosition.getManager_photo();
            if(manager_photo.length()>5) {
                Picasso.with(mContext).load(manager_photo).into(mHolder.mImageViewPhoto);
            }else{
                mHolder.mImageViewPhoto.setImageResource(R.mipmap.stand);
            }
            mHolder.mTextViewName.setText(mPosition.getManagername());
            mHolder.mTextViewAllMoney.setText(mPosition.getMoney());
            mHolder.mTextViewDingDanHao.setText(mPosition.getId());
            mHolder.QuPingJia.setOnClickListener(this);
            mHolder.QuPingJia.setTag(position);
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        switch (v.getId()) {
            case  R.id.iv_over_item_qupingjia:
                Map<String,Object> map = new HashMap<>();
                map.put("orderid",mData.get(tag).getId());
                map.put("manager_id",mData.get(tag).getManagerid());
                map.put("user_name",mData.get(tag).getUsername());
                map.put("manager_name",mData.get(tag).getManagername());
                ActivityUtils.switchTo((Activity) mContext,PingJiaActivity.class,map);
                break;
        }
    }


    public static class ViewHolder{
            private RoundImageView mImageViewPhoto;
            private ImageView QuPingJia;
            private TextView mTextViewName,mTextViewAllMoney,mTextViewDingDanHao;
            public ViewHolder(View view){
                mImageViewPhoto = (RoundImageView) view.findViewById(R.id.rv_over_item_photo);
                QuPingJia = (ImageView) view.findViewById(R.id.iv_over_item_qupingjia);
                mTextViewName = (TextView) view.findViewById(R.id.tv_over_item_name);
                mTextViewAllMoney = (TextView) view.findViewById(R.id.tv_over_item_allMoney);
                mTextViewDingDanHao = (TextView) view.findViewById(R.id.tv_over_item_dingdanhao);
            }
    }
}
