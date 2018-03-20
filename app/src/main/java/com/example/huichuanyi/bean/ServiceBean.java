package com.example.huichuanyi.bean;

import java.io.Serializable;
import java.util.List;

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
public class ServiceBean {

    private List<BodyBean> body;

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean implements Serializable{
        /**
         * buyUserId : 81
         * buyUserName :
         * consigneeAddress : 北京市,北京市,大兴区 亦庄
         * consigneeName : 屈小磊
         * consigneePhone : 18010493269
         * consigneeTime : 2017-12-20 下午
         * deleteStatus : 12
         * discountExplain :
         * evaluateState : 0
         * id : 1209
         * moneyDiscount : 0.00
         * moneyPay : 0.01
         * moneyTotal : 0.01
         * orderId : sm_151364901315
         * orderIdBcj :
         * orderNumber : 200
         * orderRemarkBuyer :
         * orderType : 3
         * payTime : 2017-12-19 10:03:50
         * payType : 2
         * sellerCityName : 北京市
         * sellerPhone : 15210675351
         * sellerUserId : 250
         * sellerUserName : 小鱼工作室
         */

        private String buyUserId;
        private String buyUserName;
        private String consigneeAddress;
        private String consigneeName;
        private String consigneePhone;
        private String consigneeTime;
        private String deleteStatus;
        private String discountExplain;
        private String evaluateState;
        private String id;
        private String moneyDiscount;
        private String moneyPay;
        private String moneyTotal;
        private String orderId;
        private String orderIdBcj;
        private String orderNumber;
        private String orderRemarkBuyer;
        private String orderType;
        private String payTime;
        private String payType;
        private String sellerCityName;
        private String sellerPhone;
        private String sellerUserId;
        private String sellerUserName;

        public String getBuyUserId() {
            return buyUserId;
        }

        public void setBuyUserId(String buyUserId) {
            this.buyUserId = buyUserId;
        }

        public String getBuyUserName() {
            return buyUserName;
        }

        public void setBuyUserName(String buyUserName) {
            this.buyUserName = buyUserName;
        }

        public String getConsigneeAddress() {
            return consigneeAddress;
        }

        public void setConsigneeAddress(String consigneeAddress) {
            this.consigneeAddress = consigneeAddress;
        }

        public String getConsigneeName() {
            return consigneeName;
        }

        public void setConsigneeName(String consigneeName) {
            this.consigneeName = consigneeName;
        }

        public String getConsigneePhone() {
            return consigneePhone;
        }

        public void setConsigneePhone(String consigneePhone) {
            this.consigneePhone = consigneePhone;
        }

        public String getConsigneeTime() {
            return consigneeTime;
        }

        public void setConsigneeTime(String consigneeTime) {
            this.consigneeTime = consigneeTime;
        }

        public String getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(String deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public String getDiscountExplain() {
            return discountExplain;
        }

        public void setDiscountExplain(String discountExplain) {
            this.discountExplain = discountExplain;
        }

        public String getEvaluateState() {
            return evaluateState;
        }

        public void setEvaluateState(String evaluateState) {
            this.evaluateState = evaluateState;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMoneyDiscount() {
            return moneyDiscount;
        }

        public void setMoneyDiscount(String moneyDiscount) {
            this.moneyDiscount = moneyDiscount;
        }

        public String getMoneyPay() {
            return moneyPay;
        }

        public void setMoneyPay(String moneyPay) {
            this.moneyPay = moneyPay;
        }

        public String getMoneyTotal() {
            return moneyTotal;
        }

        public void setMoneyTotal(String moneyTotal) {
            this.moneyTotal = moneyTotal;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderIdBcj() {
            return orderIdBcj;
        }

        public void setOrderIdBcj(String orderIdBcj) {
            this.orderIdBcj = orderIdBcj;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getOrderRemarkBuyer() {
            return orderRemarkBuyer;
        }

        public void setOrderRemarkBuyer(String orderRemarkBuyer) {
            this.orderRemarkBuyer = orderRemarkBuyer;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getSellerCityName() {
            return sellerCityName;
        }

        public void setSellerCityName(String sellerCityName) {
            this.sellerCityName = sellerCityName;
        }

        public String getSellerPhone() {
            return sellerPhone;
        }

        public void setSellerPhone(String sellerPhone) {
            this.sellerPhone = sellerPhone;
        }

        public String getSellerUserId() {
            return sellerUserId;
        }

        public void setSellerUserId(String sellerUserId) {
            this.sellerUserId = sellerUserId;
        }

        public String getSellerUserName() {
            return sellerUserName;
        }

        public void setSellerUserName(String sellerUserName) {
            this.sellerUserName = sellerUserName;
        }
    }
}
