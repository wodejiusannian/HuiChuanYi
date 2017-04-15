package com.example.huichuanyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.Video;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by 小五 on 2017/4/11.
 */

public class VideoYouHuiQuanAdapter extends BaseAdapter {

    private List<Video.BodyBean> mData;
    private Context mContext;

    public VideoYouHuiQuanAdapter(List<Video.BodyBean> data, Context context) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_youhuiquan, null);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        Video.BodyBean bodyBean = mData.get(position);
        mHolder.authPhoto.setImageURI(bodyBean.getVideo_pic());
        mHolder.videoName.setText(bodyBean.getVideo_name());
        return convertView;
    }

    public static class ViewHolder {
        private SimpleDraweeView authPhoto;
        private TextView videoName;

        public ViewHolder(View view) {
            authPhoto = (SimpleDraweeView) view.findViewById(R.id.sv_item_youhuiquan);
            videoName = (TextView) view.findViewById(R.id.tv_youhuiquan_video_name);
        }
    }
}
