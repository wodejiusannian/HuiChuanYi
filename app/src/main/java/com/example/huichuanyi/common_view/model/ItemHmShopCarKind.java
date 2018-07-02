package com.example.huichuanyi.common_view.model;

import com.example.huichuanyi.common_view.Type.TypeFactory;

/**
 * Created by 小五 on 2018/6/26.
 */

public class ItemHmShopCarKind implements Visitable {

    private String kind;

    private boolean isUpOrDown;

    public ItemHmShopCarKind(String kind, boolean isUpOrDown) {
        this.kind = kind;
        this.isUpOrDown = isUpOrDown;
    }

    public boolean isUpOrDown() {
        return isUpOrDown;
    }

    public void setUpOrDown(boolean upOrDown) {
        isUpOrDown = upOrDown;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }


    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
