package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.RTCReport;

// ┏┓　　　┏┓
// ┏┛┻━━━┛┻┓
// ┃　　　　　　　┃ 　
// ┃　　　━　　　┃
// ┃　┳┛　┗┳　┃
// ┃　　　　　　　┃
// ┃　　　┻　　　┃
// ┃　　　　　　　┃
// ┗━┓　　　┏━┛
// ┃　　　┃ 神兽保佑　　　　　　　　
// ┃　　　┃ 代码无BUG！
// ┃　　　┗━━━┓
// ┃　　　　　　　┣┓
// ┃　　　　　　　┏┛
// ┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
// ┗┻┛　┗┻┛
public class LyRTCReportHolder extends BaseViewHolder<RTCReport> {

    public LyRTCReportHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final RTCReport model, int position, MultiTypeAdapter adapter) {
        TextView name = (TextView) getView(R.id.tv_item_recycle_rtc_report_name);
        TextView phone = (TextView) getView(R.id.tv_item_recycle_rtc_report_phone);
        TextView occupation = (TextView) getView(R.id.tv_item_recycle_rtc_report_occupation);
        ImageView simple = (ImageView) getView(R.id.iv_item_recycle_rtc_report_simple);
        ImageView complete = (ImageView) getView(R.id.iv_item_recycle_rtc_report_complete);
        ImageView logo = (ImageView) getView(R.id.iv_item_recycle_rtc_report_logo);
        Context context = logo.getContext();
        Glide.with(context).load(model.getPic_url()).into(logo);
        simple.setTag(position + "=0");
        complete.setTag(position + "=1");
        complete.setOnClickListener(adapter.getmOnclick());
        simple.setOnClickListener(adapter.getmOnclick());
        name.setText(model.getName());
        phone.setText(model.getPhone());
        occupation.setText(model.getPosition());
    }

}
