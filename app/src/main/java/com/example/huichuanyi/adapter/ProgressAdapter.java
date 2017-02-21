package com.example.huichuanyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.custom.RoundImageView;
import com.example.huichuanyi.bean.Progress;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProgressAdapter extends BaseAdapter {
    private List<Progress.ListBean> mData;

    private Context mContext;

    private OnOrderClickListener mOnOrderClick;

    public void setOnOrderClick(OnOrderClickListener onOrderClick){
        mOnOrderClick = onOrderClick;
    }

    public ProgressAdapter(List<Progress.ListBean> data, Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_progress_item,null);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else{
            mHolder = (ViewHolder) convertView.getTag();
        }
        if (mData!=null&&mData.size()>0){
            Progress.ListBean  mPosition=   mData.get(position);
            String state = mPosition.getState();
            switch(state){
                case "20":
                    mHolder.BuChaJia.setVisibility(View.GONE);
                    mHolder.ShenQingTuiKuan.setVisibility(View.GONE);
                    mHolder.DaiQueRen.setVisibility(View.GONE);
                    mHolder.ZaiLaiYiDan.setVisibility(View.GONE);
                    mHolder.QuZhiFu.setVisibility(View.VISIBLE);
                    mHolder.mTextViewState.setText("待支付");
                break;
                case "10":
                    mHolder.DaiQueRen.setVisibility(View.VISIBLE);
                    mHolder.ShenQingTuiKuan.setVisibility(View.VISIBLE);
                    mHolder.BuChaJia.setVisibility(View.GONE);
                    mHolder.ZaiLaiYiDan.setVisibility(View.GONE);
                    mHolder.QuZhiFu.setVisibility(View.GONE);
                    mHolder.mTextViewState.setText("进行中");
                    break;
                case "11":
                    mHolder.DaiQueRen.setVisibility(View.VISIBLE);
                    mHolder.ShenQingTuiKuan.setVisibility(View.VISIBLE);
                    mHolder.BuChaJia.setVisibility(View.VISIBLE);
                    mHolder.ZaiLaiYiDan.setVisibility(View.GONE);
                    mHolder.QuZhiFu.setVisibility(View.GONE);
                    mHolder.mTextViewState.setText("进行中");
                    break;
                case "14":
                    mHolder.DaiQueRen.setVisibility(View.VISIBLE);
                    mHolder.BuChaJia.setVisibility(View.VISIBLE);
                    mHolder.ZaiLaiYiDan.setVisibility(View.GONE);
                    mHolder.QuZhiFu.setVisibility(View.GONE);
                    mHolder.ShenQingTuiKuan.setVisibility(View.GONE);
                    break;
                case "100":
                    mHolder.mTextViewState.setText("审核中");
                    mHolder.BuChaJia.setVisibility(View.GONE);
                    mHolder.ShenQingTuiKuan.setVisibility(View.GONE);
                    mHolder.DaiQueRen.setVisibility(View.GONE);
                    mHolder.ZaiLaiYiDan.setVisibility(View.GONE);
                    mHolder.QuZhiFu.setVisibility(View.GONE);
                    break;
                case "110":
                    mHolder.mTextViewState.setText("审核中");
                    mHolder.BuChaJia.setVisibility(View.GONE);
                    mHolder.ShenQingTuiKuan.setVisibility(View.GONE);
                    mHolder.DaiQueRen.setVisibility(View.GONE);
                    mHolder.ZaiLaiYiDan.setVisibility(View.GONE);
                    mHolder.QuZhiFu.setVisibility(View.GONE);
                    break;
                default:
                    mHolder.mTextViewState.setText("订单取消" +
                            "");
                    mHolder.BuChaJia.setVisibility(View.GONE);
                    mHolder.ShenQingTuiKuan.setVisibility(View.GONE);
                    mHolder.DaiQueRen.setVisibility(View.GONE);
                    mHolder.ZaiLaiYiDan.setVisibility(View.VISIBLE);
                    mHolder.QuZhiFu.setVisibility(View.GONE);
                break;
            }

            //进行每个item的展示
            String manager_photo = mPosition.getManager_photo();
            if(manager_photo.length()>5) {
                Picasso.with(mContext).load(manager_photo).into(mHolder.mImageViewPhoto);
            }
            mHolder.mTextViewName.setText(mPosition.getManagername());
            mHolder.mTextViewAllMoney.setText(mPosition.getMoney());
            mHolder.mTextViewDingDanHao.setText(mPosition.getId());
            //为每个空间添加TAG,添加回调接口
            mHolder.ShenQingTuiKuan.setTag(position);
            mHolder.ZaiLaiYiDan.setTag(position);
            mHolder.DaiQueRen.setTag(position);
            mHolder.BuChaJia.setTag(position);
            mHolder.QuZhiFu.setTag(position);
            mHolder.QuZhiFu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnOrderClick.onOrderClick(v);
                }
            });
            mHolder.BuChaJia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnOrderClick.onOrderClick(v);
                }
            });

            mHolder.ShenQingTuiKuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnOrderClick.onOrderClick(v);
                }
            });

            mHolder.ZaiLaiYiDan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnOrderClick.onOrderClick(v);
                }
            });

            mHolder.DaiQueRen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnOrderClick.onOrderClick(v);
                }
            });
        }

        return convertView;
    }

    public static class ViewHolder{
            private RoundImageView mImageViewPhoto;
            private ImageView ShenQingTuiKuan,BuChaJia,QuZhiFu,ZaiLaiYiDan,DaiQueRen;
            private TextView mTextViewName,mTextViewAllMoney,mTextViewDingDanHao,mTextViewState;
            public ViewHolder(View view){
                mImageViewPhoto = (RoundImageView) view.findViewById(R.id.rv_progress_item_photo);
                ShenQingTuiKuan = (ImageView) view.findViewById(R.id.iv_progress_item_shenqingtuikuan);
                BuChaJia = (ImageView) view.findViewById(R.id.iv_progress_item_buchajia);
                QuZhiFu = (ImageView) view.findViewById(R.id.iv_progress_item_quzhifu);
                ZaiLaiYiDan = (ImageView) view.findViewById(R.id.iv_progress_item_zailaiyidian);
                mTextViewName = (TextView) view.findViewById(R.id.tv_progress_item_name);
                mTextViewAllMoney = (TextView) view.findViewById(R.id.tv_progress_item_allMoney);
                mTextViewDingDanHao = (TextView) view.findViewById(R.id.tv_progress_item_dingdanhao);
                mTextViewState = (TextView) view.findViewById(R.id.tv_progress_item_isPay);
                DaiQueRen = (ImageView) view.findViewById(R.id.iv_progress_item_daiqueren);
            }
    }

    public interface OnOrderClickListener{
        void onOrderClick(View view);
    }

}
