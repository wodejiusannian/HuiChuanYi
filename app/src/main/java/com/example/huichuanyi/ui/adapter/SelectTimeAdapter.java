package com.example.huichuanyi.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.ui.modle.Week;
import com.example.huichuanyi.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;


public class SelectTimeAdapter extends RecyclerView.Adapter<SelectTimeAdapter.MyViewHolder> {

    private Context mContext;

    private List<Week> mData;

    private List<Boolean> isClicks;

    private getPos mPos;


    public SelectTimeAdapter(Context context, List<Week> data) {
        mContext = context;
        mData = data;
        isClicks = new ArrayList<>();
    }

    public void setOnItemOnClickListener(getPos pos) {
        mPos = pos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_week_time, null);
        MyViewHolder holder = new MyViewHolder(view);
        for (int i = 0; i < mData.size(); i++) {
            isClicks.add(false);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Week week = mData.get(position);
        String busy_date = week.getBusy_date();
        if (position == 0) {
            holder.time.setText("今天");
        } else {
            holder.time.setText(DateUtils.getWeekByDateStr(busy_date));
        }
        holder.date.setText(busy_date.substring(5, busy_date.length()));
        final String busy_time_slot_tag = week.getBusy_time_slot_tag();
        if (TextUtils.equals("ALL_BUSY", busy_time_slot_tag)) {
            holder.canGoDoor.setText("约满");
            holder.canGoDoor.setTextColor(Color.parseColor("#666666"));
        } else {
            holder.canGoDoor.setText("可预约");
            holder.canGoDoor.setTextColor(Color.parseColor("#ac0000"));
        }
        holder.mSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.equals("ALL_BUSY", busy_time_slot_tag)) {
                    mPos.getPos(position);
                    for (int i = 0; i < mData.size(); i++) {
                        isClicks.set(i, false);
                    }
                    isClicks.set(position, true);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "今天已约满", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (isClicks.get(position)) {
            holder.mSelect.setBackgroundResource(R.drawable.shape_red_4dp);
            holder.canGoDoor.setTextColor(Color.WHITE);
            holder.time.setTextColor(Color.WHITE);
            holder.date.setTextColor(Color.WHITE);
        } else {
            holder.mSelect.setBackgroundColor(Color.WHITE);
            holder.time.setTextColor(Color.parseColor("#666666"));
            holder.date.setTextColor(Color.parseColor("#666666"));
        }
    }


    @Override
    public int getItemCount() {
        return mData.size() == 0 ? 0 : mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView time, date, canGoDoor;
        private LinearLayout mSelect;

        public MyViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.item_select_time);
            date = (TextView) itemView.findViewById(R.id.item_select_date);
            canGoDoor = (TextView) itemView.findViewById(R.id.item_select_iscangodoor);
            mSelect = (LinearLayout) itemView.findViewById(R.id.ll_select_order);
        }
    }

    public interface getPos {
        void getPos(int pos);
    }
}
