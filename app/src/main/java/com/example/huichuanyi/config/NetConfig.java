package com.example.huichuanyi.config;

public interface NetConfig {
    /*http://hmyc365.net*/
    String BASE_URL = "http://hmyc365.net:8080/HuiMei/";
    String BASE_URL_NEW = "http://hmyc365.net:8081/HM/";
    //获取Bannner轮播图
    String BANNER_ONE = BASE_URL + "picture!getPictures.action";
    //手机号注册的链接
    String SEND_PASSWORD = BASE_URL + "user!addUser.action";
    //手机号和第三方登陆的链接
    String SEND_LOGIN = BASE_URL + "user!getId.action";
    //三方登陆的接口
    String THIRD_LOGIN = BASE_URL + "user!otherLogin.action";
    //三方登陆绑定手机号
    String THIRD_BOUND = BASE_URL + "user!addPhone.action";
    //发送反馈到服务器
    String SEND_FANKUI = BASE_URL + "user!userAdvice.action";
    //管理师详情
    String MANAGER_URL = BASE_URL + "studio!getStudioInfo.action";
    //上传订单详情的接口
    String UPLOADING_COM_DETAILS = BASE_URL + "appOrder!OrderAlipay.action";
    //我的资料的接口
    String ME_INFORMATION = BASE_URL + "user!updateData.action";
    //头像上传接口
    String USER_PHOTO = BASE_URL + "appPhotoUpload!changeHeadP.action";
    //多图上传接口
    String UP_MANY_PICTURE = BASE_URL + "appPhotoUpload!zjUserClothes.action";
    //搭配日记的接口
    String MATCH_DIARY = BASE_URL + "appMatchDiary!getmatchDiary.action";
    //衣服统计接口按款式分
    String STATISTIC_CLOTHES = BASE_URL + "appClothesOpe!getCloTypeCount.action";
    //衣服统计接口按场合分
    String STATISTIC_CLOTHES_TWO = BASE_URL + "appClothesOpe!getCloStuCount.action";
    //新增搭配
    String ADD_MATCH = BASE_URL + "appMatchDiary!addmatchDiary.action";
    //用户获取头像的接口
    String GET_USER_PHOTO = BASE_URL + "appUserInfo!getUserPic.action";
    //修改密码的接口
    String UPDATE_USER_PWD = BASE_URL + "appUserInfo!updateUserPwd.action";
    //支付宝支付的接口
    String ALI_PAY = BASE_URL + "appAlipay!OrderAlipay.action";
    //微信支付的接口
    String WE_CHAT_PAY = "http://192.168.1.143:8080/HM/pay/wxpay/WxPay.do";
    //用户查询订单_进行中
    String MY_ORDER_PROGRESS = BASE_URL + "appOrder!queryOrderA.action";
    //用户查询订单_已完成
    String MY_ORDER_OVER = BASE_URL + "appOrder!queryOrderB.action";
    //申请退款
    String TUI_KUAN = BASE_URL + "appOrder!refundOrder.action";
    //评价的接口
    String EVALUATE_ORDER = BASE_URL + "appOrdEval!addOrdEval.action";
    //补差价接口
    String BU_CHA_JIA = BASE_URL + "appAlipay!othOrderAlipay.action";
    //查看衣服
    String CHA_KAN_YI_FU = BASE_URL + "appClothesOpe!hqUserCloBySx.action";
    //获取衣服
    String GET_CLOTHES_CHA = BASE_URL + "appClothesOpe!hqUserCloBySx.action";
    //移动的接口
    String MOVE_PIC = BASE_URL + "appClothesOpe!moveClothesB.action";
    //删除的接口
    String DELETE_PIC = BASE_URL + "appClothesOpe!moveClothesA.action";
    //判断是否已经注册过
    String IS_REGISTER = BASE_URL + "user!getPhoneId.action";
    //获取用户的资料的接口
    String GET_INFORMATION = BASE_URL + "user!lookupData.action";
    //衣服彻底删除的功能接口
    String THOROUGH_DELETE_CLOTHES = BASE_URL + "appClothesOpe!deleteClothes.action";
    //一键还原的功能
    String RESTORE_ALL = BASE_URL + "appClothesOpe!moveClothesC.action";
    //获取视频的接口
    String GET_SHIPIN = BASE_URL + "appVideos!getVideos.action";
    //视频购买的接口
    String PAY_VIDEO = BASE_URL + "appVideos!getNoPayVideos.action";
    //视频支付的接口
    String PAY_BUG = BASE_URL + "appVideos!addVideoOrder.action";
    //删除搭配的接口
    String DELETE_MATCH = BASE_URL + "appMatchDiary!deletematchDiary.action";
    //更新的接口
    String IS_FRESH_PATH = BASE_URL + "appVersion!getAndVersion.action";
    //我的订单的接口
    String INDENT_URL = BASE_URL + "appVideos!getVidOrdByUser.action";
    //我的订单中的视频去支付的接口
    String INDENT_PAY_VIDEO = BASE_URL + "appVideos!getVidOrdSign.action";
    //我的订单待确认的接口
    String SURE_ORDER = BASE_URL + "appOrder!updateOrder.action";
    //获取商品详情的接口
    String GET_DETAILS_SHOPPING = BASE_URL + "appOrder!getOrderInfo.action";
    //投诉的接口
    String COMPLAIN_INTERFACE = BASE_URL + "appOrder!zjOrdComp.action";
    //修改密码的接口
    String REVISE_PATH = BASE_URL + "appUserInfo!updateUserPwd.action";
    //这个是衣服标签的接口
    String LABEL_PATH = BASE_URL + "appClothesTag!hqSysCloTag.action";
    //衣服上传的接口
    String LABEL_PATH_UP = BASE_URL + "appPhotoUpload!zjUserClothes.action";
    //衣服款式修改的接口
    String UPDATE_CLO = BASE_URL + "appClothesOpe!xgUserClothes.action";

