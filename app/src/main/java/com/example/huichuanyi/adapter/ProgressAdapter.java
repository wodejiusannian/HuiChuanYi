package com.example.huichuanyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.ServiceBean;

import java.util.List;

public class ProgressAdapter extends BaseAdapter {
    private List<ServiceBean.BodyBean> mData;

    private Context mContext;

    private OnOrderClickListener mOnOrderClick;

    public void setOnOrderClick(OnOrderClickListener onOrderClick) {
        mOnOrderClick = onOrderClick;
    }

    public ProgressAdapter(List<ServiceBean.BodyBean> data, Context context) {
        mData = data;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size() == 0 ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //订单状态对应：-2购物车；-1-20下单未支付；0-10支付成功；1-11已发货/接单；
    // 2-12已完成；3-100未接单申退；4-110已接单申退；5-101拒绝接单退款成功；6-1001或1101申请退款成功；
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_progress_item, null);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        if (mData != null && mData.size() > 0) {
            ServiceBean.BodyBean mPosition = mData.get(position);
            String state = mPosition.getDeleteStatus();
            switch (state) {
                case "-1":
                case "20":
                    mHolder.BuChaJia.setVisibility(View.GONE);
                    mHolder.ShenQingTuiKuan.setVisibility(View.GONE);
                    mHolder.DaiQueRen.setVisibility(View.GONE);
                    mHolder.ZaiLaiYiDan.setVisibility(View.GONE);
                    mHolder.QuZhiFu.setVisibility(View.VISIBLE);
                    //mHolder.mTextViewState.setText("待支付");
                    break;
                case "0":
                case "10":
                    mHolder.DaiQueRen.setVisibility(View.VISIBLE);
                    mHolder.ShenQingTuiKuan.setVisibility(View.VISIBLE);
                    mHolder.BuChaJia.setVisibility(View.GONE);
                    mHolder.ZaiLaiYiDan.setVisibility(View.GONE);
                    mHolder.QuZhiFu.setVisibility(View.GONE);
                    //mHolder.mTextViewState.setText("进行中");
                    break;
                case "1":
                case "11":
                    mHolder.DaiQueRen.setVisibility(View.VISIBLE);
                    mHolder.ShenQingTuiKuan.setVisibility(View.VISIBLE);
                    mHolder.BuChaJia.setVisibility(View.VISIBLE);
                    mHolder.ZaiLaiYiDan.setVisibility(View.GONE);
                    mHolder.QuZhiFu.setVisibility(View.GONE);
                    //mHolder.mTextViewState.setText("进行中");
                    break;
                case "2":
                case "12":
                    if ("0".equals(mPosition.getEvaluateState())) {
                        //mHolder.mTextViewState.setText("已完成");
                        mHolder.BuChaJia.setVisibility(View.GONE);
                        mHolder.ShenQingTuiKuan.setVisibility(View.GONE);
                        mHolder.DaiQueRen.setVisibility(View.GONE);
                        mHolder.ZaiLaiYiDan.setVisibility(View.VISIBLE);
                        mHolder.QuZhiFu.setVisibility(View.GONE);
                    } else {
                        mHolder.BuChaJia.setVisibility(View.GONE);
                        mHolder.ShenQingTuiKuan.setVisibility(View.GONE);
                        mHolder.DaiQueRen.setVisibility(View.GONE);
                        mHolder.ZaiLaiYiDan.setVisibility(View.VISIBLE);
                        mHolder.QuZhiFu.setVisibility(View.GONE);
                        mHolder.ZaiLaiYiDan.setImageResource(R.mipmap.qupingjia);
                    }
                    break;
                case "4":
                case "3":
                case "100":
                case "110":
                    mHolder.ZaiLaiYiDan.setVisibility(View.VISIBLE);
                    //mHolder.mTextViewState.setText("审核中");
                    mHolder.BuChaJia.setVisibility(View.GONE);
                    mHolder.ShenQingTuiKuan.setVisibility(View.GONE);
                    mHolder.DaiQueRen.setVisibility(View.GONE);
                    mHolder.QuZhiFu.setVisibility(View.GONE);
                    mHolder.ZaiLaiYiDan.setImageResource(R.mipmap.shenhezhong);
                    break;
                case "5":
                case "6":
                case "1001":
                case "1102":
                    //mHolder.mTextViewState.setText("订单取消" + "");
                    mHolder.BuChaJia.setVisibility(View.GONE);
                    mHolder.ShenQingTuiKuan.setVisibility(View.GONE);
                    mHolder.DaiQueRen.setVisibility(View.GONE);
                    mHolder.ZaiLaiYiDan.setVisibility(View.VISIBLE);
                    mHolder.QuZhiFu.setVisibility(View.GONE);
                    break;
            }

            //进行每个item的展示
            /*String manager_photo = mPosition.getManager_photo();
            Glide.with(mContext).load(manager_photo).error(R.mipmap.stand).into(mHolder.mImageViewPhoto);*/
            //mHolder.mTextViewName.setText(mPosition.getBuyUserName());
            mHolder.mTextViewAllMoney.setText("¥" + mPosition.getMoneyTotal());
            mHolder.mTextViewDingDanHao.setText(mPosition.getSellerUserName());
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

    public static class ViewHolder {
        //private RoundImageView mImageViewPhoto;
        private ImageView ShenQingTuiKuan, BuChaJia, QuZhiFu, ZaiLaiYiDan, DaiQueRen;
        private TextView mTextViewAllMoney, mTextViewDingDanHao;

        public ViewHolder(View view) {
            //mImageViewPhoto = (RoundImageView) view.findViewById(R.id.rv_progress_item_photo);
            ShenQingTuiKuan = (ImageView) view.findViewById(R.id.iv_progress_item_shenqingtuikuan);
            BuChaJia = (ImageView) view.findViewById(R.id.iv_progress_item_buchajia);
            QuZhiFu = (ImageView) view.findViewById(R.id.iv_progress_item_quzhifu);
            ZaiLaiYiDan = (ImageView) view.findViewById(R.id.iv_progress_item_zailaiyidian);
            //mTextViewName = (TextView) view.findViewById(R.id.tv_progress_item_name);
            mTextViewAllMoney = (TextView) view.findViewById(R.id.tv_progress_item_allMoney);
            mTextViewDingDanHao = (TextView) view.findViewById(R.id.tv_progress_item_dingdanhao);
            //mTextViewState = (TextView) view.findViewById(R.id.tv_progress_item_isPay);
            DaiQueRen = (ImageView) view.findViewById(R.id.iv_progress_item_daiqueren);
        }
    }

    public interface OnOrderClickListener {
        void onOrderClick(View view);
    }

}
