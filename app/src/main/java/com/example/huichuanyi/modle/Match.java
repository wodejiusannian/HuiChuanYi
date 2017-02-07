package com.example.huichuanyi.modle;

import java.util.List;

public class Match {

    /**
     * environment : 7
     * experience : 6
     * fourpic : https://www.baidu.com/img/bd_logo1.png
     * getlogopic : https://www.baidu.com/img/bd_logo1.png
     * getshowpic : https://www.baidu.com/img/bd_logo1.png
     * id :
     * onepic : https://www.baidu.com/img/bd_logo1.png
     * threepic : https://www.baidu.com/img/bd_logo1.png
     * time : 5
     * twopic : https://www.baidu.com/img/bd_logo1.png
     * userid :
     * wardrobe_id :
     * wardrobe_name :
     */

    private List<EvaluatesBean> evaluates;

    public List<EvaluatesBean> getEvaluates() {
        return evaluates;
    }

    public void setEvaluates(List<EvaluatesBean> evaluates) {
        this.evaluates = evaluates;
    }

    public static class EvaluatesBean {
        private String environment;
        private String experience;
        private String fourpic;
        private String getlogopic;
        private String getshowpic;
        private String id;
        private String onepic;
        private String threepic;
        private String time;
        private String twopic;
        private String userid;
        private String wardrobe_id;
        private String wardrobe_name;

        public String getEnvironment() {
            return environment;
        }

        public void setEnvironment(String environment) {
            this.environment = environment;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getFourpic() {
            return fourpic;
        }

        public void setFourpic(String fourpic) {
            this.fourpic = fourpic;
        }

        public String getGetlogopic() {
            return getlogopic;
        }

        public void setGetlogopic(String getlogopic) {
            this.getlogopic = getlogopic;
        }

        public String getGetshowpic() {
            return getshowpic;
        }

        public void setGetshowpic(String getshowpic) {
            this.getshowpic = getshowpic;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOnepic() {
            return onepic;
        }

        public void setOnepic(String onepic) {
            this.onepic = onepic;
        }

        public String getThreepic() {
            return threepic;
        }

        public void setThreepic(String threepic) {
            this.threepic = threepic;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTwopic() {
            return twopic;
        }

        public void setTwopic(String twopic) {
            this.twopic = twopic;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getWardrobe_id() {
            return wardrobe_id;
        }

        public void setWardrobe_id(String wardrobe_id) {
            this.wardrobe_id = wardrobe_id;
        }

        public String getWardrobe_name() {
            return wardrobe_name;
        }

        public void setWardrobe_name(String wardrobe_name) {
            this.wardrobe_name = wardrobe_name;
        }
    }
}
