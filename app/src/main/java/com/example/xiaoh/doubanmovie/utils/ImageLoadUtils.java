package com.example.xiaoh.doubanmovie.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * 加载图片的工具类
 */

public class ImageLoadUtils {


    /**
     * 加载图片
     * @param view
     * @param uri
     */
    public static void display(ImageView view, String uri) {
        Context context = checkView(view);
        if(context==null)
            return;
        try {
            Glide.with(context).load(uri).into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 检查Activity是否存在
     * @param view
     * @return
     */
    public static Context checkView(ImageView view){
        if (view == null) {
            return null;
        }
        Context context = view.getContext();
        // View你还活着吗？
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return null;
            }
        }
        return context;
    }
}
