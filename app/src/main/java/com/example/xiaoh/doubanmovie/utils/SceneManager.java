package com.example.xiaoh.doubanmovie.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


/**
 * 管理场景的切换以及之间的数据传递
 *
 */
public class SceneManager {
    public static void toScene(Context from, Class<? extends Activity> to, Bundle data) {
        Intent intent = new Intent();
        intent.setClass(from, to);
        if (data != null) {
            intent.putExtras(data);
        }
        from.startActivity(intent);
    }

    public static void toScene(Activity from, Class<? extends Activity> to, Bundle data, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(from, to);
        if (data != null) {
            intent.putExtras(data);
        }
        from.startActivityForResult(intent, requestCode);
    }

}
