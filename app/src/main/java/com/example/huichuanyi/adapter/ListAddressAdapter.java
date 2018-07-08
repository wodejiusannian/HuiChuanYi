package com.example.huichuanyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.MyAddress;

import java.util.List;

/**
 * Created by 小五 on 2017/2/13.
 */

public class ListAddressAdapter extends BaseAdapter {

    private List<MyAddress> mData;
    private Context mContext;
    public Info mInfo;
    private View.OnClickListener mOnclick;

    public void setOnItemUpDateListener(View.OnClickListener onclick) {
        mOnclick = onclick;
    }

    public void getInfo(Info info) {
        mInfo = info;
    }


    public ListAddressAdapter(Context mContext, List<MyAddress> mData) {
        this.mContext = mContext;
        this.mData = mData;
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
        MyAddress address = mData.get(position);
        final String add = address.getReceive_address();
        final String name = address.getReceive_name();
        final String phone = address.getReceive_phone();
        final String receive_city = address.getReceive_city();
        final String address_id = address.getId();
        holder.name.setText(name);
        holder.phone.setText(phone);
        holder.address.setText(receive_city + add);
        holder.mUpdate.setTag(position);
        holder.mUpdate.setOnClickListener(mOnclick);
        holder.mAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInfo.getInfo(address_id, receive_city, name, phone, add);
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        public TextView name, address, phone;
        public RelativeLayout mAll;
        public ImageView mUpdate;

        public ViewHolder(View view) {
            mAll = (RelativeLayout) view.findViewById(R.id.rl_all);
            name = (TextView) view.findViewById(R.id.tv_item_address_name);
            phone = (TextView) view.findViewById(R.id.tv_item_address_phone);
            address = (TextView) view.findViewById(R.id.tv_item_address_address);
            mUpdate = (ImageView) view.findViewById(R.id.tv_item_update_address);
        }
    }

    public interface Info {
        void getInfo(String address_id, String city, String name, String phone, String add);
    }

}
