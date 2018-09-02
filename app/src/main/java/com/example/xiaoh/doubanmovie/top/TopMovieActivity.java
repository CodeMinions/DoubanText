package com.example.xiaoh.doubanmovie.top;

import android.support.v7.widget.RecyclerView.Adapter;

import com.example.xiaoh.doubanmovie.Constants;
import com.example.xiaoh.doubanmovie.hot.HotMovieActivity;

/**
 * top250页面
 */

public class TopMovieActivity extends HotMovieActivity{
    @Override
    public Adapter getAdapter() {
        return new TopMovieAdapter(this,mList);
    }

    @Override
    public String getTitleName() {
        return "豆瓣 Top250";
    }

    @Override
    public String getAPi() {
        return Constants.TOP_API;
    }
}
