package com.example.huichuanyi.baidumap;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.example.huichuanyi.utils.MySharedPreferences;

public class MyLocationListener implements BDLocationListener {
    private Context mContext;
    public MyLocationListener(Context context){
        mContext = context;
    }
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        String addrStr = bdLocation.getAddrStr();
        if (addrStr!=null&&addrStr.contains("昆山")){
            MySharedPreferences.saveCity(mContext,"昆山市");
        }else{
            String city = bdLocation.getCity();
            if (city!=null){
                MySharedPreferences.saveCity(mContext,city);
            }
        }
    }


}
