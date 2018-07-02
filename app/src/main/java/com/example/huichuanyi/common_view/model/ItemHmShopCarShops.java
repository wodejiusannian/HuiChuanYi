package com.example.huichuanyi.common_view.model;

import com.example.huichuanyi.common_view.Type.TypeFactory;

/**
 * Created by 小五 on 2018/6/26.
 */

public class ItemHmShopCarShops implements Visitable {
    private boolean isSelect;

    private int count;

    private boolean edit;

    private String goodsColor;
    private String goodsId;
    private String goodsName;
    private String goodsPicture;
    private double goodsPrice;
    private String goodsSize;
    private String id;
    private String kindName;

    public ItemHmShopCarShops(boolean isSelect, int count, boolean edit) {
        this.isSelect = isSelect;
        this.count = count;
        this.edit = edit;
    }

    public ItemHmShopCarShops(boolean isSelect, int count, boolean edit, String goodsColor, String goodsId, String goodsName, String goodsPicture, double goodsPrice, String goodsSize, String id, String kindName) {
        this.isSelect = isSelect;
        this.count = count;
        this.edit = edit;
        this.goodsColor = goodsColor;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPicture = goodsPicture;
        this.goodsPrice = goodsPrice;
        this.goodsSize = goodsSize;
        this.id = id;
        this.kindName = kindName;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getGoodsColor() {
        return goodsColor;
    }

    public void setGoodsColor(String goodsColor) {
        this.goodsColor = goodsColor;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPicture() {
        return goodsPicture;
    }

    public void setGoodsPicture(String goodsPicture) {
        this.goodsPicture = goodsPicture;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsSize() {
        return goodsSize;
    }

    public void setGoodsSize(String goodsSize) {
        this.goodsSize = goodsSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