    /*
    * 购买365推荐衣服的接口
    * */
    String BUY_365_CLOTHES = BASE_URL + "a_04/xz_yf_dd.do\"";


    /*
    * 365工作室的数据接口
    * */

    //获取工作室的列表
    String GET_STUDIO_LIST = BASE_URL_NEW + "b_01/hq_gzx.do";
    //获取工作室的365购买者数量
    String GET_STUDIO_BUY_COUNT = BASE_URL_NEW + "a_03/hq_gzs_hy.do";
    //提交365购买订单
    String SUBMIT_ORDER_365 = BASE_URL_NEW + "a_03/xz_dd.do";
    /*
    * 365个人用户的信息
    * */

    //判断用户是否购买过365会员
    String IS_BUY_365 = BASE_URL_NEW + "a_03/hq_365_xx.do";

    //购买365VIP下单的接口
    String BUY_365_VIP = BASE_URL_NEW + "a_03/xz_dd.do";

    /*
    * 地址的增删改查
    * */

    //添加个人地址
    String ADD_PERSON_ADDRESS = BASE_URL_NEW + "a_01/xz_dz.do";
    //删除个人地址
    String DELETE_PERSON_ADDRESS = BASE_URL_NEW + "a_01/sc_dz.do";
    //修改个人地址
    String UPDATE_PERSON_ADDRESS = BASE_URL_NEW + "a_01/xg_dz.do";
    //获取或者查询个人的地址
    String GET_PERSON_ADDRESS = BASE_URL_NEW + "a_01/hq_dz.do";


    /*
    * 推荐衣服数据
    * */
    //获取所有推荐衣服
    String GET_RECOMMEND_ALL = BASE_URL_NEW + "a_02/hq_sy.do";
    //获取推荐单品衣服的信息
    String GET_RECOMMEND_SINGLE = BASE_URL_NEW + "a_02/hq_yjxq.do";
}

