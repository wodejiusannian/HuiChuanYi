package com.example.huichuanyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.MyAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小五 on 2017/2/13.
 */

public class ListAddressAdapter extends BaseAdapter {
    private List<MyAddress> mData;
    private Context mContext;
    public Info mInfo;
    private List<Boolean> isClicks;

    public void getInfo(Info info) {
        mInfo = info;
    }

    public ListAddressAdapter(Context mContext, List<MyAddress> mData) {
        this.mContext = mContext;
        this.mData = mData;
        isClicks = new ArrayList<>();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_address, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        for (int i = 0; i < mData.size(); i++) {
            if (i == 0) {
                isClicks.add(true);
            } else {
                isClicks.add(false);
            }
        }
        final MyAddress address = mData.get(position);
        boolean flag = isClicks.get(position);
        final String add = address.getAddress();
        final String name = address.getName();
        final String phone = address.getPhone();
        holder.name.setText(name);
        holder.phone.setText(phone);
        holder.address.setText(add);
        if (flag) {
            holder.mSelect.setSelected(true);
        } else {
            holder.mSelect.setSelected(false);
        }
        holder.mAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInfo.getInfo(name, phone, add);
                for (int i = 0; i < mData.size(); i++) {
                    isClicks.set(i, false);
                }
                isClicks.set(position, true);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        public TextView name, address, phone, mSelect;
        public RelativeLayout mAll;

        public ViewHolder(View view) {
            mAll = (RelativeLayout) view.findViewById(R.id.rl_all);
            name = (TextView) view.findViewById(R.id.tv_item_address_name);
            phone = (TextView) view.findViewById(R.id.tv_item_address_phone);
            address = (TextView) view.findViewById(R.id.tv_item_address_address);
            mSelect = (TextView) view.findViewById(R.id.tv_item_address_is_select);
        }
    }

    public interface Info {
        void getInfo(String name, String phone, String add);
    }

}
