package com.example.xiaoh.doubanmovie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MyImageView extends android.support.v7.widget.AppCompatImageView {//继承自ImageView,实现传url也可设置图片，并且缓存到缓存文件夹中
    public static final int GET_DATA_SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR = 3;
    private static ArrayList<String> cachepictures = new ArrayList<>();
    private LocalCacheUtils localCacheUtils = new LocalCacheUtils();


    //子线程不能操作UI，通过Handler设置图片
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_DATA_SUCCESS:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    setImageBitmap(bitmap);
                    break;
                case NETWORK_ERROR:
                    Toast.makeText(getContext(),"网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case SERVER_ERROR:
                    Toast.makeText(getContext(),"服务器发生错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //设置网络图片如何改图片已经下载过着调用缓存图片
    public void setImageURL(final String path) {
        if (isExist(path)){
            Bitmap bitmap = localCacheUtils.getBitmapFromLocal(path);
            setImageBitmap(bitmap);
        }else {
            //开启一个线程用于联网
            new Thread() {
                @Override
                public void run() {
                    try {
                        //把传过来的路径转成URL
                        URL url = new URL(path);
                        //获取连接
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        //使用GET方法访问网络
                        connection.setRequestMethod("GET");
                        //超时时间为10秒
                        connection.setConnectTimeout(10000);
                        //获取返回码
                        int code = connection.getResponseCode();
                        if (code == 200) {
                            InputStream inputStream = connection.getInputStream();
                            //使用工厂把网络的输入流生产Bitmap
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            localCacheUtils.setBitmapToLocal(path,bitmap);
                            //利用Message把图片发给Handler
                            Message msg = Message.obtain();
                            msg.obj = bitmap;
                            msg.what = GET_DATA_SUCCESS;
                            handler.sendMessage(msg);
                            inputStream.close();
                        }else {
                            //服务启发生错误
                            handler.sendEmptyMessage(SERVER_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        //网络连接错误
                        handler.sendEmptyMessage(NETWORK_ERROR);
                    }
                }
            }.start();
        }
    }

    private boolean isExist(String uri){//判断图片是否存在
        for (int i = 0; i<cachepictures.size();i++){
            if (uri.equals(cachepictures.get(i))){
                return  true;
            }
        }
        cachepictures.add(uri);
        return false;
    }

    private class LocalCacheUtils {
            private  final File CACHE_PATH= getContext().getCacheDir();

            /**
             * 从本地读取图片
             * @param url
             */
            public Bitmap getBitmapFromLocal(String url){
                String fileName = null;//把图片的url当做文件名
                try {
                    String[] strings = url.split("/");
                    String name = strings[strings.length-1];
                    fileName = name;
                    File file=new File(CACHE_PATH,fileName);
                    if(file.exists()){
                        Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                        return bitmap;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            /**
             * 从网络获取图片后,保存至本地缓存
             * @param url
             * @param bitmap
             */
            public void setBitmapToLocal(String url,Bitmap bitmap){
                try {
                    String[] strings = url.split("/");
                    String name = strings[strings.length-1];
                    String fileName =name;//把图片的url当做文件名
                    File file=new File(CACHE_PATH,fileName);

                    //通过得到文件的父文件,判断父文件是否存在
                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()){
                        parentFile.mkdirs();
                    }
                    //把图片保存至本地
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
}



