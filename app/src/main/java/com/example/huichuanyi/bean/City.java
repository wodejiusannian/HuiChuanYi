package com.example.huichuanyi.bean;

import java.util.List;

public class City {

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

    public static class BodyBean {

        private String sex;
        private String phone;
        private String id_number;
        private String jurisdiction;
        private String city;
        private String photo_set;
        private String id;
        private String username;
        private String time;
        private String pwd;
        private String name;
        private String service;
        private String photo_get;
        private String introduction;
        private String base_num1;//基础数量1
        private String base_price1;//基础价格1
        private String base_num2;//基础数量2
        private String base_price2;//基础价格2
        private String raise_num;//增加数量
        private String raise_price;//增加价格
        private String price_365;//365购买价格

        public String getSex() {
            return sex;
        }

        public String getRaise_price() {
            return raise_price;
        }

        public void setRaise_price(String raise_price) {
            this.raise_price = raise_price;
        }

        public String getRaise_num() {
            return raise_num;
        }

        public void setRaise_num(String raise_num) {
            this.raise_num = raise_num;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getBase_price1() {
            return base_price1;
        }

        public void setBase_price1(String base_price1) {
            this.base_price1 = base_price1;
        }

        public String getBase_price2() {
            return base_price2;
        }

        public void setBase_price2(String base_price2) {
            this.base_price2 = base_price2;
        }

        public String getId_number() {
            return id_number;
        }

        public void setId_number(String id_number) {
            this.id_number = id_number;
        }

        public String getJurisdiction() {
            return jurisdiction;
        }

        public void setJurisdiction(String jurisdiction) {
            this.jurisdiction = jurisdiction;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPhoto_set() {
            return photo_set;
        }

        public void setPhoto_set(String photo_set) {
            this.photo_set = photo_set;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getPrice_365() {
            return price_365;
        }

        public void setPrice_365(String price_365) {
            this.price_365 = price_365;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getBase_num1() {
            return base_num1;
        }

        public void setBase_num1(String base_num1) {
            this.base_num1 = base_num1;
        }

        public String getBase_num2() {
            return base_num2;
        }

        public void setBase_num2(String base_num2) {
            this.base_num2 = base_num2;
        }

        public String getPhoto_get() {
            return photo_get;
        }

        public void setPhoto_get(String photo_get) {
            this.photo_get = photo_get;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }
    }
}
