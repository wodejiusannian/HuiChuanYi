package com.example.huichuanyi.common_view.model;

import com.example.huichuanyi.common_view.Type.TypeFactory;

/**
 * Created by 小五 on 2018/7/6.
 */

public class ItemShopCarNoRecommend implements Visitable{
    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
