package com.example.xiaoh.doubanmovie.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.example.xiaoh.doubanmovie.App;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

/**
 * http请求工具类
 */

public class HttpUtils {
    public static final int ERROR_NETWORK = 1;
    public static final int ERROR_CODE = 2;
    public static final int ERROR_GSON = 3;

    /**
     * 网络Get请求封装
     * @param url
     * @param params
     * @param clazz
     * @param onHttpResult
     * @param <T>
     * @return
     */
    public static <T> Call getData(String url, LinkedHashMap<String, String> params, final Class<T> clazz, final OnHttpResult<T> onHttpResult) {
        String newUrl = URLUtils.attachHttpGetParams(url, params);
        OkHttpClient client = new OkHttpClient();
        Request request = new Builder()
                .url(newUrl)
                .build();
        Call newCall = client.newCall(request);
        newCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                App.getMainThreadHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        onHttpResult.onFail(ERROR_NETWORK);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {//回调的方法执行在子线程。
                    Log.d("kwwl", "获取数据成功了");
                    Log.d("kwwl", "response.code()==" + response.code());
                    Gson gson = new Gson();
                    T subject = null;
                    try {
                        subject = gson.fromJson(response.body().string(), clazz);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    final T finalSubject = subject;
                    App.getMainThreadHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            if (finalSubject == null)
                                onHttpResult.onFail(ERROR_GSON);
                            else
                                onHttpResult.onSuccess(finalSubject);
                        }
                    });
                } else {
                    App.getMainThreadHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            onHttpResult.onFail(ERROR_CODE);
                        }
                    });
                }
            }
        });
        return newCall;
    }

    public interface OnHttpResult<T> {
        void onFail(int code);

        void onSuccess(T t);
    }

}
