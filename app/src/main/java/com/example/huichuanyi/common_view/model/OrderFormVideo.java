package com.example.huichuanyi.common_view.model;

import com.example.huichuanyi.common_view.Type.TypeFactory;

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
public class OrderFormVideo {
    /**
     * ret : 0
     * msg : SUCCESS
     * body : [{"concessionCode":"QXL","discountExplain":"订单总金额:30.0元;优惠券:QXL;优惠券金额:29.99元;","moneyDiscount":"29.99","orderId":"1526029347332","orderInfo":[{"acceptTime":"","applyRefuseTime":"","completeTime":"","consigneeAddress":"北京市北京市丰台区 赵公口桥南","consigneeName":"小屈","consigneePhone":"17600920338","consigneeTime":"","deleteStatus":"0","deliveryTime":"","evaluateAverage":"","evaluateContent":"","evaluateState":"-1","evaluateTime":"","goodsColor":"","goodsIntroduction":"衣橱管理课程体系","goodsName":"视频课程第1段","goodsPicture":"http://hmyc365.net:8081/file/hm/video/yu_lao_shi/20170622_01_v1.png","goodsPrice":"30","goodsSize":"","id":"259","moneyPay":"0.01","moneyTotal":"30.0","orderNumber":"1","orderRemarkBuyer":"","orderType":"5","payTime":"2018-05-11 17:02:53","payType":"1","recommendDate":"","recommendUserId":"","recommendUserName":"","refundReason":"","refuseTime":"","sellerCityName":"北京市","sellerPhone":"010-87227397","sellerPicture":"http://hmyc365.net/hmyc/file/hm-system/logo-app.png","sellerUserGrade":"","sellerUserId":"0","sellerUserName":"慧美科技","wayCode":"","wayNo":""}]}]
     */

