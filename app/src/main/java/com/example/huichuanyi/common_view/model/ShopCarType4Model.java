package com.example.huichuanyi.common_view.model;

import android.os.Parcel;
import android.os.Parcelable;

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
public class ShopCarType4Model implements Visitable, Parcelable {

    public String goodsColor;
    public String goodsId;
    public String goodsName;
    public String goodsPicture;
    public String goodsPrice;
    public String goodsSize;
    public String id;
    public int orderNumber;
    public String orderType;

    public ShopCarType4Model(String goodsColor, String goodsId, String goodsName, String goodsPicture, String goodsPrice, String goodsSize, String id, int orderNumber, String orderType) {
        this.goodsColor = goodsColor;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPicture = goodsPicture;
        this.goodsPrice = goodsPrice;
        this.goodsSize = goodsSize;
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderType = orderType;
    }

    public ShopCarType4Model() {
    }

    protected ShopCarType4Model(Parcel in) {
        goodsColor = in.readString();
        goodsId = in.readString();
        goodsName = in.readString();
        goodsPicture = in.readString();
        goodsPrice = in.readString();
        goodsSize = in.readString();
        id = in.readString();
        orderNumber = in.readInt();
        orderType = in.readString();
    }

    public static final Creator<ShopCarType4Model> CREATOR = new Creator<ShopCarType4Model>() {
        @Override
        public ShopCarType4Model createFromParcel(Parcel in) {
            return new ShopCarType4Model(in);
        }

        @Override
        public ShopCarType4Model[] newArray(int size) {
            return new ShopCarType4Model[size];
        }
    };

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(goodsColor);
        dest.writeString(goodsId);
        dest.writeString(goodsName);
        dest.writeString(goodsPicture);
        dest.writeString(goodsPrice);
        dest.writeString(goodsSize);
        dest.writeString(id);
        dest.writeInt(orderNumber);
        dest.writeString(orderType);
    }
}
