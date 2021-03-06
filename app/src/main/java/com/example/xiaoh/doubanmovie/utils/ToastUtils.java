package com.example.xiaoh.doubanmovie.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.example.xiaoh.doubanmovie.App;


/**
 * Toast 工具类
 */

public class ToastUtils {
    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void show(CharSequence message) {
        if (TextUtils.isEmpty(message)) {
            message = "";
        }
        if (isShow)
            Toast.makeText(App.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void show(int message) {
        if (isShow)
            Toast.makeText(App.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        if (TextUtils.isEmpty(message)) {
            message = "";
        }
        if (isShow)
            Toast.makeText(App.getInstance(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(int message) {
        if (isShow)
            Toast.makeText(App.getInstance(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        if (TextUtils.isEmpty(message)) {
            message = "";
        }
        if (isShow)
            Toast.makeText(App.getInstance(), message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(int message, int duration) {
        if (isShow)
            Toast.makeText(App.getInstance(), message, duration).show();
    }

}
