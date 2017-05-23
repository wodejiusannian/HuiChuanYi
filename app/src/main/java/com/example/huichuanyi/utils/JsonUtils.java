package com.example.huichuanyi.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 小五 on 2017/1/5.
 */
public class JsonUtils {
    private static String condition;
    public static String parseJson(String result){
        try {
            JSONObject object = new JSONObject(result);
            String ret = object.getString("ret");
            switch(ret){
                case "0":
                    condition = "上传成功";
                    break;
                case "1001":
                    condition = "上传失败，请重试";
                    Log.i("TAG", "网络错误,数据获取失败");
                    break;
                case "1002":
                    condition = "上传失败，请重试";
                    Log.i("TAG", "数据库异常");
                    break;
                case "1004":
                    condition = "上传失败，请重试";
                    Log.i("TAG", "数据存在空值");
                    break;
                case "1005":
                    condition = "上传失败，请重试";
                    Log.i("TAG", "文件传输错误");
                    break;
                case "1006":
                    condition = "上传失败，请重试";
                    Log.i("TAG", "网络错误,数据获取失败");
                    break;
                default:

                    break;
            }
            return condition;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getRet(String result){
        try {
            JSONObject object = new JSONObject(result);
            return object.getString("ret");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
