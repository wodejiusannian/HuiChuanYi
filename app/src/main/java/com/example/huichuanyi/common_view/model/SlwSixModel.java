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
public class SlwSixModel implements Visitable {
    public String rec_cloName;
    public String rec_cloPic;
    public String rec_reason;
    public String rec_totalNum;

    public SlwSixModel(String rec_cloName, String rec_cloPic, String rec_reason, String rec_totalNum) {
        this.rec_cloName = rec_cloName;
        this.rec_cloPic = rec_cloPic;
        this.rec_reason = rec_reason;
        this.rec_totalNum = rec_totalNum;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
