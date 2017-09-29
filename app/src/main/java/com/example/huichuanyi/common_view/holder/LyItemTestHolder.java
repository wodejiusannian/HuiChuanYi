package com.example.huichuanyi.common_view.holder;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.LyTest;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class LyItemTestHolder extends BaseViewHolder<LyTest> {

    public LyItemTestHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final LyTest model, int position, MultiTypeAdapter adapter) {
        final TextView view = (TextView) getView(R.id.tv_ly_details_text);
        view.setText(model.getStr());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity con = (Activity) view.getContext();
                Intent intent = new Intent();
                intent.putExtra("ids", "不选择管理师");
                con.setResult(100, intent);
                con.finish();
            }
        });
    }
}
