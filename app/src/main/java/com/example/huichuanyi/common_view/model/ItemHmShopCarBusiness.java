package com.example.huichuanyi.common_view.model;

import com.example.huichuanyi.common_view.Type.TypeFactory;

/**
 * Created by 小五 on 2018/6/26.
 */

public class ItemHmShopCarBusiness implements Visitable {
    private boolean isSelect;
    private String sellerUserName;
    private String sellerUserId;
    private String sellerPicture;
    private String orderType;

    public ItemHmShopCarBusiness(boolean isSelect, String sellerUserName, String sellerUserId, String sellerPicture, String orderType) {
        this.isSelect = isSelect;
        this.sellerUserName = sellerUserName;
        this.sellerUserId = sellerUserId;
        this.sellerPicture = sellerPicture;
        this.orderType = orderType;
    }

    public String getSellerUserName() {
        return sellerUserName;
    }

    public void setSellerUserName(String sellerUserName) {
        this.sellerUserName = sellerUserName;
    }

    public String getSellerUserId() {
        return sellerUserId;
    }

    public void setSellerUserId(String sellerUserId) {
        this.sellerUserId = sellerUserId;
    }

    public String getSellerPicture() {
        return sellerPicture;
    }

    public void setSellerPicture(String sellerPicture) {
        this.sellerPicture = sellerPicture;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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
