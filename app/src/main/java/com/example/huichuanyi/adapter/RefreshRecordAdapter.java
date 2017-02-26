package com.example.huichuanyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.RecordRefresh;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by 小五 on 2017/2/8.
 */

public class RefreshRecordAdapter extends BaseAdapter {
    private List<RecordRefresh> mData;
    private Context mContext;

    public RefreshRecordAdapter(List<RecordRefresh> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_refresh_record, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.pics.removeAllViews();
        RecordRefresh recordRefresh = mData.get(position);
        String time = recordRefresh.getTime();
        String[] split = time.split("-");
        holder.mTime.setText(split[1]);
        holder.mDate.setText(split[2]);
        if (position == 0) {
            holder.line.setVisibility(View.VISIBLE);
            holder.mYear.setText("2017");
        } else {
            holder.line.setVisibility(View.GONE);
        }
        List<RecordRefresh.RefreshBean> list = recordRefresh.getList();
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                SimpleDraweeView pic = new SimpleDraweeView(mContext);
                pic.setMinimumWidth(200);
                pic.setMinimumHeight(200);
                pic.setPaddingRelative(10, 10, 10, 10);
                GenericDraweeHierarchy build = GenericDraweeHierarchyBuilder.newInstance(mContext.getResources()).
                        setRoundingParams(RoundingParams.fromCornersRadius(20)).build();
                pic.setHierarchy(build);
                if (i % 5 == 0) {
                    pic.setImageURI("http://a.hiphotos.baidu.com/image/pic/item/78310a55b319ebc497ee99908026cffc1e171620.jpg");
                } else {
                    pic.setImageURI("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
                }
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
