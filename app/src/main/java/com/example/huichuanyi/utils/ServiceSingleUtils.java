package com.example.huichuanyi.utils;

import com.example.huichuanyi.emum.OrderType;
import com.example.huichuanyi.emum.ServiceType;

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
public class ServiceSingleUtils {

    private static volatile ServiceSingleUtils instance = null;

    public ServiceType serviceType;

    public OrderType orderType;

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    private ServiceSingleUtils() {
        //do something
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public static ServiceSingleUtils getInstance() {
        if (instance == null) {
            synchronized (ServiceSingleUtils.class) {
                if (instance == null) {
                    instance = new ServiceSingleUtils();
                }
            }
        }
        return instance;
    }
}
