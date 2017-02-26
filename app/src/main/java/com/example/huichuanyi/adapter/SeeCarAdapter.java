package com.example.huichuanyi.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.CommonAdapter;
import com.example.huichuanyi.bean.SeeCar;
import com.example.huichuanyi.utils.Utils;
import com.example.huichuanyi.utils.ViewHolder;

import java.util.List;

/**
 * Created by 小五 on 2017/2/24.
 */

public class SeeCarAdapter extends CommonAdapter<SeeCar> {

    public SeeCarAdapter(Context context, List<SeeCar> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder viewHolder, SeeCar seeCar) {
        TextView time = viewHolder.getView(R.id.item_see_car_time);
        TextView address = viewHolder.getView(R.id.item_see_car_address);
        time.setText(seeCar.time);
        address.setText(seeCar.address);
    }
}
