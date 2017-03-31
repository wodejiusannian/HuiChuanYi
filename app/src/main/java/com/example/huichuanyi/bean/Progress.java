package com.example.huichuanyi.bean;

import java.util.List;

public class Progress {

    /**
     * selector_address : 1
     * city : 北京
     * contactnumber : 1
     * id : 14.0
     * manager_photo :
     * managerid : 61
     * managername : 1
     * managernumber : 1
     * money : 1
     * number : 1
     * ordername : 1
     * ordertime : 1
     * remarks :
     * servicemode : 1
     * state : 13
     * time : 1
     * usercode : 61
     * username : 1
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String address;
        private String city;
        private String contactnumber;
        private String id;
        private String manager_photo;
        private String managerid;
        private String managername;
        private String managernumber;
        private String money;
        private String number;
        private String ordername;
        private String ordertime;
        private String remarks;
        private String servicemode;
        private String state;
        private String time;
        private String usercode;
        private String username;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getContactnumber() {
            return contactnumber;
        }

        public void setContactnumber(String contactnumber) {
            this.contactnumber = contactnumber;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getManager_photo() {
            return manager_photo;
        }

        public void setManager_photo(String manager_photo) {
            this.manager_photo = manager_photo;
        }

        public String getManagerid() {
            return managerid;
        }

        public void setManagerid(String managerid) {
            this.managerid = managerid;
        }

        public String getManagername() {
            return managername;
        }

        public void setManagername(String managername) {
            this.managername = managername;
        }

        public String getManagernumber() {
            return managernumber;
        }

        public void setManagernumber(String managernumber) {
            this.managernumber = managernumber;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getOrdername() {
            return ordername;
        }

        public void setOrdername(String ordername) {
            this.ordername = ordername;
        }

        public String getOrdertime() {
            return ordertime;
        }

        public void setOrdertime(String ordertime) {
            this.ordertime = ordertime;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getServicemode() {
            return servicemode;
        }

        public void setServicemode(String servicemode) {
            this.servicemode = servicemode;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUsercode() {
            return usercode;
        }

        public void setUsercode(String usercode) {
            this.usercode = usercode;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
