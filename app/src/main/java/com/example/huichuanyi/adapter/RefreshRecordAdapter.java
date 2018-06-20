package com.example.huichuanyi.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.RecordRefresh;
import com.example.huichuanyi.common_view.model.PrivateRecommendModel;
import com.example.huichuanyi.custom.GlideRoundTransform;
import com.example.huichuanyi.ui.activity.Item_DetailsActivity;

import java.util.List;


/**
 * Created by 小五 on 2017/2/8.
 */

public class RefreshRecordAdapter extends BaseAdapter {
    private List<RecordRefresh> mData;
    private Activity mActivity;

    public RefreshRecordAdapter(List<RecordRefresh> mData, Activity mActivity) {
        this.mData = mData;
        this.mActivity = mActivity;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_fragment_refresh_record, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.pics.removeAllViews();
        RecordRefresh recordRefresh = mData.get(position);
        String time = recordRefresh.getTime();
        String[] split = time.split("-");
        holder.mTime.setText(split[1] + "月");
        holder.mDate.setText(split[2]);
        if (position == 0) {
            holder.line.setVisibility(View.VISIBLE);
            holder.mYear.setText("2018");
        } else {
            holder.line.setVisibility(View.GONE);
        }
        final List<RecordRefresh.RefreshBean> list = recordRefresh.getList();
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                RelativeLayout relativeLayout = new RelativeLayout(mActivity);
                relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(150, 150));
                relativeLayout.setPadding(5, 5, 5, 5);
                ImageView pic = new ImageView(mActivity);
                pic.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
                pic.setScaleType(ImageView.ScaleType.FIT_XY);
                relativeLayout.addView(pic);
                relativeLayout.setTag(i);
                RecordRefresh.RefreshBean bean = list.get(i);
                final String clothes_get = bean.getClothes_get();
                final String id = bean.getId();
                final String color = bean.getColor();
                final String deleteStatus = bean.getDeleteStatus();
                final String reason = bean.getReason();
                final String price_dj = bean.getPrice_dj();
                final String size_name = bean.getSize_name();
                final String name = bean.getName();
                final String recommend_id = bean.getRecommend_id();
                Glide.with(mActivity).load(clothes_get).transform(new GlideRoundTransform(mActivity)).into(pic);
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (deleteStatus.contains("-")) {
                            PrivateRecommendModel bean = new PrivateRecommendModel();
                            bean.setId(id);
                            bean.setColor(color);
                            bean.setClothes_name(name);
                            bean.setClothes_get(clothes_get);
                            bean.setPrice_dj(price_dj);
                            bean.setSize_name(size_name);
                            bean.setReason(reason);
                            bean.setRecommend_id(recommend_id);
                            Intent in = new Intent(mActivity, Item_DetailsActivity.class);
                            in.putExtra("bean", bean);
                            mActivity.startActivity(in);
                        }
                    }
                });
                holder.pics.addView(relativeLayout);
            }
        } else {
        }

        return convertView;
    }

    public static class ViewHolder {
        private LinearLayout pics, line;
        private TextView mTime, mDate, mYear;

        public ViewHolder(View view) {
            mDate = (TextView) view.findViewById(R.id.tv_item_refresh_record_date);
            mTime = (TextView) view.findViewById(R.id.tv_item_refresh_record_month);
            pics = (LinearLayout) view.findViewById(R.id.ll_item_refresh_record);
            mYear = (TextView) view.findViewById(R.id.tv_item_refresh_record_year);
            line = (LinearLayout) view.findViewById(R.id.line);
        }
    }
}
