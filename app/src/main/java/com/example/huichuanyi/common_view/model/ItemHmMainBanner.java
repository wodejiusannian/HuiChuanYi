package com.example.huichuanyi.common_view.model;

import com.example.huichuanyi.common_view.Type.TypeFactory;

import java.util.List;

/**
 * Created by 小五 on 2018/6/26.
 */

public class ItemHmMainBanner implements Visitable {
    private List<Banner> mData;

    public ItemHmMainBanner(List<Banner> mData) {
        this.mData = mData;
    }

    public List<Banner> getmData() {
        return mData;
    }

    public void setmData(List<Banner> mData) {
        this.mData = mData;
    }

    public static class Banner{
        public String bannerName;
        public String clickType;
        public String clickUrl;
        public String pictureUrl;
        public String shareUrl;
    }
    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
