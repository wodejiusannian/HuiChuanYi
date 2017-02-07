package com.example.huichuanyi.modle;

import java.util.List;

public class Banner{

    /**
     * retcode : 200
     * list : [{"explain":"轮播图1","linkaddress":"https://www.baidu.com/","photopath":"http://pimg1.126.net/movie/product/movie/147487941234210430_260_346_webp.jpg","type":"1"},{"explain":"轮播图2","linkaddress":"https://www.baidu.com/","photopath":"http://pimg1.126.net/movie/product/movie/147589812803610010_webp.jpg","type":"1"}]
     */

    private int retcode;
    /**
     * explain : 轮播图1
     * linkaddress : https://www.baidu.com/
     * photopath : http://pimg1.126.net/movie/product/movie/147487941234210430_260_346_webp.jpg
     * type : 1
     */

    private List<ListBean> list;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String explain;
        private String linkaddress;
        private String photopath;
        private String type;

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getLinkaddress() {
            return linkaddress;
        }

        public void setLinkaddress(String linkaddress) {
            this.linkaddress = linkaddress;
        }

        public String getPhotopath() {
            return photopath;
        }

        public void setPhotopath(String photopath) {
            this.photopath = photopath;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
