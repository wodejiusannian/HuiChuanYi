package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.LyItemSort;
import com.example.huichuanyi.ui.activity.lanyang.LyShopListActivity;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class LyItemSortHolder extends BaseViewHolder<LyItemSort> {

    public LyItemSortHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final LyItemSort model, int position, MultiTypeAdapter adapter) {
        final RelativeLayout root = (RelativeLayout) getView(R.id.rl_item_ly_sort_root);
        TextView tv = (TextView) getView(R.id.tv_item_lysort_name);
        tv.setText(model.getName());
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = root.getContext();
                Intent intent = new Intent(context, LyShopListActivity.class);
                intent.putExtra("pos", model.getPos());
                context.startActivity(intent);
            }
        });
    }
}
