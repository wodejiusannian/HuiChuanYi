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

    private String title;

    public SlwFourModel(List<Four> data, String title) {
        Data = data;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        public String title;
        public int count;

        public Four() {
        }

        public Four(int resID, int showResID, int type, String title, int count) {
            this.resID = resID;
            this.showResID = showResID;
            this.type = type;
            this.title = title;
            this.count = count;
        }
    }
}
