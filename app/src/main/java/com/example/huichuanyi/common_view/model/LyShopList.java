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
public class LyShopList {

    /**
     * ret : 0
     * body : [{"price":18,"goods_id":3,"name":"商品3","pic_url":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505394743687&di=29ead24f90cfb862e9aed0dfe25edc2d&imgtype=0&src=http%3A%2F%2Fpic39.nipic.com%2F20140328%2F18181467_093229522197_2.jpg"},{"price":5.880000114440918,"goods_id":4,"name":"商品4","pic_url":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505394743684&di=c8c0476a900a3b3483317ddcf8eb4398&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F01%2F08%2F46%2F58d4b35b384e0.png"}]
     * msg : SUCCESS
     */

    private String ret;
    private String msg;
    private List<BodyBean> body;



    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean implements Visitable {


        private String price;
        private int goods_id;
        private String name;
        private String pic_url;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        @Override
        public int type(TypeFactory typeFactory) {
            return typeFactory.type(this);
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }
    }
}