    private String ret;
    private String msg;
    private List<BodyBean> body;



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

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean implements Visitable{
        /**
         * concessionCode : QXL
         * discountExplain : 订单总金额:30.0元;优惠券:QXL;优惠券金额:29.99元;
         * moneyDiscount : 29.99
         * orderId : 1526029347332
         * orderInfo : [{"acceptTime":"","applyRefuseTime":"","completeTime":"","consigneeAddress":"北京市北京市丰台区 赵公口桥南","consigneeName":"小屈","consigneePhone":"17600920338","consigneeTime":"","deleteStatus":"0","deliveryTime":"","evaluateAverage":"","evaluateContent":"","evaluateState":"-1","evaluateTime":"","goodsColor":"","goodsIntroduction":"衣橱管理课程体系","goodsName":"视频课程第1段","goodsPicture":"http://hmyc365.net:8081/file/hm/video/yu_lao_shi/20170622_01_v1.png","goodsPrice":"30","goodsSize":"","id":"259","moneyPay":"0.01","moneyTotal":"30.0","orderNumber":"1","orderRemarkBuyer":"","orderType":"5","payTime":"2018-05-11 17:02:53","payType":"1","recommendDate":"","recommendUserId":"","recommendUserName":"","refundReason":"","refuseTime":"","sellerCityName":"北京市","sellerPhone":"010-87227397","sellerPicture":"http://hmyc365.net/hmyc/file/hm-system/logo-app.png","sellerUserGrade":"","sellerUserId":"0","sellerUserName":"慧美科技","wayCode":"","wayNo":""}]
         */
        @Override
        public int type(TypeFactory typeFactory) {
            return typeFactory.type(this);
        }
        private String concessionCode;
        private String discountExplain;
        private String moneyDiscount;
        private String orderId;
        private List<OrderInfoBean> orderInfo;

        public String getConcessionCode() {
            return concessionCode;
        }

        public void setConcessionCode(String concessionCode) {
            this.concessionCode = concessionCode;
        }

        public String getDiscountExplain() {
            return discountExplain;
        }

        public void setDiscountExplain(String discountExplain) {
            this.discountExplain = discountExplain;
        }

        public String getMoneyDiscount() {
            return moneyDiscount;
        }

        public void setMoneyDiscount(String moneyDiscount) {
            this.moneyDiscount = moneyDiscount;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public List<OrderInfoBean> getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(List<OrderInfoBean> orderInfo) {
            this.orderInfo = orderInfo;
        }

        public static class OrderInfoBean {
            /**
             * acceptTime :
             * applyRefuseTime :
             * completeTime :
             * consigneeAddress : 北京市北京市丰台区 赵公口桥南
             * consigneeName : 小屈
             * consigneePhone : 17600920338
             * consigneeTime :
             * deleteStatus : 0
             * deliveryTime :
             * evaluateAverage :
             * evaluateContent :
             * evaluateState : -1
             * evaluateTime :
             * goodsColor :
             * goodsIntroduction : 衣橱管理课程体系
             * goodsName : 视频课程第1段
             * goodsPicture : http://hmyc365.net:8081/file/hm/video/yu_lao_shi/20170622_01_v1.png
             * goodsPrice : 30
             * goodsSize :
             * id : 259
             * moneyPay : 0.01
             * moneyTotal : 30.0
             * orderNumber : 1
             * orderRemarkBuyer :
             * orderType : 5
             * payTime : 2018-05-11 17:02:53
             * payType : 1
             * recommendDate :
             * recommendUserId :
             * recommendUserName :
             * refundReason :
             * refuseTime :
             * sellerCityName : 北京市
             * sellerPhone : 010-87227397
             * sellerPicture : http://hmyc365.net/hmyc/file/hm-system/logo-app.png
             * sellerUserGrade :
             * sellerUserId : 0
             * sellerUserName : 慧美科技
             * wayCode :
             * wayNo :
             */

            private String acceptTime;
            private String applyRefuseTime;
            private String completeTime;
            private String consigneeAddress;
            private String consigneeName;
            private String consigneePhone;
            private String consigneeTime;
            private String deleteStatus;
            private String deliveryTime;
            private String evaluateAverage;
            private String evaluateContent;
            private String evaluateState;
            private String evaluateTime;
            private String goodsColor;
            private String goodsIntroduction;
            private String goodsName;
            private String goodsPicture;
            private String goodsPrice;
            private String goodsSize;
            private String id;
            private String moneyPay;
            private String moneyTotal;
            private String orderNumber;
            private String orderRemarkBuyer;
            private String orderType;
            private String payTime;
            private String payType;
            private String recommendDate;
            private String recommendUserId;
            private String recommendUserName;
            private String refundReason;
            private String refuseTime;
            private String sellerCityName;
            private String sellerPhone;
            private String sellerPicture;
            private String sellerUserGrade;
            private String sellerUserId;
            private String sellerUserName;
            private String wayCode;
            private String wayNo;

            public String getAcceptTime() {
                return acceptTime;
            }

            public void setAcceptTime(String acceptTime) {
                this.acceptTime = acceptTime;
            }

            public String getApplyRefuseTime() {
                return applyRefuseTime;
            }

            public void setApplyRefuseTime(String applyRefuseTime) {
                this.applyRefuseTime = applyRefuseTime;
            }

            public String getCompleteTime() {
                return completeTime;
            }

            public void setCompleteTime(String completeTime) {
                this.completeTime = completeTime;
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

            public String getDeliveryTime() {
                return deliveryTime;
            }

            public void setDeliveryTime(String deliveryTime) {
                this.deliveryTime = deliveryTime;
            }

            public String getEvaluateAverage() {
                return evaluateAverage;
            }

            public void setEvaluateAverage(String evaluateAverage) {
                this.evaluateAverage = evaluateAverage;
            }

            public String getEvaluateContent() {
                return evaluateContent;
            }

            public void setEvaluateContent(String evaluateContent) {
                this.evaluateContent = evaluateContent;
            }

            public String getEvaluateState() {
                return evaluateState;
            }

            public void setEvaluateState(String evaluateState) {
                this.evaluateState = evaluateState;
            }

            public String getEvaluateTime() {
                return evaluateTime;
            }

            public void setEvaluateTime(String evaluateTime) {
                this.evaluateTime = evaluateTime;
            }

            public String getGoodsColor() {
                return goodsColor;
            }

            public void setGoodsColor(String goodsColor) {
                this.goodsColor = goodsColor;
            }

            public String getGoodsIntroduction() {
                return goodsIntroduction;
            }

            public void setGoodsIntroduction(String goodsIntroduction) {
                this.goodsIntroduction = goodsIntroduction;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsPicture() {
                return goodsPicture;
            }

            public void setGoodsPicture(String goodsPicture) {
                this.goodsPicture = goodsPicture;
            }

            public String getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(String goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public String getGoodsSize() {
                return goodsSize;
            }

            public void setGoodsSize(String goodsSize) {
                this.goodsSize = goodsSize;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getRecommendDate() {
                return recommendDate;
            }

            public void setRecommendDate(String recommendDate) {
                this.recommendDate = recommendDate;
            }

            public String getRecommendUserId() {
                return recommendUserId;
            }

            public void setRecommendUserId(String recommendUserId) {
                this.recommendUserId = recommendUserId;
            }

            public String getRecommendUserName() {
                return recommendUserName;
            }

            public void setRecommendUserName(String recommendUserName) {
                this.recommendUserName = recommendUserName;
            }

            public String getRefundReason() {
                return refundReason;
            }

            public void setRefundReason(String refundReason) {
                this.refundReason = refundReason;
            }

            public String getRefuseTime() {
                return refuseTime;
            }

            public void setRefuseTime(String refuseTime) {
                this.refuseTime = refuseTime;
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

            public String getSellerPicture() {
                return sellerPicture;
            }

            public void setSellerPicture(String sellerPicture) {
                this.sellerPicture = sellerPicture;
            }

            public String getSellerUserGrade() {
                return sellerUserGrade;
            }

            public void setSellerUserGrade(String sellerUserGrade) {
                this.sellerUserGrade = sellerUserGrade;
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

            public String getWayCode() {
                return wayCode;
            }

            public void setWayCode(String wayCode) {
                this.wayCode = wayCode;
            }

            public String getWayNo() {
                return wayNo;
            }

            public void setWayNo(String wayNo) {
                this.wayNo = wayNo;
            }
        }
    }
}
