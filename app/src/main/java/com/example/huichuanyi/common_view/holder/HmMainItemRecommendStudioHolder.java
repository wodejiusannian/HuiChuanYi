package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ItemHmMainRecommendStudio;
import com.example.huichuanyi.common_view.model.Visitable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小五 on 2018/6/26.
 */

public class HmMainItemRecommendStudioHolder extends BaseViewHolder<ItemHmMainRecommendStudio> {

    public HmMainItemRecommendStudioHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(ItemHmMainRecommendStudio model, int position, MultiTypeAdapter adapter) {
        RecyclerView scrollRecommendStudio = (RecyclerView) getView(R.id.rv_hmmain_recommendstudio);
        ImageView havaData = (ImageView) getView(R.id.have_data);
        if (model.getmData()!=null && model.getmData().size()>0){
            havaData.setVisibility(View.GONE);
        }else{
            havaData.setVisibility(View.VISIBLE);
        }
        Context context = scrollRecommendStudio.getContext();
        scrollRecommendStudio.setLayoutManager(new GridLayoutManager(context, 1, LinearLayoutManager.HORIZONTAL, false));
        List<Visitable> mData = new ArrayList<>();
        MultiTypeAdapter ad =  new MultiTypeAdapter(mData);
        mData.addAll(model.getmData());
        scrollRecommendStudio.setAdapter(ad);
    }
}
