package com.example.huichuanyi.common_view.model;

import com.example.huichuanyi.bean.Banner;
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
public class SlwTwoModel implements Visitable {

    public List<Banner> data;

    public SlwTwoModel(List<Banner> data) {
        this.data = data;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }


    /*public static class Banner {
        public String pic_url;
        public String share_name;
        public String share_url;
        public int type;
        public String web_url;

        public Banner(String pic_url, String share_name, String share_url, int type, String web_url) {
            this.pic_url = pic_url;
            this.share_name = share_name;
            this.share_url = share_url;
            this.type = type;
            this.web_url = web_url;
        }
    }*/
}
