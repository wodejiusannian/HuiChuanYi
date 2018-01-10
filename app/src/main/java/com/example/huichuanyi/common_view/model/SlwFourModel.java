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
public class SlwFourModel implements Visitable {

    private List<Four> Data;

    public SlwFourModel(List<Four> data) {
        Data = data;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }

    public List<Four> getData() {
        return Data;
    }

    public void setData(List<Four> data) {
        Data = data;
    }

    public static class Four {
        public int resID;
        public int showResID;
        public int type;

        public Four() {
        }

        public Four(int resID, int showResID, int type) {
            this.resID = resID;
            this.showResID = showResID;
            this.type = type;
        }
    }
}
