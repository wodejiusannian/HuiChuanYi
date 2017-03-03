package com.example.huichuanyi.bean;

import java.util.List;

/**
 * Created by 小五 on 2017/2/21.
 */

public class RecordRefresh {
    private String time;
    private List<RefreshBean> list;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<RefreshBean> getList() {
        return list;
    }

    public void setList(List<RefreshBean> list) {
        this.list = list;
    }

    public static class RefreshBean {
        private String clothes_get;
        private String recommend_id;
        private String id;
        private String introduction;
        private String price_dj;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getPrice_dj() {
            return price_dj;
        }

        public void setPrice_dj(String price_dj) {
            this.price_dj = price_dj;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getSize_name() {
            return size_name;
        }

        public void setSize_name(String size_name) {
            this.size_name = size_name;
        }

        private String color;
        private String reason;
        private String size_name;

        public String getRecommend_id() {
            return recommend_id;
        }

        public void setRecommend_id(String recommend_id) {
            this.recommend_id = recommend_id;
        }

        public String getClothes_get() {
            return clothes_get;
        }

        public void setClothes_get(String clothes_get) {
            this.clothes_get = clothes_get;
        }
    }
}
