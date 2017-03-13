package com.example.huichuanyi.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.RecordRefresh;
import com.example.huichuanyi.ui.activity.Item_DetailsActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
            holder.mYear.setText("2017");
        } else {
            holder.line.setVisibility(View.GONE);
        }
        final List<RecordRefresh.RefreshBean> list = recordRefresh.getList();
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                SimpleDraweeView pic = new SimpleDraweeView(mActivity);
                pic.setMinimumWidth(200);
                pic.setMinimumHeight(200);
                pic.setPaddingRelative(10, 10, 10, 10);
                GenericDraweeHierarchy build = GenericDraweeHierarchyBuilder.newInstance(mActivity.getResources()).
                        setRoundingParams(RoundingParams.fromCornersRadius(20)).build();
                pic.setHierarchy(build);
                pic.setTag(i);
                RecordRefresh.RefreshBean bean = list.get(i);
                final String clothes_get = bean.getClothes_get();
                final String id = bean.getId();
                final String color = bean.getColor();
                final String introduction = bean.getIntroduction();
                final String reason = bean.getReason();
                final String price_dj = bean.getPrice_dj();
                final String size_name = bean.getSize_name();
                final String name = bean.getName();
                pic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        int tag = (int) v.getTag();
                        String recommend_id = list.get(tag).getRecommend_id();
                        map.put("recommend_id", recommend_id);
                        map.put("clothes_get", clothes_get);
                        map.put("id", id);
                        map.put("introduction", introduction);
                        map.put("clothes_get", clothes_get);
                        map.put("price_dj", price_dj);
                        map.put("name", name);
                        map.put("color_name", color);
                        map.put("reason", reason);
                        map.put("size_name", size_name);
                        map.put("type", "3");
                        ActivityUtils.switchTo(mActivity, Item_DetailsActivity.class, map);
                    }
                });
                pic.setImageURI(clothes_get);
                holder.pics.addView(pic);
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
