package com.example.huichuanyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.model.LyShopCar;

import java.util.List;


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
public class LyBuyAdapter extends BaseAdapter {

    private List<LyShopCar.BodyBean> mData;
    private Context context;

    public LyBuyAdapter(List<LyShopCar.BodyBean> mData, Context context) {
        this.mData = mData;
        this.context = context;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lybuy_shop_car, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LyShopCar.BodyBean bean = mData.get(position);
        holder.num.setText("x" + bean.getNum());
        holder.name.setText(bean.getGoods_name());
        holder.introduce.setText(bean.getGoods_name());
        Glide.with(context).load(bean.getPic_url()).error(R.mipmap.stand).into(holder.photo);
        return convertView;
    }

    public static class ViewHolder {
        private ImageView photo;
        private TextView name, price, num, introduce;

        public ViewHolder(View v) {
            photo = (ImageView) v.findViewById(R.id.iv_item_ly_shopcar_singleShop);
            name = (TextView) v.findViewById(R.id.tv_item_lyshopcar_name);
            introduce = (TextView) v.findViewById(R.id.tv_item_lyshopcar_jieshao);
            num = (TextView) v.findViewById(R.id.tv_item_ly_shopcar_count);
        }
    }
}
