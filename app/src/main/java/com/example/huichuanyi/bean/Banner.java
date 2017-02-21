package com.example.huichuanyi.bean;

import java.util.List;

public class Banner {



    private int retcode;


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
