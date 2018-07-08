package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.HmMainItemStarStudioAdapter;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ItemHmMainStarStudio;
import com.example.huichuanyi.ui.activity.HMWebActivity;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

/**
 * Created by 小五 on 2018/6/26.
 */

public class HmMainItemStarStudioHolder extends BaseViewHolder<ItemHmMainStarStudio> {

    public HmMainItemStarStudioHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final ItemHmMainStarStudio model, int position, MultiTypeAdapter adapter) {
        final RollPagerView pager = (RollPagerView) getView(R.id.roll_hmmain_kind);
        HmMainItemStarStudioAdapter adapter1 = new HmMainItemStarStudioAdapter(pager, model.getmData(), pager.getContext());
        pager.setAdapter(adapter1);
        pager.setHintView(new ColorPointHintView(pager.getContext(), Color.parseColor("#00ac0000"), Color.parseColor("#00ac0000")));
        pager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ItemHmMainStarStudio.DataBean banner = model.getmData().get(position);
                String type = banner.clickType;
                switch (type) {
                    case "1":
                        Context context = pager.getContext();
                        Intent intent = new Intent(context, HMWebActivity.class);
                        String web_url = banner.clickUrl;
                        String share_name = banner.bannerName;
                        String share_url = banner.shareUrl;
                        intent.putExtra("hm_adpage_webview_url", web_url);
                        intent.putExtra("hm_activity_name", share_name);
                        intent.putExtra("hm_adpage_share_url", share_url);
                        context.startActivity(intent);
                        break;
                }
            }
        });
    }
}
