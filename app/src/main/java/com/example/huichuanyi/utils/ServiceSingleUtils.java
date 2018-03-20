package com.example.huichuanyi.utils;

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

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    private ServiceSingleUtils() {
        //do something
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
