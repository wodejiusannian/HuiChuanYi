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
public class LyMain {

    /**
     * ret : 0
     * body : [{"click_url":"","supplier_id":1,"brand":"美藤果","click_type":0,"pic_url":"1"},{"click_url":"","supplier_id":2,"brand":"每日氢元素","click_type":0,"pic_url":"1"},{"click_url":"","supplier_id":3,"brand":"防辐射","click_type":0,"pic_url":"1"},{"click_url":"WWW.BAIDU.COM","supplier_id":4,"brand":"RTC","click_type":1,"pic_url":"1"}]
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

    public static class BodyBean implements Visitable{
        /**
         * click_url :
         * supplier_id : 1
         * brand : 美藤果
         * click_type : 0
         * pic_url : 1
         */

        private String click_url;
        private int supplier_id;
        private String brand;
        private int click_type;
        private String pic_url;

        public String getClick_url() {
            return click_url;
        }

        public void setClick_url(String click_url) {
            this.click_url = click_url;
        }

        public int getSupplier_id() {
            return supplier_id;
        }

        public void setSupplier_id(int supplier_id) {
            this.supplier_id = supplier_id;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public int getClick_type() {
            return click_type;
        }

        public void setClick_type(int click_type) {
            this.click_type = click_type;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        @Override
        public int type(TypeFactory typeFactory) {
            return typeFactory.type(this);
        }
    }
}
