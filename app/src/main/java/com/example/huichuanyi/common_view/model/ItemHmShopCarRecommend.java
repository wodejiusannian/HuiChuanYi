package com.example.huichuanyi.common_view.model;

import com.example.huichuanyi.common_view.Type.TypeFactory;

/**
 * Created by 小五 on 2018/6/26.
 */

public class ItemHmShopCarRecommend implements Visitable {

    private boolean isSelect;

    public ItemHmShopCarRecommend(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
