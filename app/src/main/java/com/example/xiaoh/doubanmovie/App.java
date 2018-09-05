package com.example.xiaoh.doubanmovie;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * 唯一的Application入口
 */

public class App extends Application {

    private static Context context;

    // 获取到主线程的handler
    private static Handler mMainThreadHandler;
    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        App.instance = this;
        App.mMainThreadHandler = new Handler();
    }

    public static App getInstance() {
        return instance;
    }

    // 对外暴露一个主线程的handelr
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static Context getContext(){
        return context;
    }
}
