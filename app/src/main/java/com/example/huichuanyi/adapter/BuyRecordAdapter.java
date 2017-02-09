package com.example.huichuanyi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.CommonAdapter;
import com.example.huichuanyi.utils.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by 小五 on 2017/2/8.
 */

public class BuyRecordAdapter extends CommonAdapter<String> {

    public BuyRecordAdapter(Context context, List<String> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder viewHolder, String s) {
        SimpleDraweeView sv = viewHolder.getView(R.id.sv_clothe_item_info_record);
        sv.setImageURI("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        TextView style = viewHolder.getView(R.id.tv_clothe_item_info_record_style);
        TextView color = viewHolder.getView(R.id.tv_clothe_item_info_record_color);
        TextView size = viewHolder.getView(R.id.tv_clothe_item_info_record_size);
        TextView name = viewHolder.getView(R.id.tv_clothe_item_info_record_name);
        TextView price = viewHolder.getView(R.id.tv_clothe_item_info_record_price);
        TextView count = viewHolder.getView(R.id.tv_clothe_item_info_record_count);
        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
                Toast.makeText(context, ""+tag, Toast.LENGTH_SHORT).show();
            }
        });
        style.setText(s);
        color.setText("红黄蓝");
        name.setText("长款风衣");
        size.setText("32");
        price.setText("¥598");
        count.setText("*1");
    }


}
