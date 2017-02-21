package com.example.huichuanyi.bean;

import java.io.Serializable;
import java.util.List;

public class MyClothess {


    /**
     * ret : 0
     * msg :
     * body : {"clothesInfo":[{"clothes_brand":"","clothes_buyTime":"","clothes_caizhi":"","clothes_id":"2409","clothes_location":"","clothes_pic":"http://192.168.1.116:8080/images/photo/user81/clothes/1484796697524_0.png","clothes_price":"","clothes_season":"","clothes_situation":"社交","clothes_size":"","clothes_styleId":"32","clothes_typeId":"3","clothes_uploadTime":"2017-01-19 11:31:18"},{"clothes_brand":"","clothes_buyTime":"","clothes_caizhi":"","clothes_id":"2411","clothes_location":"","clothes_pic":"http://192.168.1.116:8080/images/photo/user81/clothes/1484796708713_1.png","clothes_price":"","clothes_season":"","clothes_situation":"商务","clothes_size":"","clothes_styleId":"32","clothes_typeId":"3","clothes_uploadTime":"2017-01-19 11:31:37"}]}
     */

    private String ret;
    private String msg;
    private BodyBean body;

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

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        private List<ClothesInfoBean> clothesInfo;

        public List<ClothesInfoBean> getClothesInfo() {
            return clothesInfo;
        }

        public void setClothesInfo(List<ClothesInfoBean> clothesInfo) {
            this.clothesInfo = clothesInfo;
        }

        public static class ClothesInfoBean implements Serializable{
            /**
             * clothes_brand :
             * clothes_buyTime :
             * clothes_caizhi :
             * clothes_id : 2409
             * clothes_location :
             * clothes_pic : http://192.168.1.116:8080/images/photo/user81/clothes/1484796697524_0.png
             * clothes_price :
             * clothes_season :
             * clothes_situation : 社交
             * clothes_size :
             * clothes_styleId : 32
             * clothes_typeId : 3
             * clothes_uploadTime : 2017-01-19 11:31:18
             */

            private String clothes_brand;
            private String clothes_buyTime;
            private String clothes_caizhi;
            private String clothes_id;
            private String clothes_location;
            private String clothes_pic;
            private String clothes_price;
            private String clothes_season;
            private String clothes_situation;
            private String clothes_size;
            private String clothes_styleId;
            private String clothes_typeId;
            private String clothes_uploadTime;

            public String getClothes_brand() {
                return clothes_brand;
            }

            public void setClothes_brand(String clothes_brand) {
                this.clothes_brand = clothes_brand;
            }

            public String getClothes_buyTime() {
                return clothes_buyTime;
            }

            public void setClothes_buyTime(String clothes_buyTime) {
                this.clothes_buyTime = clothes_buyTime;
            }

            public String getClothes_caizhi() {
                return clothes_caizhi;
            }

            public void setClothes_caizhi(String clothes_caizhi) {
                this.clothes_caizhi = clothes_caizhi;
            }

            public String getClothes_id() {
                return clothes_id;
            }

            public void setClothes_id(String clothes_id) {
                this.clothes_id = clothes_id;
            }

            public String getClothes_location() {
                return clothes_location;
            }

            public void setClothes_location(String clothes_location) {
                this.clothes_location = clothes_location;
            }

            public String getClothes_pic() {
                return clothes_pic;
            }

            public void setClothes_pic(String clothes_pic) {
                this.clothes_pic = clothes_pic;
            }

            public String getClothes_price() {
                return clothes_price;
            }

            public void setClothes_price(String clothes_price) {
                this.clothes_price = clothes_price;
            }

            public String getClothes_season() {
                return clothes_season;
            }

            public void setClothes_season(String clothes_season) {
                this.clothes_season = clothes_season;
            }

            public String getClothes_situation() {
                return clothes_situation;
            }

            public void setClothes_situation(String clothes_situation) {
                this.clothes_situation = clothes_situation;
            }

            public String getClothes_size() {
                return clothes_size;
            }

            public void setClothes_size(String clothes_size) {
                this.clothes_size = clothes_size;
            }

            public String getClothes_styleId() {
                return clothes_styleId;
            }

            public void setClothes_styleId(String clothes_styleId) {
                this.clothes_styleId = clothes_styleId;
            }

            public String getClothes_typeId() {
                return clothes_typeId;
            }

            public void setClothes_typeId(String clothes_typeId) {
                this.clothes_typeId = clothes_typeId;
            }

            public String getClothes_uploadTime() {
                return clothes_uploadTime;
            }

            public void setClothes_uploadTime(String clothes_uploadTime) {
                this.clothes_uploadTime = clothes_uploadTime;
            }
        }
    }
}
