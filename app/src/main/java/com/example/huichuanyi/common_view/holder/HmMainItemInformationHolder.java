package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ItemHmMainInformation;
import com.example.huichuanyi.custom.marqueeview.MarqueeView;
import com.example.huichuanyi.ui.activity.HMWebActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小五 on 2018/6/26.
 */

public class HmMainItemInformationHolder extends BaseViewHolder<ItemHmMainInformation> {

    public HmMainItemInformationHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final ItemHmMainInformation model, int position, MultiTypeAdapter adapter) {
        MarqueeView textView = (MarqueeView) getView(R.id.mv_hmmain_information);
        List<String> strData = new ArrayList<>();
        List<ItemHmMainInformation.DataBean> dataBeen = model.getmData();
        strData.clear();
        for (int i = 0; i < dataBeen.size(); i++) {
            strData.add(dataBeen.get(i).bannerName);
        }
        textView.startWithList(strData);
        textView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, TextView textView) {
                ItemHmMainInformation.DataBean banner = model.getmData().get(pos);
                String type = banner.clickType;
                switch (type) {
                    case "1":
                        Context context = textView.getContext();
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
