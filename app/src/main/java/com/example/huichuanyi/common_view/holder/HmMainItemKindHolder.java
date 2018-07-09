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
import com.example.huichuanyi.fragment_first.SinglePersonActivity;
import com.example.huichuanyi.ui.activity.HomeDaPeiRiJiActivity;
import com.example.huichuanyi.ui.activity.HomeVideoCoverActivity;
import com.example.huichuanyi.ui.activity.HomeWoDeYiChuActivity;
import com.example.huichuanyi.ui.activity.lanyang.LyMainActivity;
import com.example.huichuanyi.ui.activity.lanyang.RTCWebActivity;
import com.example.huichuanyi.ui.newpage.HMURL2Activity;
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
        final List<ItemHmMainKind.DataBean> dataList = model.mData;
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
                    String idd = dataList.get(position).id;
                    switch (idd) {
                        case "1":
                            //电子衣橱
                            context.startActivity(new Intent(context, HomeWoDeYiChuActivity.class));
                            break;
                        case "2":
                            //上门服务
                            context.startActivity(new Intent(context, HMURL2Activity.class));
                            break;
                        case "3":
                            //365私人衣橱服务
                            context.startActivity(new Intent(context, SinglePersonActivity.class));
                            break;
                        case "4":
                            //慧美微课
                            context.startActivity(new Intent(context, HomeVideoCoverActivity.class));
                            break;
                        case "5":
                            //慧美商城
                            context.startActivity(new Intent(context, HmShopMallActivity.class));
                            break;
                        case "6":
                            //慧美黑科技
                            context.startActivity(new Intent(context, LyMainActivity.class));
                            break;
                        case "7":
                            //搭配日记
                            context.startActivity(new Intent(context, HomeDaPeiRiJiActivity.class));
                            break;
                        case "8":
                            //RTC测试
                            Intent intent = new Intent(context, RTCWebActivity.class);
                            intent.putExtra("brand", "RTC");
                            intent.putExtra("click_url", "http://hmyc365.net:8081/file/html/rtc/index.html");
                            context.startActivity(intent);
                            break;
                        default:
                            break;
                    }
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
