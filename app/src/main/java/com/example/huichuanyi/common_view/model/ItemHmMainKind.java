package com.example.huichuanyi.common_view.model;

import com.example.huichuanyi.common_view.Type.TypeFactory;

import java.util.List;

/**
 * Created by 小五 on 2018/6/26.
 */

public class ItemHmMainKind implements Visitable {

    public List<DataBean> mData;

    public ItemHmMainKind(List<DataBean> mData) {
        this.mData = mData;
    }

    public static class DataBean{
        public String clickUrl;
        public String id;
        public String name;
        public String pictureUrl;
    }
    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
