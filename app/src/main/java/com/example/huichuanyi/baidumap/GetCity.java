package com.example.huichuanyi.baidumap;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by 小五 on 2017/3/24.
 */

public class GetCity {

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    public WillGetCity mWillGetCity;
    public Context mContext;

    public GetCity(Context mContext) {
        this.mContext = mContext;
    }

    public void setGetCity(WillGetCity willGetCity) {
        mWillGetCity = willGetCity;
    }

    public void startLocation() {
        initLocation();
    }


    public void stopLocation() {
        mLocationClient.stop();
    }

    private void initLocation() {

        mLocationClient = new LocationClient(mContext);
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000;
        option.setScanSpan(span);

        option.setIsNeedAddress(true);

        option.setOpenGps(true);

        option.setLocationNotify(true);

        option.setIsNeedLocationDescribe(true);

        option.setIsNeedLocationPoiList(true);

        option.setIgnoreKillProcess(false);

        option.SetIgnoreCacheException(false);

        option.setEnableSimulateGps(false);

        mLocationClient.setLocOption(option);

        mLocationClient.start();
    }


    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            mWillGetCity.getWillGetCity(location.getCity());
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }


    public interface WillGetCity {
        void getWillGetCity(String city);
    }
}
