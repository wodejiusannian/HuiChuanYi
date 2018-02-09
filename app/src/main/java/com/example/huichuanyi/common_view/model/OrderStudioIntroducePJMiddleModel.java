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
public class OrderStudioIntroducePJMiddleModel implements Visitable {

    public String content;
    public String number;
    public String orderid;
    public String oth_num;
    public String oth_state;
    public String stars1;
    public String stars2;
    public String stars3;
    public String time;
    public String user_name;
    public String order_name;

    public OrderStudioIntroducePJMiddleModel(String content, String number, String orderid, String oth_num, String oth_state, String stars1, String stars2, String stars3, String time, String user_name, String order_name) {
        this.content = content;
        this.number = number;
        this.orderid = orderid;
        this.oth_num = oth_num;
        this.oth_state = oth_state;
        this.stars1 = stars1;
        this.stars2 = stars2;
        this.stars3 = stars3;
        this.time = time;
        this.user_name = user_name;
        this.order_name = order_name;
    }

    public OrderStudioIntroducePJMiddleModel() {
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }


}
