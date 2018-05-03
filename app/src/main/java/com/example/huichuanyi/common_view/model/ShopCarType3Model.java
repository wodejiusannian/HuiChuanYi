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
public class ShopCarType3Model implements Visitable {
    public boolean isCheck;
    /*"goodsColor": "规格:168元/盒",
    "goodsId": "8",
    "goodsName": "美藤—净皙皎颜回眸面膜",
    "goodsPicture": "http://hmyc365.net:8082/file/hkj/goods_pic/11.jpg",
    "goodsPrice": "151.20",
    "goodsSize": "品名:美藤回眸面膜",
    "id": "185",
    "orderNumber": "4",
    "orderType": "6"*/
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

    public ShopCarType3Model(String goodsColor, String goodsId, String goodsName, String goodsPicture, String goodsPrice, String goodsSize, String id, int orderNumber, String orderType, int Tag,boolean isShow) {
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
