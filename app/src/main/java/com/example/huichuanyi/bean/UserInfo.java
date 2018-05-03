package com.example.huichuanyi.bean;

// ┏┓　　　┏┓
// ┏┛┻━━━┛┻┓
// ┃　　　　　　　┃ 　
// ┃　　　━　　　┃
// ┃　┳┛　┗┳　┃
// ┃　　　　　　　┃
// ┃　　　┻　　　┃
// ┃　　　　　　　┃
// ┗━┓　　　┏━┛
// ┃　　　┃ 神兽保佑　　　　　　　　
// ┃　　　┃ 代码无BUG！
// ┃　　　┗━━━┓
// ┃　　　　　　　┣┓
// ┃　　　　　　　┏┛
// ┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
// ┗┻┛　┗┻┛
public class UserInfo {


    /**
     * ret : 0
     * msg : SUCCESS
     * body : {"addTime":"2018-03-09 15:18:36.0","concessionCode":"AYWLl4U9","concessionMoney":"298","deleteStatus":"1","managerGrade":"2","managerName":"熊宝宝工作室","managerUrl":"http://hmyc365.net:8084/file/pic/45/15114236564651.jpg","managerUserId":"45","timeEnd":"2018-06-07 15:18:36","timeStart":"2018-03-09 15:18:36","userCharactor":"可爱|精致","userCity":"北京","userId":"3348","userName":"看看","userOccupation":"看看","userPic":"http://hmyc365.net:8080/images/photo/user3348/feec047c-e81c-4559-acf7-1421e824c4c2.jpg","vipEndDate":"2019-01-11"}
     */

    private String ret;
    private String msg;
    private BodyBean body;

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

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * addTime : 2018-03-09 15:18:36.0
         * concessionCode : AYWLl4U9
         * concessionMoney : 298
         * deleteStatus : 1
         * managerGrade : 2
         * managerName : 熊宝宝工作室
         * managerUrl : http://hmyc365.net:8084/file/pic/45/15114236564651.jpg
         * managerUserId : 45
         * timeEnd : 2018-06-07 15:18:36
         * timeStart : 2018-03-09 15:18:36
         * userCharactor : 可爱|精致
         * userCity : 北京
         * userId : 3348
         * userName : 看看
         * userOccupation : 看看
         * userPic : http://hmyc365.net:8080/images/photo/user3348/feec047c-e81c-4559-acf7-1421e824c4c2.jpg
         * vipEndDate : 2019-01-11
         */

        private String addTime;
        private String concessionCode;
        private String concessionMoney;
        private String deleteStatus;
        private String managerGrade;
        private String managerName;
        private String managerUrl;
        private String managerUserId;
        private String timeEnd;
        private String timeStart;
        private String userCharactor;
        private String userCity;
        private String userId;
        private String userName;
        private String userOccupation;
        private String userPic;
        private String vipEndDate;

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getConcessionCode() {
            return concessionCode;
        }

        public void setConcessionCode(String concessionCode) {
            this.concessionCode = concessionCode;
        }

        public String getConcessionMoney() {
            return concessionMoney;
        }

        public void setConcessionMoney(String concessionMoney) {
            this.concessionMoney = concessionMoney;
        }

        public String getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(String deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public String getManagerGrade() {
            return managerGrade;
        }

        public void setManagerGrade(String managerGrade) {
            this.managerGrade = managerGrade;
        }

        public String getManagerName() {
            return managerName;
        }

        public void setManagerName(String managerName) {
            this.managerName = managerName;
        }

        public String getManagerUrl() {
            return managerUrl;
        }

        public void setManagerUrl(String managerUrl) {
            this.managerUrl = managerUrl;
        }

        public String getManagerUserId() {
            return managerUserId;
        }

        public void setManagerUserId(String managerUserId) {
            this.managerUserId = managerUserId;
        }

        public String getTimeEnd() {
            return timeEnd;
        }

        public void setTimeEnd(String timeEnd) {
            this.timeEnd = timeEnd;
        }

        public String getTimeStart() {
            return timeStart;
        }

        public void setTimeStart(String timeStart) {
            this.timeStart = timeStart;
        }

        public String getUserCharactor() {
            return userCharactor;
        }

        public void setUserCharactor(String userCharactor) {
            this.userCharactor = userCharactor;
        }

        public String getUserCity() {
            return userCity;
        }

        public void setUserCity(String userCity) {
            this.userCity = userCity;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserOccupation() {
            return userOccupation;
        }

        public void setUserOccupation(String userOccupation) {
            this.userOccupation = userOccupation;
        }

        public String getUserPic() {
            return userPic;
        }

        public void setUserPic(String userPic) {
            this.userPic = userPic;
        }

        public String getVipEndDate() {
            return vipEndDate;
        }

        public void setVipEndDate(String vipEndDate) {
            this.vipEndDate = vipEndDate;
        }
    }
}
