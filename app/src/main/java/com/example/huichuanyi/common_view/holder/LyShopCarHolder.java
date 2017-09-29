package com.example.huichuanyi.common_view.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.LyShopCar;

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
public class LyShopCarHolder extends BaseViewHolder<LyShopCar.BodyBean> {
    private static final String TAG = "LyShopCarHolder";

    public LyShopCarHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final LyShopCar.BodyBean model, int position, final MultiTypeAdapter adapter) {
        final ImageView cb = (ImageView) getView(R.id.iv_item_ly_shopcar_select);
        LinearLayout lEdit = (LinearLayout) getView(R.id.ll_item_ly_shopcar_edit);
        RelativeLayout rShow = (RelativeLayout) getView(R.id.rl_item_ly_shopcar_show);
        ImageView add = (ImageView) getView(R.id.iv_shopcar_add);
        TextView tvCount = (TextView) getView(R.id.iv_shopcar_count);
        ImageView single = (ImageView) getView(R.id.iv_item_ly_shopcar_singleShop);
        Glide.with(single.getContext()).load(model.getPic_url()).into(single);
        TextView name = (TextView) getView(R.id.tv_item_lyshopcar_name);
        TextView editName = (TextView) getView(R.id.tv_edit_name);
        TextView editPrice = (TextView) getView(R.id.tv_edit_price);
        editName.setText(model.getGoods_name());
        editPrice.setText("¥" + model.getPrice_one() * model.getNum());
        TextView jieshao = (TextView) getView(R.id.tv_item_lyshopcar_jieshao);
        TextView count = (TextView) getView(R.id.tv_item_ly_shopcar_count);
        count.setText("x" + model.getNum());
        name.setText(model.getGoods_name());
        jieshao.setText(model.getShop_name());
        final ImageView delete = (ImageView) getView(R.id.iv_shopcar_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = model.getNum();
                if (count > 1)
                    count--;
                else
                    Toast.makeText(delete.getContext(), "数量不能为0", Toast.LENGTH_SHORT).show();
                model.setNum(count);
                adapter.notifyDataSetChanged();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = model.getNum();
                count++;
                model.setNum(count);
                adapter.notifyDataSetChanged();
            }
        });
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean select = model.isSelect();
                select = !select;
                model.setSelect(select);
                adapter.notifyDataSetChanged();
            }
        });
        boolean select = model.isSelect();
        boolean edit = model.isEdit();
        tvCount.setText("" + model.getNum());
        if (edit) {
            lEdit.setVisibility(View.VISIBLE);
            rShow.setVisibility(View.GONE);
        } else {
            lEdit.setVisibility(View.GONE);
            rShow.setVisibility(View.VISIBLE);
        }

        if (select)
            cb.setImageResource(R.mipmap.hm_shopcar_select);
        else
            cb.setImageResource(R.mipmap.hm_shopcar_noselect);
    }
}
