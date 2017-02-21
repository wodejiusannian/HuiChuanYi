package com.example.huichuanyi.bean;

import java.util.List;

public class Video {


    

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String buynum;
        private String geticon;
        private String getpath;
        private String id;
        private String introduce;
        private String length;
        private String money;
        private String name;
        private String playnum;
        private String praise;
        private String time;
        private String userid;
        public boolean isChecked;
        private String makeuser;
        private String linkurl;

        public String getLinkurl() {
            return linkurl;
        }

        public void setLinkurl(String linkurl) {
            this.linkurl = linkurl;
        }

        public String getMakeuser() {
            return makeuser;
        }

        public void setMakeuser(String makeuser) {
            this.makeuser = makeuser;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public String getBuynum() {
            return buynum;
        }

        public void setBuynum(String buynum) {
            this.buynum = buynum;
        }

        public String getGeticon() {
            return geticon;
        }

        public void setGeticon(String geticon) {
            this.geticon = geticon;
        }

        public String getGetpath() {
            return getpath;
        }

        public void setGetpath(String getpath) {
            this.getpath = getpath;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlaynum() {
            return playnum;
        }

        public void setPlaynum(String playnum) {
            this.playnum = playnum;
        }

        public String getPraise() {
            return praise;
        }

        public void setPraise(String praise) {
            this.praise = praise;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
