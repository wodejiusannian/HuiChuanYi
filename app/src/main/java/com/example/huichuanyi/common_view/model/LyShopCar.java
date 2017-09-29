package com.example.huichuanyi.common_view.model;

import android.os.Parcel;
import android.os.Parcelable;

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
public class LyShopCar {


    /**
     * ret : 0
     * body : [{"id":5,"price_one":18.99,"num":1,"goods_id":1,"goods_name":"商品1","shop_logo":"https://www.baidu.com/img/bd_logo1.png","pic_url":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505394743353&di=2e7a380a87036996b6bbd0beb55215f5&imgtype=0&src=http%3A%2F%2Fpic33.nipic.com%2F20130912%2F2531170_211446713000_2.jpg","shop_name":"黑科技"},{"id":6,"price_one":18,"num":2,"goods_id":3,"goods_name":"商品3","shop_logo":"https://www.baidu.com/img/bd_logo1.png","pic_url":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505394743687&di=29ead24f90cfb862e9aed0dfe25edc2d&imgtype=0&src=http%3A%2F%2Fpic39.nipic.com%2F20140328%2F18181467_093229522197_2.jpg","shop_name":"黑科技"},{"id":3,"price_one":5.88,"num":7,"goods_id":4,"goods_name":"商品4","shop_logo":"https://www.baidu.com/img/bd_logo1.png","pic_url":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505394743684&di=c8c0476a900a3b3483317ddcf8eb4398&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F01%2F08%2F46%2F58d4b35b384e0.png","shop_name":"黑科技"},{"id":4,"price_one":17,"num":5,"goods_id":5,"goods_name":"商品5","shop_logo":"https://www.baidu.com/img/bd_logo1.png","pic_url":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505394743684&di=7d0dc05cacccf2d1c64b0a9a3b7b5152&imgtype=0&src=http%3A%2F%2Fscimg.jb51.net%2Fallimg%2F160324%2F10-160324200219615.jpg","shop_name":"黑科技"}]
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

    public static class BodyBean implements Visitable, Parcelable {
        /**
         * id : 5
         * price_one : 18.99
         * num : 1
         * goods_id : 1
         * goods_name : 商品1
         * shop_logo : https://www.baidu.com/img/bd_logo1.png
         * pic_url : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505394743353&di=2e7a380a87036996b6bbd0beb55215f5&imgtype=0&src=http%3A%2F%2Fpic33.nipic.com%2F20130912%2F2531170_211446713000_2.jpg
         * shop_name : 黑科技
         */

        public boolean select;
        public boolean edit;
        public int id;
        public double price_one;
        public int num;
        public int goods_id;
        public String goods_name;
        public String shop_logo;
        public String pic_url;
        public String shop_name;
        public int stock_id;

        public int getStock_id() {
            return stock_id;
        }

        public void setStock_id(int stock_id) {
            this.stock_id = stock_id;
        }

        public BodyBean() {
        }

        public BodyBean(boolean select, boolean edit, int id, double price_one, int num, int goods_id, String goods_name, String shop_logo, String pic_url, String shop_name) {
            this.select = select;
            this.edit = edit;
            this.id = id;
            this.price_one = price_one;
            this.num = num;
            this.goods_id = goods_id;
            this.goods_name = goods_name;
            this.shop_logo = shop_logo;
            this.pic_url = pic_url;
            this.shop_name = shop_name;
        }

        protected BodyBean(Parcel in) {
            select = in.readByte() != 0;
            edit = in.readByte() != 0;
            id = in.readInt();
            price_one = in.readDouble();
            num = in.readInt();
            goods_id = in.readInt();
            goods_name = in.readString();
            shop_logo = in.readString();
            pic_url = in.readString();
            shop_name = in.readString();
        }

        public static final Creator<BodyBean> CREATOR = new Creator<BodyBean>() {
            @Override
            public BodyBean createFromParcel(Parcel in) {
                return new BodyBean(in);
            }

            @Override
            public BodyBean[] newArray(int size) {
                return new BodyBean[size];
            }
        };

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public boolean isEdit() {
            return edit;
        }

        public void setEdit(boolean edit) {
            this.edit = edit;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getPrice_one() {
            return price_one;
        }

        public void setPrice_one(double price_one) {
            this.price_one = price_one;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        @Override
        public int type(TypeFactory typeFactory) {
            return typeFactory.type(this);
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getShop_logo() {
            return shop_logo;
        }

        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte((byte) (select ? 1 : 0));
            dest.writeByte((byte) (edit ? 1 : 0));
            dest.writeInt(id);
            dest.writeDouble(price_one);
            dest.writeInt(num);
            dest.writeInt(goods_id);
            dest.writeString(goods_name);
            dest.writeString(shop_logo);
            dest.writeString(pic_url);
            dest.writeString(shop_name);
        }
    }
}
