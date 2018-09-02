package com.example.xiaoh.doubanmovie;

public interface ISticky {//用于判断是否需要头回调函数（跟粘性头相关的接口）
    boolean isFirstPostion(int pos);
    String getGroupTitle(int pos);
}
