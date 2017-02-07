package com.example.huichuanyi.modle;

import java.util.List;

public class Match_Popup {

    /**
     * color :
     * getphoto : http://192.168.1.104:8080/HuiMei/images/user47/wardrobe0/type0/dc5a35d8-93bb-41ba-81fc-d7105f52230e.jpg
     * id : 19
     * remarks :
     * season :
     * sex :
     * type :
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String color;
        private String getphoto;
        private String id;
        private String remarks;
        private String season;
        private String sex;
        private String type;

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
