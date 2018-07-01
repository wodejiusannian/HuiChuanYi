package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.hmmainkind.GridViewAdapter;
import com.example.huichuanyi.adapter.hmmainkind.MViewPagerAdapter;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ItemHmMainKind;
import com.example.huichuanyi.ui.newpage.HmShopMallActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小五 on 2018/6/26.
 */

public class HmMainItemKindHolder extends BaseViewHolder<ItemHmMainKind> {

    public HmMainItemKindHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(ItemHmMainKind model, int position, MultiTypeAdapter adapter) {
        ViewPager kind = (ViewPager) getView(R.id.vp_hmmain_kind);
        final LinearLayout dots = (LinearLayout) getView(R.id.ll_hmmiankind_dots);
        final Context context = kind.getContext();
        MViewPagerAdapter mAdapter = new MViewPagerAdapter();
        kind.setAdapter(mAdapter);
        List<ItemHmMainKind.DataBean> dataList = model.mData;
        List<GridView> gridList = new ArrayList<>();
        final int pageSize = dataList.size() % 8 == 0 ? dataList.size() / 8 : dataList.size() / 8 + 1;
        for (int i = 0; i < pageSize; i++) {
            if (pageSize > 1) {
                ImageView imageView = new ImageView(context);
                if (i == 0)
                    imageView.setImageResource(R.mipmap.hmmain_kond_select);
                else
                    imageView.setImageResource(R.mipmap.hmmain_kind_noselect);
                dots.addView(imageView);
            }
            GridView gridView = new GridView(context);
            GridViewAdapter adapters = new GridViewAdapter(dataList, i);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    context.startActivity(new Intent(context, HmShopMallActivity.class));
                }
            });
            gridView.setNumColumns(4);
            gridView.setAdapter(adapters);
            gridList.add(gridView);
        }
        mAdapter.add(gridList);
        kind.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
              /*  for (int i = 0; i < pageSize; i++) {
                    ImageView imageView = (ImageView) dots.getChildAt(i);
                    if (position == i) {
                        imageView.setImageResource(R.mipmap.hmmain_kond_select);
                    } else {
                        imageView.setImageResource(R.mipmap.hmmain_kind_noselect);
                    }
                }*/
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
