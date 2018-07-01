package com.example.huichuanyi.common_view.holder;

import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ItemHmMainInformation;
import com.example.huichuanyi.custom.marqueeview.MarqueeView;

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
    public void setUpView(ItemHmMainInformation model, int position, MultiTypeAdapter adapter) {
        MarqueeView textView = (MarqueeView) getView(R.id.mv_hmmain_information);
        List<String> strData = new ArrayList<>();
        List<ItemHmMainInformation.DataBean> dataBeen = model.getmData();
        for(int i = 0;i<dataBeen.size();i++){
            strData.add(dataBeen.get(i).bannerName);
        }
        textView.startWithList(strData);
    }
}
