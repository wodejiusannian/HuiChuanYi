package com.example.huichuanyi.common_view.model;

import com.example.huichuanyi.common_view.Type.TypeFactory;

import java.util.List;

/**
 * Created by 小五 on 2018/6/26.
 */

public class ItemHmMainRecommendStudio implements Visitable {
    private List<ItemHmMainRecommendStudio.DataBean> mData;

    public ItemHmMainRecommendStudio(List<ItemHmMainRecommendStudio.DataBean> mData) {
        this.mData = mData;
    }

    public List<ItemHmMainRecommendStudio.DataBean> getmData() {
        return mData;
    }

    public void setmData(List<ItemHmMainRecommendStudio.DataBean> mData) {
        this.mData = mData;
    }

    public static class DataBean implements Visitable{
        public String appExtensionIntro;
        public String headPicture;
        public String name;
        public String userId;

        @Override
        public int type(TypeFactory typeFactory) {
            return typeFactory.type(this);
        }
    }
    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
