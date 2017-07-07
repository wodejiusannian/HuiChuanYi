package com.example.huichuanyi.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.Location;
import com.example.huichuanyi.base.CommonAdapter;
import com.example.huichuanyi.bean.City;
import com.example.huichuanyi.utils.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class PersonAdapter extends CommonAdapter<City.BodyBean> {

    public PersonAdapter(Context context, List<City.BodyBean> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder viewHolder, City.BodyBean bodyBean) {
        SimpleDraweeView CSMCPhoto = viewHolder.getView(R.id.sv_csmc_photo);
        TextView jl = viewHolder.getView(R.id.tv_person_jl);
        TextView name = viewHolder.getView(R.id.tv_person_name);
        TextView introduction = viewHolder.getView(R.id.tv_person_introduction);
        TextView city = viewHolder.getView(R.id.tv_person_city);
        TextView news = viewHolder.getView(R.id.tv_person_news);
        CSMCPhoto.setImageURI(bodyBean.getPhoto_get());
        name.setText(bodyBean.getName());
        if (!TextUtils.isEmpty(Location.lat) && Location.lat != null)
            jl.setText(bodyBean.getJl() + "km");
        else
            jl.setText(null);
        introduction.setText(bodyBean.getIntroduction());
        city.setText(bodyBean.getCity());
        String service = bodyBean.getService();
        if (!TextUtils.equals("已开通", service)) {
            news.setVisibility(View.GONE);
        } else {
            news.setVisibility(View.VISIBLE);
        }
    }

}
