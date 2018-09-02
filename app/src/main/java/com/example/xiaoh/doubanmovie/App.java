package com.example.xiaoh.doubanmovie;

import android.app.Application;
import android.os.Handler;

/**
 * 唯一的Application入口
 */

public class App extends Application {

    // 获取到主线程的handler
    private static Handler mMainThreadHandler;
    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
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

}
