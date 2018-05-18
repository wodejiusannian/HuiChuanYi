package com.example.huichuanyi.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

/**
 * Created by 小五 on 2017/1/12.
 */
public class MUtilsInternet {

    private static MUtilsInternet instance;

    private Handler handler;


    private MUtilsInternet() {
        handler = new Handler(Looper.getMainLooper());
    }

    public static MUtilsInternet getInstance() {
        if (instance == null) {
            synchronized (MUtilsInternet.class) {
                if (instance == null) {
                    instance = new MUtilsInternet();
                }
            }
        }
        return instance;
    }

    public void post(String url, Context context, Map<String, String> maps, final XCallBack callback) {
        final ProgressDialog dialog = ProgressDialog.show(context, "提示", "正在加载，请稍后....", true, true);

        RequestParams params = new RequestParams(url);

        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }

        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                onSuccessResponse(result, callback);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void post2(String url, Map<String, String> maps, final XCallBack callback) {

        RequestParams params = new RequestParams(url);

        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }

        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                onSuccessResponse(result, callback);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    /**
     * 异步get请求返回结果,json字符串
     *
     * @param result
     * @param callBack
     */
    private void onSuccessResponse(final String result, final XCallBack callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(result);
                }
            }
        });
    }

    public interface XCallBack {
        void onResponse(String result);
    }

}
