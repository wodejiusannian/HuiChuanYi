package com.example.huichuanyi.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.huichuanyi.ui.activity.HMWebActivity;
import com.example.huichuanyi.ui.activity.HomeDaPeiRiJiActivity;
import com.example.huichuanyi.ui.activity.HomeStatisticsActivity;
import com.example.huichuanyi.ui.activity.HomeVideoCoverActivity;
import com.example.huichuanyi.ui.activity.HomeWoDeYiChuActivity;
import com.example.huichuanyi.ui.activity.LiJiYuYueActivity;
import com.example.huichuanyi.ui.activity.MainActivity;
import com.example.huichuanyi.ui.activity.MineStyleActivity;
import com.example.huichuanyi.ui.activity.ZhenDuanActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

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
public class MyPushService extends BroadcastReceiver {
    private static String TAG = "MyPushService";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.e(TAG, "onReceive: ---------" + extra);
            Log.e(TAG, "onReceive: " + "-----------------2");
            try {
                JSONObject obj = new JSONObject(extra);
                String click_type = obj.getString("click_type");
                String web_url = obj.getString("web_url");
                String share_url = obj.getString("share_url");
                String share_name = obj.getString("share_name");
                switch (click_type) {
                    case "1":
                        Intent i1 = new Intent(context, MainActivity.class); // 自定义打开的界面
                        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i1);
                        break;
                    case "2":
                        Intent i2 = new Intent(context, HMWebActivity.class);
                        i2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i2.putExtra("hm_adpage_webview_url", web_url);
                        i2.putExtra("hm_activity_name", share_name);
                        i2.putExtra("hm_adpage_share_url", share_url);
                        context.startActivity(i2);
                        break;
                    case "3":
                        Intent i3 = new Intent(context, MainActivity.class); // 自定义打开的界面
                        i3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i3.putExtra("page", 1);
                        context.startActivity(i3);
                        break;
                    case "4":
                        Intent i4 = new Intent(context, HomeVideoCoverActivity.class); // 自定义打开的界面
                        i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i4);
                        break;
                    case "5":
                        Intent i5 = new Intent(context, HomeDaPeiRiJiActivity.class); // 自定义打开的界面
                        i5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i5);
                        break;
                    case "6":
                        Intent i6 = new Intent(context, LiJiYuYueActivity.class); // 自定义打开的界面
                        i6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i6);
                        break;
                    case "7":
                        Intent i7 = new Intent(context, MainActivity.class); // 自定义打开的界面
                        i7.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i7.putExtra("page", 2);
                        context.startActivity(i7);
                        break;
                    case "8":
                        Intent i8 = new Intent(context, MineStyleActivity.class); // 自定义打开的界面
                        i8.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i8);
                        break;
                    case "9":
                        Intent i9 = new Intent(context, ZhenDuanActivity.class); // 自定义打开的界面
                        i9.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i9);
                        break;
                    case "10":
                        Intent i10 = new Intent(context, MainActivity.class); // 自定义打开的界面
                        i10.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i10.putExtra("page", 2);
                        context.startActivity(i10);
                        break;
                /*    case "11":
                        Intent i11 = new Intent(context, MainActivity.class); // 自定义打开的界面
                        i11.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i11);
                        break;*/
                    case "12":
                        Intent i12 = new Intent(context, HomeWoDeYiChuActivity.class); // 自定义打开的界面
                        i12.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i12);
                        break;
                    case "13":
                        Intent i13 = new Intent(context, HomeStatisticsActivity.class); // 自定义打开的界面
                        i13.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i13);
                        break;
                    case "14":

                        break;
                    default:
                        Intent i15 = new Intent(context, MainActivity.class); // 自定义打开的界面
                        i15.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i15);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Intent i1 = new Intent(context, MainActivity.class); // 自定义打开的界面
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i1);
            }
        }
    }
}