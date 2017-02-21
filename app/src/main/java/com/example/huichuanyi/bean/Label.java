package com.example.huichuanyi.bean;

import java.util.List;

/**
 * Created by 小五 on 2017/1/5.
 */
public class Label {

    private List<CloTypeBean> cloType;
    private List<CloStyleBean> cloStyle;
    private List<CloQualityBean> cloQuality;

    public List<CloTypeBean> getCloType() {
        return cloType;
    }

    public void setCloType(List<CloTypeBean> cloType) {
        this.cloType = cloType;
    }

    public List<CloStyleBean> getCloStyle() {
        return cloStyle;
    }

    public void setCloStyle(List<CloStyleBean> cloStyle) {
        this.cloStyle = cloStyle;
    }

    public List<CloQualityBean> getCloQuality() {
        return cloQuality;
    }

    public void setCloQuality(List<CloQualityBean> cloQuality) {
        this.cloQuality = cloQuality;
    }

    public static class CloTypeBean {
        /**
         * cloType_id : 1
         * cloType_name : 上衣
         */

        private String cloType_id;
        private String cloType_name;

        public String getCloType_id() {
            return cloType_id;
        }

        public void setCloType_id(String cloType_id) {
            this.cloType_id = cloType_id;
        }

        public String getCloType_name() {
            return cloType_name;
        }

        public void setCloType_name(String cloType_name) {
            this.cloType_name = cloType_name;
        }
    }

    public static class CloStyleBean {
        /**
         * cloStyle_id : 4
         * cloStyle_name : 西服套装
         * cloType_id : 1
         */

        private String cloStyle_id;
        private String cloStyle_name;
        private String cloType_id;

        public String getCloStyle_id() {
            return cloStyle_id;
        }

        public void setCloStyle_id(String cloStyle_id) {
            this.cloStyle_id = cloStyle_id;
        }

        public String getCloStyle_name() {
            return cloStyle_name;
        }

        public void setCloStyle_name(String cloStyle_name) {
            this.cloStyle_name = cloStyle_name;
        }

        public String getCloType_id() {
            return cloType_id;
        }

        public void setCloType_id(String cloType_id) {
            this.cloType_id = cloType_id;
        }
    }

    public static class CloQualityBean {
        /**
         * cloQuality_id : 1
         * cloQuality_name : 棉
         */

        private String cloQuality_id;
        private String cloQuality_name;

        public String getCloQuality_id() {
            return cloQuality_id;
        }

        public void setCloQuality_id(String cloQuality_id) {
            this.cloQuality_id = cloQuality_id;
        }

        public String getCloQuality_name() {
            return cloQuality_name;
        }

        public void setCloQuality_name(String cloQuality_name) {
            this.cloQuality_name = cloQuality_name;
        }
    }
}
