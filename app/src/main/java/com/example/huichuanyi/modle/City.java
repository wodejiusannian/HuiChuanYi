package com.example.huichuanyi.modle;

import java.util.List;

public class City {




    private String city_name;
    private String sort_type;
    private String ret;
    private String msg;
    private List<BodyBean> body;

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getSort_type() {
        return sort_type;
    }

    public void setSort_type(String sort_type) {
        this.sort_type = sort_type;
    }

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

    public static class BodyBean {
        /**
         * gzs_city : 北京市
         * gzs_fuwu : 已开通
         * gzs_id : 143
         * gzs_jianjie : 我们是慧美衣橱管理专家：会美，学会基础的生活美学，在生活和工作中穿着不出错；汇美，汇集美丽，在各种场合展示独特的魅力，穿出多姿多彩；慧美，智慧管理，穿出自己的格调美。
         我们秉承衣橱管理的理念，整合您的自有衣橱资源，结合您的个性特色和需求，帮助您实现从衣橱管理，到衣橱升级，到智慧衣橱的打造，满足您所希望的365天的精彩。人是没有缺点的，只有特点，我们会让更多的人通过得体的着装，散发出个性的美，实现美的最高境界——格调美。
         * gzs_name : 静静工作室
         * gzs_phone : 13691136934
         * gzs_photo : http://101.201.36.18:8080/images/photo/manager143/d1c8bdbb-f881-4c99-9fba-413f7d4a0ec6.jpg
         * price_baseNum1 : 200
         * price_baseNum2 : null
         * price_basePrice1 : 298
         * price_basePrice2 : null
         * price_raiseNum : 200
         * price_raisePrice : 150
         */

        private String gzs_city;
        private String gzs_fuwu;
        private String gzs_id;
        private String gzs_jianjie;
        private String gzs_name;
        private String gzs_phone;
        private String gzs_photo;
        private String price_baseNum1;
        private String price_baseNum2;
        private String price_basePrice1;
        private String price_basePrice2;
        private String price_raiseNum;
        private String price_raisePrice;

        public String getGzs_city() {
            return gzs_city;
        }

        public void setGzs_city(String gzs_city) {
            this.gzs_city = gzs_city;
        }

        public String getGzs_fuwu() {
            return gzs_fuwu;
        }

        public void setGzs_fuwu(String gzs_fuwu) {
            this.gzs_fuwu = gzs_fuwu;
        }

        public String getGzs_id() {
            return gzs_id;
        }

        public void setGzs_id(String gzs_id) {
            this.gzs_id = gzs_id;
        }

        public String getGzs_jianjie() {
            return gzs_jianjie;
        }

        public void setGzs_jianjie(String gzs_jianjie) {
            this.gzs_jianjie = gzs_jianjie;
        }

        public String getGzs_name() {
            return gzs_name;
        }

        public void setGzs_name(String gzs_name) {
            this.gzs_name = gzs_name;
        }

        public String getGzs_phone() {
            return gzs_phone;
        }

        public void setGzs_phone(String gzs_phone) {
            this.gzs_phone = gzs_phone;
        }

        public String getGzs_photo() {
            return gzs_photo;
        }

        public void setGzs_photo(String gzs_photo) {
            this.gzs_photo = gzs_photo;
        }

        public String getPrice_baseNum1() {
            return price_baseNum1;
        }

        public void setPrice_baseNum1(String price_baseNum1) {
            this.price_baseNum1 = price_baseNum1;
        }

        public String getPrice_baseNum2() {
            return price_baseNum2;
        }

        public void setPrice_baseNum2(String price_baseNum2) {
            this.price_baseNum2 = price_baseNum2;
        }

        public String getPrice_basePrice1() {
            return price_basePrice1;
        }

        public void setPrice_basePrice1(String price_basePrice1) {
            this.price_basePrice1 = price_basePrice1;
        }

        public String getPrice_basePrice2() {
            return price_basePrice2;
        }

        public void setPrice_basePrice2(String price_basePrice2) {
            this.price_basePrice2 = price_basePrice2;
        }

        public String getPrice_raiseNum() {
            return price_raiseNum;
        }

        public void setPrice_raiseNum(String price_raiseNum) {
            this.price_raiseNum = price_raiseNum;
        }

        public String getPrice_raisePrice() {
            return price_raisePrice;
        }

        public void setPrice_raisePrice(String price_raisePrice) {
            this.price_raisePrice = price_raisePrice;
        }
    }
}
