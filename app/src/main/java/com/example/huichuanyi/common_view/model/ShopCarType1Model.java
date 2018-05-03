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
public class ShopCarType1Model implements Visitable {

    public boolean isCheck;

    public String sellerPicture;
    public String sellerUserId;
    public String sellerUserName;

    public int Tag;

    public ShopCarType1Model(String sellerPicture, String sellerUserId, String sellerUserName, int Tag) {
        this.sellerPicture = sellerPicture;
        this.sellerUserId = sellerUserId;
        this.sellerUserName = sellerUserName;
        this.Tag = Tag;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
