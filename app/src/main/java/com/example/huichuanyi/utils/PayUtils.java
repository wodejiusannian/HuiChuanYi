package com.example.huichuanyi.utils;

import com.example.huichuanyi.config.NetConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 小五 on 2017/5/9.
 */

public class PayUtils implements UtilsInternet.XCallBack {
    private static PayUtils instance;

    private UtilsInternet net;
    private Map<String, String> map;
    private Sign mSign;

    public static PayUtils getInstance() {
        if (instance == null) {
            synchronized (PayUtils.class) {
                if (instance == null) {
                    return new PayUtils();
                }
            }
        }
        return instance;
    }

    public PayUtils() {
        net = UtilsInternet.getInstance();
        map = new HashMap<>();
    }

    public void payYWT(String order_id, String type, Sign sign) {
        mSign = sign;
        map.put("order_id", order_id);
        map.put("type", type);
        net.post(NetConfig.YWT_PAY, map, this);
    }


    @Override
    public void onResponse(String result) {
        mSign.getSign(result);
    }

    public interface Sign {
        void getSign(String sign);
    }

    public void aLiSign(String type, String order_id, String clothes_num_oth, String blue_box_num, Sign sign) {
        mSign = sign;
        map.put("type", type);
        map.put("order_id", order_id);
        map.put("clothes_num_oth", clothes_num_oth);
        map.put("blue_box_num", blue_box_num);
        net.post(NetConfig.ALI_PAY_SIGN, map, this);
    }

    public void weChatSign(String type, String order_id, String clothes_num_oth, String blue_box_num, Sign sign) {
        mSign = sign;
        map.put("type", type);
        map.put("order_id", order_id);
        map.put("clothes_num_oth", clothes_num_oth);
        map.put("blue_box_num", blue_box_num);
        net.post(NetConfig.WECHAT_SIGN, map, this);
    }

    public void cMBSing() {

    }
}
