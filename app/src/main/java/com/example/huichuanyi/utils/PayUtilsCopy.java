package com.example.huichuanyi.utils;

import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 小五 on 2017/5/9.
 */

public class PayUtilsCopy implements UtilsInternet.XCallBack {

    private static PayUtilsCopy instance;

    private UtilsInternet net;

    private Map<String, String> map;

    private Sign mSign;

    public static PayUtilsCopy getInstance() {
        if (instance == null) {
            synchronized (PayUtilsCopy.class) {
                if (instance == null) {
                    return new PayUtilsCopy();
                }
            }
        }
        return instance;
    }

    public PayUtilsCopy() {
        net = UtilsInternet.getInstance();
        map = new HashMap<>();
    }


    @Override
    public void onResponse(String result) {
        mSign.getSign(result);
    }

    public interface Sign {
        void getSign(String sign);
    }

    public void aLiSign(String type, String order_id, String blue_box_num, Sign sign, String kind) {
        mSign = sign;
        map.put("pay_type", type);
        map.put("order_id", order_id);
        map.put("num", blue_box_num);
        if (TextUtils.equals("6", kind)) {
            net.post("http://hmyc365.net:8081/HM/app/doorToDoorService/pay/supplementaryPriceDifference/box/getSign.do", map, this);
        } else {
            net.post("http://hmyc365.net:8081/HM/app/doorToDoorService/pay/supplementaryPriceDifference/clothes/getSign.do", map, this);
        }
    }

    public void weChatSign(String type, String order_id, String blue_box_num, Sign sign, String kind) {
        mSign = sign;
        map.put("pay_type", type);
        map.put("order_id", order_id);
        map.put("num", blue_box_num);
        if (TextUtils.equals("6", kind)) {
            net.post("http://hmyc365.net:8081/HM/app/doorToDoorService/pay/supplementaryPriceDifference/box/getSign.do", map, this);
        } else {
            net.post("http://hmyc365.net:8081/HM/app/doorToDoorService/pay/supplementaryPriceDifference/clothes/getSign.do", map, this);
        }
    }

    public void cMBSing() {

    }
}
