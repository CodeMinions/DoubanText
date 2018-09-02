package com.example.xiaoh.doubanmovie;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Now_popular_fragemnt extends Fragment {//正在热映的fragment
    private boolean isSee = true;//存储是否可见的值
    private String requestUrl = "https://api.douban.com/v2/movie/in_theaters?city=";//正在热映的接口
    private String cityName = "重庆";//存储要搜索城市
    private ArrayList<ChlMovie> movies ;//用于适配的最终数据
    private XRecyclerView recyclerView;
    private PsAdapter adapter;

    private RelativeLayout progress;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_now_popular,container,false);
        recyclerView = (XRecyclerView) view.findViewById(R.id.movie_npshow_xrevyvleview);
        progress = (RelativeLayout) view.findViewById(R.id.progress_2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        movies = new ArrayList<>();
        adapter = new PsAdapter( movies,getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpeleitemDecoration());
        progress.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initData();
            }
            @Override
            public void onLoadMore() {
                recyclerView.refreshComplete();
            }
        });
        initData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {//用于判断是否在MainPopularFragment中是否隐藏
        if (hidden){
            isSee = false;
        }else {
            isSee = true;
        }
    }

    public boolean isSee() {
        return isSee;
    }

    private void initData(){//初始化movies数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(requestUrl+cityName).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        Gson gson = new Gson();
                        PSData psData ;
                        psData = gson.fromJson(responseData,PSData.class);
                        movies.clear();//清除以前的数据
                        for (int i = 0; i < psData.getSubjects().size() ; i++){
                            movies.add(psData.getSubjects().get(i));
                        }
                        Message message = Message.obtain();
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void notifyDataChaged(String newcityName){
        this.cityName = newcityName;
        initData();
    }

    public static class SimpeleitemDecoration extends RecyclerView.ItemDecoration{
        private Paint mDividerPaint;

        public SimpeleitemDecoration() {
            mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mDividerPaint.setStyle(Paint.Style.FILL);
            mDividerPaint.setColor(Color.BLACK);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.top=1;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            int childCount = parent.getChildCount();
            for (int i = 0 ; i < childCount; i++){
                View view = parent.getChildAt(i);
                int left =parent.getPaddingLeft();
                int right = parent.getWidth()-parent.getPaddingRight();
                int top = view.getTop() -1;
                int bottom = view.getTop();
                c.drawRect(left,top,right,bottom,mDividerPaint);
            }
        }
    }

    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            adapter.notifyDataSetChanged();
            recyclerView.refreshComplete();
            progress.setVisibility(View.GONE);
        }
    };


}
