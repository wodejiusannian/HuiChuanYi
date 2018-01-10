package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.LyMain;
import com.example.huichuanyi.ui.activity.lanyang.LyShopListActivity;
import com.example.huichuanyi.ui.activity.lanyang.RTCWebActivity;

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
public class LyMainHolder extends BaseViewHolder<LyMain.BodyBean> {

    public LyMainHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final LyMain.BodyBean model, int position, final MultiTypeAdapter adapter) {
        ImageView view = (ImageView) getView(R.id.iv_item_ly_main_sort);
        final Context context = view.getContext();
        Glide.with(context).load(model.getPic_url()).into(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int click_type = model.getClick_type();
                switch (click_type) {
                    case 0:
                        Intent intent = new Intent(context, LyShopListActivity.class);
                        intent.putExtra("supplier_id", model.getSupplier_id());
                        intent.putExtra("brand", model.getBrand());
                        context.startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(context, RTCWebActivity.class);
                        intent2.putExtra("supplier_id", model.getSupplier_id());
                        intent2.putExtra("brand", model.getBrand());
                        intent2.putExtra("click_url", model.getClick_url());
                        context.startActivity(intent2);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
