package com.example.huichuanyi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.CommonAdapter;
import com.example.huichuanyi.bean.RecordBuy;
import com.example.huichuanyi.utils.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by 小五 on 2017/2/8.
 */

public class BuyRecordAdapter extends CommonAdapter<RecordBuy> {
    private View.OnClickListener mOnClick;
   /*private List<RecordBuy> mData;
    private Context mContext;

    public BuyRecordAdapter(List<RecordBuy> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }*/

    public void setOnItemClick(View.OnClickListener onItemClick) {
        mOnClick = onItemClick;
    }

    public BuyRecordAdapter(Context context, List<RecordBuy> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder viewHolder, RecordBuy record) {
        SimpleDraweeView sv = viewHolder.getView(R.id.sv_clothe_item_info_record);
        sv.setImageURI(record.getClothes_get());
        TextView style = viewHolder.getView(R.id.tv_clothe_item_info_record_style);
        TextView color = viewHolder.getView(R.id.tv_clothe_item_info_record_color);
        TextView size = viewHolder.getView(R.id.tv_clothe_item_info_record_size);
        TextView price = viewHolder.getView(R.id.tv_clothe_item_info_record_price);
        TextView count = viewHolder.getView(R.id.tv_clothe_item_info_record_count);
        style.setText(record.getClothes_name());
        color.setText(record.getColor_name());
        size.setText("尺码:" + record.getSize_name());
        price.setText("￥:" + record.getTotal_price());
        count.setText("X" + record.getNum());
        String state = record.getState();
        Button connection_our = viewHolder.getView(R.id.btn_connection_our);
        Button see_car = viewHolder.getView(R.id.btn_see_car);
        Button confirm_receipt = viewHolder.getView(R.id.btn_confirm_receipt);
        Button go_pay = viewHolder.getView(R.id.btn_go_pay);
        go_pay.setOnClickListener(mOnClick);
        connection_our.setOnClickListener(mOnClick);
        see_car.setOnClickListener(mOnClick);
        confirm_receipt.setOnClickListener(mOnClick);

        switch (state) {
            case "11":
                confirm_receipt.setVisibility(View.VISIBLE);
                see_car.setVisibility(View.VISIBLE);
                connection_our.setVisibility(View.VISIBLE);
                go_pay.setVisibility(View.GONE);
                break;
            case "10":
                confirm_receipt.setVisibility(View.VISIBLE);
                see_car.setVisibility(View.VISIBLE);
                connection_our.setVisibility(View.VISIBLE);
                go_pay.setVisibility(View.GONE);
                break;
            case "20":
                go_pay.setVisibility(View.VISIBLE);
                confirm_receipt.setVisibility(View.GONE);
                see_car.setVisibility(View.GONE);
                connection_our.setVisibility(View.GONE);
                break;
            default:
                break;
        }

    }


}
