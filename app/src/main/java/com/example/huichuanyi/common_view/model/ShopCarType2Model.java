package com.example.huichuanyi.common_view.model;

import com.example.huichuanyi.common_view.Type.TypeFactory;

// ┏┓　　　┏┓
// ┏┛┻━━━┛┻┓
// ┃　　　　　　　┃ 　
// ┃　　　━　　　┃
// ┃　┳┛　┗┳　┃
// ┃　　　　　　　┃
// ┃　　　┻　　　┃
// ┃　　　　　　　┃
// ┗━┓　　　┏━┛
// ┃　　　┃ 神兽保佑　　　　　　　　
// ┃　　　┃ 代码无BUG！
// ┃　　　┗━━━┓
// ┃　　　　　　　┣┓
// ┃　　　　　　　┏┛
// ┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
// ┗┻┛　┗┻┛
public class ShopCarType2Model implements Visitable {

    public boolean isCheck;

    public String goodsColor;
    public String goodsId;
    public String goodsName;
    public String goodsPicture;
    public String goodsPrice;
    public String goodsSize;
    public String id;
    public int orderNumber;
    public String orderType;
    public int Tag;
    public boolean isShow;

    public ShopCarType2Model(String goodsColor, String goodsId, String goodsName, String goodsPicture, String goodsPrice, String goodsSize, String id, int orderNumber, String orderType, int Tag, boolean isShow) {
        this.goodsColor = goodsColor;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPicture = goodsPicture;
        this.goodsPrice = goodsPrice;
        this.goodsSize = goodsSize;
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderType = orderType;
        this.Tag = Tag;
        this.isShow = isShow;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
