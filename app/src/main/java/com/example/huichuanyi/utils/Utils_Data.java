package com.example.huichuanyi.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by 小五 on 2017/1/19.
 */
public class Utils_Data {

    public static String getDataObject(Map<String,String> map){
        JSONObject object = new JSONObject();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                try {
                    object.put(entry.getKey(), entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return object.toString();
        }
        return null;
    }
}
