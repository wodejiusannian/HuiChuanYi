package com.example.huichuanyi.common_view.model;

import com.example.huichuanyi.common_view.Type.TypeFactory;

import java.util.List;

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
public class OrderStudioIntroduceTopModel implements Visitable {
    public String studioName;
    public String studioRealName;
    public String studioIntroduce;
    public String studioLogo;
    public List<String> picList;
    public List<String> txtList;
    public OrderStudioIntroduceTopModel(String studioName, String studioIntroduce, String studioLogo, List<String> picList, String studioRealName,List<String> txtList) {
        this.studioName = studioName;
        this.studioIntroduce = studioIntroduce;
        this.studioLogo = studioLogo;
        this.picList = picList;
        this.studioRealName = studioRealName;
        this.txtList = txtList;
    }

    public OrderStudioIntroduceTopModel() {
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }


}
