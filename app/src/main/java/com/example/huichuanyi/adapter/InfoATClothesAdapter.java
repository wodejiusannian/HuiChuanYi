package com.example.huichuanyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.huichuanyi.R;
import com.example.huichuanyi.custom.HistogramView;
import com.example.huichuanyi.modle.Statistics;

import java.util.List;

public class InfoATClothesAdapter extends BaseAdapter{
    private List<Statistics.ListBean> clothesName;
    private Context mContext;
    private String[] arr = {"#01aeae","#be9a96","#fdb488","#8e996f","#f5ab9e",
            "#dd9abc","#83b894","#a184ab","#808bd6","#608db6"};
    private int my  = 0;
    public InfoATClothesAdapter(List<Statistics.ListBean> clothesName,  Context context) {
        this.clothesName = clothesName;
        mContext = context;
    }

    @Override
    public int getCount() {
        return clothesName.size()==0?0:clothesName.size();
    }

    @Override
    public Object getItem(int position) {
        return clothesName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if(convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.info_at_clothes,null);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        if(position % 10==0) {
            my = 0;
        }
        String count1 = clothesName.get(0).getCount();
        Statistics.ListBean sl = clothesName.get(position);
        mHolder.clothesName.setText(sl.getType());
        String count = sl.getCount();
        int anInt = Integer.parseInt(count);
        int count2 = Integer.parseInt(count1);
        mHolder.clothesCount.setText(anInt+"件");
        Double i = (double)anInt /count2 ;
        mHolder.mHv.setProgress(i);
        mHolder.mHv.setRateBackgroundColor(arr[my]);
        my++;
        return convertView;
    }
    public static class ViewHolder{
        private TextView clothesName,clothesCount;
        private HistogramView mHv;
        public ViewHolder(View convertView){
            clothesName = (TextView) convertView.findViewById(R.id.tv_atClothes_name);
            clothesCount = (TextView) convertView.findViewById(R.id.tv_atClothes_count);
            mHv = (HistogramView) convertView.findViewById(R.id.hv_atClothes_progress);
        }
    }
}
