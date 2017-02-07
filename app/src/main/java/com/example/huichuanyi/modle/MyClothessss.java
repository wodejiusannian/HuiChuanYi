package com.example.huichuanyi.modle;

import java.io.Serializable;
import java.util.List;

public class MyClothessss {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * clothes_brand :
         * clothes_buyTime :
         * clothes_caizhi : 其他
         * clothes_location :
         * clothes_price :
         * clothes_season : 春
         * clothes_situation : 休闲
         * clothes_size :
         * clothes_styleId : 4
         * clothes_typeId : 1
         * clothes_uploadTime : 2017-01-10 09:35:39
         * color :
         * getphoto : http://192.168.1.184:8080/images/photo/user81/wardrobe1/type1/style4/90807cfe-529e-404e-927c-f2458bd77b55.jpg
         * id : 1455
         * remarks :
         * season :
         * sex :
         * type :
         */

        private String clothes_brand;
        private String clothes_buyTime;
        private String clothes_caizhi;
        private String clothes_location;
        private String clothes_price;
        private String clothes_season;
        private String clothes_situation;
        private String clothes_size;
        private String clothes_styleId;
        private String clothes_typeId;
        private String clothes_uploadTime;
        private String color;
        private String getphoto;
        private String id;
        private String remarks;
        private String season;
        private String sex;
        private String type;

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

        public String getClothes_location() {
            return clothes_location;
        }

        public void setClothes_location(String clothes_location) {
            this.clothes_location = clothes_location;
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

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getGetphoto() {
            return getphoto;
        }

        public void setGetphoto(String getphoto) {
            this.getphoto = getphoto;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getSeason() {
            return season;
        }

        public void setSeason(String season) {
            this.season = season;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
