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
public class OrderStudioIntroduceThirdModel implements Visitable {

    public String pj;
    public String count;
    public String orderer;

    public OrderStudioIntroduceThirdModel( String orderer,String count,String pj ) {
        this.pj = pj;
        this.count = count;
        this.orderer = orderer;
    }

    public OrderStudioIntroduceThirdModel() {
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }


}
